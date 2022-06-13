package org.hallebarde.recrutement.api.storage;

/**
 * This interface allows plugins to store their own options in game files, in a key -> value manner.
 * Implementations shall be permissive and perform casts when possible to satisfy the caller's requested type.
 */
public interface Options {

    /**
     * Retrieves a boolean value from these options.
     *
     * @param key                       the key to retrieve the value for
     * @return                          the corresponding value, as a boolean
     * @throws NoSuchOptionException    if the requested key does not exist
     * @throws ClassCastException       if the requested key exists but cannot be interpreted as a boolean
     */
    boolean getBoolean(String key) throws NoSuchOptionException, ClassCastException;

    /**
     * Retrieves a boolean value from these options.
     * If the key is not present in these options, it is inserted along with the default value given by the caller.
     *
     * @param key           the key to retrieve the value for
     * @param defaultValue  a default value to insert and return if the key is not present in these options.
     *
     * @return              the value associated to the given key in these options, or the default value if not present
     */
    boolean getBoolean(String key, boolean defaultValue);

    /**
     * Retrieves an int value from these options.
     *
     * @param key                       the key to retrieve the value for
     * @return                          the corresponding value, as an int
     * @throws NoSuchOptionException    if the requested key does not exist
     * @throws ClassCastException       if the requested key exists but cannot be interpreted as an int
     */
    int getInteger(String key) throws NoSuchOptionException, ClassCastException;

    /**
     * Retrieves an int value from these options.
     * If the key is not present in these options, it is inserted along with the default value given by the caller.
     *
     * @param key           the key to retrieve the value for
     * @param defaultValue  a default value to insert and return if the key is not present in these options.
     *
     * @return              the value associated to the given key in these options, or the default value if not present
     */
    int getInteger(String key, int defaultValue);

    /**
     * Retrieves a float value from these options.
     *
     * @param key                       the key to retrieve the value for
     * @return                          the corresponding value, as a float
     * @throws NoSuchOptionException    if the requested key does not exist
     * @throws ClassCastException       if the requested key exists but cannot be interpreted as a float
     */
    float getFloat(String key) throws NoSuchOptionException, ClassCastException;

    /**
     * Retrieves a float value from these options.
     * If the key is not present in these options, it is inserted along with the default value given by the caller.
     *
     * @param key           the key to retrieve the value for
     * @param defaultValue  a default value to insert and return if the key is not present in these options.
     *
     * @return              the value associated to the given key in these options, or the default value if not present
     */
    float getFloat(String key, float defaultValue);

    /**
     * Retrieves a long value from these options.
     *
     * @param key                       the key to retrieve the value for
     * @return                          the corresponding value, as a long+
     * @throws NoSuchOptionException    if the requested key does not exist
     * @throws ClassCastException       if the requested key exists but cannot be interpreted as a long
     */
    long getLong(String key) throws NoSuchOptionException, ClassCastException;

    /**
     * Retrieves a long value from these options.
     * If the key is not present in these options, it is inserted along with the default value given by the caller.
     *
     * @param key           the key to retrieve the value for
     * @param defaultValue  a default value to insert and return if the key is not present in these options.
     *
     * @return              the value associated to the given key in these options, or the default value if not present
     */
    long getLong(String key, long defaultValue);

    /**
     * Retrieves a double value from these options.
     *
     * @param key                       the key to retrieve the value for
     * @return                          the corresponding value, as a double
     * @throws NoSuchOptionException    if the requested key does not exist
     * @throws ClassCastException       if the requested key exists but cannot be interpreted as a double
     */
    double getDouble(String key) throws NoSuchOptionException, ClassCastException;

    /**
     * Retrieves a double value from these options.
     * If the key is not present in these options, it is inserted along with the default value given by the caller.
     *
     * @param key           the key to retrieve the value for
     * @param defaultValue  a default value to insert and return if the key is not present in these options.
     *
     * @return              the value associated to the given key in these options, or the default value if not present
     */
    double getDouble(String key, double defaultValue);

    /**
     * Retrieves a String value from these options.
     *
     * @param key                       the key to retrieve the value for
     * @return                          the corresponding value, as a String
     * @throws NoSuchOptionException    if the requested key does not exist
     * @throws ClassCastException       if the requested key exists but cannot be interpreted as a String
     */
    String getString(String key) throws NoSuchOptionException, ClassCastException;

    /**
     * Retrieves a String value from these options.
     * If the key is not present in these options, it is inserted along with the default value given by the caller.
     *
     * @param key           the key to retrieve the value for
     * @param defaultValue  a default value to insert and return if the key is not present in these options.
     *
     * @return              the value associated to the given key in these options, or the default value if not present
     */
    String getString(String key, String defaultValue);

    /**
     * Retrieves a sub category from these options.
     *
     * @param key                       the key to retrieve the sub category for
     * @return                          the corresponding sub category as another {@link Options}
     * @throws NoSuchOptionException    if the requested key does not exist
     * @throws ClassCastException       if the requested key exists but isn't a sub category
     */
    Options getExistingCategory(String key) throws NoSuchOptionException, ClassCastException;

    /**
     * Retrieves a sub category from these options.
     * If the key is not present in these options, a new sub category is created.
     *
     * @param key           the key to retrieve the value for
     *
     * @return              the sub category associated to the given key in these options, creating it if not present
     */
    Options getCategory(String key);

}