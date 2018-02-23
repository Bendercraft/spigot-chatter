package world.avatarhorizon.spigot.chatter.formatters;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.api.IChatFormatter;

import java.util.Arrays;
import java.util.List;

public class PlayerFormatter implements IChatFormatter
{
    private static final String[] TAGS = {"{NAME}", "{SENDER}"};

    @Override
    public List<String> getHandledTags()
    {
        return Arrays.asList(TAGS);
    }

    @Override
    public String formatMessage(CommandSender sender, String format)
    {
        format = format.replace("{NAME}", sender.getName());
        if (sender instanceof Player)
        {
            format = format.replace("{SENDER}", ((Player)sender).getDisplayName());
        }
        else
        {
            format = format.replace("{SENDER}", sender.getName());
        }
        return format;
    }
}
