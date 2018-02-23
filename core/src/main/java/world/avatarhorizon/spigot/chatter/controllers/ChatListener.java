package world.avatarhorizon.spigot.chatter.controllers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import world.avatarhorizon.spigot.chatter.ChatterSettings;
import world.avatarhorizon.spigot.chatter.api.IChatFormatter;
import world.avatarhorizon.spigot.chatter.models.ChatterChannel;
import world.avatarhorizon.spigot.chatter.models.ChatterPlayer;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ChatListener implements Listener
{
    private ChatManager chatManager;
    private ResourceBundle messages;

    public ChatListener (ChatManager chatManager)
    {
        this.chatManager = chatManager;
        this.messages = ResourceBundle.getBundle("messages/listener");
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerLogin(PlayerJoinEvent event)
    {
        chatManager.loginPlayer(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerLogout(PlayerQuitEvent event)
    {
        chatManager.logoutPLayer(event.getPlayer());
    }


    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        String message = event.getMessage();
        ChatterChannel channel;
        if (message.charAt(0) == ChatterSettings.getGlobalPrefix())
        {
            channel = chatManager.getGlobalChannel();
            message = message.substring(1);
            event.setMessage(message);
        }
        else
        {
            ChatterPlayer chatterPlayer = chatManager.getChatterPlayer(event.getPlayer());
            channel = chatterPlayer.getCurrentChannel();
        }

        if (!channel.getHandler().isAccessAllowed(event.getPlayer()))
        {
            event.getPlayer().sendMessage(messages.getString("error.channel.no_access"));
            event.setCancelled(true);
        }
        else if (!channel.getHandler().canSendMessage(event.getPlayer()))
        {
            event.getPlayer().sendMessage(messages.getString("error.channel.cannot_send"));
            event.setCancelled(true);
        }
        else
        {
            Set<Player> spies = new HashSet<>();
            channel.getHandler().filterRecipients(event.getPlayer(), event.getRecipients(), spies);

            if (!event.getRecipients().isEmpty())
            {
                String format = channel.getFormat().replace("{MESSAGE}", "%2$s");
                for (IChatFormatter formatter : channel.getFormatters())
                {
                    format = formatter.formatMessage(event.getPlayer(), format);
                }
                event.setFormat(format);
            }

            if (!spies.isEmpty())
            {
                String formattedMessage = channel.getSpyFormat().replace("{MESSAGE}", message);
                for (IChatFormatter formatter : channel.getSpyFormatters())
                {
                    formattedMessage = formatter.formatMessage(event.getPlayer(), formattedMessage);
                }

                for (Player spy : spies)
                {
                    spy.sendMessage(formattedMessage);
                }
            }
        }
    }
}
