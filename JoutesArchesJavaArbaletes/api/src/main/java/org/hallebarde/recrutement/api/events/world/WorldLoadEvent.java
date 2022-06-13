package org.hallebarde.recrutement.api.events.world;

import org.hallebarde.recrutement.api.annotations.DoNotFire;
import org.hallebarde.recrutement.api.gameplay.world.World;

import java.io.File;

/**
 * This class simply exists to group together {@link Pre} and {@link Post}
 */
public final class WorldLoadEvent {

    /**
     * This event is fired before the world is loaded.
     * Cancelling this will cancel the loading process,
     * and let you be responsible for setting up the world and make it usable,
     * without what the server will crash.
     * A valid world as at least one room, which is its spawn room.
     */
    @DoNotFire
    public static class Pre extends CancelableWorldEvent {

        private File directory;

        public Pre(World world, File worldDirectory) {
            super(world);
            this.directory = worldDirectory;
        }

        public File getWorldDirectory() {
            return this.directory;
        }

        public void setWorldDirectory(File directory) {
            this.directory = directory;
        }
    }

    /**
     * This event is fired after the world has loaded.
     * You can use it to apply further modifications to the world programmatically.
     * It is encouraged to restrain from using this event to apply modifications that could be
     * implemented in the world configuration directory,
     * to minimize the risk of plugin incompatibilities.
     */
    @DoNotFire
    public static class Post extends WorldEvent {

        public Post(World world) {
            super(world);
        }

    }

    private WorldLoadEvent() {
        throw new IllegalStateException(this.getClass() + " should not be instantiated");
    }

}
