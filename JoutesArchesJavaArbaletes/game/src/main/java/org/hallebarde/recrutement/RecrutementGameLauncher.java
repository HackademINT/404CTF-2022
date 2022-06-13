package org.hallebarde.recrutement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.jul.Log4jBridgeHandler;
import org.hallebarde.recrutement.util.LoggerPrintStream;

import static org.hallebarde.recrutement.util.InternalHelper.getInternalConsole;

public class RecrutementGameLauncher {

    private static final Logger LOGGER  = LogManager.getLogger("Game Launcher");
    private static RecrutementGame game;

    public static void main(String... args) {
        org.apache.logging.log4j.jul.LogManager.getLogManager();
        Log4jBridgeHandler.install(true, "plugin", true);
        System.setOut(new LoggerPrintStream(LogManager.getLogger("StdoutCapture")));
        try {
            game = new RecrutementGame();
            game.load();
        } catch (Exception e) {
            LOGGER.fatal("A fatal error occurred when initializing the game");
            LOGGER.catching(e);
            System.exit(1);
            return; // So the compiler does not freak out
        }
        try {
            getInternalConsole(game.getConsole()).start();
            game.start();
        } catch (Exception e) {
            LOGGER.fatal("A fatal error occurred when running the game");
            LOGGER.catching(e);
            System.exit(2);
        }
    }

    public static RecrutementGame getGame() {
        if (game == null) throw new IllegalStateException("Game not loaded");
        return game;
    }
}
