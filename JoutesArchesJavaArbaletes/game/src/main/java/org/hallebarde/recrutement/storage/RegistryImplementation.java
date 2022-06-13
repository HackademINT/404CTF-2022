package org.hallebarde.recrutement.storage;

import org.hallebarde.recrutement.api.storage.Options;
import org.hallebarde.recrutement.api.storage.Registry;

import java.util.HashMap;
import java.util.function.Function;

public class RegistryImplementation<T> extends HashMap<String, Function<Options, T>> implements Registry<T> {

    @Override
    public T instantiate(String id, Options options) {
        Function<Options, T> constructor = this.get(id);
        if (constructor == null) return null;
        return constructor.apply(options);
    }

}
