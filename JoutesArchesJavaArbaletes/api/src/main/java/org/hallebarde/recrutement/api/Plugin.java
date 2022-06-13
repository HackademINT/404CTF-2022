package org.hallebarde.recrutement.api;

import org.hallebarde.recrutement.api.annotations.FreeToImplement;

import java.util.logging.Logger;

/**
 * A plugin that can be loaded into the game.
 */
@FreeToImplement
public interface Plugin {

    /**
     * Called when this plugin is being loaded by the game.
     *
     * @param game      the game this plugin is being loaded by.
     * @param metadata  the metadata the game has loaded for this plugin
     */
    void onLoad(Game game, PluginMetadata metadata, Logger logger);

    /**
     * Called when this plugin is being unloaded by the game.
     *
     * @param game the game this plugin is being unloaded by.
     */
    void onUnload(Game game);

}
