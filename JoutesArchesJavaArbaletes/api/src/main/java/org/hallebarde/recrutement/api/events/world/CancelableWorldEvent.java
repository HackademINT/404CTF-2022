package org.hallebarde.recrutement.api.events.world;

import org.hallebarde.recrutement.api.events.CancelableEvent;
import org.hallebarde.recrutement.api.gameplay.world.World;

/**
 * An abstract event for events that hold a world object and can be cancelled.
 */
public abstract class CancelableWorldEvent extends CancelableEvent {

    private final World world;

    public CancelableWorldEvent(World world) {
        this.world = world;
    }

    public World getWorld() {
        return this.world;
    }

}
