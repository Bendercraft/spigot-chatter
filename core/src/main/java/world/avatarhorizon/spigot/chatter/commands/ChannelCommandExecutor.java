package world.avatarhorizon.spigot.chatter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.chatter.commands.subcommands.CmdChannelHelp;
import world.avatarhorizon.spigot.chatter.commands.subcommands.CmdChannelList;
import world.avatarhorizon.spigot.chatter.commands.subcommands.CmdChannelSet;
import world.avatarhorizon.spigot.chatter.commands.subcommands.CmdChannelSpy;
import world.avatarhorizon.spigot.chatter.controllers.ChatManager;
import world.avatarhorizon.spigot.chatter.models.ChatterCommandException;

import java.util.*;
import java.util.logging.Logger;

public class ChannelCommandExecutor implements CommandExecutor
{
    private ResourceBundle messages;

    private List<SubCommand> subCommands;

    public ChannelCommandExecutor(ChatManager manager, Logger logger)
    {
        this.messages = ResourceBundle.getBundle("messages/commands_channel");

        this.subCommands = new ArrayList<>(3);
        this.subCommands.add(new CmdChannelList(manager, logger, messages));
        this.subCommands.add(new CmdChannelSet(manager, logger, messages));
        this.subCommands.add(new CmdChannelSpy(manager, logger, messages));
        this.subCommands.add(new CmdChannelHelp(logger, messages, this.subCommands));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (args.length < 1)
        {
            return false;
        }

        List<String> argsList = new LinkedList<>(Arrays.asList(args));

        String sub = argsList.remove(0).toLowerCase();

        for (SubCommand subCommand : subCommands)
        {
            if (subCommand.isCommand(sub))
            {
                try
                {
                    subCommand.execute(sender, argsList);
                }
                catch (ChatterCommandException ex)
                {
                    sender.sendMessage(ex.getMessage());
                }
                return true;
            }
        }
        return false;
    }
}
