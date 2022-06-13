package org.hallebarde.recrutement.api.gameplay.world;

import org.hallebarde.recrutement.api.gameplay.user.User;

/**
 * Represents something a {@link User} can interaction with inside a {@link Room}.
 */
public interface RoomInteraction {

    /**
     * @return the name of the interaction
     */
    String name();

    /**
     * @return the description of the interaction
     */
    String description();

    /**
     * Called when a {@link User} interacts with this interaction.
     *
     * @param user  the user performing the interaction
     * @param room  the room the interaction is in
     * @param args  the arguments passed by the {@link User}
     */
    void interact(User user, Room room, String... args);

}
