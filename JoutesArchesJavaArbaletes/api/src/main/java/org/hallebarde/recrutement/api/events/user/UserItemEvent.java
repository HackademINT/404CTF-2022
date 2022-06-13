package org.hallebarde.recrutement.api.events.user;

import org.hallebarde.recrutement.api.gameplay.Item;
import org.hallebarde.recrutement.api.gameplay.user.User;

/**
 * A generic cancelable event used whenever a user interacts with an item.
 */
public abstract class UserItemEvent extends CancelableUserEvent {

    private final Item item;

    public UserItemEvent(User user, Item item) {
        super(user);
        this.item = item;
    }

    /**
     * @return the item involved in the event
     */
    public Item getItem() {
        return item;
    }

}
