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
    private List<String> spiedChannels;
    private boolean globalActive;
    private boolean globalMuted;
    private boolean localMuted;

    public ChatterPlayer(Player player)
    {
        this.player = player;
        this.spiedChannels = new LinkedList<>();
        this.globalMuted = false;
        this.localMuted = false;
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

    public boolean isGlobalMuted()
    {
        return this.globalMuted;
    }

    public boolean isLocalMuted()
    {
        return localMuted;
    }

    public void setGlobalMuted(boolean globalMuted)
    {
        this.globalMuted = globalMuted;
    }


    public void setLocalMuted(boolean localMuted)
    {
        this.localMuted = localMuted;
    }

    /**
     * Toggle the global muted state.
     * @return the new value of the globalMuted;
     */
    public boolean toggleGlobalMuted()
    {
        this.globalMuted = !this.globalMuted;
        return this.globalMuted;
    }

    /**
     * Toggle the local muted state.
     * @return the new value of the localMuted;
     */
    public boolean toggleLocalMuted()
    {
        this.localMuted = !this.localMuted;
        return this.localMuted;
    }
}
