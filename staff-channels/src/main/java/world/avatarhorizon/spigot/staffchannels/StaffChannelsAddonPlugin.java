package world.avatarhorizon.spigot.staffchannels;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import world.avatarhorizon.spigot.chatter.models.ChatExceptionCause;
import world.avatarhorizon.spigot.chatter.models.ChatterChatException;
import world.avatarhorizon.spigot.staffchannels.channels.AdminChannel;
import world.avatarhorizon.spigot.staffchannels.channels.AnimationChannel;
import world.avatarhorizon.spigot.staffchannels.channels.HelperChannel;
import world.avatarhorizon.spigot.chatter.api.IChatSenderService;
import world.avatarhorizon.spigot.chatter.api.IChatterRegisterService;

import java.util.ResourceBundle;
import java.util.logging.Logger;

public class StaffChannelsAddonPlugin extends JavaPlugin
{
    private Logger logger;
    private ResourceBundle messages;

    private IChatSenderService senderService;

    @Override
    public void onLoad()
    {
        this.logger = getLogger();
        this.messages = ResourceBundle.getBundle("messages/commands");

        this.logger.info("Loading Chatter Services");
        this.senderService = getServer().getServicesManager().load(IChatSenderService.class);
        IChatterRegisterService registerService = getServer().getServicesManager().load(IChatterRegisterService.class);

        this.logger.info("Adding channels to Chatter");
        registerService.registerChannel(new AdminChannel());
        registerService.registerChannel(new HelperChannel());
        registerService.registerChannel(new AnimationChannel());
    }

    @Override
    public void onEnable()
    {
        this.logger.info("Enabled");
    }

    @Override
    public void onDisable()
    {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        try
        {
            if ("chatadmin".equals(command.getName()))
            {
                senderService.sendChat(sender, AdminChannel.NAME, String.join(" ", args));
            }
            else if ("chathelp".equals(command.getName()))
            {
                senderService.sendChat(sender, HelperChannel.NAME, String.join(" ", args));
            }
            else if ("chatanimation".equals(command.getName()))
            {
                senderService.sendChat(sender, AnimationChannel.NAME, String.join(" ", args));
            }
        }
        catch (ChatterChatException e)
        {
            ChatExceptionCause cause = e.getExceptionCause();
            if (ChatExceptionCause.CHANNEL_NOT_FOUND.equals(cause))
            {
                sender.sendMessage(messages.getString("error.channel.not_found"));
            }
            else if (ChatExceptionCause.ACCESS_NOT_ALLOWED.equals(cause))
            {
                sender.sendMessage(messages.getString("error.channel.not_allowed"));
            }
            else if (ChatExceptionCause.CANNOT_SEND_MESSAGE.equals(cause))
            {
                sender.sendMessage(messages.getString("error.channel.cannot_send"));
            }
        }
        return true;
    }
}
