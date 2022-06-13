package org.hallebarde.recrutement.api;

import java.io.Serializable;

/**
 * The metadata loaded from a plugin's plugin.json metadata file.
 */
public record PluginMetadata(String id, String mainClass, String version) implements Serializable {

}
