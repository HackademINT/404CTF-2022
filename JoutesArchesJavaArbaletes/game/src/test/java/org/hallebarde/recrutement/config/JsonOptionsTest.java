package org.hallebarde.recrutement.config;

import org.hallebarde.recrutement.RecrutementGame;
import org.hallebarde.recrutement.api.storage.NoSuchOptionException;
import org.hallebarde.recrutement.api.storage.Options;
import org.hallebarde.recrutement.storage.JsonOptions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonOptionsTest {

    @Test
    public void canSetupAndReadOptions() throws NoSuchOptionException {
        Options options = new JsonOptions();
        assertTrue(options.getBoolean("testBoolean", true));
        assertEquals(42, options.getInteger("testInteger", 42));
        assertEquals(69.0F, options.getFloat("testFloat", 69.0F));
        assertEquals(Integer.MAX_VALUE + 8L, options.getLong("testLong", Integer.MAX_VALUE + 8L));
        assertEquals(-78.95, options.getDouble("testDouble", -78.95));
        assertEquals("Test", options.getString("testString", "Test"));
        Options subCategory = options.getCategory("testCategory");
        subCategory.getString("testSubCategoryString", "Test sub string");
        assertTrue(options.getBoolean("testBoolean"));
        assertEquals(42, options.getInteger("testInteger"));
        assertEquals(69.0F, options.getFloat("testFloat"));
        assertEquals(Integer.MAX_VALUE + 8L, options.getLong("testLong"));
        assertEquals(-78.95, options.getDouble("testDouble"));
        assertEquals("Test", options.getString("testString"));
        Options testSubOptions = options.getExistingCategory("testCategory");
        assertEquals("Test sub string", testSubOptions.getString("testSubCategoryString"));
    }

    @Test
    public void canSaveAndReadJson() throws NoSuchOptionException {
        Options options = new JsonOptions();
        options.getBoolean("testBoolean", true);
        options.getInteger("testInteger", 42);
        options.getFloat("testFloat", 69.0F);
        options.getLong("testLong", Integer.MAX_VALUE + 8L);
        options.getDouble("testDouble", -78.95);
        options.getString("testString", "Test");
        Options subCategory = options.getCategory("testCategory");
        subCategory.getString("testSubCategoryString", "Test sub string");
        options = RecrutementGame.GSON.fromJson(RecrutementGame.GSON.toJson(options), JsonOptions.class);
        assertTrue(options.getBoolean("testBoolean"));
        assertEquals(42, options.getInteger("testInteger"));
        assertEquals(69.0F, options.getFloat("testFloat"));
        assertEquals(Integer.MAX_VALUE + 8L, options.getLong("testLong"));
        assertEquals(-78.95, options.getDouble("testDouble"));
        assertEquals("Test", options.getString("testString"));
        Options testSubOptions = options.getExistingCategory("testCategory");
        assertEquals("Test sub string", testSubOptions.getString("testSubCategoryString"));
    }

}
