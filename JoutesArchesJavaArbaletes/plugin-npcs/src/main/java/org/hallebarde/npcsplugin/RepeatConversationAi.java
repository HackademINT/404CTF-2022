package org.hallebarde.npcsplugin;

import org.hallebarde.recrutement.api.gameplay.user.User;

public class RepeatConversationAi implements ConversationAI {

    private boolean moreToSay = true;

    @Override
    public String respondTo(User from, String message) {
        if (message.contains("stop")) {
            this.moreToSay = false;
            return "Ok ok, I wont bother you anymore";
        }
        return "You said: " + message;
    }

    @Override
    public String greet(User from) {
        return "Hi " + from.getName() + ", my name is dumb, and i will repeat everything you say.";
    }

    @Override
    public boolean hasMoreToSay(User user) {
        return this.moreToSay;
    }

}
