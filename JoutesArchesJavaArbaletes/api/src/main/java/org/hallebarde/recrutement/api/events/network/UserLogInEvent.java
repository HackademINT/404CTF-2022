package org.hallebarde.recrutement.api.events.network;

import org.hallebarde.recrutement.api.annotations.DoNotFire;
import org.hallebarde.recrutement.api.events.CancelableEvent;
import org.hallebarde.recrutement.api.network.UserConnection;

/**
 * This event is fired when a user is trying to log in with a given username.
 *
 * Cancelling this event will restart the login process, unless the connection is closed.
 */
@DoNotFire
public class UserLogInEvent extends CancelableEvent {

    private final String userName;
    private final UserConnection connection;

    public UserLogInEvent(String userName, UserConnection connection) {
        this.userName = userName;
        this.connection = connection;
    }

    public String getUserName() {
        return userName;
    }

    public UserConnection getConnection() {
        return connection;
    }

}
