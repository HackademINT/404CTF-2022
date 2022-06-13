package org.hallebarde.recrutement.api.gameplay.world;

import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.annotations.DoNotImplement;

import java.util.Map;

/**
 * A world the server is running.
 */
@DoNotImplement
public interface World {

    /**
     * @return the rooms in this world, as an unmodifiable map
     */
    Map<String, Room> getRooms();

    /**
     * Gets an existing room, or creates it if it does not yet exist.
     *
     * @param roomId    the room id
     * @return the room
     */
    Room getRoom(String roomId);

    /**
     * Gets an existing room.
     *
     * @param roomId    the room id
     * @return the room if it exists, null if it does not
     */
    Room getExistingRoom(String roomId);

    /**
     * @return the room new players spawn in
     */
    Room getSpawnRoom();

    /**
     * Changes the spawn room of this world.
     *
     * @param spawnRoom a new spawn room for this world
     */
    void setSpawnRoom(Room spawnRoom);

    /**
     * @return the game this world is running in
     */
    Game getGame();

}
