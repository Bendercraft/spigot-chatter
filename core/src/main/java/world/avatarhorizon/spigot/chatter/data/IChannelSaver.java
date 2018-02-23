package world.avatarhorizon.spigot.chatter.data;

import world.avatarhorizon.spigot.chatter.models.ChatterChannel;
import world.avatarhorizon.spigot.chatter.models.ChatterChannelFormats;

import java.util.Collection;
import java.util.List;

public interface IChannelSaver
{
    public List<ChatterChannelFormats> loadFormats();

    public void saveFormats(Collection<ChatterChannel> channels);
}
