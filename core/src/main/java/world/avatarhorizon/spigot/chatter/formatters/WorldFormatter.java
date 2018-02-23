package world.avatarhorizon.spigot.chatter.formatters;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.api.IChatFormatter;

import java.util.Arrays;
import java.util.List;

public class WorldFormatter implements IChatFormatter
{
    private static final String[] TAGS = {"{WORLD}"};

    @Override
    public List<String> getHandledTags()
    {
        return Arrays.asList(TAGS);
    }

    @Override
    public String formatMessage(CommandSender sender, String format)
    {
        if (sender instanceof Player)
        {
            Player p = (Player) sender;
            return format.replace("{WORLD}", p.getWorld().getName());
        }
        else
        {
            return format.replace("{WORLD}", "Server");
        }
    }
}
