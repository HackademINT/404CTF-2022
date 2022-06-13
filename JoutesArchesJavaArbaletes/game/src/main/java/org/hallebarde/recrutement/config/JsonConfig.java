package org.hallebarde.recrutement.config;

import org.hallebarde.recrutement.RecrutementGame;
import org.hallebarde.recrutement.api.GameConfig;
import org.hallebarde.recrutement.util.IoUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonConfig implements GameConfig {

    private final File file;
    private ConfigFile parsedFile;

    public JsonConfig(File file) {
        this.file = file;
        this.parsedFile = new ConfigFile();
    }

    @Override
    public String hostname() {
        return this.parsedFile.hostname;
    }

    @Override
    public int port() {
        return this.parsedFile.port;
    }

    @Override
    public String motd() {
        return this.parsedFile.motd;
    }

    @Override
    public String pluginDirectory() {
        return this.parsedFile.pluginDirectory;
    }

    @Override
    public String worldDirectory() {
        return this.parsedFile.worldDirectory;
    }

    @Override
    public boolean debug() {
        return this.parsedFile.debug;
    }

    @Override
    public void reload() throws IOException {
        if (this.file.createNewFile()) {
            RecrutementGame.LOGGER.info("{} did not exist and was created with default values", file.getAbsolutePath());
            String json = RecrutementGame.GSON.toJson(this.parsedFile);
            try (OutputStream stream = new BufferedOutputStream(new FileOutputStream(this.file))) {
                stream.write(json.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            this.parsedFile = IoUtil.loadJsonFile(this.file, ConfigFile.class);
        }
    }

    private static class ConfigFile {
        String hostname = "0.0.0.0";
        int port = 1337;
        String motd = "Welcome";
        String pluginDirectory = "plugins";
        String worldDirectory = "world";
        boolean debug = false;
    }

}
