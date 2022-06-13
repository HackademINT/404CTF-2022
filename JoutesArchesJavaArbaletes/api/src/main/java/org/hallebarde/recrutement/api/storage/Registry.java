package org.hallebarde.recrutement.api.storage;

import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.PluginMetadata;
import org.hallebarde.recrutement.api.Plugin;
import org.hallebarde.recrutement.api.gameplay.Item;

import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Registries store information about the various classes that implement gameplay related types,
 * like {@link Item}.
 * A registry simply is a map with String keys and object constructors as values.
 * The keys are the registered object type ID and the values are constructor that take an {@link Options} object as parameter.
 * When the game loads the world and custom entities are specified within the world files,
 * the game will use the relevant registry to instantiate the relevant class.
 *
 * Registries should only be written before the world is loaded (in your plugins {@link Plugin#onLoad(Game, PluginMetadata, Logger)} method.
 * Writing to one at a latter stage is undefined behavior and may make your server unstable.
 *
 * @param <T>   the type of entities managed by this registry
 */
public interface Registry<T> extends Map<String, Function<Options, T>> {

    /**
     * Gets an instance from this registry, using a specific id and options.
     *
     * @param id        the id
     * @param options   the options to pass to the constructor
     *
     * @return a new object, or null if the given id is not known to this registry
     */
    T instantiate(String id, Options options);

}
