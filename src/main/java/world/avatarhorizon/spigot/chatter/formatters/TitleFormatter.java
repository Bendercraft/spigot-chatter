package world.avatarhorizon.spigot.chatter.formatters;

import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.api.IChatFormatter;
import world.avatarhorizon.spigot.chatter.controllers.ChatManager;
import world.avatarhorizon.spigot.chatter.models.ChatterPlayer;

import java.util.Arrays;
import java.util.List;

public class TitleFormatter implements IChatFormatter
{
    private static final String[] TAGS = {"{TITLE}"};

    private ChatManager chatManager;

    public TitleFormatter(ChatManager chatManager)
    {
        this.chatManager = chatManager;
    }

    @Override
    public List<String> getHandledTags()
    {
        return Arrays.asList(TAGS);
    }

    @Override
    public String formatMessage(Player sender, String format)
    {
        ChatterPlayer chatPlayer = chatManager.getChatterPlayer(sender);
        return format.replace("{TITLE}", chatPlayer.getTitle() == null ? "" : chatPlayer.getTitle());
    }
}
