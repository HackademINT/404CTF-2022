package org.hallebarde.recrutement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hallebarde.recrutement.api.*;
import org.hallebarde.recrutement.api.Console;
import org.hallebarde.recrutement.api.commands.Command;
import org.hallebarde.recrutement.api.events.Event;
import org.hallebarde.recrutement.api.events.world.WorldLoadEvent;
import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.Activity;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.gameplay.world.RoomInteraction;
import org.hallebarde.recrutement.api.gameplay.world.World;
import org.hallebarde.recrutement.api.storage.Options;
import org.hallebarde.recrutement.api.storage.Registry;
import org.hallebarde.recrutement.commands.CommandManager;
import org.hallebarde.recrutement.commands.DebugCommands;
import org.hallebarde.recrutement.config.JsonConfig;
import org.hallebarde.recrutement.storage.JsonOptions;
import org.hallebarde.recrutement.events.EventBus;
import org.hallebarde.recrutement.gameplay.user.PlayerManager;
import org.hallebarde.recrutement.plugins.DuplicatePluginException;
import org.hallebarde.recrutement.plugins.PluginContainer;
import org.hallebarde.recrutement.plugins.PluginLoader;
import org.hallebarde.recrutement.plugins.PluginMetadataTypeAdapter;
import org.hallebarde.recrutement.gameplay.world.GameWorld;
import org.hallebarde.recrutement.gameplay.world.JsonWorldTemplate;
import org.hallebarde.recrutement.storage.RegistryImplementation;
import org.hallebarde.recrutement.util.InternalHelper;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Math.max;
import static java.util.Collections.unmodifiableMap;
import static org.hallebarde.recrutement.api.GameState.*;

public class RecrutementGame implements Game {

    // Tick delay in milliseconds
    private static final int TICK_DURATION = 100;
    private static final String CONFIG_FILE = "config.json";
    public static final Logger LOGGER = LogManager.getLogger("Internal");
    public static final Gson GSON = new GsonBuilder()
            .setLenient().setPrettyPrinting()
            .registerTypeAdapter(PluginMetadata.class, new PluginMetadataTypeAdapter())
            .registerTypeAdapter(Options.class, new JsonOptions.OptionsJsonAdapter())
            .create();

    private final EventBus eventBus = new EventBus();
    private GameConfig config;
    private final GameWorld world = new GameWorld(this);
    private final PlayerManager playerManager = new PlayerManager(this);
    private final NetworkManager networkManager = new NetworkManager(this);
    private final CommandManager commandManager = new CommandManager(this);
    private final Registry<Item> itemRegistry = new RegistryImplementation<>();
    private final Registry<RoomInteraction> interactionRegistry  = new RegistryImplementation<>();

    private GameState state = STARTING;

    private List<Runnable>  taskQueue = new LinkedList<>();
    private final Lock taskQueueLock = new ReentrantLock();
    private final Map<UUID, Activity> onGoingActivities = new HashMap<>();
    private final Map<UUID, Activity> scheduledActivities = new HashMap<>();

    private final PluginLoader pluginLoader = new PluginLoader(this);
    private final Map<String, PluginContainer> plugins = new HashMap<>();
    private final Map<String, Plugin> pluginMap = new HashMap<>();
    private final Map<String, Plugin> unmodifiablePluginMap = unmodifiableMap(this.pluginMap);

    private final GameConsole console = new GameConsole(this);

    public RecrutementGame() throws IOException {
    }

    public void load() throws DuplicatePluginException, IOException {
        this.loadConfig(CONFIG_FILE);
        this.loadPlugins();
        this.registerDefaultGameObjects();
        this.loadWorld();
    }

    private void loadConfig(String filePath) throws IOException {
        LOGGER.debug("Loading config from {}...", filePath);
        this.config = new JsonConfig(new File(filePath));
        this.config.reload();
        if(this.config.debug()) DebugCommands.registerDebugCommands(this);
        LOGGER.info("Config loaded.");
    }

