package org.hallebarde.recrutement.api;

import org.hallebarde.recrutement.api.annotations.DoNotImplement;
import org.hallebarde.recrutement.api.commands.Command;
import org.hallebarde.recrutement.api.events.Event;
import org.hallebarde.recrutement.api.events.SubscribeEvent;
import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.Activity;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.gameplay.world.RoomInteraction;
import org.hallebarde.recrutement.api.gameplay.world.World;
import org.hallebarde.recrutement.api.storage.Registry;

import java.util.Map;

/**
 * The game you will be interacting with.
 */
@DoNotImplement
public interface Game {

    /**
     * @see #getUsers()
     * @see #getUser(String)
     *
     * @return the number of active users.
     */
    int getUserCount();

    /**
     * Gives access to logged-in users by name.
     *
     * @see #getUserCount()
     * @see #getUsers()
     *
     * @param name the name of the user to find.
     * @return the user, or null if it is not found.
     */
    User getUser(String name);

    /**
     * @see #getUserCount() 
     * @see #getUser(String)
     *
     * @return all logged-in users.
     */
    User[] getUsers();

    /**
     * Sends a message to all logged-in players.
     *
     * @param message a message to send.
     */
    void broadcast(String message);

    /**
     * Registers a command the server can respond to.
     *
     * @param prefix    the prefix this command should be invoked with.
     * @param command   a command to register.
     *
     * @throws IllegalStateException in case a command with the same prefix has been registered already.
     * @throws NullPointerException if either the command prefix or the command executor is null.
     */
    void registerCommand(String prefix, Command command, String description) throws IllegalStateException;

    /**
     * @return this game's config.
     */
    GameConfig getConfig();

    /**
     * Schedules a task to be run on the main game thread.
     * @param task a task to run
     */
    void runOnGameLoop(Runnable task);

    /**
     * Stops the server.
     *
     * @param message a message to broadcast to players to inform them, or null to send nothing
     */
    void stop(String message);

    /**
     * @return the world this game is running
     */
    World getWorld();

    /**
     * Starts the given activity.
     * If any of the users of the given activity already has an activity going, it will be stopped.
     * 
     * @see #startActivityWhenPossible(Activity)
     * @see #stopActivity(Activity) 
     *
     * @param activity  the activity to start
     *
     * @throws NullPointerException if activity is null
     */
    void startActivityNow(Activity activity);

    /**
     * Starts the given activity.
     * Waits for all users involved to not have an activity in-going.
     * 
     * @see #startActivityNow(Activity)
     * @see #stopActivity(Activity)
     *
     * @param activity  the activity to start
     *
     * @throws NullPointerException if activity is null
     */
    void startActivityWhenPossible(Activity activity);

    /**
     * Stops the given activity.
     *
     * @see #startActivityNow(Activity)
     * @see #startActivityWhenPossible(Activity)
     *
     * @param activity  the activity to stop
     *
     * @throws NullPointerException if activity is null
     * @throws IllegalStateException if the activity is not ongoing
     */
    void stopActivity(Activity activity);

    /**
     * Provides access to the plugins which have been loaded into this game server.
     *
     * @return an unmodifiable map which associate plugin ids with their instances
     */
    Map<String, Plugin> getPlugins();

    /**
     * Provides access to a plugin's metadata.
     *
     * @param pluginId  the id of the plugin you need the metadata for
     * @return  the plugin's metadata
     */
    PluginMetadata getPluginMetadata(String pluginId);

    /**
     * Registers an event handler.
     * An event handler is an object with at least one method annotated with {@link SubscribeEvent}.
     * Such methods are void and consume a single {@link Event} argument.
     * They are called when an event of the corresponding type is fired using {@link #fireEvent(Event)}.
     *
     * @param object    the object to register as an event handler
     *
     * @see Event
     * @see #fireEvent(Event)
     * @see SubscribeEvent
     */
    void registerEventHandler(Object object);

    /**
     * Fires an {@link Event} that will be propagated to the relevant event subscribing methods.
     *
     * @param event the event to fire
     *
     * @return the event that was fired
     *
     * @see #registerEventHandler(Object)
     * @see Event
     * @see SubscribeEvent
     */
    <E extends Event> E fireEvent(E event);

    /**
     * @return the state the game is currently in
     */
    GameState getState();

    /**
     * @return the game server console
     */
    Console getConsole();

    /**
     * @return the registry that contains all item types known to this server
     */
    Registry<Item> getItemRegistry();

    /**
     * @return the registry that contains all room interactions known to this server
     */
    Registry<RoomInteraction> getRoomInteractionRegistry();

}
