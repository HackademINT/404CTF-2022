package org.hallebarde.recrutement.storage;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.hallebarde.recrutement.api.storage.NoSuchOptionException;
import org.hallebarde.recrutement.api.storage.Options;
import org.hallebarde.recrutement.util.StringUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.gson.stream.JsonToken.END_OBJECT;

@JsonAdapter(JsonOptions.OptionsJsonAdapter.class)
public class JsonOptions extends OptionImplementation implements Options {

    private final Map<String, OptionImplementation> options = new HashMap<>();

    @Override
    public boolean getBoolean(String key) throws NoSuchOptionException, ClassCastException {
        return this.getExisting(key).asBoolean();
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return this.options.computeIfAbsent(key, k -> new BooleanOption(defaultValue)).asBoolean();
    }

    @Override
    public int getInteger(String key) throws NoSuchOptionException, ClassCastException {
        return this.getExisting(key).asInt();
    }

    @Override
    public int getInteger(String key, int defaultValue) {
        return this.options.computeIfAbsent(key, k -> new NumberOption(defaultValue)).asInt();
    }

    @Override
    public float getFloat(String key) throws NoSuchOptionException, ClassCastException {
        return this.getExisting(key).asFloat();
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return this.options.computeIfAbsent(key, k -> new NumberOption(defaultValue)).asFloat();
    }

    @Override
    public long getLong(String key) throws NoSuchOptionException, ClassCastException {
        return this.getExisting(key).asLong();
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return this.options.computeIfAbsent(key, k -> new NumberOption(defaultValue)).asLong();
    }

    @Override
    public double getDouble(String key) throws NoSuchOptionException, ClassCastException {
        return this.getExisting(key).asDouble();
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        return this.options.computeIfAbsent(key, k -> new NumberOption(defaultValue)).asDouble();
    }

    @Override
    public String getString(String key) throws NoSuchOptionException, ClassCastException {
        return this.getExisting(key).toString();
    }

    @Override
    public String getString(String key, String defaultValue) {
        return this.options.computeIfAbsent(key, k -> new StringOption(defaultValue)).toString();
    }

    @Override
    public Options getExistingCategory(String key) throws NoSuchOptionException, ClassCastException {
        return this.getExisting(key).asOptions();
    }

    @Override
    public Options getCategory(String key) {
        return this.options.computeIfAbsent(key, k -> new JsonOptions()).asOptions();
    }

    @Override
    Options asOptions() throws ClassCastException {
        return this;
    }

    private OptionImplementation getExisting(String key) throws NoSuchOptionException {
        OptionImplementation option = this.options.get(key);
        if (option == null) {
            if (this.options.size() < 100) {
                String best = "";
                int bestLength = 0;
                for (String existingKey: this.options.keySet()) {
                    int length = StringUtil.longestCommonSubscript(existingKey, key).length();
                    if (length > bestLength) {
                        best = existingKey;
                        bestLength = length;
                    }
                }
                if (bestLength > best.length() / 5) throw new NoSuchOptionException(key, best);
            }
            throw new NoSuchOptionException(key);
        }
        return option;
    }

    @Override
    void writeJson(JsonWriter out) throws IOException {
        out.beginObject();
        for (Map.Entry<String, OptionImplementation> option: this.options.entrySet()) {
            out.name(option.getKey());
            option.getValue().writeJson(out);
        }
        out.endObject();
    }

    public static class OptionsJsonAdapter extends TypeAdapter<JsonOptions> {

        @Override
        public void write(JsonWriter out, JsonOptions value) throws IOException {
            value.writeJson(out);
        }

        @Override
        public JsonOptions read(JsonReader in) throws IOException {
            in.beginObject();
            JsonOptions options = new JsonOptions();
            while (in.peek() != END_OBJECT) {
                String name = in.nextName();
                OptionImplementation option = switch (in.peek()) {
                    case BOOLEAN -> new BooleanOption(in.nextBoolean());
                    case NUMBER -> new NumberOption(in.nextDouble());
                    case STRING -> new StringOption(in.nextString());
                    case BEGIN_OBJECT -> this.read(in);
                    default -> throw new IOException();
                };
                options.options.put(name, option);
            }
            in.endObject();
            return options;
        }
    }

}
