package org.hallebarde.recrutement.gameplay.user;

import org.hallebarde.recrutement.RecrutementGame;
import org.hallebarde.recrutement.NetworkManager;
import org.hallebarde.recrutement.api.gameplay.user.Inventory;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.world.World;
import org.hallebarde.recrutement.api.gameplay.Activity;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.network.UserConnection;


public class Player implements User {

    private final String name;
    private final UserConnection connection;
    private final InventoryImplementation inventory = new InventoryImplementation();
    protected final RecrutementGame game;
    protected final World world;
    protected Room room;
    protected Activity activity;

    public Player(String name, RecrutementGame game, UserConnection connection) {
        this.game = game;
        this.world = game.getWorld();
        this.room = this.world.getSpawnRoom();
        this.name = name;
        this.connection = connection;
    }

    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public Room getRoom() {
        return this.room;
    }

    @Override
    public void moveTo(Room room) {
        Room otherRoom = this.world.getRoom(room.id());
        if (otherRoom != room) throw new IllegalArgumentException("Room is not known to world");
        RecrutementGame.LOGGER.info("User {} is moving from room {} to room {}", this.getName(), this.room.id(), room.id());
        this.room.getUsersIn().remove(this);
        this.room = room;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public Activity getOngoingActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        if (activity != null) {
            RecrutementGame.LOGGER.trace("Setting activity {} with UUID {} for user {}", activity.getClass(), activity.getUUID(), this.getName());
        } else {
            RecrutementGame.LOGGER.trace("Setting null activity for user {}", this.getName());
        }
        this.activity = activity;
    }

    public Void listenForMessages() {
        while (this.isOnline()) {
            try {
                String message  = this.connection.readLine().get();
                if (message != null) {
                    this.processMessage(message);
                }
            } catch (Exception e) {
                NetworkManager.LOGGER.error("Error when reading a message from a user");
            }
        }
        return null;
    }

    private void processMessage(String message) {
        message = message.trim();
        if (message.startsWith("/")) {
            this.game.getCommandManager().processCommand(message.substring(1), this);
        } else if (message.length() > 0) {
            Activity onGoing = this.getOngoingActivity();
            if (onGoing != null) {
                onGoing.processMessage(this, message);
            } else {
                this.game.broadcast('<' + this.name + "> " + message);
            }
        }
    }

    @Override
    public UserConnection getConnection() {
        return this.connection;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

}