    private void loadPlugins() throws DuplicatePluginException {
        try {
            File directory = this.pluginLoader.preparePluginDirectory(this.config.pluginDirectory());
            this.pluginLoader.exploreClasspath();
            this.pluginLoader.exploreDirectory(directory);
            for (PluginContainer container: this.pluginLoader.load()) {
                this.plugins.put(container.getMetadata().id(), container);
                this.pluginMap.put(container.getMetadata().id(), container.getPlugin());
            }
                PluginLoader.LOGGER.info("Loaded {} plugins", this.plugins.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerDefaultGameObjects() {
    }

    private void loadWorld() throws IOException {
        LOGGER.debug("Loading world...");
        File worldDir = new File((this.config.worldDirectory()));
        WorldLoadEvent.Pre preEvent = this.fireEvent(new WorldLoadEvent.Pre(this.world, worldDir));
        JsonWorldTemplate template = new JsonWorldTemplate(preEvent.getWorldDirectory(), this.itemRegistry, this.interactionRegistry);
        this.world.setFireEvents(false);
        if (!preEvent.isCancelled()) template.load(this.world);
        this.world.setFireEvents(true);
        this.fireEvent(new WorldLoadEvent.Post(this.world));
        if (this.world.getSpawnRoom() == null) throw new IllegalStateException("World is supposed to be loaded but doesn't have a spawn room!");
        LOGGER.info("Loaded a {} rooms world.", this.world.getRooms().size());
    }

    public void start() throws IOException {
        this.setState(RUNNING);
        this.startServerThread();
        this.networkManager.start();
        LOGGER.info("Game server started on " + this.networkManager.getServerSocket().getLocalSocketAddress());

    }

    private void tick() {
        this.playerManager.removeOfflinePlayers();
        this.taskQueueLock.lock();
        List<Runnable> tasks = this.taskQueue;
        this.taskQueue = new LinkedList<>();
        this.taskQueueLock.unlock();
        for (Runnable task: tasks) {
            task.run();
        }
        // Iterate over a copy so the original can be modified safely
        Set<Activity> activities = new HashSet<>(this.onGoingActivities.values());
        activities.forEach(Activity::tick);
        Iterator<Map.Entry<UUID, Activity>> scheduled = this.scheduledActivities.entrySet().iterator();
        while (scheduled.hasNext()) {
            Activity activity = scheduled.next().getValue();
            if (activity.getUsers().stream().allMatch(e -> e.getOngoingActivity() == null)) {
                this.startActivity(activity);
                scheduled.remove();
            }
        }
    }

    private void startServerThread() {
        Thread thread = new Thread(this::mainServerLoop);
        thread.setDaemon(false);
        thread.setName("Server Thread");
        thread.start();
    }

    public void mainServerLoop() {
        while (this.getState() == RUNNING) {
            long stime = System.currentTimeMillis();
            this.tick();
            try {
                Thread.sleep(max(0, stime + TICK_DURATION - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                throw new IllegalStateException("Interrupted server thread when waiting for next tick!!");
            }
        }
    }

    @Override
    public int getUserCount() {
        return this.playerManager.getPlayerCount();
    }

    @Override
    public User getUser(String name) {
        return this.playerManager.getPlayer(name);
    }

    @Override
    public User[] getUsers() {
        return this.playerManager.getPlayers();
    }

    @Override
    public void broadcast(String message) {
        LOGGER.info("[CHAT] {}", message);
        this.playerManager.sendAll(message);
    }

    @Override
    public void registerCommand(String prefix, Command command, String description) throws IllegalStateException {
        this.commandManager.registerCommand(prefix, command, description);
    }

    @Override
    public GameConfig getConfig() {
        return this.config;
    }

    @Override
    public void runOnGameLoop(Runnable task) {
        this.taskQueueLock.lock();
        this.taskQueue.add(task);
        this.taskQueueLock.unlock();
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public PluginLoader getPluginLoader() {
        return this.pluginLoader;
    }

    private synchronized void setState(GameState state) {
        this.state = state;
    }

    @Override
    public void stop(String message) {
        if (message != null) this.broadcast(message);
        LOGGER.info("Stopping the server!");
        this.setState(STOPPING);
        for (PluginContainer container: this.plugins.values()) {
            container.getPlugin().onUnload(this);
        }
    }

    @Override
    public synchronized GameState getState()  {
        return this.state;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public void startActivityNow(Activity activity) {
        if (activity == null) throw new NullPointerException("Cannot start null activity.");
        if (activity.isDone()) throw new IllegalStateException("Trying to start an activity that's already finished.");
        synchronized (this.onGoingActivities) {
            for (User user: activity.getUsers()) {
                Activity userActivity = user.getOngoingActivity();
                if (userActivity != null) {
                    this.stopActivity(activity);
                }
            }
            this.startActivity(activity);
        }
    }

    @Override
    public void startActivityWhenPossible(Activity activity) {
        if (activity == null) throw new NullPointerException("Cannot stop null activity");
        if (activity.isDone()) throw new IllegalStateException("Trying to start an activity that's already finished.");
        synchronized (this.onGoingActivities) {
            this.scheduledActivities.put(activity.getUUID(), activity);
        }
    }

    @Override
    public void stopActivity(Activity activity) {
        if (activity == null) throw new NullPointerException("Cannot stop null activity");
        synchronized (this.onGoingActivities) {
            if (this.onGoingActivities.remove(activity.getUUID()) == null) throw new IllegalStateException("Cannot stop on activity that is not ongoing");
            activity.terminate();
            for (User user : activity.getUsers()) {
                InternalHelper.getInternalPlayer(user).setActivity(null);
            }
        }
    }

    @Override
    public Map<String, Plugin> getPlugins() {
        return this.unmodifiablePluginMap;
    }

    @Override
    public PluginMetadata getPluginMetadata(String pluginId) {
        return this.plugins.get(pluginId).getMetadata();
    }

    @Override
    public void registerEventHandler(Object object) {
        this.eventBus.registerHandler(object);
    }

    @Override
    public <E extends Event> E fireEvent(E event) {
        return this.eventBus.fire(event);
    }

    @Override
    public Console getConsole() {
        return this.console;
    }

    @Override
    public Registry<Item> getItemRegistry() {
        return this.itemRegistry;
    }

    @Override
    public Registry<RoomInteraction> getRoomInteractionRegistry() {
        return this.interactionRegistry;
    }

    private void startActivity(Activity activity) {
        if (activity.isDone()) throw new IllegalStateException("Trying to start an activity that's already finished.");
        this.onGoingActivities.put(activity.getUUID(), activity);
        for (User user : activity.getUsers()) {
            InternalHelper.getInternalPlayer(user).setActivity(activity);
        }
        LOGGER.debug("Starting activity {} (UUID {}) with {} users", activity.getClass(), activity.getUUID(), activity.getUsers().size());
        activity.start();
    }

}