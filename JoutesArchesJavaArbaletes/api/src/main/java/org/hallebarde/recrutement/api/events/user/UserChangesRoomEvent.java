package org.hallebarde.recrutement.api.events.user;

import org.hallebarde.recrutement.api.annotations.FreeToFire;
import org.hallebarde.recrutement.api.events.HandlerPriority;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.gameplay.world.Room;

/**
 * Fired when a user attempts to move from one room to another.
 * You can fire this event yourself to emulate such an action.
 *
 * The event is processed by the game with a priority of {@link HandlerPriority#NORMAL},
 * so changing anything in the event at this stage or later may have no effect.
 */
@FreeToFire
public class UserChangesRoomEvent extends CancelableUserEvent {

    private final Room roomFrom;
    private Room roomTo;
    private String message;

    public UserChangesRoomEvent(User user, Room roomFrom, Room roomTo, String message) {
        super(user);
        this.roomFrom = roomFrom;
        this.roomTo = roomTo;
        this.message = message;
    }

    /**
     * @return the room the user is currently in
     */
    public Room getRoomFrom() {
        return this.roomFrom;
    }

    /**
     * @return the room the user is trying to get to
     */
    public Room getRoomTo() {
        return this.roomTo;
    }

    /**
     * Changes the room the user will arrive to if the event is not canceled.
     *
     * @param roomTo    the new room the user should arrive in
     */
    public void setRoomTo(Room roomTo) {
        this.roomTo = roomTo;
    }

    /**
     * @return the message that will be sent to the user once they reached the destination room,
     * or null if no message should be sent
     */
    public String getMessage() {
        return message;
    }

    /**
     * Changes the message that will be sent to the user once they reach the destination room.
     *
     * @param message   the new message to send to the user, or null to not send anything
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
