package org.hallebarde.recrutement.plugins;

import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hallebarde.recrutement.RecrutementGame;
import org.hallebarde.recrutement.api.PluginMetadata;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

public class PluginLoader {

    public static final Logger LOGGER = LogManager.getLogger("Plugin Loader");

    private final RecrutementGame game;

    private static final String PLUGIN_METADATA_FILE = "plugin.json";

    private final List<PluginContainer> containers = new ArrayList<>();

    private boolean loaded = false;

    public PluginLoader(RecrutementGame game) {
        this.game = game;
    }

    public List<PluginContainer> load() throws DuplicatePluginException {
        this.checkLoaded();
        this.loaded = true;
        this.sortPluginList();
        for (PluginContainer container: this.containers) {
            try {
                container.load(this.game);
            } catch (CorruptedPluginException | PluginLoadingException e) {
                LOGGER.error("Failed to load plugin {} ", container.getMetadata().id());
                LOGGER.catching(e);
            }
        }
        return this.containers;

    }

    private void sortPluginList() throws DuplicatePluginException{
        //TODO dependency support
        LOGGER.trace("Sorting plugin list");
        this.containers.sort(PluginContainer::compareTo);
        for (int i = 1; i < this.containers.size(); i++) {
            String currentId = this.containers.get(i).getMetadata().id();
            String previousId = this.containers.get(i - 1).getMetadata().id();
            if (currentId.equals(previousId))
                throw new DuplicatePluginException(currentId);
        }
        LOGGER.info("Effective plugin list: {}", this.containers.stream().map(p -> p.getMetadata().id()).collect(Collectors.joining(", ")));
    }

    public void exploreDirectory(File directory) throws IOException {
        this.checkLoaded();
        LOGGER.info("Exploring directory {} for plugins", directory.getAbsolutePath());
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files == null) throw new IOException("Failed to list files in directory " + directory.getAbsolutePath());
        for (File file: files) {
            try {
                this.exploreJar(file);
            } catch (IOException e) {
                LOGGER.warn("Failed to explore potential plugin file: {}", file.getAbsolutePath());
                LOGGER.catching(e);
            }
        }
        LOGGER.debug("Done exploring {} for plugins", directory.getAbsolutePath());
    }

    public void exploreClasspath() throws IOException {
        this.checkLoaded();
        try (InputStream stream = this.getClass().getResourceAsStream("/plugin.json")) {
            if (stream == null) return;
            PluginMetadata metadata = this.readMetadata(stream);
            this.containers.add(new ClasspathPluginContainer(metadata));
        }
    }

    public void exploreJar(File file) throws IOException {
        this.checkLoaded();
        LOGGER.debug("Exploring file {}", file.getAbsolutePath());
        JarFile jar = new JarFile(file);
        PluginMetadata metadata = this.readJarPluginMetadata(jar);
        if (metadata != null) {
            URL url;
            try {
                url = file.getAbsoluteFile().toURI().toURL();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            this.containers.add(new JarPluginContainer(metadata, url));
            LOGGER.info("Found plugin: {} version {}. Main class: {}", metadata.id(), metadata.version(), metadata.mainClass());
        }
    }

    public File preparePluginDirectory(String directory) throws IOException {
        File dir = new File(directory);
        if (dir.exists()) {
            if(!dir.isDirectory()) throw new IOException(directory + " exists but is not a directory");
            LOGGER.debug("Found existing plugin directory {}", dir.getAbsolutePath());
        } else {
            if (!dir.mkdirs()) throw new IOException("Failed to create plugin directory " + directory);
            LOGGER.debug("Created plugin directory {}", dir.getAbsolutePath());
        }
        if (!dir.canRead()) throw new IOException("Plugin directory " + directory + " is not readable");
        LOGGER.trace("Plugin directory is writeable");
        return dir;
    }

    protected PluginMetadata readJarPluginMetadata(JarFile jar) throws IOException {
        ZipEntry entry = jar.getEntry(PLUGIN_METADATA_FILE);
        if (entry == null) return null;
        if (entry.isDirectory()) throw new CorruptedPluginException("Found plugin metadata zip entry, but it's a directory!");
        return this.readMetadata(jar.getInputStream(entry));
    }

    private PluginMetadata readMetadata(InputStream stream) throws CorruptedPluginException {
        Reader reader = new InputStreamReader(new BufferedInputStream(stream));
        try {
            return RecrutementGame.GSON.fromJson(reader, PluginMetadata.class);
        } catch (JsonSyntaxException e) {
            throw new CorruptedPluginException("Invalid JSON", e);
        }
    }

    public void dumpPlugin(String id, ObjectOutputStream stream) throws IOException {
        for (PluginContainer container: this.containers) {
            if (container.getMetadata().id().equals(id)) {
                stream.writeObject(container);
                return;
            }
        }
    }

    public PluginContainer reloadDumpedPlugin(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        PluginContainer container = (PluginContainer) stream.readObject();
        this.containers.add(container);
        return container;
    }

    private void checkLoaded() {
        if (this.loaded) throw new IllegalStateException("Plugins have already been loaded");
    }

}
