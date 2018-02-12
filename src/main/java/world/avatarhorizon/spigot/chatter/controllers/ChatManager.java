package world.avatarhorizon.spigot.chatter.controllers;

import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.ChatterPlugin;
import world.avatarhorizon.spigot.chatter.api.IChatFormatter;
import world.avatarhorizon.spigot.chatter.data.IChannelSaver;
import world.avatarhorizon.spigot.chatter.data.IChatterPlayerSaver;
import world.avatarhorizon.spigot.chatter.data.JsonChannelSaver;
import world.avatarhorizon.spigot.chatter.data.JsonChatterPlayerSaver;
import world.avatarhorizon.spigot.chatter.models.*;

import java.util.*;
import java.util.logging.Logger;

public class ChatManager
{
    private ChatterPlugin plugin;

    private Logger logger;
    private ResourceBundle messages;
    private IChatterPlayerSaver chatterPlayerSaver;

    private ChatterChannel globalChannel;
    private ChatterChannel localChannel;

    private List<IChatFormatter> formatters;
    private Map<String, ChatterChannel> channels;
    private Map<UUID, ChatterPlayer> chatterPlayers;

    public ChatManager(ChatterPlugin plugin, Logger logger)
    {
        this.plugin = plugin;
        this.logger = logger;
        this.messages = ResourceBundle.getBundle("messages/base");

        this.formatters = new ArrayList<>();
        this.channels = new HashMap<>();
        this.chatterPlayers = new HashMap<>();

        this.chatterPlayerSaver = new JsonChatterPlayerSaver(plugin.getDataFolder(), this.channels, logger);
    }

    public void loadFormats()
    {
        IChannelSaver chanSaver = new JsonChannelSaver(plugin.getDataFolder(),logger);
        List<ChatterChannelFormats> formats = chanSaver.loadFormats();
        boolean shouldSave = false;
        for (ChatterChannel channel : this.channels.values())
        {
            shouldSave = updateFormats(formats, channel, shouldSave);
            updatesFormatters(channel);
        }
        if (shouldSave)
        {
            chanSaver.saveFormats(this.channels.values());
        }
    }

    private void updatesFormatters(ChatterChannel channel)
    {
        channel.clearFormatters();
        for (IChatFormatter formatter : formatters)
        {
            if (formatter.getHandledTags() != null)
            {
                for (String s : formatter.getHandledTags())
                {
                    if (channel.getFormat().contains(s))
                    {
                        channel.addFormatter(formatter);
                    }
                    if (channel.getSpyFormat().contains(s))
                    {
                        channel.addSpyFormatter(formatter);
                    }
                }
            }
        }
    }

    private boolean updateFormats(List<ChatterChannelFormats> formats, ChatterChannel channel, boolean shouldSave)
    {
        ChatterChannelFormats format = findFormat(formats, channel.getName());
        if (format == null)
        {
            channel.setFormat(channel.getHandler().getDefaultFormat());
            channel.setSpyFormat(channel.getHandler().getDefaultSpyFormat());
            shouldSave = true;
        }
        else
        {
            channel.setFormat(format.format);
            channel.setSpyFormat(format.spy_format);
        }
        return shouldSave;
    }

    private ChatterChannelFormats findFormat(List<ChatterChannelFormats> formats, String name)
    {
        for (ChatterChannelFormats format : formats)
        {
            if (name.equals(format.name))
            {
                return format;
            }
        }
        return null;
    }

    public void registerFormatter(IChatFormatter formatter)
    {
        if (!formatters.contains(formatter))
        {
            this.formatters.add(formatter);
        }
    }

    public void registerChannel(ChatterChannel channel)
    {
        if (!channels.containsKey(channel.getName()))
        {
            this.channels.put(channel.getName().toLowerCase(), channel);
        }
    }

    public ChatterChannel getGlobalChannel()
    {
        return globalChannel;
    }

    public void setGlobalChannel(ChatterChannel globalChannel)
    {
        this.globalChannel = globalChannel;
        registerChannel(globalChannel);
    }

    public ChatterChannel getLocalChannel()
    {
        return localChannel;
    }

    public void setLocalChannel(ChatterChannel localChannel)
    {
        this.localChannel = localChannel;
        registerChannel(localChannel);
    }

    public ChatterChannel getChannel(String channelName)
    {
        return this.channels.get(channelName.toLowerCase());
    }

    public void loginPlayer(Player player)
    {
        ChatterPlayer chatPlayer = chatterPlayerSaver.load(player);
        if (chatPlayer == null)
        {
            chatPlayer = new ChatterPlayer(player);
            chatPlayer.setCurrentChannel(localChannel);
            chatPlayer.setGlobalActive(true);
            chatterPlayerSaver.save(chatPlayer);
        }
        else
        {
            if (chatPlayer.getCurrentChannel() == null)
            {
                chatPlayer.setCurrentChannel(localChannel);
            }
        }
        chatterPlayers.put(player.getUniqueId(), chatPlayer);

        if (!chatPlayer.isGlobalActive())
        {
            player.sendMessage(messages.getString("user.settings.global.disabled"));
        }
    }

    public void logoutPLayer(Player player)
    {
        chatterPlayers.remove(player.getUniqueId());
    }

    public ChatterPlayer getChatterPlayer(Player player)
    {
        return chatterPlayers.get(player.getUniqueId());
    }

    public void updateTitle(Player player, String title)
    {
        ChatterPlayer chatPlayer = getChatterPlayer(player);
        chatPlayer.setTitle(title);
        chatterPlayerSaver.save(chatPlayer);
    }

    public void toggleSpyForChannel(Player sender, ChatterChannel channel)
    {
        ChatterPlayer chatPlayer = getChatterPlayer(sender);
        if (chatPlayer.isSpying(channel.getName()))
        {
            chatPlayer.removeSpyChannel(channel.getName());
            sender.sendMessage(messages.getString("success.channel.spy.disabled").replace("{CHANNEL}", channel.getName()));
        }
        else
        {
            chatPlayer.addSpyChannel(channel.getName());
            sender.sendMessage(messages.getString("success.channel.spy.enabled").replace("{CHANNEL}", channel.getName()));
        }
        chatterPlayerSaver.save(chatPlayer);
    }

    public void setCurrentChannel(Player sender, String channelName) throws ChatterException
    {
        ChatterChannel channel = getChannel(channelName);
        if (channel == null)
        {
            throw new ChatterException(messages.getString("error.channel.not_found"));
        }
        if (!channel.getHandler().isAccessAllowed(sender))
        {
            throw new ChatterException(messages.getString("error.channel.no_access"));
        }

        ChatterPlayer chatterPlayer = getChatterPlayer(sender);
        chatterPlayer.setCurrentChannel(channel);
        chatterPlayerSaver.save(chatterPlayer);
    }

    public boolean toggleGlobal(Player sender)
    {
        ChatterPlayer chatPlayer = getChatterPlayer(sender);
        chatPlayer.toggleGlobalActive();
        chatterPlayerSaver.save(chatPlayer);
        return chatPlayer.isGlobalActive();
    }
}
