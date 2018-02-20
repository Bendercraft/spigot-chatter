package world.avatarhorizon.spigot.chatter;

import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import world.avatarhorizon.spigot.chatter.api.IChatSenderService;
import world.avatarhorizon.spigot.chatter.api.IChatterRegisterService;
import world.avatarhorizon.spigot.chatter.commands.ChannelCommandExecutor;
import world.avatarhorizon.spigot.chatter.commands.ChatterCommandExecutor;
import world.avatarhorizon.spigot.chatter.controllers.ChatListener;
import world.avatarhorizon.spigot.chatter.controllers.ChatManager;
import world.avatarhorizon.spigot.chatter.controllers.ChatSenderService;
import world.avatarhorizon.spigot.chatter.controllers.ChatterRegisterService;
import world.avatarhorizon.spigot.chatter.formatters.PlayerFormatter;
import world.avatarhorizon.spigot.chatter.formatters.TitleFormatter;
import world.avatarhorizon.spigot.chatter.formatters.WorldFormatter;
import world.avatarhorizon.spigot.chatter.handlers.GlobalChannel;
import world.avatarhorizon.spigot.chatter.handlers.LocalChannel;
import world.avatarhorizon.spigot.chatter.models.ChatterChannel;

import java.util.logging.Logger;

public class ChatterPlugin extends JavaPlugin
{
    private ChatManager manager;
    private IChatterRegisterService register;
    private IChatSenderService sender;

    @Override
    public void onLoad()
    {
        ChatterSettings.loadSettings(this);
        Logger logger = getLogger();

        manager = new ChatManager(this, logger);

        manager.registerFormatter(new WorldFormatter());
        manager.registerFormatter(new PlayerFormatter());
        manager.registerFormatter(new TitleFormatter(manager));

        manager.setGlobalChannel(new ChatterChannel(new GlobalChannel(manager)));
        manager.setLocalChannel(new ChatterChannel(new LocalChannel(manager)));

        register = new ChatterRegisterService(manager);
        sender = new ChatSenderService(manager);

        getServer().getServicesManager().register(IChatterRegisterService.class, register, this, ServicePriority.Normal);
        getServer().getServicesManager().register(IChatSenderService.class, sender, this, ServicePriority.Normal);
    }

    @Override
    public void onEnable()
    {
        Logger logger = getLogger();

        manager.loadFormats();

        getServer().getPluginManager().registerEvents(new ChatListener(manager), this);

        getCommand("chatter").setExecutor(new ChatterCommandExecutor(manager, logger));
        getCommand("channel").setExecutor(new ChannelCommandExecutor(manager, logger));
    }

    @Override
    public void onDisable()
    {
        getServer().getServicesManager().unregister(IChatterRegisterService.class, register);
        getServer().getServicesManager().unregister(IChatSenderService.class, sender);
        register = null;
        manager = null;
        sender = null;
    }
}
