package org.hallebarde.npcsplugin;

import org.hallebarde.recrutement.api.annotations.FreeToImplement;
import org.hallebarde.recrutement.api.gameplay.user.User;

/**
 * An AI that can hold a back and forth conversation.
 */
@FreeToImplement
public interface ConversationAI {

    /**
     * Responds to a user message.
     *
     * @param from      the user to respond to
     * @param message   the message the user sent
     * @return the response to send them back
     */
    String respondTo(User from, String message);

    /**
     * Gets a message to send to a user when starting a conversation with them.
     *
     * @param from  the user to greet
     * @return the message to send
     */
    String greet(User from);

    /**
     * Indicates whether a conversation is over.
     *
     * @param user   the user we are talking to
     * @return whether we want to keep the conversation going
     */
    boolean hasMoreToSay(User user);

}
