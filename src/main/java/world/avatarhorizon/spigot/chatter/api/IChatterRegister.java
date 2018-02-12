package world.avatarhorizon.spigot.chatter.api;

public interface IChatterRegister
{
    void registerFormatter(IChatFormatter formatter);

    void registerChannel(ChannelHandler channel);
}
