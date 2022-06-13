package org.hallebarde.recrutement.plugins;

import org.hallebarde.recrutement.RecrutementGame;
import org.hallebarde.recrutement.api.PluginMetadata;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarFile;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PluginLoaderTest {

    @Test
    public void testMetadataLoading() throws URISyntaxException, IOException {
        URL pluginResource =  this.getClass().getResource("plugin.jar");
        assertNotNull(pluginResource);
        File pluginFile = new File(pluginResource.toURI());
        JarFile jar = new JarFile(pluginFile);
        PluginLoader loader = new PluginLoader(new RecrutementGame());
        PluginMetadata metadata = loader.readJarPluginMetadata(jar);
    }

}
