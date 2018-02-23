package world.avatarhorizon.spigot.chatter.api;

import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.chatter.models.ChatterChatException;

public interface IChatSenderService
{
    /**
     * Send a message to a channel
     * @param sender The player that sent the message
     * @param channelName The name of the channel in which to send the message
     * @param message The message that need to be sent
     * @throws NullPointerException if the Player or the message is null
     * @throws ChatterChatException if no channel with this name was found or if the player do no have the access to that channel
     */
    void sendChat(CommandSender sender, String channelName, String message) throws ChatterChatException;
}
