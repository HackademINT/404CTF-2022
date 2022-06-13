package org.hallebarde.recrutement.gameplay.world;

import org.hallebarde.recrutement.RecrutementGame;
import org.hallebarde.recrutement.api.events.world.ChangeRoomConnectionEvent;
import org.hallebarde.recrutement.api.events.world.ChangeRoomDescriptionEvent;
import org.hallebarde.recrutement.api.events.world.ChangeRoomNameEvent;
import org.hallebarde.recrutement.api.gameplay.world.RoomInteraction;
import org.hallebarde.recrutement.api.gameplay.world.World;
import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.util.InternalHelper;

import java.util.*;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

public class GameRoom implements Room {

    private final GameWorld world;
    private final String roomId;
    private String name = "Unknown room";
    private String description = "";

    private final Map<String, GameRoom> neighbors = new HashMap<>();
    private final Map<String, Room> unmodifiableNeighbors = unmodifiableMap(this.neighbors);
    public final List<User> users = new ArrayList<>();
    public final List<Item> items = new ArrayList<>();
    public final List<RoomInteraction> interactions = new ArrayList<>();
    public final List<RoomInteraction> unmodifiableInteractions = unmodifiableList(this.interactions);

    public GameRoom(String roomId, GameWorld world) {
        this.roomId = roomId;
        this.world = world;
    }

    @Override
    public String id() {
        return this.roomId;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Map<String, Room> getReachableRooms() {
        return this.unmodifiableNeighbors;
    }

    @Override
    public List<User> getUsersIn() {
        return this.users;
    }

    @Override
    public List<Item> getItemsIn() {
        return this.items;
    }

    @Override
    public List<RoomInteraction> getInteractions() {
        return this.unmodifiableInteractions;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public World world() {
        return this.world;
    }

    @Override
    public void setName(String name) {
        if (this.world.shouldFireEvents()) {
            ChangeRoomNameEvent event = new ChangeRoomNameEvent(this, name);
            if (this.world.getGame().fireEvent(event).isCancelled()) return;
            name = event.getNewName();
        }
        this.name = name;
    }

    @Override
    public void setDescription(String description) {
        if (this.world.shouldFireEvents()) {
            ChangeRoomDescriptionEvent event = new ChangeRoomDescriptionEvent(this, description);
            if (this.world.getGame().fireEvent(event).isCancelled()) return;
            description = event.getNewDescription();
        }
        this.description = description;
    }

    @Override
    public void connectOneWay(Room other) {
        if (this.world.getExistingRoom(other.id()) != other) throw new IllegalStateException("Room is not in world");
        if (this.world.shouldFireEvents()) {
            if (this.world.getGame().fireEvent(new ChangeRoomConnectionEvent.OneWay(this, other)).isCancelled()) {
                return;
            }
        }
        this.neighbors.put(other.id(), InternalHelper.getInternalRoom(other));
        RecrutementGame.LOGGER.trace("Added a one way connection from room {} to room {}", this.id(), other.id());
    }

    @Override
    public void connectBothWays(Room other) {
        if (this.world.getExistingRoom(other.id()) != other) throw new IllegalStateException("Room is not in world");
        if (this.world.shouldFireEvents()) {
            if (this.world.getGame().fireEvent(new ChangeRoomConnectionEvent.BothWays(this, other)).isCancelled()) {
                return;
            }
        }
        this.neighbors.put(other.id(), InternalHelper.getInternalRoom(other));
        InternalHelper.getInternalRoom(other).neighbors.put(this.roomId, this);
        RecrutementGame.LOGGER.trace("Added a two ways connection between rooms {} and {}", this.id(), other.id());
    }

    @Override
    public void disconnectFrom(Room other) {
        if (this.world.getExistingRoom(other.id()) != other) throw new IllegalStateException("Room is not in world");
        if (this.world.shouldFireEvents()) {
            if (this.world.getGame().fireEvent(new ChangeRoomConnectionEvent.Disconnect(this, other)).isCancelled()) {
                return;
            }
        }
        this.neighbors.remove(other.id(), InternalHelper.getInternalRoom(other));
        InternalHelper.getInternalRoom(other).neighbors.remove(this.roomId, this);
        RecrutementGame.LOGGER.trace("Disconnected rooms {} and {}", this.id(), other.id());
    }

}
