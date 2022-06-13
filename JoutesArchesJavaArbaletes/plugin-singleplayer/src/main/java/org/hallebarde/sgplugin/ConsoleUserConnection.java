package org.hallebarde.sgplugin;

import org.hallebarde.recrutement.api.ConsoleInputEvent;
import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.events.HandlerPriority;
import org.hallebarde.recrutement.api.events.SubscribeEvent;
import org.hallebarde.recrutement.api.network.UserConnection;

import java.net.InetAddress;
import java.util.concurrent.*;
import java.util.logging.Level;

public class ConsoleUserConnection implements UserConnection {

    private final Game game;
    private final SinglePlayerPlugin plugin;
    private final Executor executor = Executors.newSingleThreadExecutor(this::createThread);
    private final BlockingQueue<String> commandQueue = new LinkedBlockingQueue<>();

    public ConsoleUserConnection(SinglePlayerPlugin plugin) {
        this.plugin = plugin;
        this.game = plugin.getGame();
    }

    @Override
    public CompletableFuture<String> readLine() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return this.commandQueue.take();
            } catch (InterruptedException e) {
                this.plugin.getLogger().log(Level.WARNING, "Error when retrieving last command from queue", e);
            }
            return "";
        }, this.executor);
    }

    @Override
    public CompletableFuture<Void> writeLine(String line) {
        return this.write(line);
    }

    @Override
    public CompletableFuture<Void> write(String text) {
        if (text != null) {
            this.game.getConsole().sendMessage(text);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public boolean isOpened() {
        return true;
    }

    @Override
    public CompletableFuture<Void> close(String message) {
        this.writeLine(message);
        this.game.stop("Stopping server as single user is quitting.");
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public InetAddress getAddress() {
        return null;
    }

    @SuppressWarnings("unused")
    @SubscribeEvent(HandlerPriority.HIGHEST)
    public void onConsoleTryInput(ConsoleInputEvent event) {
        event.cancel();
        try {
            this.commandQueue.put(event.getText().strip());
        } catch (InterruptedException e) {
            this.plugin.getLogger().log(Level.WARNING, "Error when adding last command to queue", e);
        }
    }

    private Thread createThread(Runnable run) {
        Thread thread = new Thread(run);
        thread.setDaemon(true);
        thread.setName("Single player console thread");
        return thread;
    }

}
