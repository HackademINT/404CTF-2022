package org.hallebarde.recrutement.plugins;

import java.io.IOException;

public class CorruptedPluginException extends IOException {

    public CorruptedPluginException() {
    }

    public CorruptedPluginException(String message) {
        super(message);
    }

    public CorruptedPluginException(String message, Throwable cause) {
        super(message, cause);
    }

    public CorruptedPluginException(Throwable cause) {
        super(cause);
    }

}
