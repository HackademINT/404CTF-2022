package org.hallebarde.recrutement.commands;

import org.hallebarde.recrutement.RecrutementGame;
import org.hallebarde.recrutement.api.commands.Command;
import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.commands.CommandExecutor;
import org.hallebarde.recrutement.api.gameplay.user.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Map<String, RegisteredCommand> commands = new HashMap<>();
    private final Game game;

    public CommandManager(Game game) {
        this.game = game;
        ItemCommands itemCommands = new ItemCommands();
        RoomCommands roomCommands = new RoomCommands();
        this.game.registerEventHandler(itemCommands);
        this.game.registerEventHandler(roomCommands);
        this.registerCommand("quit", this::commandQuit, "Disconnect from the server.");
        this.registerCommand("users", this::commandUsers, "List online users.");
        this.registerCommand("stop", this::commandStop, "Stop the server.");
        this.registerCommand("help", this::commandHelp, "List available commands.");
        this.registerCommand("move", roomCommands::commandMove, "Move to an other room: /move <room>.");
        this.registerCommand("explore", roomCommands::commandExplore, "Explore your surroundings.");
        this.registerCommand("interact", roomCommands::commandInteract, "Interact with one of the objects in the room.");
        this.registerCommand("inventory", itemCommands::commandInventory, "List the content of your inventory.");
        this.registerCommand("pickup", itemCommands::commandPickup, "Pickup an item from the room you are in.");
        this.registerCommand("drop", itemCommands::commandDrop, "Drop an item from your inventory to the room you are in.");
        this.registerCommand("use", itemCommands::commandUseItem, "Uses an item from your inventory.");
    }

    synchronized public void registerCommand(String prefix, Command command, String description) {
        if (prefix == null) throw new IllegalArgumentException("Command prefix cannot be null.");
        if (command == null) throw new IllegalArgumentException("Command handler cannot be null.");
        if (description == null) throw new IllegalArgumentException("Command description cannot be null");
        if (this.commands.containsKey(prefix)) throw new IllegalStateException("A command has already been registered with this prefix.");
        this.commands.put(prefix, new RegisteredCommand(prefix, command, description));
        RecrutementGame.LOGGER.debug("Registered command {} to class {}", prefix, command.getClass());
    }

    synchronized public void processCommand(final String message, final CommandExecutor commandExecutor) {
        String[] arguments = message.split("[ \\t\\n\\r]+");
        if (arguments.length < 1) {
            commandExecutor.sendMessage("Missing command prefix.");
            return;
        }
        RegisteredCommand executor = this.commands.get(arguments[0]);
        if (executor == null) {
            commandExecutor.sendMessage("Unknown command.");
            return;
        }
        this.game.runOnGameLoop(() -> {
            String name = commandExecutor instanceof User user ? user.getName(): "Server console";
            RecrutementGame.LOGGER.info("{} issued command {}", name, message);
            executor.command.execute(this.game, commandExecutor, Arrays.copyOfRange(arguments, 1, arguments.length));
        });
    }

    private void commandQuit(Game game, CommandExecutor executor, String... args) {
        if (!(executor instanceof User user)) {
            executor.sendMessage("This command can only be executed by a player");
            return;
        }
        user.getConnection().close("Goodbye!");
    }

    private void commandUsers(Game game, CommandExecutor executor, String... args) {
        User[] users = game.getUsers();
        String message = String.format("%s online user%s: %s",
                users.length,
                users.length == 1 ? "": "s",
                String.join(", ", Arrays.stream(users).map(User::getName).toList())
        );
        executor.sendMessage(message);
    }

    private void commandStop(Game game, CommandExecutor executor, String... args) {
        String message = args.length > 0 ? String.join(" "): "The server is stopping...";
        game.stop(message);
    }

    private void commandHelp(Game game, CommandExecutor executor, String... args) {
        final StringBuilder builder = new StringBuilder();
        builder.append("List of available commands: ");
        this.commands.forEach((prefix, cmd) ->
            builder.append("\n\t")
                    .append(prefix)
                    .append(": ")
                   .append(cmd.description)
        );
        executor.sendMessage(builder.toString());
    }

    private record RegisteredCommand(String prefix, Command command, String description) {}

}