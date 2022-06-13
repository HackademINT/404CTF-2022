package com.example.plugin;

import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.world.RoomInteraction;
import org.hallebarde.recrutement.api.storage.Options;

import java.util.Arrays;

public class ExampleInteraction implements RoomInteraction {

    private final String name;
    private final String description;

    public ExampleInteraction(Options options) {
        // Options allow you to read parameters from the room's json file
        this.name = options.getString("name", "Default Name");
        this.description = options.getString("description", "Default description");
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public void interact(User user, Room room, String... args) {
        // the use method is called when a user uses the /use command on this item
        user.sendMessage("You are interacting with " + this.name);
        ExamplePlugin.getInstance().getLogger().info(String.format("User %s is interacting with %s with arguments %s in room %s.",
                user.getName(),
                this.name,
                Arrays.toString(args),
                room.id()));
    }

}
