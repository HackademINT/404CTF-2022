package org.hallebarde.recrutement.plugins;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.hallebarde.recrutement.api.PluginMetadata;

import java.io.IOException;

public final class PluginMetadataTypeAdapter extends TypeAdapter<PluginMetadata> {

    @Override
    public void write(JsonWriter out, PluginMetadata value) throws IOException {
        out.beginObject();
        out.name("pluginId").value(value.id());
        out.name("mainClass").value(value.mainClass());
        out.name("version").value(value.version());
        out.endObject();
    }

    @Override
    public PluginMetadata read(JsonReader in) throws IOException {
        String id = null;
        String mainClass = null;
        String version = null;
        in.beginObject();
        while (in.peek() != JsonToken.END_OBJECT) {
            String name = in.nextName();
            switch (name) {
                case "pluginId" -> id = in.nextString();
                case "mainClass" -> mainClass = in.nextString();
                case "version" -> version = in.nextString();
                default -> {
                    PluginLoader.LOGGER.warn("Unknown plugin metadata json property: " + name);
                    in.skipValue();
                }
            }
        }
        if (id == null) throw new CorruptedPluginException("Plugin metadata JSON is missing an id");
        if (mainClass == null) throw new CorruptedPluginException("Plugin metadata JSON is missing a main class");
        if (version == null) {
            PluginLoader.LOGGER.warn("Plugin metadata JSON is missing a version, will be assuming 1.0.0. Id: " + id);
            version = "1.0.0";
        }
        in.endObject();
        return new PluginMetadata(id, mainClass, version);
    }
}