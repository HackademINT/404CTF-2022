package org.hallebarde.recrutement.world;

import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.world.RoomInteraction;
import org.hallebarde.recrutement.api.storage.Options;

public class DummyInteraction implements RoomInteraction {

    private final String name, description;

    public DummyInteraction(Options options) {
        this.name = options.getString("name", "");
        this.description = options.getString("description", "");
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public void interact(User user, Room room, String... args) {
    }

}
