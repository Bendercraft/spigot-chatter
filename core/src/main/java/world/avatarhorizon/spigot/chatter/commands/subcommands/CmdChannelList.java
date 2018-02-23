package world.avatarhorizon.spigot.chatter.commands.subcommands;

import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.chatter.commands.SubCommand;
import world.avatarhorizon.spigot.chatter.controllers.ChatManager;
import world.avatarhorizon.spigot.chatter.models.ChatterChannel;
import world.avatarhorizon.spigot.chatter.models.ChatterCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CmdChannelList extends SubCommand
{
    private ChatManager chatManager;

    public CmdChannelList(ChatManager chatManager, Logger logger, ResourceBundle messages)
    {
        super("list", logger, messages);
        this.aliases = new ArrayList<>(1);
        this.aliases.add("l");

        this.chatManager = chatManager;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws ChatterCommandException
    {
        validatePermission(sender, "chatter.command.list");
        String list = chatManager.getChannels().values()
                .stream()
                .map(ChatterChannel::getName)
                .sorted()
                .collect(Collectors.joining(", "));
        sender.sendMessage(list);
    }

    @Override
    protected String getHelpKey()
    {
        return "help.channel.list";
    }
}
