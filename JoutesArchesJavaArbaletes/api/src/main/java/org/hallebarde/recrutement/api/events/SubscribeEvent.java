package org.hallebarde.recrutement.api.events;

import org.hallebarde.recrutement.api.Game;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks an object's method as subscribing to an event.
 * Such a method must be public, non-static, and non-abstract,
 * and accept a single argument that must extend {@link  Event}.
 *
 * If the object is then registered as an event handler using {@link Game#registerEventHandler(Object)},
 * it will be called whenever an {@link Event} with exactly the same type as the method's parameter is fired,
 * with that {@link Event} as parameter.
 * 
 * @see Event
 * @see Game#registerEventHandler(Object)
 * @see Game#fireEvent(Event)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubscribeEvent {

    HandlerPriority value() default HandlerPriority.NORMAL;

}
