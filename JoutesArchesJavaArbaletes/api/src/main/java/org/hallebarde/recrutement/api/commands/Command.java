package org.hallebarde.recrutement.api.commands;

import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.annotations.FreeToImplement;

/**
 * Implement this class to add custom game commands.
 */
@FreeToImplement
public interface Command {

    /**
     * Executes this command.
     *
     * @param game          the game this command is being executed by
     * @param executor      whoever is using the command
     * @param arguments     the arguments passed to the command
     */
    void execute(Game game, CommandExecutor executor, String... arguments);

}
