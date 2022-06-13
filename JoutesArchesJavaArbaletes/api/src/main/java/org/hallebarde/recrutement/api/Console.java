package org.hallebarde.recrutement.api;

import org.hallebarde.recrutement.api.annotations.DoNotImplement;
import org.hallebarde.recrutement.api.commands.CommandExecutor;

/**
 * An administration console.
 * Usually, this is just the terminal that started the game.
 */
@DoNotImplement
public interface Console extends CommandExecutor {

}
