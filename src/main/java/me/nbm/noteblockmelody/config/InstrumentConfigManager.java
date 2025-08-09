package me.nbm.noteblockmelody.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class InstrumentConfigManager {

    private static final String[] instrumentFiles = {
            "Harp", "Bass", "SnareDrum", "Hat", "Flute",
            "Bell", "Guitar", "Chime", "Xylophone"
    };

    private static final Map<String, FileConfiguration> configs = new HashMap<>();

    public static void init(JavaPlugin plugin) {
        File folder = plugin.getDataFolder();
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (String name : instrumentFiles) {
            File file = new File(folder, name + ".yml");

            if (!file.exists()) {
                try {
                    plugin.saveResource(name + ".yml", false);
                    plugin.getLogger().info("[NBM] Created default file: " + name + ".yml");
                } catch (Exception e) {
                    plugin.getLogger().warning("[NBM] Failed to create default file: " + name + ".yml - " + e.getMessage());
                    continue;
                }
            }

            try {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                configs.put(name, config);
            } catch (Exception e) {
                plugin.getLogger().warning("[NBM] Failed to load config file: " + name + ".yml - " + e.getMessage());
                           }
           }

           File lowercaseGuitar = new File(folder, "guitar.yml");
        if (lowercaseGuitar.exists()) {
            try {
                configs.put("guitar", YamlConfiguration.loadConfiguration(lowercaseGuitar));
            } catch (Exception e) {
                plugin.getLogger().warning("[NBM] Failed to load guitar.yml - " + e.getMessage());
            }
        }
    }

    public static FileConfiguration get(String name) {
        return configs.get(name);
    }

    public static Map<String, FileConfiguration> getAll() {
        return configs;
    }
}
