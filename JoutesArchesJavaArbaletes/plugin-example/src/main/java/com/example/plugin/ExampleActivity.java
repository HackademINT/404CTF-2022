package com.example.plugin;

import org.hallebarde.recrutement.api.gameplay.Activity;
import org.hallebarde.recrutement.api.gameplay.user.User;

/**
 * An example activity with a single user that repeats back what they say until they say stop.
 */
public class ExampleActivity extends Activity {

    public ExampleActivity(User user) {
        super(user);
    }

    @Override
    public void tick() {
        // This is called at every game server tick, 10 times per seconds.
    }

    @Override
    public void processMessage(User from, String message) {
        // This is called when a user involved in this activity sends a message
        if (message.contains("stop")) {
            // We can stop the activity now
            from.getWorld().getGame().stopActivity(this);
        } else {
            // Repeat back what was said
            from.sendMessage("You said: " + message);
        }
    }

    @Override
    public void start() {
        ExamplePlugin.getInstance().getLogger().info("Starting example activity for user " + this.getUsers().get(0).getName());
    }

    @Override
    public void finish() {
        this.getUsers().get(0).sendMessage("Quitting example activity.");
        ExamplePlugin.getInstance().getLogger().info("Finishing example activity for user " + this.getUsers().get(0).getName());
    }

}
