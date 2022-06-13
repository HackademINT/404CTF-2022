package org.hallebarde.recrutement.api;

import org.hallebarde.recrutement.api.annotations.DoNotImplement;

import java.io.IOException;

/**
 * The game's configuration.
 */
@DoNotImplement
public interface GameConfig {

    /**
     * @return the IP address the game is configured to bind to
     */
    String hostname();

    /**
     * @return the TCP port the game is configured to bind to
     */
    int port();

    /**
     * @return the "message of the day" this game should great users with when they connect
     */
    String motd();

    /**
     * @return the name of the directory the game loads plugins from
     */
    String pluginDirectory();

    /**
     * @return the name of the directory from which to load the world
     */
    String worldDirectory();

    /**
     * @return whether the game runs in debug mode of not
     */
    boolean debug();

    /**
     * Reloads the game's config.
     * Beware that some config value are only read when the game starts,
     * and changing them afterwards will have no effect.
     *
     * @throws IOException if reading the configuration failed
     */
    void reload() throws IOException;

}
