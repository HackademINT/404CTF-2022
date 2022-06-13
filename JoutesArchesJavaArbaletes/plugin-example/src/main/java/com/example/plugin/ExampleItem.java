package com.example.plugin;

import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.storage.Options;

import java.util.Arrays;

public class ExampleItem implements Item {

    private final String name;
    private final String description;

    public ExampleItem(Options options) {
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
    public void use(User user, String... args) {
        // the use method is called when a user uses the /use command on this item
        user.sendMessage("You are using " + this.name);
        ExamplePlugin.getInstance().getLogger().info(String.format("User %s is using item %s with arguments %s .",
                user.getName(),
                this.name,
                Arrays.toString(args)));
    }

}
