package world.avatarhorizon.spigot.chatter.data;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.models.ChatterChannel;
import world.avatarhorizon.spigot.chatter.models.ChatterPlayer;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;

public class JsonChatterPlayerSerializer implements JsonSerializer<ChatterPlayer>, JsonDeserializer<ChatterPlayer>
{
    private Map<String, ChatterChannel> channels;

    public JsonChatterPlayerSerializer(Map<String, ChatterChannel> channels)
    {
        this.channels = channels;
    }

    @Override
    public ChatterPlayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException
    {
        JsonObject root = (JsonObject) jsonElement;

        String pId = root.get("player_id").getAsString();
        Player player = Bukkit.getPlayer(UUID.fromString(pId));

        ChatterPlayer chatPlayer = new ChatterPlayer(player);
        chatPlayer.setGlobalActive(root.get("global_active").getAsBoolean());
        JsonElement channel = root.get("current_channel");
        if (channel != null)
        {
            chatPlayer.setCurrentChannel(this.channels.get(channel.getAsString()));
        }

        JsonElement title = root.get("title");
        if (title != null)
        {
            chatPlayer.setTitle(title.getAsString());
        }

        JsonElement spied = root.get("spied_channels");
        JsonArray chans = spied.getAsJsonArray();
        for (JsonElement chan : chans)
        {
            chatPlayer.addSpyChannel(chan.getAsString());
        }

        return chatPlayer;
    }

    @Override
    public JsonElement serialize(ChatterPlayer player, Type type, JsonSerializationContext ctx)
    {
        JsonObject root = new JsonObject();

        root.addProperty("player_id", player.getPlayer().getUniqueId().toString());
        root.addProperty("player_name", player.getPlayer().getName());
        root.addProperty("title", player.getTitle());
        root.addProperty("global_active", player.isGlobalActive());
        if (player.getCurrentChannel() != null)
        {
            root.addProperty("current_channel", player.getCurrentChannel().getName());
        }
        JsonArray spied = new JsonArray();
        for (String chan : player.getSpiedChannels())
        {
            spied.add(chan);
        }
        root.add("spied_channels", spied);

        return root;
    }
}
