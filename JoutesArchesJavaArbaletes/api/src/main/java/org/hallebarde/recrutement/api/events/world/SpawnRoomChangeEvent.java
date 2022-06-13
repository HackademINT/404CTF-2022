package org.hallebarde.recrutement.api.events.world;

import org.hallebarde.recrutement.api.annotations.DoNotFire;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.world.World;

/**
 * This event is fired by the game when a world's spawn room is about to change.
 */
@DoNotFire
public class SpawnRoomChangeEvent extends CancelableWorldEvent {

    private final Room oldRoom;
    private Room newRoom;

    public SpawnRoomChangeEvent(World world, Room oldRoom, Room newRoom) {
        super(world);
        this.oldRoom = oldRoom;
        this.newRoom = newRoom;
    }


    public Room getOldSpawnRoom() {
        return this.oldRoom;
    }

    public Room getNewSpawnRoom() {
        return this.newRoom;
    }

    /**
     * Changes the new spawn room.
     *
     * @param newRoom   the new room (cannot be null)
     */
    public void setNewRoom(Room newRoom) {
        this.newRoom = newRoom;
    }

}
