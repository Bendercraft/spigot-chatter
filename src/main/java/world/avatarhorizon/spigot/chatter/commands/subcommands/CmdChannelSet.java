package world.avatarhorizon.spigot.chatter.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.api.ChannelHandler;
import world.avatarhorizon.spigot.chatter.commands.SubCommand;
import world.avatarhorizon.spigot.chatter.controllers.ChatManager;
import world.avatarhorizon.spigot.chatter.models.ChatterChannel;
import world.avatarhorizon.spigot.chatter.models.ChatterCommandException;
import world.avatarhorizon.spigot.chatter.models.ChatterException;
import world.avatarhorizon.spigot.chatter.models.ChatterPlayer;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CmdChannelSet extends SubCommand
{
    private ChatManager manager;

    public CmdChannelSet(ChatManager manager, Logger logger, ResourceBundle messages)
    {
        super("set", logger, messages);
        this.manager = manager;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws ChatterCommandException
    {
        validatePlayer(sender);
        validatePermission(sender, "chatter.channel.set");

        try
        {
            String channelName = args.remove(0);
            manager.setCurrentChannel((Player) sender, channelName);
            sender.sendMessage(messages.getString("success.channel.set").replace("{CHANNEL}", channelName));
        }
        catch (ChatterException e)
        {
            throw new ChatterCommandException(e.getMessage());
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.channel.set";
    }
}
