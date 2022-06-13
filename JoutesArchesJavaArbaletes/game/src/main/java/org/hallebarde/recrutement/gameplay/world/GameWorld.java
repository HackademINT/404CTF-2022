package org.hallebarde.recrutement.gameplay.world;

import org.hallebarde.recrutement.RecrutementGame;
import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.events.world.SpawnRoomChangeEvent;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.world.World;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static org.hallebarde.recrutement.util.InternalHelper.getInternalRoom;

public class GameWorld implements World {

    private final RecrutementGame game;
    private final Map<String, GameRoom> rooms = new HashMap<>();
    private final Map<String, Room> readOnlyRooms = unmodifiableMap(this.rooms);
    private GameRoom defaultRoom;
    private boolean fireEvents = true;

    public GameWorld(RecrutementGame game) {
        this.game = game;
    }

    @Override
    public Map<String, Room> getRooms() {
        return this.readOnlyRooms;
    }

    @Override
    public Room getRoom(String roomId) {
        GameRoom room = this.rooms.get(roomId);
        if (room == null) {
            room = new GameRoom(roomId, this);
            this.rooms.put(roomId, room);
        }
        return room;
    }

    @Override
    public Room getExistingRoom(String roomId) {
        return this.rooms.get(roomId);
    }

    @Override
    public Room getSpawnRoom() {
        return this.defaultRoom;
    }

    @Override
    public void setSpawnRoom(Room spawnRoom) {
        GameRoom oldRoom = this.defaultRoom;
        SpawnRoomChangeEvent event = new SpawnRoomChangeEvent(this, oldRoom, spawnRoom);
        if ((this.fireEvents && this.game.fireEvent(event).isCancelled())) return;
        spawnRoom = event.getNewSpawnRoom(); // Could have been changed by handlers
        if (spawnRoom == null) throw new IllegalStateException("New spawn room is null!");
        this.defaultRoom = getInternalRoom(spawnRoom);
        RecrutementGame.LOGGER.debug("Changed world spawn room from {} to {}", oldRoom, this.defaultRoom);
    }

    @Override
    public Game getGame() {
        return this.game;
    }

    public boolean shouldFireEvents() {
        return fireEvents;
    }

    public void setFireEvents(boolean fireEvents) {
        this.fireEvents = fireEvents;
    }
}
