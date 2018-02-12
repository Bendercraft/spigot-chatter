package world.avatarhorizon.spigot.chatter.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.chatter.models.ChatterChannel;
import world.avatarhorizon.spigot.chatter.models.ChatterPlayer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

public class JsonChatterPlayerSaver implements IChatterPlayerSaver
{
    private static final String PLAYERS_FOLDER_NAME = "data/players";

    private Logger logger;

    private Gson gson;
    private final File playersFolder;

    public JsonChatterPlayerSaver(File pluginFolder, Map<String, ChatterChannel> channels, Logger logger)
    {
        this.logger = logger;

        GsonBuilder b = new GsonBuilder();
        b.registerTypeAdapter(ChatterPlayer.class, new JsonChatterPlayerSerializer(channels));
        this.gson = b.create();

        this.playersFolder = new File(pluginFolder, PLAYERS_FOLDER_NAME);
        if (!playersFolder.exists())
        {
            playersFolder.mkdirs();
        }
    }

    @Override
    public void save(ChatterPlayer player)
    {
        File playerFile = new File(playersFolder, player.getPlayer().getUniqueId().toString()+".json");
        if (!playerFile.exists())
        {
            try
            {
                playerFile.createNewFile();
            }
            catch (IOException e)
            {
                logger.severe("Unable to create save file for " + player.getPlayer().getName());
                logger.severe(e.getMessage());
            }
        }

        if (playerFile.exists())
        {
            try (OutputStream fos = new FileOutputStream(playerFile))
            {
                OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                String json = gson.toJson(player);
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

    @Override
    public ChatterPlayer load(Player player)
    {
        File file = new File(playersFolder, player.getUniqueId().toString() + ".json");
        if (file.exists() && file.isFile())
        {
            try (InputStream is = new FileInputStream(file))
            {
                InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                ChatterPlayer p = gson.fromJson(reader, ChatterPlayer.class);
                return p;
            }
            catch (IOException e)
            {
                logger.warning(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void delete(ChatterPlayer player)
    {
        File file = new File(playersFolder, player.getPlayer().getUniqueId().toString() + ".json");
        if (file.exists())
        {
            file.delete();
        }
    }
}
