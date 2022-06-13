package org.hallebarde.recrutement.api.events.user;

import org.hallebarde.recrutement.api.annotations.FreeToFire;
import org.hallebarde.recrutement.api.events.CancelableEvent;
import org.hallebarde.recrutement.api.events.HandlerPriority;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.world.RoomInteraction;

/**
 * Fired when a user interacts with something in a room.
 * You can fire this event yourself to simulate such an interaction.
 * The interaction happens with a priority of {@link HandlerPriority#NORMAL},
 * so changing the event with equivalent or lower priorities might not work.
 */
@FreeToFire
public class RoomInteractionEvent extends CancelableEvent {

    private final User user;
    private final Room room;
    private final RoomInteraction interaction;
    private String[] args;

    public RoomInteractionEvent(User user, Room room, RoomInteraction interaction, String... args) {
        this.user = user;
        this.room = room;
        this.interaction = interaction;
        this.args = args;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public RoomInteraction getInteraction() {
        return interaction;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String... args) {
        this.args = args;
    }
}
