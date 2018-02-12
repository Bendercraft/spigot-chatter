package world.avatarhorizon.spigot.chatter.controllers;

import world.avatarhorizon.spigot.chatter.api.ChannelHandler;
import world.avatarhorizon.spigot.chatter.api.IChatFormatter;
import world.avatarhorizon.spigot.chatter.api.IChatterRegister;
import world.avatarhorizon.spigot.chatter.models.ChatterChannel;

/*
    Use another class instead of making ChatManager inheritance to IChatterRegister
    because I do not want to risk someone to cast it and getting the access to all methods.
 */
public class ChatterRegister implements IChatterRegister
{
    private ChatManager chatManager;

    public ChatterRegister(ChatManager manager)
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
