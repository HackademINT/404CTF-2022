package org.hallebarde.recrutement.commands;

import org.hallebarde.recrutement.RecrutementGame;
import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.commands.CommandExecutor;
import org.hallebarde.recrutement.plugins.PluginContainer;

import java.io.*;

import static org.hallebarde.recrutement.util.InternalHelper.getInternalGame;

public class DebugCommands {

    public static void registerDebugCommands(Game game) {
        game.registerCommand(
                "plgdump",
                DebugCommands::dumpPluginsCommand,
                "'plgdump file' Debug command: dump plugin object to file.");
        game.registerCommand(
                "plgload",
                DebugCommands::loadDumpedPluginCommand,
                "'plgload file' Debug command: load dumped plugins object from file.");
    }

    public static void dumpPluginsCommand(Game game, CommandExecutor executor, String... arguments) {
        if (arguments.length != 2) {
            executor.sendMessage("Syntax: 'plgdump plugin_id file'");
            return;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arguments[1]))) {
            getInternalGame(game).getPluginLoader().dumpPlugin(arguments[0], out);
            executor.sendMessage("Dumped plugin " + arguments[0] + " to " + arguments[1]);
        } catch (IOException e) {
            executor.sendMessage("Failed to dump plugin! " + e.getLocalizedMessage());
            RecrutementGame.LOGGER.error("Failed to dump plugin!");
            RecrutementGame.LOGGER.catching(e);
        }
    }

    public static void loadDumpedPluginCommand(Game game, CommandExecutor executor, String... arguments) {
        if (arguments.length != 1) {
            executor.sendMessage("Syntax: 'plgload file'");
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arguments[0]))) {
            PluginContainer container = getInternalGame(game).getPluginLoader().reloadDumpedPlugin(in);
            executor.sendMessage("Loaded plugin " + container + " from " + arguments[0]);
        } catch (Exception e) {
            executor.sendMessage("Failed to load plugin: " + e);
            RecrutementGame.LOGGER.catching(e);
        }
    }

}
