package world.avatarhorizon.spigot.chatter.controllers;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.api.IChatFormatter;
import world.avatarhorizon.spigot.chatter.api.IChatSenderService;
import world.avatarhorizon.spigot.chatter.models.ChatExceptionCause;
import world.avatarhorizon.spigot.chatter.models.ChatterChannel;
import world.avatarhorizon.spigot.chatter.models.ChatterChatException;

import java.util.HashSet;
import java.util.Set;

public class ChatSenderService implements IChatSenderService
{
    private ChatManager chatManager;

    public ChatSenderService(ChatManager chatManager)
    {
        this.chatManager = chatManager;
    }

    @Override
    public void sendChat(CommandSender sender, String channelName, String message) throws ChatterChatException
    {
        if (sender == null)
        {
            throw new NullPointerException();
        }

        ChatterChannel channel = chatManager.getChannel(channelName);
        if (channel == null)
        {
            throw new ChatterChatException(ChatExceptionCause.CHANNEL_NOT_FOUND);
        }

        if (!channel.getHandler().isAccessAllowed(sender))
        {
            throw new ChatterChatException(ChatExceptionCause.ACCESS_NOT_ALLOWED);
        }

         if (!channel.getHandler().canSendMessage(sender))
        {
            throw new ChatterChatException(ChatExceptionCause.CANNOT_SEND_MESSAGE);
        }

        Set<Player> recipients = new HashSet<>(Bukkit.getOnlinePlayers());
        Set<Player> spies = new HashSet<>();

        channel.getHandler().filterRecipients(sender, recipients, spies);

        if (!recipients.isEmpty())
        {
            String formattedMessage = channel.getFormat().replace("{MESSAGE}", message);
            for (IChatFormatter formatter : channel.getFormatters())
            {
                formattedMessage = formatter.formatMessage(sender, formattedMessage);
            }

            for (Player recipient : recipients)
            {
                recipient.sendMessage(formattedMessage);
            }
        }

        if (!spies.isEmpty())
        {
            String formattedMessage = channel.getSpyFormat().replace("{MESSAGE}", message);
            for (IChatFormatter formatter : channel.getSpyFormatters())
            {
                formattedMessage = formatter.formatMessage(sender, formattedMessage);
            }

            for (Player spy : spies)
            {
                spy.sendMessage(formattedMessage);
            }
        }
    }
}
