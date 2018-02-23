package world.avatarhorizon.spigot.staffchannels.channels;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.api.ChannelHandler;

import java.util.Set;

public class HelperChannel extends ChannelHandler
{
    public static final String NAME = "Help";

    public HelperChannel()
    {
        super(NAME);
    }

    @Override
    public String getDefaultFormat()
    {
        return "[HELPER]{PLAYER}: {MESSAGE}";
    }

    @Override
    public String getDefaultSpyFormat()
    {
        return "SPY`[HELPER]{PLAYER}: {MESSAGE}";
    }

    @Override
    public void filterRecipients(CommandSender sender, Set<Player> recipients, Set<Player> spies)
    {
        recipients.removeIf(recipient -> !recipient.hasPermission("staffchannels.help.read"));
    }

    @Override
    public boolean isAccessAllowed(CommandSender sender)
    {
        return sender.hasPermission("staffchannels.help.read");
    }

    @Override
    public boolean canSendMessage(CommandSender sender)
    {
        return sender.hasPermission("staffchannels.help.write");
    }
}
