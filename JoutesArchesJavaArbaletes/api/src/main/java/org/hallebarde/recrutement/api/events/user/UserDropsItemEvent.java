package org.hallebarde.recrutement.api.events.user;

import org.hallebarde.recrutement.api.annotations.DoNotFire;
import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.user.User;

/**
 * Fired when a user drops an item.
 */
@DoNotFire
public final class UserDropsItemEvent extends UserItemEvent {

    private String message;
    private Room room;

    public UserDropsItemEvent(User user, Item item, Room room, String message) {
        super(user, item);
        this.room = room;
        this.message = message;
    }

    /**
     * @return the message that will be displayed to the user once the item has been dropped
     */
    public String getMessage() {
        return message;
    }

    /**
     * Changes the message that will be displayed to the user once the item has been dropped
     *
     * @param message   the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the room the item is being dropped in
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room the item will be dropped in.
     * If null, the item will be deleted.
     *
     * @param room  the room where the item will be placed
     */
    public void setRoom(Room room) {
        this.room = room;
    }

}
