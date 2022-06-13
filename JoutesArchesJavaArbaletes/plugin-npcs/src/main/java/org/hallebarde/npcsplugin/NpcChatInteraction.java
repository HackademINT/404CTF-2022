package org.hallebarde.npcsplugin;

import org.hallebarde.recrutement.api.Game;
import org.hallebarde.recrutement.api.gameplay.user.User;
import org.hallebarde.recrutement.api.gameplay.world.Room;
import org.hallebarde.recrutement.api.gameplay.world.RoomInteraction;
import org.hallebarde.recrutement.api.storage.Options;

public class NpcChatInteraction implements RoomInteraction {

    private final String name;
    private final String description;
    private final String ai;
    private final Options aiOptions;

    public NpcChatInteraction(Options options) {
        this.name = options.getString("name", "NPC guy");
        this.description = options.getString("description", "");
        this.ai = options.getString("ai", "");
        this.aiOptions = options.getCategory("ai_options");
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public void interact(User user, Room room, String... args) {
        ConversationAI ai = NpcsPlugin.getInstance().getAis().instantiate(this.ai, this.aiOptions);
        Game game = user.getWorld().getGame();
        game.startActivityWhenPossible(new TalkActivity(user, ai));
    }

}
