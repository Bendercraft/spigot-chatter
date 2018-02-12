package world.avatarhorizon.spigot.chatter.formatters;

import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.api.IChatFormatter;

import java.util.Arrays;
import java.util.List;

public class PlayerFormatter implements IChatFormatter
{
    private static final String[] TAGS = {"{NAME}", "{PLAYER}"};

    @Override
    public List<String> getHandledTags()
    {
        return Arrays.asList(TAGS);
    }

    @Override
    public String formatMessage(Player sender, String format)
    {
        return format
                .replace("{NAME}", sender.getName())
                .replace("{PLAYER}", "%1$s"); //%1$s means sender.getDisplayName() since it will be handled by bukkit itself
    }
}
