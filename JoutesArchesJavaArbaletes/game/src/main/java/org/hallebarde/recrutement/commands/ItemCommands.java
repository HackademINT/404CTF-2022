package org.hallebarde.recrutement.commands;

import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.commands.CommandExecutor;
import org.hallebarde.recrutement.api.events.HandlerPriority;
import org.hallebarde.recrutement.api.events.SubscribeEvent;
import org.hallebarde.recrutement.api.events.user.UserDropsItemEvent;
import org.hallebarde.recrutement.api.events.user.UserUsesItemEvent;
import org.hallebarde.recrutement.api.events.user.UserPicksUpItemEvent;
import org.hallebarde.recrutement.api.gameplay.user.Inventory;
import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.user.User;

import java.util.Arrays;
import java.util.List;

public final class ItemCommands {

    ItemCommands() {}

    void commandInventory(Game game, CommandExecutor executor, String... args) {
        if (!(executor instanceof User user)) {
            executor.sendMessage("This command can only be executed by a player");
            return;
        }
        Inventory inventory = user.getInventory();
        if (inventory.size() <= 0) {
            executor.sendMessage("Your inventory is empty.");
        }
        int i = 0;
        for (Item item : inventory) {
            executor.sendMessage(i++ + "\t" + item.name() + ": " + item.description());
        }
    }

    void commandPickup(Game game, CommandExecutor executor, String... args) {
        if (!(executor instanceof User user)) {
            executor.sendMessage("This command can only be executed by a player");
            return;
        }
        int index;
        try {
            index = Integer.parseInt(args[0]);
        } catch (Exception e) {
            executor.sendMessage("Syntax: /pickup <item number>");
            return;
        }
        Room room = user.getRoom();
        Inventory inventory = user.getInventory();
        List<Item> items = room.getItemsIn();
        if (index >= items.size() || index < 0) {
            executor.sendMessage("Invalid item number");
            return;
        }
        Item item = items.get(index);
        UserPicksUpItemEvent event = game.fireEvent(new UserPicksUpItemEvent(user, item, room, "You picked up " + item.name()));
        if (event.isCancelled()) return;

        items.remove(index);
        inventory.add(item);
        executor.sendMessage(event.getMessage());
    }

    void commandDrop(Game game, CommandExecutor executor, String... args) {
        if (!(executor instanceof User user)) {
            executor.sendMessage("This command can only be executed by a player");
            return;
        }
        int index;
        try {
            index = Integer.parseInt(args[0]);
        } catch (Exception e) {
            executor.sendMessage("Syntax: /drop <item number>");
            return;
        }
        Room room = user.getRoom();
        Inventory inventory = user.getInventory();
        List<Item> items = room.getItemsIn();
        if (index >= inventory.size() || index < 0) {
            executor.sendMessage("Invalid item number");
            return;
        }

        Item item = inventory.get(index);
        UserDropsItemEvent event = game.fireEvent(new UserDropsItemEvent(user, item, room, "You dropped " + item.name()));
        if (event.isCancelled()) return;

        inventory.remove(index);
        items.add(item);
        executor.sendMessage(event.getMessage());
    }

    void commandUseItem(Game game, CommandExecutor executor, String... args) {
        if (!(executor instanceof User user)) {
            executor.sendMessage("This command can only be executed by a player");
            return;
        }
        int index;
        try {
            index = Integer.parseInt(args[0]);
        } catch (Exception e) {
            executor.sendMessage("Syntax: /use <item number>");
            return;
        }
        Inventory inventory = user.getInventory();
        Item item;
        if (index >= inventory.size() || index < 0) {
            executor.sendMessage("Invalid item number");
            return;
        }
        item = inventory.get(index);
        game.fireEvent(new UserUsesItemEvent(user, item, Arrays.copyOfRange(args, 1, args.length)));
    }

    @SubscribeEvent(HandlerPriority.NORMAL)
    public void useItem(UserUsesItemEvent event) {
        if (event.isCancelled()) return;
        event.getItem().use(event.getUser(),event.getArgs());
    }

}
