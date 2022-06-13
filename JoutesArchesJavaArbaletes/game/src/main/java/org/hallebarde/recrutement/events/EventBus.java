package org.hallebarde.recrutement.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hallebarde.recrutement.api.events.Event;
import org.hallebarde.recrutement.api.events.HandlerPriority;
import org.hallebarde.recrutement.api.events.SubscribeEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

public class EventBus {

    public static final Logger LOGGER = LogManager.getLogger("Event Bus");

    private static final Class<SubscribeEvent> SUBSCRIBE_ANNOTATION = SubscribeEvent.class;
    private static final Class<Event> EVENT_CLASS = Event.class;
    private final Map<Class<?>, Queue<EventHandler>> handlers = new HashMap<>();

    public void registerHandler(Object object) {
        LOGGER.debug("Registering an object of class {} as an event handler", object.getClass());
        for (Method method: object.getClass().getMethods()) {
            SubscribeEvent[] annotations = method.getAnnotationsByType(SUBSCRIBE_ANNOTATION);
            if (annotations.length == 0) continue;
            if (annotations.length > 1) throw new IllegalStateException("Multiple SubscribeEvent found on the same method??");
            HandlerPriority priority = annotations[0].value();
            LOGGER.trace("Found a potential event handler subscription method {} with priority {}", method.getName(), priority);
            int modifier = method.getModifiers();
            if (Modifier.isStatic(modifier)) throw new IllegalArgumentException("Method is static");
            if (!Modifier.isPublic(modifier)) throw new IllegalArgumentException("Method is not public");
            if (Modifier.isAbstract(modifier)) throw new IllegalArgumentException("Method is abstract");
            if (!Modifier.isPublic(method.getClass().getModifiers())) throw new IllegalArgumentException("Class is not public");
            Type[] parameters = method.getParameterTypes();
            if (parameters.length != 1) throw new IllegalArgumentException("Method needs to take exactly one argument");
            if (!EVENT_CLASS.isAssignableFrom((Class<?>) parameters[0])) throw new IllegalArgumentException("Method parameter is not a subclass of Event");
            Queue<EventHandler> handlers = this.handlers.get(parameters[0]);
            if (handlers == null) {
                handlers = new PriorityQueue<>();
                this.handlers.put((Class<?>) parameters[0], handlers);
            }
            handlers.add(new EventHandler(method, object, priority));
            LOGGER.debug("Registered event handler: {} ", method.toString());
        }
    }

    public <E extends Event> E fire(E event) {
        Queue<EventHandler> queue = this.handlers.get(event.getClass());
        if (queue == null) return event;
        for (EventHandler handler: queue) handler.invoke(event);
        return event;
    }

}
