package org.hallebarde.recrutement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hallebarde.recrutement.api.GameConfig;
import org.hallebarde.recrutement.api.events.SubscribeEvent;
import org.hallebarde.recrutement.api.events.network.StartNetworkEvent;
import org.hallebarde.recrutement.api.events.network.UserConnectEvent;
import org.hallebarde.recrutement.api.events.network.UserJoinEvent;
import org.hallebarde.recrutement.api.network.UserConnection;
import org.hallebarde.recrutement.network.PlayerSocket;
import org.hallebarde.recrutement.util.ThreadFactoryBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static org.hallebarde.recrutement.api.GameState.RUNNING;
import static org.hallebarde.recrutement.util.InternalHelper.getInternalPlayer;

public class NetworkManager {

    private final RecrutementGame game;
    private final ServerSocket serverSocket;
    public static final Logger LOGGER = LogManager.getLogger("Network");
    private final ExecutorService networkExecutor = Executors.newCachedThreadPool(new ThreadFactoryBuilder()
            .setName("Network thread")
            .setDaemon(true)
            .build());

    public NetworkManager(RecrutementGame game) throws IOException {
        this.game = game;
        this.game.registerEventHandler(this);
        this.serverSocket = new ServerSocket();
    }

    void start() throws IOException {
        GameConfig config = this.game.getConfig();
        StartNetworkEvent event = new StartNetworkEvent(new InetSocketAddress(config.hostname(), config.port()));
        if (this.game.fireEvent(event).isCancelled()) {
            LOGGER.warn("Network system starting was cancelled, the server will not be reachable!");
            return;
        }
        this.serverSocket.bind(event.getAddress());
        CompletableFuture.runAsync(this::mainNetworkLoop, this.networkExecutor);
    }

    private void mainNetworkLoop() {
        while (this.game.getState() == RUNNING) {
            try {
                Socket socket = this.serverSocket.accept();
                UserConnection connection = new PlayerSocket(this, socket);
                UserConnectEvent event = new UserConnectEvent(connection);

                // We rely on the player manager to pick that up and act if the event hasn't been cancelled
                CompletableFuture.runAsync(() -> this.game.fireEvent(event), this.networkExecutor);
            } catch (Exception e) {
                LOGGER.error("An error occurred when handling a new connection ");
            }
        }
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void startPlayerLoop(UserJoinEvent event) {
        this.doNetwork(getInternalPlayer(event.getUser())::listenForMessages);
    }

    public <T> CompletableFuture<T>doNetwork(Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, this.networkExecutor).exceptionally(t -> null);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

}
