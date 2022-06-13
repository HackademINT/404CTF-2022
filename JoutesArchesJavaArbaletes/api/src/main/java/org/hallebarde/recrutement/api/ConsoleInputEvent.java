package org.hallebarde.recrutement.api;

import org.hallebarde.recrutement.api.annotations.FreeToFire;
import org.hallebarde.recrutement.api.events.CancelableEvent;

@FreeToFire
public class ConsoleInputEvent extends CancelableEvent {

    private String text;

    public ConsoleInputEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
