package org.hallebarde.recrutement.api.gameplay;

import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.annotations.DoNotCall;
import org.hallebarde.recrutement.api.annotations.FreeToImplement;
import org.hallebarde.recrutement.api.gameplay.user.User;

import java.util.List;
import java.util.UUID;

/**
 * An activity one or more users can be performing.
 * Each user can only perform one activity at a time
 * Extend this class to create new activities.
 */
@FreeToImplement
public abstract class Activity {

    private final List<User> users;
    private final UUID uuid;
    private boolean terminated = false;

    public Activity(User... users) {
        this.users = List.of(users);
        this.uuid = UUID.randomUUID();
    }

    /**
     * This method is called by the game when an activity is stopped using {@link Game#stopActivity(Activity)}
     * or {@link Game#startActivityNow(Activity)}
     */
    @DoNotCall
    public void terminate() {
        if (this.terminated) throw new IllegalStateException("Trying to terminate an activity but its already done! Activity class: " + this.getClass());
        this.finish();
        this.terminated = true;
    }

    /**
     * Called on each server tick.
     */
    public abstract void tick();

    /**
     * Called when a user that is conducting this activity sends a message.
     *
     * @param from      the user the message is coming from
     * @param message   the message that was sent
     */
    public abstract void processMessage(User from, String message);

    /**
     * Called by the game starts this activity.
     */
    public abstract void start();

    /**
     * Called when the activity finishes.
     */
    public abstract void finish();

    /**
     * @return and unmodifiable list of the users involved in this activity
     */
    public final List<User> getUsers(){
        return this.users;
    }

    /**
     * @return a unique {@link UUID} that identifies this activity
     */
    public final UUID getUUID() {
        return this.uuid;
    }

    /**
     * @return whether this activity has finished
     */
    public final boolean isDone() {
        return this.terminated;
    }

}
