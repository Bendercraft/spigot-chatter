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

public class CmdChatterMute extends SubCommand
{
    private ChatManager chatManager;

    public CmdChatterMute(ChatManager chatManager, Logger logger, ResourceBundle messages)
    {
        super("mute", logger, messages);
        this.chatManager = chatManager;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws ChatterCommandException
    {
        validatePermission(sender, "chatter.command.mute");
        if (args.isEmpty())
        {
            throw new ChatterCommandException(messages.getString("error.mute.no_args"));
        }

        Player player = Bukkit.getPlayer(args.remove(0));
        if (player == null)
        {
            throw new ChatterCommandException(messages.getString("error.player.not_found"));
        }

        ChatterPlayer chatPlayer = chatManager.getChatterPlayer(player);

        boolean local = false;

        if (!args.isEmpty())
        {
            String channelName = args.get(0).toLowerCase();
            if (channelName.equals("local") || channelName.equals("l"))
            {
                local = true;
            }
        }

        boolean muted = false;
        if (local)
        {
            muted = chatPlayer.toggleLocalMuted();
        }
        else
        {
            muted = chatPlayer.toggleGlobalMuted();
        }

        if (muted)
        {
            sender.sendMessage(messages.getString("success.chatter.mute.other").replace("{PLAYER}", player.getName()));
            player.sendMessage(messages.getString("success.chatter.mute.you"));
        }
        else
        {
            sender.sendMessage(messages.getString("success.chatter.unmute.other").replace("{PLAYER}", player.getName()));
            player.sendMessage(messages.getString("success.chatter.unmute.you"));
        }

    }

    @Override
    protected String getHelpKey()
    {
        return "help.chatter.mute";
    }
}
