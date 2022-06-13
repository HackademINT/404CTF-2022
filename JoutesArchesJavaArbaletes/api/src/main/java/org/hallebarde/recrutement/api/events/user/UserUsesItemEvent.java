package org.hallebarde.recrutement.api.events.user;

import org.hallebarde.recrutement.api.annotations.FreeToFire;
import org.hallebarde.recrutement.api.events.HandlerPriority;
import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.user.User;

/**
 * Fired when a user uses an item.
 * You can fire this event yourself to trigger the use of an item by a user,
 * even if said user do not have said item in their inventory.
 *
 * The actual use of the event happens with priority {@link HandlerPriority#NORMAL},
 * so changing the event in handlers that have the same priority or lower might have no effect.
 */
@FreeToFire
public final class UserUsesItemEvent extends UserItemEvent {

    private String[] args;

    public UserUsesItemEvent(User user, Item item, String... args) {
        super(user, item);
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String... args) {
        this.args = args;
    }

}
