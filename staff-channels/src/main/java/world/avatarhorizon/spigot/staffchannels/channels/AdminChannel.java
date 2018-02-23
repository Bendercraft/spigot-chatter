package world.avatarhorizon.spigot.staffchannels.channels;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.api.ChannelHandler;

import java.util.Set;

public class AdminChannel extends ChannelHandler
{
    public static final String NAME = "Admin";

    public AdminChannel()
    {
        super(NAME);
    }

    @Override
    public String getDefaultFormat()
    {
        return "[ADMIN]{PLAYER}: {MESSAGE}";
    }

    @Override
    public String getDefaultSpyFormat()
    {
        return "SPY`[ADMIN]{PLAYER}: {MESSAGE}"; //This does not have any sense but for consistency...
    }

    @Override
    public void filterRecipients(CommandSender sender, Set<Player> recipients, Set<Player> spies)
    {
        recipients.removeIf(recipient -> !recipient.hasPermission("staffchannels.admin.read"));
    }

    @Override
    public boolean isAccessAllowed(CommandSender sender)
    {
        return sender.hasPermission("staffchannels.admin.read");
    }

    @Override
    public boolean canSendMessage(CommandSender sender)
    {
        return sender.hasPermission("staffchannels.admin.write");
    }
}
