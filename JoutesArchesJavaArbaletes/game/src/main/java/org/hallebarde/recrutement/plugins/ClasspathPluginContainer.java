package org.hallebarde.recrutement.plugins;

import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.PluginMetadata;

public class ClasspathPluginContainer extends PluginContainer {

    public ClasspathPluginContainer(PluginMetadata metadata) {
        super(metadata);
    }

    @Override
    public void load(Game game) throws CorruptedPluginException, PluginLoadingException {
        this.load(game, this.getClass().getClassLoader());
    }

}
