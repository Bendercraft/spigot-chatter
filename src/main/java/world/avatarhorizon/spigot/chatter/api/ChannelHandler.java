package world.avatarhorizon.spigot.chatter.api;

import org.bukkit.entity.Player;

import java.util.Set;

public abstract class ChannelHandler
{
    protected final String name;

    public ChannelHandler(String name)
    {
        this.name = name;
    }

    public final String getName()
    {
        return this.name;
    }

    /**
     * Only used for initialisation. <br>
     * Get the default format string used for that channel
     * @return a String containing the default format.
     */
    public abstract String getDefaultFormat();

    /**
     * Only used for initialisation. <br>
     * Get the default format string used when spying that channel
     * @return a String containing the default spy format.
     */
    public abstract String getDefaultSpyFormat();

    /**
     *  Filter the set of players that will receive the message
     * @param recipients a Set of online players that will receive a message if not filtered
     * @param spies an empty Set that you should fill with the players that can spy on the channel. (You should not add them if you keep them in the recipients set though.)
     */
    public abstract void filterRecipients(Player sender, Set<Player> recipients, Set<Player> spies);

    /**
     *  Check if the player is allowed to access that channel. To send message to chat channel or to set this channel as default one.
     */
    public abstract boolean isAccessAllowed(Player player);

}
