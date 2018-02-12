package world.avatarhorizon.spigot.chatter.models;

public class ChatterException extends Exception
{
    private String message;

    public ChatterException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return this.message;
    }
}
