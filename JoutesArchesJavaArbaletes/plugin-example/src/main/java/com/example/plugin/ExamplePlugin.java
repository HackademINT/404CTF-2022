package com.example.plugin;

import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.Plugin;
import org.hallebarde.recrutement.api.PluginMetadata;
import org.hallebarde.recrutement.api.commands.CommandExecutor;
import org.hallebarde.recrutement.api.events.HandlerPriority;
import org.hallebarde.recrutement.api.events.SubscribeEvent;
import org.hallebarde.recrutement.api.events.user.RoomInteractionEvent;
import org.hallebarde.recrutement.api.gameplay.user.User;

import java.util.logging.Logger;

public class ExamplePlugin implements Plugin {

    private static ExamplePlugin instance;
    private Logger logger;

    @Override
    public void onLoad(Game game, PluginMetadata metadata, Logger logger) {
        instance = this;
        this.logger = logger;

        // Items, interactions and commands need to be registered to the game server
        game.getItemRegistry().put("example_item", ExampleItem::new);
        game.getRoomInteractionRegistry().put("example_interaction", ExampleInteraction::new);
        game.registerCommand("example", this::exampleCommand, "Example command starts an example activity");

        // This will make sure this#onRoomInteraction(RoomInteractionEvent) is called when a user interacts with a room
        game.registerEventHandler(this);

        this.logger.info("Example plugin loaded! version: " + metadata.version());

    }

    @SubscribeEvent(HandlerPriority.HIGHEST)
    public void onRoomInteraction(RoomInteractionEvent event) {
        // Prevent any interaction which is not the example interaction from happening
        if (!(event.getInteraction() instanceof ExampleInteraction)) {
            event.cancel();
        }
    }

    @Override
    public void onUnload(Game game) {
        this.logger.info("Example plugin unloaded!");
    }

    private void exampleCommand(Game game, CommandExecutor commandExecutor, String... strings) {
        if (commandExecutor instanceof User user) {
            user.sendMessage("Starting example activity, say stop to stop");
            game.startActivityNow(new ExampleActivity(user));
        } else {
            // The command has been run from the console and not by a player, we can't start the activity
            commandExecutor.sendMessage("Can only run example command as a user, not from the console.");
        }
    }

    public Logger getLogger() {
        return this.logger;
    }

    public static ExamplePlugin getInstance() {
        return instance;
    }

}