package org.hallebarde.recrutement.events;

import org.hallebarde.recrutement.api.events.Event;
import org.hallebarde.recrutement.api.events.HandlerPriority;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public record EventHandler(Method method, Object object, HandlerPriority priority) implements Comparable<EventHandler> {

    public void invoke(Event event) {
        try {
            EventBus.LOGGER.trace("Firing event {} to handler {} in class {}", event.getClass(), this.method, this.object.getClass());
            this.method.invoke(this.object, event);
        } catch (InvocationTargetException | IllegalAccessException e) {
            EventBus.LOGGER.error("Exception when invoking event handler");
            EventBus.LOGGER.catching(e);
        }
    }

    @Override
    public int compareTo(EventHandler o) {
        return this.priority.compareTo(o.priority);
    }

}
