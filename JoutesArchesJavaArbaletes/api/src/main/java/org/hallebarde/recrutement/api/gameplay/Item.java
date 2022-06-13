package org.hallebarde.recrutement.api.gameplay;

import org.hallebarde.recrutement.api.annotations.FreeToImplement;
import org.hallebarde.recrutement.api.gameplay.user.User;

/**
 * An item users can collect and use.
 */
@FreeToImplement
public interface Item {

    /**
     * @return the name of the item as it should be displayed
     */
    String name();

    /**
     * @return the description of the item as it should be displayed
     */
    String description();

    /**
     * Make a given user use the item.
     *
     * @param user    the user using the item
     * @param args      the arguments the user passed to the command to use the item
     */
    void use(User user, String... args);

}