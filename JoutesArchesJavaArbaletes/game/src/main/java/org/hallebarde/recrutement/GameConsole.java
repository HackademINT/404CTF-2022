package org.hallebarde.recrutement;

import net.minecrell.terminalconsole.SimpleTerminalConsole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hallebarde.recrutement.api.Console;
import org.hallebarde.recrutement.api.ConsoleInputEvent;
import org.hallebarde.recrutement.api.GameState;
import org.hallebarde.recrutement.api.events.SubscribeEvent;

import java.util.concurrent.CompletableFuture;

public class GameConsole extends SimpleTerminalConsole implements Console {

    private static final Logger LOGGER = LogManager.getLogger("Console");

    private final RecrutementGame game;

    public GameConsole(RecrutementGame game) {
        this.game = game;
        this.game.registerEventHandler(this);
    }

    @Override
    public void start() {
        Thread thread = new Thread(super::start);
        thread.setName("Console Thread");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    protected boolean isRunning() {
        return this.game.getState() != GameState.STOPPING;
    }

    @Override
    protected void runCommand(String command) {
        this.game.fireEvent(new ConsoleInputEvent(command));
    }

    @Override
    protected void shutdown() {
        this.game.stop(null);
    }

    @Override
    public CompletableFuture<Void> sendMessage(final String message) {
        LOGGER.info(message);
        return CompletableFuture.completedFuture(null);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onConsoleInput(ConsoleInputEvent event) {
        if (event.isCancelled()) return;
        if (this.game.getState() != GameState.RUNNING) {
            LOGGER.warn("Cannot process commands until the game has fully started.");
        } else {
            this.game.getCommandManager().processCommand(event.getText(), this);
        }
    }

}
