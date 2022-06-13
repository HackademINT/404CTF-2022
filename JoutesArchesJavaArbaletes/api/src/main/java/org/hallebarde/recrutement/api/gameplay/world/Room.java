package org.hallebarde.recrutement.api.gameplay.world;

import org.hallebarde.recrutement.api.annotations.DoNotImplement;
import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.user.User;

import java.util.List;
import java.util.Map;

/**
 * A room players can explore.
 */
@DoNotImplement
public interface Room {

    /**
     * @return the unique ID for this room in this world
     */
    String id();

    /**
     * @return this room's name
     */
    String name();

    /**
     * @return this room's description
     */
    String description();

    /**
     * @return the world this room belongs to
     */
    World world();

    /**
     * @return the rooms accessible from this one as an unmodifiable map
     */
    Map<String, Room> getReachableRooms();

    /**
     * @return the users present in this room, as a non thread safe list
     */
    List<User> getUsersIn();

    /**
     * @return the items present in this room, as a non thread safe list
     */
    List<Item> getItemsIn();

    /**
     * @return the list of interactions in this room, as an unmodifiable list
     */
    List<RoomInteraction> getInteractions();

    /**
     * Changes this rooms name's.
     *
     * @param name a new name for this room
     */
    void setName(String name);

    /**
     * Changes this room's description.
     *
     * @param description a new description for this room
     */
    void setDescription(String description);

    /**
     * Makes the other room accessible from this one without making this one accessible to the other.
     *
     * @param other the room to make accessible
     */
    void connectOneWay(Room other);

    /**
     * Connects this two rooms together.
     *
     * @param other the room to connect with
     */
    void connectBothWays(Room other);

    /**
     * Disconnects these two rooms from each other.
     *
     * @param other the room to disconnect from
     */
    void disconnectFrom(Room other);

}
