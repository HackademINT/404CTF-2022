package org.hallebarde.recrutement.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

public class ThreadFactoryBuilder {

    private final List<Consumer<Thread>> modifiers = new LinkedList<>();

    public ThreadFactoryBuilder setName(final String name) {
        this.modifiers.add(t -> t.setName(name));
        return this;
    }

    public ThreadFactoryBuilder setDaemon(final boolean daemon) {
        this.modifiers.add(t -> t.setDaemon(daemon));
        return this;
    }

    public ThreadFactory build() {
        final List<Consumer<Thread>> consumers = new LinkedList<>(this.modifiers);
        return run -> {
            Thread thread = new Thread(run);
            consumers.forEach(c -> c.accept(thread));
            return thread;
        };
    }

}
