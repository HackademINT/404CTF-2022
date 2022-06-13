package org.hallebarde.recrutement.api.events;

import org.hallebarde.recrutement.api.annotations.FreeToImplement;
import org.hallebarde.recrutement.api.Game;

/**
 * Events are fired at specific steps of running the game server,
 * so plugins can react and run their own code when needed.
 * 
 * Plugins can also add their own events by extending this class with their own implementation.
 * 
 * Events are fired by calling {@link Game#fireEvent(Event)},
 * and are intercepted by handlers that have been registered using {@link Game#registerEventHandler(Object)}.
 *
 * @see Game#fireEvent(Event)
 * @see Game#registerEventHandler(Object)
 * @see SubscribeEvent
 */
@FreeToImplement
public abstract class Event {
}
