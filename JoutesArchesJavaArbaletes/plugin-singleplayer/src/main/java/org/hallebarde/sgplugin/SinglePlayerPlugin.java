package org.hallebarde.sgplugin;

import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.Plugin;
import org.hallebarde.recrutement.api.PluginMetadata;

import java.util.logging.Logger;

public class SinglePlayerPlugin implements Plugin {
    
    private Game game;
    private ConsoleUserConnection connection;
    private Logger logger;

    @Override
    public void onLoad(Game game, PluginMetadata metadata, Logger logger) {
        this.game = game;
        this.connection = new ConsoleUserConnection(this);
        this.logger = logger;
        game.registerEventHandler(new NetworkEventHandler(this));
        game.registerEventHandler(this.connection);
        this.logger.fine(String.format("Loaded single player plugin version %s !", metadata.version()));
    }

    @Override
    public void onUnload(Game game) {
    }

    public Game getGame() {
        return game;
    }

    public ConsoleUserConnection getConnection() {
        return connection;
    }

    public Logger getLogger() {
        return this.logger;
    }

}
