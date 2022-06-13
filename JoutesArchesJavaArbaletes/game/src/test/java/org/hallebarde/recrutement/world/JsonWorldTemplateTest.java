package org.hallebarde.recrutement.world;

import org.hallebarde.recrutement.api.gameplay.world.RoomInteraction;
import org.hallebarde.recrutement.storage.RegistryImplementation;
import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.storage.Registry;
import org.hallebarde.recrutement.gameplay.world.GameWorld;
import org.hallebarde.recrutement.gameplay.world.JsonWorldTemplate;
import org.hallebarde.recrutement.util.IoUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static java.nio.file.Files.createTempDirectory;
import static org.junit.jupiter.api.Assertions.*;

public class JsonWorldTemplateTest {

    @Test
    public void canLoadWorldFromJsonFiles() throws IOException {
        Path worldPath = createTempDirectory("world");
        Path roomsPath = worldPath.resolve("rooms");
        assertTrue(roomsPath.toFile().mkdir());
        IoUtil.writeResourceToFile(this.getClass(), "world.json", worldPath.resolve("world.json").toFile());
        IoUtil.writeResourceToFile(this.getClass(), "rooms/room1.json", roomsPath.resolve("room1.json").toFile());
        IoUtil.writeResourceToFile(this.getClass(), "rooms/room2.json", roomsPath.resolve("room2.json").toFile());
        IoUtil.writeResourceToFile(this.getClass(), "rooms/room3.json", roomsPath.resolve("room3.json").toFile());

        Registry<Item> itemRegistry = new RegistryImplementation<>();
        itemRegistry.put("dummy", DummyItem::new);
        Registry<RoomInteraction> interactionRegistry = new RegistryImplementation<>();
        interactionRegistry.put("dummy", DummyInteraction::new);
        JsonWorldTemplate template = new JsonWorldTemplate(worldPath.toFile(), itemRegistry, interactionRegistry);
        GameWorld world = new GameWorld(null);
        world.setFireEvents(false); // Game is null, can't fire events
        template.load(world);

        Room room1 = world.getExistingRoom("room1");
        Room room2 = world.getExistingRoom("room2");
        Room room3 = world.getExistingRoom("room3");

        assertNotNull(room1);
        assertNotNull(room2);
        assertNotNull(room3);
        assertEquals("room1", room1.id());
        assertEquals("room2", room2.id());
        assertEquals("room3", room3.id());
        assertEquals("Room 1", room1.name());
        assertEquals("Room 2", room2.name());
        assertEquals("Room 3", room3.name());
        assertEquals("Room 1 description", room1.description());
        assertEquals("Room 2 description", room2.description());
        assertEquals("Room 3 description", room3.description());
        assertSame(room1, world.getSpawnRoom());
        assertEquals(Map.of("room2", room2, "room3", room3), room1.getReachableRooms());
        assertEquals(Map.of("room1", room1), room2.getReachableRooms());
        assertEquals(Map.of(), room3.getReachableRooms());

        assertEquals(1, room1.getItemsIn().size());
        Item item = room1.getItemsIn().get(0);
        assertTrue(item instanceof DummyItem);
        assertEquals("Test Item", item.name());
        assertEquals("Test item description", item.description());

        assertEquals(2, room2.getInteractions().size());
        RoomInteraction interaction1 = room2.getInteractions().get(0);
        RoomInteraction interaction2 = room2.getInteractions().get(1);
        assertTrue(interaction1 instanceof DummyInteraction);
        assertEquals("Test Interaction 1", interaction1.name());
        assertEquals("Test Interaction 1 description", interaction1.description());
        assertTrue(interaction2 instanceof DummyInteraction);
        assertEquals("Test Interaction 2", interaction2.name());
        assertEquals("Test Interaction 2 description", interaction2.description());
    }

    @Test
    public void canGenerateDefaultWorld() throws IOException {
        Path worldPath = createTempDirectory("world-test").resolve("world");
        Path roomsPath = worldPath.resolve("rooms");
        Path worldJsonPath = worldPath.resolve("world.json");
        Path roomJsonPath = roomsPath.resolve("spawn.json");
        File worldDir = worldPath.toFile();
        worldDir.deleteOnExit();
        File roomsDir = roomsPath.toFile();
        File worldFile = worldJsonPath.toFile();
        File roomFile = roomJsonPath.toFile();

        Registry<Item> itemRegistry = new RegistryImplementation<>();
        itemRegistry.put("dummy", DummyItem::new);
        Registry<RoomInteraction> interactionRegistry = new RegistryImplementation<>();
        interactionRegistry.put("dummy", DummyInteraction::new);
        JsonWorldTemplate template = new JsonWorldTemplate(worldPath.toFile(), itemRegistry, interactionRegistry);
        GameWorld world = new GameWorld(null);
        world.setFireEvents(false); // Game is null, can't fire events
        template.load(world);

        // Files existence
        assertTrue(worldDir.exists());
        assertTrue(worldDir.isDirectory());
        assertTrue(roomsDir.exists());
        assertTrue(roomsDir.isDirectory());
        assertTrue(worldFile.exists());
        assertTrue(worldFile.isFile());
        assertTrue(roomFile.exists());
        assertTrue(roomFile.isFile());

        // File contents
        assertEquals("""
                {
                  "spawn": "spawn"
                }""", Files.readString(worldJsonPath, StandardCharsets.UTF_8));
        assertEquals("""
                {
                  "name": "Spawn room",
                  "description": "Spawn room of the default world",
                  "neighbors" : [],
                  "items": [],
                  "interactions": []
                }""", Files.readString(roomJsonPath, StandardCharsets.UTF_8));

        // Loaded world
        Map<String, Room> rooms = world.getRooms();
        assertEquals(1, rooms.size());
        Room room = rooms.get("spawn");
        assertEquals("spawn", room.id());
        assertEquals("Spawn room", room.name());
        assertEquals("Spawn room of the default world", room.description());
        assertEquals(0, room.getReachableRooms().size());
        assertEquals(0, room.getItemsIn().size());
        assertEquals(0, room.getInteractions().size());
    }

}
