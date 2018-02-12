package world.avatarhorizon.spigot.chatter.models;

import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ChatterPlayer
{
    private Player player;
    private String title;
    private ChatterChannel currentChannel;
    private boolean globalActive;
    private List<String> spiedChannels;

    public ChatterPlayer(Player player)
    {
        this.player = player;
        this.spiedChannels = new LinkedList<>();
    }

    public final Player getPlayer()
    {
        return player;
    }

    public final void setPlayer(Player player)
    {
        if (this.player == null && player != null)
        {
            this.player = player;
        }
    }

    public ChatterChannel getCurrentChannel()
    {
        return currentChannel;
    }

    public void setCurrentChannel(ChatterChannel currentChannel)
    {
        this.currentChannel = currentChannel;
    }

    public boolean isGlobalActive()
    {
        return globalActive;
    }

    public void setGlobalActive(boolean globalActive)
    {
        this.globalActive = globalActive;
    }

    public void toggleGlobalActive()
    {
        this.globalActive = !this.globalActive;
    }

    public boolean isSpying(String channelName)
    {
        return spiedChannels.contains(channelName);
    }

    public void addSpyChannel(String channelName)
    {
        if (!spiedChannels.contains(channelName))
        {
            spiedChannels.add(channelName);
        }
    }

    public void removeSpyChannel(String channelName)
    {
        spiedChannels.remove(channelName);
    }

    public List<String> getSpiedChannels()
    {
        return Collections.unmodifiableList(spiedChannels);
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
