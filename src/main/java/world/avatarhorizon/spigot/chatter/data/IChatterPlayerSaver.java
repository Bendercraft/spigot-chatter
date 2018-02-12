package world.avatarhorizon.spigot.chatter.data;

import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.models.ChatterPlayer;

public interface IChatterPlayerSaver
{
    void save(ChatterPlayer player);
    ChatterPlayer load(Player player);
    void delete(ChatterPlayer player);
}
