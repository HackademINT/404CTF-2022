package org.hallebarde.recrutement.api.events.world;

import org.hallebarde.recrutement.api.events.Event;
import org.hallebarde.recrutement.api.gameplay.world.World;

/**
 * An abstract event for events that hold a world object and cannot be cancelled.
 */
public abstract class WorldEvent extends Event {

    private final World world;

    public WorldEvent(World world) {
        this.world = world;
    }

    public World getWorld() {
        return this.world;
    }

}
