package world.avatarhorizon.spigot.chatter.handlers;

import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.api.ChannelHandler;
import world.avatarhorizon.spigot.chatter.controllers.ChatManager;
import world.avatarhorizon.spigot.chatter.models.ChatterPlayer;

import java.util.Iterator;
import java.util.Set;

public class GlobalChannel extends ChannelHandler
{
    private ChatManager chatManager;

    public GlobalChannel(ChatManager chatManager)
    {
        super("Global");
        this.chatManager = chatManager;
    }

    @Override
    public String getDefaultFormat()
    {
        return "[G]{TITLE}~{PLAYER}: {MESSAGE}";
    }

    @Override
    public String getDefaultSpyFormat()
    {
        return "[SPY][G]{PLAYER}: {MESSAGE}";
    }

    @Override
    public void filterRecipients(Player sender, Set<Player> recipients, Set<Player> spies)
    {
        Iterator<Player> recs = recipients.iterator();
        while (recs.hasNext())
        {
            Player rec = recs.next();
            ChatterPlayer chatterPlayer = chatManager.getChatterPlayer(rec);
            if (!chatterPlayer.isGlobalActive())
            {
                recs.remove();
            }
        }
    }

    @Override
    public boolean isAccessAllowed(Player player)
    {
        return true;
    }

    @Override
    public boolean canSendMessage(Player player)
    {
        ChatterPlayer chatterPlayer = chatManager.getChatterPlayer(player);
        if (chatterPlayer == null)
        {
            return false;
        }
        return !chatterPlayer.isGlobalMuted();
    }
}
