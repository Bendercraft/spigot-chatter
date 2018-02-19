package world.avatarhorizon.spigot.chatter.controllers;

import world.avatarhorizon.spigot.chatter.api.ChannelHandler;
import world.avatarhorizon.spigot.chatter.api.IChatFormatter;
import world.avatarhorizon.spigot.chatter.api.IChatterRegisterService;
import world.avatarhorizon.spigot.chatter.models.ChatterChannel;

/*
    Use another class instead of making ChatManager inheritance to IChatterRegisterService
    because I do not want to risk someone to cast it and getting the access to all methods.
 */
public class ChatterRegisterService implements IChatterRegisterService
{
    private ChatManager chatManager;

    public ChatterRegisterService(ChatManager manager)
    {
        this.chatManager = manager;
    }

    @Override
    public void registerFormatter(IChatFormatter formatter)
    {
        this.chatManager.registerFormatter(formatter);
    }

    @Override
    public void registerChannel(ChannelHandler channelHandler)
    {
        ChatterChannel channel = new ChatterChannel(channelHandler);
        this.chatManager.registerChannel(channel);
    }
}
