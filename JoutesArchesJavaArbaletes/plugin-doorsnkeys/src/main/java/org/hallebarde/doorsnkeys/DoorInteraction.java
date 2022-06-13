package org.hallebarde.doorsnkeys;

import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.world.RoomInteraction;
import org.hallebarde.recrutement.api.gameplay.world.World;
import org.hallebarde.recrutement.api.storage.Options;


public class DoorInteraction implements RoomInteraction {

    private final String descriptionOpen;
    private final String descriptionClose;
    private final String password;
    private final String destination;
    private boolean opened = false;

    public DoorInteraction(Options options) {
        this.descriptionOpen = options.getString("open_description", "A generic opened door.");
        this.descriptionClose = options.getString("close_description", "A generic closed door.");
        this.password = options.getString("password", "");
        this.destination = options.getString("destination", "");
    }

    @Override
    public String name() {
        return "Door";
    }

    @Override
    public String description() {
        return this.opened ? this.descriptionOpen : this.descriptionClose;
    }

    @Override
    public void interact(User user, Room room, String... args) {
        if (this.opened) {
            user.sendMessage("This door is already open");
            return;
        }
        for (Item item: user.getInventory()) if (item instanceof KeyItem key) {
            if (key.getDoorPassword().equals(this.password)) {
                if (this.open(user.getWorld(), room)) {
                    user.sendMessage("You successfully opened the door, you can now got to " + this.destination);
                } else {
                    user.sendMessage("This door does not seem to lead anywhere...");
                }
                return;
            }
        }
        user.sendMessage("You do not have the right key to open this door.");
    }

    private boolean open(World world, Room room) {
        Room destinationRoom = world.getRoom(this.destination);
        if (room == null) return false;
        room.connectOneWay(destinationRoom);
        this.opened = true;
        for (RoomInteraction interaction: destinationRoom.getInteractions()) {
            if (interaction instanceof DoorInteraction doorInteraction && doorInteraction.destination.equals(room.id())) {
                destinationRoom.connectOneWay(room);
                doorInteraction.opened = true;
            }
        }
        return true;
    }

}
