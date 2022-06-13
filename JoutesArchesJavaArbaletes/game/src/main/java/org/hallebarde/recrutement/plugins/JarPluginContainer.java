package org.hallebarde.recrutement.plugins;

import org.hallebarde.recrutement.RecrutementGameLauncher;
import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.PluginMetadata;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;

public class JarPluginContainer extends PluginContainer {

    private final URL url;
    private boolean loaded;

    public JarPluginContainer(PluginMetadata metadata, URL url) {
        super(metadata);
        this.url = url;
        this.loaded = false;
    }

    public JarPluginContainer(PluginMetadata metadata, URL url, boolean loaded) {
        super(metadata);
        this.url = url;
        this.loaded = loaded;
    }

    public void load(Game game) throws CorruptedPluginException, PluginLoadingException {
        URLClassLoader loader = new URLClassLoader(new URL[] {this.url}, this.getClass().getClassLoader());
        this.load(game, loader);
        this.loaded = true;
    }

    @Serial
    private Object writeReplace() throws ObjectStreamException {
        if (this.plugin instanceof Serializable) {
            return this;
        }
        PluginLoader.LOGGER.warn("Serializing a plugin that does not support serialization, things may get unstable!");
        PluginLoader.LOGGER.warn("Offender: " + this.metadata);
        PluginLoader.LOGGER.warn("Please implement serialization if you wish to use plugin dumps.");
        return new JarPluginContainer(this.metadata, this.url, this.loaded);
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (this.loaded && this.plugin == null) {
            try {
                PluginLoader.LOGGER.warn("Deserializing a plugin that does not support serialization, loading it manually!");
                PluginLoader.LOGGER.warn("Offender: " + this.metadata);
                PluginLoader.LOGGER.warn("Please implement serialization in your plugins if you wish to use plugin dumps.");
                this.load(RecrutementGameLauncher.getGame());
            } catch (PluginLoadingException e) {
                throw new IllegalStateException("Failed to re-load a plugin after deserialization!", e);
            }
        }
    }

}
