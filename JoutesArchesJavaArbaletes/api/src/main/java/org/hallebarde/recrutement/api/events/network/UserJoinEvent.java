package org.hallebarde.recrutement.api.events.network;

import org.hallebarde.recrutement.api.annotations.DoNotFire;
import org.hallebarde.recrutement.api.events.Event;
import org.hallebarde.recrutement.api.gameplay.user.User;

/**
 * This event is fired once a user has joined the server.
 */
@DoNotFire
public class UserJoinEvent extends Event {

    private final User user;

    public UserJoinEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
