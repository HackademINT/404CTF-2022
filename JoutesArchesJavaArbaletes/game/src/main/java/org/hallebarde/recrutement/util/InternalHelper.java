package org.hallebarde.recrutement.util;

import org.hallebarde.recrutement.RecrutementGame;
import org.hallebarde.recrutement.GameConsole;
import org.hallebarde.recrutement.api.Console;
import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.gameplay.user.Player;
import org.hallebarde.recrutement.gameplay.world.GameRoom;
import org.hallebarde.recrutement.api.gameplay.world.Room;

public final class InternalHelper {

    public static RecrutementGame getInternalGame(Game game) {
        if (game instanceof RecrutementGame internalGame) return internalGame;
        throw new IllegalStateException("Illegal Game implementation");
    }

    public static Player getInternalPlayer(User user) {
        if (user instanceof Player player) return player;
        throw new IllegalStateException("Illegal User implementation");
    }

    public static GameRoom getInternalRoom(Room room) {
        if (room instanceof GameRoom gameRoom) return gameRoom;
        throw new IllegalStateException("Illegal room implementation: " + room.getClass());
    }

    public static GameConsole getInternalConsole(Console console) {
        if (console instanceof GameConsole gameConsole) return gameConsole;
        throw new IllegalStateException("Illegal console implementation " + console.getClass());
    }

    private InternalHelper() {
        throw new IllegalStateException("Cannot instantiate class");
    }

}
