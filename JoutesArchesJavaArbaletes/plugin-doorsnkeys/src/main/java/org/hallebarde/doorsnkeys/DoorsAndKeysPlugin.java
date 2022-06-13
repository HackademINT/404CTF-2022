package org.hallebarde.doorsnkeys;

import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.Plugin;
import org.hallebarde.recrutement.api.PluginMetadata;

import java.util.logging.Logger;

public class DoorsAndKeysPlugin implements Plugin {

    public static Logger logger;

    @Override
    public void onLoad(Game game, PluginMetadata metadata, Logger logger) {
        DoorsAndKeysPlugin.logger = logger;
        game.getItemRegistry().put("key", KeyItem::new);
        game.getRoomInteractionRegistry().put("door", DoorInteraction::new);
        logger.info("Loaded doorsnkey version " + metadata.version());
    }

    @Override
    public void onUnload(Game game) {
    }

}
