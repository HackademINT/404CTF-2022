package org.hallebarde.recrutement.api.events.user;

import org.hallebarde.recrutement.api.annotations.DoNotFire;
import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.user.User;

/**
 * Fired when a user picks up an item from a room.
 */
@DoNotFire
public final class UserPicksUpItemEvent extends UserItemEvent {

    private String message;
    private final Room room;

    public UserPicksUpItemEvent(User user, Item item, Room room, String message) {
        super(user, item);
        this.message = message;
        this.room = room;
    }

    /**
     * @return the message that would be shown to the user once the item has been picked-up
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Changes the message that should be displayed to the user once the item has been picked-up.
     *
     * @param message   the new message to send to the user
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the room where the item is being picked-up
     */
    public Room getRoom() {
        return this.room;
    }

}
