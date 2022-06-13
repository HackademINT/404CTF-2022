package org.hallebarde.recrutement.plugins;

public class PluginLoadingException extends Exception {

    public PluginLoadingException() {
    }

    public PluginLoadingException(String message) {
        super(message);
    }

    public PluginLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginLoadingException(Throwable cause) {
        super(cause);
    }

}
