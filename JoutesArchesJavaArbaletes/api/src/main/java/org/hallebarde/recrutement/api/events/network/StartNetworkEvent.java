package org.hallebarde.recrutement.api.events.network;

import org.hallebarde.recrutement.api.annotations.DoNotFire;
import org.hallebarde.recrutement.api.events.CancelableEvent;

import java.net.InetSocketAddress;

/**
 * This event is fired when the server's network system is about to start.
 *
 * Cancelling this event will prevent the system from starting, and no player will be able to join as a consequence.
 */
@DoNotFire
public class StartNetworkEvent extends CancelableEvent {

    private InetSocketAddress address;

    public StartNetworkEvent(InetSocketAddress address) {
        this.address = address;
    }

    /**
     * @return the address the server will bind to
     */
    public InetSocketAddress getAddress() {
        return address;
    }

    /**
     * Changes the address the server should bind to.
     *
     * @param address   the address to bind to
     */
    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

}
