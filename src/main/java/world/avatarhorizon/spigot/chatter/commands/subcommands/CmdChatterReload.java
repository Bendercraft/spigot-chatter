package world.avatarhorizon.spigot.chatter.commands.subcommands;

import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.chatter.commands.SubCommand;
import world.avatarhorizon.spigot.chatter.controllers.ChatManager;
import world.avatarhorizon.spigot.chatter.models.ChatterCommandException;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CmdChatterReload extends SubCommand
{
    private ChatManager chatManager;
    public CmdChatterReload(ChatManager chatManager, Logger logger, ResourceBundle messages)
    {
        super("reload", logger, messages);
        this.chatManager = chatManager;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws ChatterCommandException
    {
        validatePermission(sender, "chatter.command.reload");
        chatManager.loadFormats();
        sender.sendMessage(messages.getString("success.chatter.reload"));
    }

    @Override
    protected String getHelpKey()
    {
        return "help.chatter.reload";
    }
}
