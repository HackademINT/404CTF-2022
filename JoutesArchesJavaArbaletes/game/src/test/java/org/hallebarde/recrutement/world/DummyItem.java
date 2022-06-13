package org.hallebarde.recrutement.world;

import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.storage.Options;

public class DummyItem implements Item {

    public String name;
    public String desc;

    public DummyItem(Options options) {
        this.name = options.getString("name", "DummyItem");
        this.desc = options.getString("description", "This is a dummy item");
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String description() {
        return this.desc;
    }

    @Override
    public void use(User user, String... args) {

    }

}