package org.hallebarde.recrutement.gameplay.user;

import org.hallebarde.recrutement.RecrutementGame;
import org.hallebarde.recrutement.api.events.SubscribeEvent;
import org.hallebarde.recrutement.api.events.network.UserConnectEvent;
import org.hallebarde.recrutement.api.events.network.UserJoinEvent;
import org.hallebarde.recrutement.api.events.network.UserLogInEvent;
import org.hallebarde.recrutement.api.network.UserConnection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlayerManager {

    private final RecrutementGame game;
    private final HashMap<String, Player> players = new HashMap<>();

    public PlayerManager(RecrutementGame game) {
        this.game = game;
        this.game.registerEventHandler(this);
    }

    public synchronized void removeOfflinePlayers() {
        Iterator<Map.Entry<String, Player>> entryIterator = this.players.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Player player = entryIterator.next().getValue();
            if (!player.isOnline()) {
                entryIterator.remove();
                this.game.broadcast(player.getName() + " left the game.");
            }
        }
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void acceptPlayer(UserConnectEvent event) {
        if (event.isCancelled()) {
            event.getConnection().close(null);
            return;
        }
        UserConnection connection = event.getConnection();
        try {
            Player player = null;
            while (player == null) {
                connection.write("Please enter your username: ");
                String name = connection.readLine().get();
                UserLogInEvent logInEvent = new UserLogInEvent(name, connection);
                if (this.game.fireEvent(logInEvent).isCancelled()) continue;
                player = this.join(name, connection);
                if (player == null) {
                    connection.write("Name is taken");
                }
            }
            player.sendMessage(this.game.getConfig().motd());
            this.sendAll(player.getName() + " has joined the game.");

            // We rely on the network manager to catch this and start listening for player inputs
            this.game.fireEvent(new UserJoinEvent(player));
        } catch (Exception e) {
            RecrutementGame.LOGGER.error("Encountered an exception when accepting a player connection");
            RecrutementGame.LOGGER.catching(e);
        }
    }

    private synchronized Player join(String name, UserConnection connection) {
        if (this.players.containsKey(name)) {
            return null;
        } else {
            Player player = new Player(name, this.game, connection);
            this.players.put(name, player);
            return player;
        }
    }

    public synchronized void sendAll(String message) {
        this.players.values().forEach(u -> u.sendMessage(message));
    }

    public synchronized int getPlayerCount() {
        return this.players.size();
    }

    public synchronized Player getPlayer(String name) {
        return this.players.get(name);
    }

    public synchronized Player[] getPlayers() {
        return this.players.values().toArray(new Player[0]);
    }

}
