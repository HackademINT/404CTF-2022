package org.hallebarde.recrutement.config;

import org.hallebarde.recrutement.api.GameConfig;

public class SystemPropertyConfig implements GameConfig {

    private String hostname;
    private int port;
    private String motd;
    private String pluginDir;
    private String worldDir;
    private boolean debug;

    @Override
    public String hostname() {
        return this.hostname;
    }

    @Override
    public int port() {
        return this.port;
    }

    @Override
    public String motd() {
        return this.motd;
    }

    @Override
    public String pluginDirectory() {
        return this.pluginDir;
    }

    @Override
    public String worldDirectory() {
        return this.worldDir;
    }

    @Override
    public boolean debug() {
        return this.debug;
    }

    @Override
    public void reload() {
        this.hostname = System.getProperty("game.host", "localhost");
        this.port = Integer.parseInt(System.getProperty("game.port", "1337"));
        this.motd = System.getProperty("game.motd", "Welcome!");
        this.pluginDir = System.getProperty("game.plugin_directory", "plugins");
        this.worldDir = System.getProperty("game.world_directory", "world");
        this.debug = Boolean.parseBoolean(System.getProperty("game.debug", "false"));
    }

}
