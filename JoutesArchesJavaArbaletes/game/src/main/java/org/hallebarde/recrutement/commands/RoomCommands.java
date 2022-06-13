package org.hallebarde.recrutement.commands;

import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.commands.CommandExecutor;
import org.hallebarde.recrutement.api.events.HandlerPriority;
import org.hallebarde.recrutement.api.events.SubscribeEvent;
import org.hallebarde.recrutement.api.events.user.RoomInteractionEvent;
import org.hallebarde.recrutement.api.events.user.UserChangesRoomEvent;
import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.world.RoomInteraction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RoomCommands {

    RoomCommands() {}

    void commandMove(Game game, CommandExecutor executor, String... args) {
        if (!(executor instanceof User user)) {
            executor.sendMessage("This command can only be executed by a player");
            return;
        }
        if (args.length != 1) {
            user.sendMessage("Usage: /move <room>");
            return;
        }
        Room newRoom = user.getRoom().getReachableRooms().get(args[0]);
        if (newRoom == null) {
            user.sendMessage(String.format("Cannot go to %s from the current room.%n", args[0]));
            return;
        }
        game.fireEvent(new UserChangesRoomEvent(user, user.getRoom(), newRoom, String.format("You just entered %s%n", newRoom.name())));

    }

    void commandExplore(Game game, CommandExecutor executor, String... args) {
        if (!(executor instanceof User user)) {
            executor.sendMessage("This command can only be executed by a player");
            return;
        }
        final StringBuilder builder = new StringBuilder();
        Room room = user.getRoom();
        builder.append(String.format("You are in %s.%n", room.name()))
                .append(String.format("%s%n%n", room.description()));
        Map<String, Room> neighbors = user.getRoom().getReachableRooms();
        if (neighbors.size() == 0) {
            builder.append("How did you get here! This is a dead end, and there is no way back.");
        } else {
            builder.append("This room has access to a few others, use /move <room> to get to one of the following rooms:\n");
            neighbors.forEach((id, r) ->
                    builder.append(String.format("\t%s%n", id))
            );
        }
        List<Item> items = room.getItemsIn();
        if (items.size() > 0) {
            builder.append("\n");
            builder.append("There are a few interesting things you can gather here:");
            builder.append("\n");
            int i = 0;
            for (Item item: items) {
                builder.append(i++).append("\t").append(item.name()).append(": ").append(item.description()).append("\n");
            }
        }
        List<RoomInteraction> interactions = room.getInteractions();
        if (interactions.size() > 0) {
            builder.append("\n");
            builder.append("You can interact with a few things in this room:");
            builder.append("\n");
            int i = 0;
            for (RoomInteraction interaction : interactions) {
                builder.append(i++).append("\t").append(interaction.name()).append(": ").append(interaction.description()).append("\n");
            }
        }
        user.sendMessage(builder.toString());
    }

    void commandInteract(Game game, CommandExecutor executor, String... args) {
        if (!(executor instanceof User user)) {
            executor.sendMessage("This command can only be executed by a player");
            return;
        }
        int index;
        try {
            index = Integer.parseInt(args[0]);
        } catch (Exception e) {
            executor.sendMessage("Syntax: /interact <entity number>");
            return;
        }
        Room room = user.getRoom();
        List<RoomInteraction> interactions = room.getInteractions();
        if (index >= interactions.size() || index < 0) {
            executor.sendMessage("Invalid interaction number");
            return;
        }
        RoomInteraction interaction = interactions.get(index);
        game.fireEvent(new RoomInteractionEvent(user, room, interaction, Arrays.copyOfRange(args, 1, args.length)));
    }

    @SubscribeEvent(HandlerPriority.NORMAL)
    public void interact(RoomInteractionEvent event) {
        if (event.isCancelled()) return;
        event.getInteraction().interact(event.getUser(), event.getRoom(), event.getArgs());
    }

    @SubscribeEvent
    public void move(UserChangesRoomEvent event) {
        if (event.isCancelled()) return;
        User user = event.getUser();
        user.moveTo(event.getRoomTo());
        String message = event.getMessage();
        if (message != null) user.sendMessage(message);
    }

}
