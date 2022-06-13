package org.hallebarde.recrutement.api.events.world;

import org.hallebarde.recrutement.api.annotations.DoNotFire;
import org.hallebarde.recrutement.api.gameplay.world.Room;

/**
 * This class is only meant to group together {@link OneWay}, {@link BothWays} and {@link Disconnect}.
 */
public final class ChangeRoomConnectionEvent {

    /**
     * The event is fired when a room's neighbors are updated to add a one way connection from one room to another.
     * 
     * @see Room#connectOneWay(Room)
     */
    @DoNotFire
    public static class OneWay extends CancelableRoomEvent {

        private final Room destinationRoom;

        public OneWay(Room room, Room destinationRoom) {
            super(room);
            this.destinationRoom = destinationRoom;
        }

        public Room getDestinationRoom() {
            return this.destinationRoom;
        }

    }

    /**
     * The event is fired when a room's neighbors are updated to add a two ways connection between two rooms.
     * 
     * @see Room#connectBothWays(Room) 
     */
    @DoNotFire
    public static class BothWays extends CancelableRoomEvent {

        private final Room otherRoom;

        public BothWays(Room room, Room otherRoom) {
            super(room);
            this.otherRoom = otherRoom;
        }

        public Room getOtherRoom() {
            return this.otherRoom;
        }

    }

    /**
     * This event is fired when a room's neighbors are updated to remove a connection between two rooms.
     * 
     * @see Room#disconnectFrom(Room)
     */
    @DoNotFire
    public static class Disconnect extends CancelableRoomEvent {

        private final Room otherRoom;

        public Disconnect(Room room, Room otherRoom) {
            super(room);
            this.otherRoom = otherRoom;
        }

        public Room getOtherRoom() {
            return this.otherRoom;
        }

    }

}
