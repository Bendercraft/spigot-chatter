package world.avatarhorizon.spigot.chatter.api;

import org.bukkit.command.CommandSender;
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
     * Filter the set of players that will receive the message
     * @param sender The console or a connected player
     * @param recipients a Set of online players that will receive a message if not filtered
     * @param spies an empty Set that you should fill with the players that can spy on the channel. (You should not add them if you keep them in the recipients set though.)
     */
    public abstract void filterRecipients(CommandSender sender, Set<Player> recipients, Set<Player> spies);

    /**
     *  Check if the player is allowed to access that channel.
     *  This method is called when a Player want to set the channel as default channel or when he tries to send a message into it.
     *  @param sender A sender (console or player) that will be checked.
     *  @return <code>true</code> if the sender can access the channel. <br><code>false</code> otherwise
     */
    public abstract boolean isAccessAllowed(CommandSender sender);

    /**
     * Check if the player can send a message into that channel. <br>
     * For instance, if you want to mute a player.
     * @param sender The player or the console whose required to be checked.
     * @return <code>true</code> if the player can send a message. <br><code>false</code> if the player is not allowed to send a message to this channel.
     */
    public abstract boolean canSendMessage(CommandSender sender);

}
