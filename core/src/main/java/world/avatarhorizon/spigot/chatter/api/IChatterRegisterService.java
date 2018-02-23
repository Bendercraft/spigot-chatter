package world.avatarhorizon.spigot.chatter.api;

public interface IChatterRegisterService
{
    void registerFormatter(IChatFormatter formatter);

    void registerChannel(ChannelHandler channel);
}
