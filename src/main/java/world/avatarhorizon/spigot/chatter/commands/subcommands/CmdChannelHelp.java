package world.avatarhorizon.spigot.chatter.commands.subcommands;

import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.chatter.commands.SubCommand;
import world.avatarhorizon.spigot.chatter.models.ChatterCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CmdChannelHelp extends SubCommand
{
    private List<SubCommand> subCommands;

    public CmdChannelHelp(Logger logger, ResourceBundle messages, List<SubCommand> commands)
    {
        super("help", logger, messages);
        this.aliases = new ArrayList<>(2);
        this.aliases.add("h");
        this.aliases.add("?");

        this.subCommands = commands;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws ChatterCommandException
    {
        validatePlayer(sender);
        validatePermission(sender, "chatter.channel.help");
        if (args.isEmpty())
        {
            for (SubCommand command : subCommands)
            {
                command.sendHelp(sender);
            }
        }
        else
        {
            boolean found = false;
            String action = args.remove(0);
            for (SubCommand command : subCommands)
            {
                if (command.isCommand(action))
                {
                    command.sendHelp(sender);
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                sender.sendMessage(messages.getString("error.help.not_found"));
            }
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.channel.help";
    }
}
