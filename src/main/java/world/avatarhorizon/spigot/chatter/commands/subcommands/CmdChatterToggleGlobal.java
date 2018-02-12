package world.avatarhorizon.spigot.chatter.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.commands.SubCommand;
import world.avatarhorizon.spigot.chatter.controllers.ChatManager;
import world.avatarhorizon.spigot.chatter.models.ChatterCommandException;
import world.avatarhorizon.spigot.chatter.models.ChatterPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CmdChatterToggleGlobal extends SubCommand
{
    private ChatManager manager;
    public CmdChatterToggleGlobal(ChatManager manager, Logger logger, ResourceBundle messages)
    {
        super("toggleglobal", logger, messages);

        this.manager = manager;

        this.aliases = new ArrayList<>(2);
        this.aliases.add("toggle");
        this.aliases.add("tg");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws ChatterCommandException
    {
        validatePlayer(sender);
        validatePermission(sender, "chatter.command.toggleglobal");

        boolean enabled = manager.toggleGlobal((Player) sender);

        if (enabled)
        {
            sender.sendMessage(messages.getString("success.chatter.global_enabled"));
        }
        else
        {
            sender.sendMessage(messages.getString("success.chatter.global_disabled"));
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.chatter.toggle_global";
    }
}
