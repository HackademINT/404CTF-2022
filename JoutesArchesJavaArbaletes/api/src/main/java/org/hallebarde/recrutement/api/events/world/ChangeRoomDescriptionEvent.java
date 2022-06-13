package org.hallebarde.recrutement.api.events.world;

import org.hallebarde.recrutement.api.gameplay.world.Room;

/**
 * This event is fired when a room's description is updated.
 *
 * @see Room#setDescription(String)
 */
public class ChangeRoomDescriptionEvent extends CancelableRoomEvent {

    private String newDescription;

    public ChangeRoomDescriptionEvent(Room room, String newDescription) {
        super(room);
        this.newDescription = newDescription;
    }

    public String getNewDescription() {
        return this.newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

}
