package org.hallebarde.recrutement.api.events.world;

import org.hallebarde.recrutement.api.gameplay.world.Room;

/**
 * This event is fired when a room's name is changed.
 * 
 * @see Room#setName(String)
 */
public class ChangeRoomNameEvent extends CancelableRoomEvent {

    private String newName;

    public ChangeRoomNameEvent(Room room, String newName) {
        super(room);
        this.newName = newName;
    }

    public String getNewName() {
        return this.newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

}
