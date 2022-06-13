package org.hallebarde.npcsplugin;

import org.hallebarde.recrutement.api.gameplay.Activity;
import org.hallebarde.recrutement.api.gameplay.user.User;

public class TalkActivity extends Activity {

    private final User user;
    private final ConversationAI ai;

    public TalkActivity(User user, ConversationAI ai) {
        super(user);
        this.user = user;
        this.ai = ai;
    }

    @Override
    public void tick() {
    }

    @Override
    public void processMessage(User from, String message) {
        String response = this.ai.respondTo(from, message);
        if (response.length() > 0) {
            this.user.sendMessage(response);
        }
        if (!this.ai.hasMoreToSay(this.user)) {
            this.user.getWorld().getGame().stopActivity(this);
        }
    }

    @Override
    public void start() {
        this.user.sendMessage(this.ai.greet(this.user));
    }

    @Override
    public void finish() {
    }

}
