package org.hallebarde.doorsnkeys;

import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.storage.Options;

public class KeyItem implements Item {

    private final String doorPassword;
    private final String description;

    public KeyItem(Options options) {
        this.doorPassword = options.getString("door_password", "");
        this.description = options.getString("description", "A generic key. What does it open ?");
    }

    @Override
    public String name() {
        return "Key";
    }

    @Override
    public String description() {
        return this.description;
    }

    public String getDoorPassword() {
        return doorPassword;
    }

    @Override
    public void use(User user, String... args) {
        DoorsAndKeysPlugin.logger.info("Looks like this may be used to open a door. Try interacting with one.");
    }

}
