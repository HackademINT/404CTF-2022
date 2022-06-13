package org.hallebarde.sgplugin;

import org.hallebarde.recrutement.api.events.SubscribeEvent;
import org.hallebarde.recrutement.api.events.network.StartNetworkEvent;
import org.hallebarde.recrutement.api.events.network.UserConnectEvent;

public class NetworkEventHandler {

    private final SinglePlayerPlugin plugin;

    public NetworkEventHandler(SinglePlayerPlugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void insteadOfStartNetwork(StartNetworkEvent event) {
        event.cancel();
        this.plugin.getLogger().info("Starting single player mode!");
        this.plugin.getGame().fireEvent(new UserConnectEvent(this.plugin.getConnection()));
    }

}
