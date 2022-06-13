package org.hallebarde.recrutement.api.events.network;

import org.hallebarde.recrutement.api.annotations.FreeToFire;
import org.hallebarde.recrutement.api.events.CancelableEvent;
import org.hallebarde.recrutement.api.network.UserConnection;

/**
 * Fired when a user attempts to connect to the game server.
 *
 * Cancelling this will prevent the user from connecting.
 *
 * Firing this event with a custom connection will start the login process for a new user, with the underlying connection.
 */
@FreeToFire
public class UserConnectEvent extends CancelableEvent {

    private final UserConnection connection;

    public UserConnectEvent(UserConnection connection) {
        this.connection = connection;
    }

    public UserConnection getConnection() {
        return this.connection;
    }

}
