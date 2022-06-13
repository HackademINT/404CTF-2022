package org.hallebarde.recrutement.plugins;

import org.hallebarde.recrutement.RecrutementGameLauncher;
import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.Plugin;
import org.hallebarde.recrutement.api.PluginMetadata;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

public abstract class PluginContainer implements Comparable<PluginContainer>, Serializable {

    protected static final Class<Plugin> PLUGIN_CLASS = Plugin.class;

    protected final PluginMetadata metadata;
    protected Plugin plugin;

    public PluginContainer(PluginMetadata metadata) {
        this.metadata = metadata;
    }

    public abstract void load(Game game) throws CorruptedPluginException, PluginLoadingException;

    protected void load(Game game, ClassLoader loader) throws CorruptedPluginException, PluginLoadingException {
        try {
            Class<?> clazz = loader.loadClass(this.metadata.mainClass());
            if (!PLUGIN_CLASS.isAssignableFrom(clazz)) throw new CorruptedPluginException("Class " + clazz + " does not properly implement the plugin interface");
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            this.plugin = (Plugin) constructor.newInstance();
            this.plugin.onLoad(game, this.metadata, Logger.getLogger(this.metadata.id()));
        } catch (ClassNotFoundException e){
            throw new CorruptedPluginException("Wrong main plugin class: " + this.metadata.mainClass());
        } catch(NoSuchMethodException e) {
            throw new CorruptedPluginException("Main plugin class " + this.metadata.mainClass() + "does not have an empty constructor and cannot be instantiated");
        } catch (InstantiationException e) {
            throw new CorruptedPluginException("Main plugin class " + this.metadata.mainClass() + " cannot be instantiated");
        } catch (Throwable t) {
            throw new PluginLoadingException("Encountered an exception when instantiating plugin", t);
        }
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public PluginMetadata getMetadata() {
        return this.metadata;
    }

    @Override
    public int compareTo(PluginContainer o) {
        return this.getMetadata().id().compareTo(o.getMetadata().id());
    }

}
