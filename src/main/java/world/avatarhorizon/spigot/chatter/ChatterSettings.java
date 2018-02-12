package world.avatarhorizon.spigot.chatter;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatterSettings
{
    private static int localMaxDistance = 32;
    private static char globalPrefix = '!';

    public static void loadSettings(JavaPlugin plugin)
    {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        localMaxDistance = config.getInt("local_max_distance", 32);
        String prefix = config.getString("global_prefix", "!");
        globalPrefix = prefix.charAt(0);
    }

    public static int getLocalMaxDistance()
    {
        return localMaxDistance;
    }

    public static char getGlobalPrefix()
    {
        return globalPrefix;
    }
}
