package world.avatarhorizon.spigot.chatter.models;

import world.avatarhorizon.spigot.chatter.api.ChannelHandler;
import world.avatarhorizon.spigot.chatter.api.IChatFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatterChannel
{
    private ChannelHandler handler;
    private String format;
    private String spyFormat;
    private List<IChatFormatter> formatters;
    private List<IChatFormatter> spyFormatters;

    private List<IChatFormatter> safeFormatters;
    private List<IChatFormatter> safeSpyFormatters;

    public ChatterChannel(ChannelHandler handler)
    {
        this.handler = handler;
        this.formatters = new ArrayList<>();
        this.spyFormatters = new ArrayList<>();

        this.safeFormatters = Collections.unmodifiableList(formatters);
        this.safeSpyFormatters = Collections.unmodifiableList(spyFormatters);
    }

    public final String getFormat()
    {
        return this.format;
    }

    public final String getSpyFormat()
    {
        return this.spyFormat;
    }

    public final void setFormat(String format)
    {
        this.format = format;
    }

    public final void setSpyFormat(String spyFormat)
    {
        this.spyFormat = spyFormat;
    }

    public final String getName()
    {
        return this.handler.getName();
    }

    public List<IChatFormatter> getFormatters()
    {
        return this.safeFormatters;
    }

    public List<IChatFormatter> getSpyFormatters()
    {
        return this.safeSpyFormatters;
    }

    public void addFormatter(IChatFormatter formatter)
    {
        if (!this.formatters.contains(formatter))
        {
            this.formatters.add(formatter);
        }
    }

    public void addSpyFormatter(IChatFormatter formatter)
    {
        if (!this.spyFormatters.contains(formatter))
        {
            this.spyFormatters.add(formatter);
        }
    }

    public void clearFormatters()
    {
        this.formatters.clear();
        this.spyFormatters.clear();
    }

    public ChannelHandler getHandler()
    {
        return this.handler;
    }
}
