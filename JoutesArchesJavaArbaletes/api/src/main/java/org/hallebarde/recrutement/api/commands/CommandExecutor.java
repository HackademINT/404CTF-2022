package org.hallebarde.recrutement.api.commands;

import org.hallebarde.recrutement.api.annotations.DoNotImplement;
import org.hallebarde.recrutement.api.annotations.FreeToCall;

import java.util.concurrent.CompletableFuture;

/**
 * Something that can execute a command (either a user or the console).
 *
 * @see Command
 */
@DoNotImplement
public interface CommandExecutor {

    /**
     * Sends a message to the executors.
     *
     * @param message - the message to send.
     * @return a completable future that will be completed once the message has been sent
     */
    @FreeToCall
    CompletableFuture<Void> sendMessage(String message);

}
