package world.avatarhorizon.spigot.chatter.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import world.avatarhorizon.spigot.chatter.models.ChatterChannel;
import world.avatarhorizon.spigot.chatter.models.ChatterChannelFormats;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class JsonChannelSaver implements IChannelSaver
{
    private static final String FOLDER_NAME = "data";
    private static final String FILE_NAME = "formats.json";

    private Logger logger;

    private Gson gson;
    private final File formatsFile;

    public JsonChannelSaver(File pluginFolder, Logger logger)
    {
        this.logger = logger;

        GsonBuilder b = new GsonBuilder();
        JsonChannelSerializer serializer = new JsonChannelSerializer();
        b.registerTypeAdapter(ChatterChannel.class, serializer);
        b.registerTypeAdapter(ChatterChannelFormats.class, serializer);
        b.setPrettyPrinting();
        this.gson = b.create();

        File folder = new File(pluginFolder, FOLDER_NAME);
        if (!folder.exists())
        {
            folder.mkdirs();
        }
        formatsFile = new File(folder, FILE_NAME);
        if (!formatsFile.exists())
        {
            try
            {
                formatsFile.createNewFile();
            }
            catch (IOException e)
            {
                logger.severe("Unable to create formats.json");
                logger.severe(e.getMessage());
            }
        }
    }

    @Override
    public List<ChatterChannelFormats> loadFormats()
    {
        Type type = new TypeToken<LinkedList<ChatterChannelFormats>>() {}.getType();
        try (InputStream is = new FileInputStream(formatsFile))
        {
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            List<ChatterChannelFormats> formats = gson.fromJson(reader, type);
            if (formats == null || formats.isEmpty())
            {
                formats = Collections.emptyList();
            }
            return formats;
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
        }
        return null;
    }

    @Override
    public void saveFormats(Collection<ChatterChannel> channels)
    {
        if (formatsFile.exists())
        {
            try (OutputStream fos = new FileOutputStream(formatsFile))
            {
                OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                String json = gson.toJson(channels);
                writer.write(json);
                writer.close();
            }
            catch (IOException e)
            {
                logger.severe("IOException while writing player file");
                logger.severe(e.getMessage());
            }
        }
    }
}
