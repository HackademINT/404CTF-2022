package org.hallebarde.recrutement.api.gameplay.user;

import org.hallebarde.recrutement.api.annotations.DoNotImplement;
import org.hallebarde.recrutement.api.commands.CommandExecutor;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.world.World;
import org.hallebarde.recrutement.api.gameplay.Activity;
import org.hallebarde.recrutement.api.network.UserConnection;

import java.util.concurrent.CompletableFuture;

/**
 * A user logged in the server.
 * You should not implement this class.
 */
@DoNotImplement
public interface User extends CommandExecutor {

    /**
     * @return the name for this user
     */
    String getName();

    /**
     * @return the room this user is in
     */
    Room getRoom();

    /**
     * Move this user to a given room.
     *
     * @param room the room to move to
     *
     * @throws IllegalArgumentException if the room is not known
     */
    void moveTo(Room room);

    /**
     * @return  the world the user is in
     */
    World getWorld();

    /**
     * @return the activity this user is currently performing, or null if none.
     */
    Activity getOngoingActivity();

    /**
     * @return whether this user is online.
     */
    default boolean isOnline() {
        return this.getConnection().isOpened();
    }

    /**
     * @return this user's connection, it may be null if they are offline
     */
    UserConnection getConnection();

    @Override
    default CompletableFuture<Void> sendMessage(String message) {
        return this.getConnection().writeLine(message);
    }

    /**
     * @return this user's inventory
     */
    Inventory getInventory();

}
