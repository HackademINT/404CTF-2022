package org.hallebarde.recrutement.config;

import org.hallebarde.recrutement.util.IoUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class JsonConfigTest {

    @Test
    public void canCreateNewFileWithDefaultValues() throws IOException {
        Path directory = Files.createTempDirectory("config-unittest");
        directory.toFile().deleteOnExit();
        File file = directory.resolve("config.json").toFile();
        JsonConfig config = new JsonConfig(file);
        config.reload();
        assertEquals("0.0.0.0", config.hostname());
        assertEquals(1337, config.port());
        assertEquals("Welcome", config.motd());
        assertEquals("plugins", config.pluginDirectory());
        assertEquals("world", config.worldDirectory());
        assertFalse(config.debug());
    }

    @Test
    public void canReadExistingFile() throws IOException {
        File file = Files.createTempFile("config-unittest", ".json").toFile();
        file.deleteOnExit();
        IoUtil.writeResourceToFile(this.getClass(), "test_config.json", file);

        JsonConfig config = new JsonConfig(file);
        config.reload();
        assertEquals("127.0.0.1", config.hostname());
        assertEquals(4444, config.port());
        assertEquals("Bienvenue", config.motd());
        assertEquals("Plugins", config.pluginDirectory());
        assertEquals("World", config.worldDirectory());
        assertTrue(config.debug());
    }

}
