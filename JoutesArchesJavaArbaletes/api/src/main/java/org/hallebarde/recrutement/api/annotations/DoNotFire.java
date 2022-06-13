package org.hallebarde.recrutement.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated event should not be fired by plugins but only subscribed to.
 * Firing the event is unsupported (meaning things will crash if you try it).
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface DoNotFire {
}
