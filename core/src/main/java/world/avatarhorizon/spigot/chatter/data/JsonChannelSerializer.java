package world.avatarhorizon.spigot.chatter.data;

import com.google.gson.*;
import world.avatarhorizon.spigot.chatter.models.ChatterChannel;
import world.avatarhorizon.spigot.chatter.models.ChatterChannelFormats;

import java.lang.reflect.Type;

public class JsonChannelSerializer implements JsonSerializer<ChatterChannel>, JsonDeserializer<ChatterChannelFormats>
{
    @Override
    public ChatterChannelFormats deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        ChatterChannelFormats formats = new ChatterChannelFormats();

        JsonObject root = (JsonObject) jsonElement;

        formats.name = root.get("name").getAsString();
        formats.format = root.get("format").getAsString();
        formats.spy_format = root.get("spy_format").getAsString();

        return formats;
    }

    @Override
    public JsonElement serialize(ChatterChannel channel, Type type, JsonSerializationContext context)
    {
        JsonObject root = new JsonObject();
        root.addProperty("name", channel.getName());
        root.addProperty("format", channel.getFormat());
        root.addProperty("spy_format", channel.getSpyFormat());
        return root;
    }
}
