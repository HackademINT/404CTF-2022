package org.hallebarde.npcsplugin;

import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.Plugin;
import org.hallebarde.recrutement.api.PluginMetadata;
import org.hallebarde.recrutement.api.storage.Registry;

import java.util.logging.Logger;

public class NpcsPlugin implements Plugin {

    private Logger logger;
    private static NpcsPlugin instance;
    private final Registry<ConversationAI> ais = new RegistryImplementation<>();

    @Override
    public void onLoad(Game game, PluginMetadata metadata, Logger logger) {
        if (instance != null) throw new IllegalStateException("Plugin NPCs has been loaded twice ??");
        NpcsPlugin.instance = this;
        this.logger = logger;
        this.ais.put("flag_carrier", FlagAI::new);
        game.getRoomInteractionRegistry().put("npc", NpcChatInteraction::new);
        logger.info("Loaded NPCs plugin version " + metadata.version());
    }

    @Override
    public void onUnload(Game game) {

    }

    public Registry<ConversationAI> getAis() {
        return ais;
    }

    public Logger getLogger() {
        return logger;
    }

    public static NpcsPlugin getInstance() {
        return instance;
    }

}