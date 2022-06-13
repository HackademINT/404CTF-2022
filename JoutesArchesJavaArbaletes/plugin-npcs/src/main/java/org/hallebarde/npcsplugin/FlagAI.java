package org.hallebarde.npcsplugin;

import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.storage.Options;

public class FlagAI implements ConversationAI {

    private static final String FLAG;
    private final String flag;

    static {
        String flag = System.getProperty("ctf404.flag.cjgJF4GCxj2QD5Lg");
        if (flag == null) throw new IllegalStateException("Ce challenge semble être cassé, merci de signaler cette erreur aux organisateurs.");
        FLAG = flag;
        System.setProperty("ctf404.flag.cjgJF4GCxj2QD5Lg", "Sorry but the flag is no longer here.");
    }

    private boolean hasMore = true;

    public FlagAI(Options options) {
        this.flag = FLAG;
    }

    @Override
    public String respondTo(User from, String message) {
        if (message.toLowerCase().contains("yes")) {
            hasMore = false;
            return "Well... I'm sorry but it's private so I can't really give it to you... Ha, accessors...";
        } else if (message.toLowerCase().contains("no")) {
            hasMore = false;
            return "Ok, I can't help you then, bye.";
        }
        return "Sorry, I didn't quite get that, do you want the flag or not ? (yes/no)";
    }

    @Override
    public String greet(User from) {
        return "Hi, I guess you are here for the flag, right ? (yes/no)";
    }

    @Override
    public boolean hasMoreToSay(User user) {
        return this.hasMore;
    }

}
