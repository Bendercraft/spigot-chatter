package world.avatarhorizon.spigot.chatter.handlers;

import org.bukkit.command.CommandSender;
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
        return "[L]{TITLE}~{SENDER}: {MESSAGE}";
    }

    @Override
    public String getDefaultSpyFormat()
    {
        return "[SPY][L]{SENDER}: {MESSAGE}";
    }

    @Override
    public void filterRecipients(final CommandSender sender, final Set<Player> recipients, final Set<Player> spies)
    {
        if (ChatterSettings.getLocalMaxDistance() > 0)
        {
            Player pSender = (Player) sender;
            Iterator<Player> iterator = recipients.iterator();
            while (iterator.hasNext())
            {
                Player recipient = iterator.next();
                if (recipient.getWorld() != pSender.getWorld()
                        || recipient.getLocation().distance(pSender.getLocation()) > ChatterSettings.getLocalMaxDistance())
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
    public boolean isAccessAllowed(CommandSender sender)
    {
       return (sender instanceof Player);
    }

    @Override
    public boolean canSendMessage(CommandSender sender)
    {
        //Since isAccessAllowed is called before canSendMessage, we assume sender IS a Player
        ChatterPlayer chatterPlayer = chatManager.getChatterPlayer((Player) sender);
        if (chatterPlayer == null)
        {
            return false;
        }
        return !chatterPlayer.isLocalMuted();
    }
}
