package world.avatarhorizon.spigot.chatter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.chatter.commands.subcommands.CmdChatterHelp;
import world.avatarhorizon.spigot.chatter.commands.subcommands.CmdChatterReload;
import world.avatarhorizon.spigot.chatter.commands.subcommands.CmdChatterTitle;
import world.avatarhorizon.spigot.chatter.commands.subcommands.CmdChatterToggleGlobal;
import world.avatarhorizon.spigot.chatter.controllers.ChatManager;
import world.avatarhorizon.spigot.chatter.models.ChatterCommandException;

import java.util.*;
import java.util.logging.Logger;

public class ChatterCommandExecutor implements CommandExecutor
{
    private ResourceBundle messages;
    private List<SubCommand> subCommands;

    public ChatterCommandExecutor(ChatManager manager, Logger logger)
    {
        this.messages = ResourceBundle.getBundle("messages/commands_chatter");

        this.subCommands = new ArrayList<>(4);

        this.subCommands.add(new CmdChatterToggleGlobal(manager, logger, messages));
        this.subCommands.add(new CmdChatterTitle(manager, logger, messages));
        this.subCommands.add(new CmdChatterReload(manager, logger, messages));
        this.subCommands.add(new CmdChatterHelp(logger, messages, this.subCommands));
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
