package org.hallebarde.recrutement.api.events;

/**
 * The priority an event subscribing method has over others.
 * The behavior is undefined in case of two subscribers with the same priority,
 * and the order they will be called relative to each other will be random.
 *
 * @see SubscribeEvent
 */
public enum HandlerPriority {

    HIGHEST, HIGHER, NORMAL, LOWER, LOWEST

}
