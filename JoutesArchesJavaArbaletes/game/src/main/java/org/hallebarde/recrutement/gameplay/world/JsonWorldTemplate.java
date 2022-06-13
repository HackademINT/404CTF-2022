package org.hallebarde.recrutement.gameplay.world;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.world.RoomInteraction;
import org.hallebarde.recrutement.api.gameplay.world.World;
import org.hallebarde.recrutement.api.storage.Options;
import org.hallebarde.recrutement.api.storage.Registry;
import org.hallebarde.recrutement.util.IoUtil;
import org.hallebarde.recrutement.util.StringUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hallebarde.recrutement.util.InternalHelper.getInternalRoom;

public class JsonWorldTemplate {

    private static final Logger LOGGER = LogManager.getLogger("World loader");
    private static final String ROOM_DIRECTORY = "rooms";
    private static final String WORLD_JSON_FILE = "world.json";

    private final File directory;
    private final File worldFile;
    private final File roomDirectory;

    private final Registry<Item> itemRegistry;
    private final Registry<RoomInteraction> interactionRegistry;

    public JsonWorldTemplate(File directory, Registry<Item> itemRegistry, Registry<RoomInteraction> interactionRegistry) {
        this.directory = directory;
        this.worldFile = this.directory.toPath().resolve(WORLD_JSON_FILE).toFile();
        this.roomDirectory = this.directory.toPath().resolve(ROOM_DIRECTORY).toFile();
        this.itemRegistry = itemRegistry;
        this.interactionRegistry = interactionRegistry;
    }

    public void load(World world) throws IOException {
        if (!this.directory.exists()) {
            this.generateDefaults();
        }
        this.loadFromFiles(world);
    }

    private void loadFromFiles(World world) throws IOException{
        JsonWorld jsonWorld = IoUtil.loadJsonFile(this.worldFile, JsonWorld.class);
        Map<String, JsonRoom> jsonRooms = this.loadRooms();
        for (Map.Entry<String, JsonRoom> entry: jsonRooms.entrySet()) {
            Room room = world.getRoom(entry.getKey());
            room.setName(entry.getValue().name);
            room.setDescription(entry.getValue().description);
        }
        for (Map.Entry<String, JsonRoom> entry: jsonRooms.entrySet()) {
            GameRoom room = getInternalRoom(world.getRoom(entry.getKey()));
            for (String neighbourId: entry.getValue().neighbors) {
                Room neighbour = world.getExistingRoom(neighbourId);
                if (neighbour == null) {
                    LOGGER.warn("Room {} contains a reference to a neighbour that does not exist ({}), " +
                            "it will be ignored", entry.getKey(), neighbourId);
                    continue;
                }
                room.connectOneWay(neighbour);
            }
            List<JsonSerializedRegistryObject> items = entry.getValue().items;
            if (items == null) {
                LOGGER.warn("Room {} is missing an item list", entry.getKey());
            } else {
                for (JsonSerializedRegistryObject serializedItem: entry.getValue().items) {
                    Item item = this.itemRegistry.instantiate(serializedItem.type, serializedItem.options);
                    if (item == null) {
                        LOGGER.warn("Failed to instantiate item for type {}.", serializedItem.type);
                        continue;
                    }
                    room.items.add(item);
                }
            }
            List<JsonSerializedRegistryObject> interactions = entry.getValue().interactions;
            if (interactions == null) {
                LOGGER.warn("Room {} is missing an interaction list", entry.getKey());
            } else {
                for (JsonSerializedRegistryObject serializedInteraction: interactions) {
                    RoomInteraction interaction = this.interactionRegistry.instantiate(serializedInteraction.type, serializedInteraction.options);
                    if (interaction == null) {
                        LOGGER.warn("Failed to instantiate interaction for type {}", serializedInteraction.type);
                        continue;
                    }
                    room.interactions.add(interaction);
                }
            }
        }
        Room spawn = world.getExistingRoom(jsonWorld.spawn);
        if (spawn != null) world.setSpawnRoom(spawn);
    }

    private void generateDefaults() throws IOException{
        if (!this.roomDirectory.mkdirs()) throw new IOException("Did not create room directory");
        IoUtil.writeResourceToFile(this.getClass(),"world_default.json", this.directory.toPath().resolve("world.json").toFile());
        IoUtil.writeResourceToFile(this.getClass(),"room_default.json", this.roomDirectory.toPath().resolve("spawn.json").toFile());
    }

    private Map<String, JsonRoom> loadRooms() throws IOException {
        if (!this.roomDirectory.isDirectory()) throw new IOException(String.format("%s is not a directory", this.roomDirectory.getAbsolutePath()));
        Map<String, JsonRoom> rooms = new HashMap<>();
        for (Path path: Files.newDirectoryStream(this.roomDirectory.toPath())) {
            File file = path.toFile();
            String fileName = file.getName();
            if (!file.isFile()) {
                LOGGER.warn("Skipping non regular file {} in world room directory", fileName);
                continue;
            }
            if (!fileName.endsWith(".json")) {
                LOGGER.warn("Skipping non json file {} in world room directory." +
                        " If this is indeed a json file, make sure it has the proper extension " +
                        "(.json)", fileName);
                continue;
            }
            String roomId = fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            if (!StringUtil.isValidId(roomId)) {
                LOGGER.warn("Skipping file {} in world room directory because the name is not a valid room id", fileName);
                continue;
            }
            JsonRoom room = IoUtil.loadJsonFile(file, JsonRoom.class);
            rooms.put(roomId, room);
        }
        return rooms;
    }

    private static final class JsonWorld {
        String spawn;
    }

    private static final class JsonRoom {
        // The room id is in the file name
        String name;
        String description;
        List<String> neighbors;
        List<JsonSerializedRegistryObject> items;
        List<JsonSerializedRegistryObject> interactions;
    }

    private static final class JsonSerializedRegistryObject {
        String type;
        Options options;
    }

}
