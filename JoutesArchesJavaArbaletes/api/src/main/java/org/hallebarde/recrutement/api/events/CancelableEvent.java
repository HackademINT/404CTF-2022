package org.hallebarde.recrutement.api.events;

import org.hallebarde.recrutement.api.annotations.FreeToCall;
import org.hallebarde.recrutement.api.annotations.FreeToImplement;

/**
 * An event that can be cancelled.
 *
 * Cancelling an event cancels the action it was fired for.
 */
@FreeToImplement
public abstract class CancelableEvent extends Event {

    private boolean cancelled = false;

    @FreeToCall
    public final boolean isCancelled() {
        return this.cancelled;
    }

    @FreeToCall
    public final void cancel() {
        this.cancelled = true;
    }

}
