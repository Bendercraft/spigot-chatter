package world.avatarhorizon.spigot.chatter.api;

import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * A chat formatter that will be called <b>only once</b> per message to format the given message
 */
public interface IChatFormatter
{
    /**
     * The list of tags handled by this formatter. <br>
     * It is used to link this formatter with channels that has a format containing these tags. <br>
     * A tag should look like "<i>{YOUR_TAG}</i>"
     * @return a List of Tags
     */
    List<String> getHandledTags();

    /**
     * This method is called once per message. <br>
     * You should implement it so that it edit the message according to the format and the tags you handle. <br>
     * You should return the new string once it is formatted.
     * @param sender The Player that sent the message.
     * @param format The format of the message.
     * @return a String containing the format with the tags replaced by actual values.
     */
    String formatMessage(CommandSender sender, String format);
}
