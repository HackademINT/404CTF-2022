package org.hallebarde.recrutement.api.events.world;

import org.hallebarde.recrutement.api.annotations.DoNotFire;
import org.hallebarde.recrutement.api.gameplay.world.Room;

/**
 * An abstract event for events that hold a reference to a {@link Room}.
 */
@DoNotFire
public abstract class CancelableRoomEvent extends CancelableWorldEvent {

    private final Room room;

    public CancelableRoomEvent(Room room) {
        super(room.world());
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

}
