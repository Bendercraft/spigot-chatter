package world.avatarhorizon.spigot.chatter.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.commands.SubCommand;
import world.avatarhorizon.spigot.chatter.controllers.ChatManager;
import world.avatarhorizon.spigot.chatter.models.ChatterCommandException;
import world.avatarhorizon.spigot.chatter.models.ChatterPlayer;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CmdChatterTitle extends SubCommand
{
    private ChatManager chatManager;

    public CmdChatterTitle(ChatManager chatManager, Logger logger, ResourceBundle messages)
    {
        super("title", logger, messages);
        this.chatManager = chatManager;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws ChatterCommandException
    {
        validatePermission(sender, "chatter.command.title");
        String name = args.remove(0);

        if (args.isEmpty())
        {
            throw new ChatterCommandException(messages.getString("error.title.empty"));
        }

        Player player = Bukkit.getPlayer(name);
        if (player == null || !player.isOnline())
        {
            throw new ChatterCommandException(messages.getString("error.player.not_found"));
        }

        String title = null;
        if (!args.isEmpty())
        {
            title = args.remove(0).replace("&&","§");
        }

        chatManager.updateTitle(player, title);
        sender.sendMessage(messages.getString("success.title"));
    }

    @Override
    protected String getHelpKey()
    {
        return "help.chatter.title";
    }
}
