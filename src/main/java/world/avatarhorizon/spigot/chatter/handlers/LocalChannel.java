package world.avatarhorizon.spigot.chatter.handlers;

import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.ChatterSettings;
import world.avatarhorizon.spigot.chatter.api.ChannelHandler;
import world.avatarhorizon.spigot.chatter.controllers.ChatManager;
import world.avatarhorizon.spigot.chatter.models.ChatterPlayer;

import java.util.Iterator;
import java.util.Set;

public class LocalChannel extends ChannelHandler
{
    private ChatManager chatManager;

    public LocalChannel(ChatManager chatManager)
    {
        super("Local");
        this.chatManager = chatManager;
    }

    @Override
    public String getDefaultFormat()
    {
        return "[L]{TITLE}~{PLAYER}: {MESSAGE}";
    }

    @Override
    public String getDefaultSpyFormat()
    {
        return "[SPY][L]{PLAYER}: {MESSAGE}";
    }

    @Override
    public void filterRecipients(final Player sender, final Set<Player> recipients, final Set<Player> spies)
    {
        if (ChatterSettings.getLocalMaxDistance() > 0)
        {
            Iterator<Player> iterator = recipients.iterator();
            while (iterator.hasNext())
            {
                Player recipient = iterator.next();
                if (recipient.getWorld() != sender.getWorld()
                        || recipient.getLocation().distance(sender.getLocation()) > ChatterSettings.getLocalMaxDistance())
                {
                    iterator.remove();
                    if (recipient.hasPermission("chatter.admin.socialspy.local"))
                    {
                        ChatterPlayer p = chatManager.getChatterPlayer(recipient);
                        if (p.isSpying(name))
                        {
                            spies.add(recipient);
                        }
                    }
                }
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
        return !chatterPlayer.isLocalMuted();
    }
}
