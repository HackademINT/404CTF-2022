package org.hallebarde.recrutement.api.storage;


/**
 * This exception is thrown in {@link Options} when trying to access a key
 * that does not exist without providing a default value.
 */
public class NoSuchOptionException extends Exception {

    /**
     * Constructs an exception with a simple error message that indicates which key was requested.
     *
     * @param key   the key that was requested
     */
    public NoSuchOptionException(String key) {
        super(String.format("No such key: %s", key));
    }

    /**
     * Constructs an exception with a simple error message that indicates which key was requested,
     * and proposes a similar key which does exist.
     *
     * @param key   the key that was requested
     * @param maybe the similar key the caller was potentially trying to use
     */
    public NoSuchOptionException(String key, String maybe) {
        super(String.format("No such key: %s, did you mean %s ?", key, maybe));
    }

}
