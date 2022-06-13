package org.hallebarde.recrutement.api.events.user;

import org.hallebarde.recrutement.api.events.CancelableEvent;
import org.hallebarde.recrutement.api.gameplay.user.User;

/**
 * An abstract class from which cancelable events triggered by user actions inherit.
 */
public abstract class CancelableUserEvent extends CancelableEvent {

    private final User user;

    public CancelableUserEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
