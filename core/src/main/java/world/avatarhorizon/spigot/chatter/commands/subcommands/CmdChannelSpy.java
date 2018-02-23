package world.avatarhorizon.spigot.chatter.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.commands.SubCommand;
import world.avatarhorizon.spigot.chatter.controllers.ChatManager;
import world.avatarhorizon.spigot.chatter.models.ChatterChannel;
import world.avatarhorizon.spigot.chatter.models.ChatterCommandException;
import world.avatarhorizon.spigot.chatter.models.ChatterPlayer;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CmdChannelSpy extends SubCommand
{
    private ChatManager chatManager;

    public CmdChannelSpy(ChatManager chatManager, Logger logger, ResourceBundle messages)
    {
        super("spy", logger, messages);
        this.chatManager = chatManager;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws ChatterCommandException
    {
        validatePlayer(sender);
        validatePermission(sender, "chatter.command.socialspy");

        if (args.isEmpty())
        {
            throw new ChatterCommandException(messages.getString("error.channel.not_found"));
        }

        ChatterChannel channel = chatManager.getChannel(args.get(0));
        if (channel == null)
        {
            throw new ChatterCommandException(messages.getString("error.channel.not_found"));
        }

        chatManager.toggleSpyForChannel((Player)sender, channel);
    }

    @Override
    protected String getHelpKey()
    {
        return "help.channel.spy";
    }
}
