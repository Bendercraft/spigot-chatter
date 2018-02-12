package world.avatarhorizon.spigot.chatter.models;

public class ChatterChatException extends Exception
{
    private ChatExceptionCause cause;

    public ChatterChatException(ChatExceptionCause cause)
    {
        this.cause = cause;
    }

    public ChatExceptionCause getExceptionCause()
    {
        return cause;
    }
}
