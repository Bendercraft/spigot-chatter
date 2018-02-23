package world.avatarhorizon.spigot.chatter.models;

public class ChatterChannelFormats
{
    public String name;
    public String format;
    public String spy_format;

    @Override
    public String toString()
    {
        return "{name: \"" + name + "\", format: \"" + format + "\", spy_format: \"" + spy_format + "\"}";
    }
}
