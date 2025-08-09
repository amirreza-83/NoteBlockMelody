package me.nbm.noteblockmelody;

import me.nbm.noteblockmelody.config.InstrumentConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class NBMCommand implements CommandExecutor {

    private final NoteBlockMelody plugin;

    public NBMCommand(NoteBlockMelody plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
        
        if (!player.hasPermission("nbm.use")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (player.hasPermission("nbm.admin")) {
                reloadAllConfigs(player);
            } else {
                player.sendMessage(ChatColor.RED + "You don't have admin permission to use this command.");
            }
            return true;
        }

        player.sendMessage(ChatColor.GOLD + "=== NoteBlockMelody Commands ===");
        if (player.hasPermission("nbm.admin")) {
            player.sendMessage(ChatColor.YELLOW + "/nbm reload " + ChatColor.WHITE + "- Reload all instrument configs (Admin)");
        }
        player.sendMessage(ChatColor.YELLOW + "/guitar " + ChatColor.WHITE + "- Open guitar GUI");
        player.sendMessage(ChatColor.YELLOW + "/bass " + ChatColor.WHITE + "- Open bass GUI");
        player.sendMessage(ChatColor.YELLOW + "/bell " + ChatColor.WHITE + "- Open bell GUI");
        player.sendMessage(ChatColor.YELLOW + "/chime " + ChatColor.WHITE + "- Open chime GUI");
        player.sendMessage(ChatColor.YELLOW + "/flute " + ChatColor.WHITE + "- Open flute GUI");
        player.sendMessage(ChatColor.YELLOW + "/harp " + ChatColor.WHITE + "- Open harp GUI");
        player.sendMessage(ChatColor.YELLOW + "/hat " + ChatColor.WHITE + "- Open hat GUI");
        player.sendMessage(ChatColor.YELLOW + "/snare_drum " + ChatColor.WHITE + "- Open snare drum GUI");
        player.sendMessage(ChatColor.YELLOW + "/xylophone " + ChatColor.WHITE + "- Open xylophone GUI");

        return true;
    }

    private void reloadAllConfigs(Player player) {
        Map<String, String> configFiles = new HashMap<>();
        configFiles.put("Guitar", "Guitar.yml");
        configFiles.put("Bass", "Bass.yml");
        configFiles.put("Bell", "Bell.yml");
        configFiles.put("Chime", "Chime.yml");
        configFiles.put("Flute", "Flute.yml");
        configFiles.put("Harp", "Harp.yml");
        configFiles.put("Hat", "Hat.yml");
        configFiles.put("SnareDrum", "SnareDrum.yml");
        configFiles.put("Xylophone", "Xylophone.yml");

        int successCount = 0;
        int failCount = 0;

        player.sendMessage(ChatColor.YELLOW + "Starting to reload all instrument configs...");

        for (Map.Entry<String, String> entry : configFiles.entrySet()) {
            String instrumentName = entry.getKey();
            String fileName = entry.getValue();
            
            File file = new File(plugin.getDataFolder(), fileName);
            if (file.exists()) {
                try {
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    InstrumentConfigManager.getAll().put(instrumentName, config);
                    successCount++;
                    plugin.getLogger().info("[NBM] Successfully reloaded " + fileName);
                } catch (Exception e) {
                    failCount++;
                    plugin.getLogger().warning("[NBM] Failed to reload " + fileName + ": " + e.getMessage());
                    player.sendMessage(ChatColor.RED + "Failed to reload " + fileName + ": " + e.getMessage());
                }
            } else {
                failCount++;
                plugin.getLogger().warning("[NBM] Config file not found: " + fileName);
                player.sendMessage(ChatColor.RED + fileName + " not found!");
            }
        }

        // Reload main config
        try {
            plugin.reloadConfig();
            plugin.config = plugin.getConfig();
            player.sendMessage(ChatColor.GREEN + "Main config reloaded!");
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Failed to reload main config: " + e.getMessage());
        }

        // Reload instrument items after config reload
        if (successCount > 0) {
            try {
                plugin.guitarvalue();
                plugin.bassvalue();
                plugin.bellvalue();
                plugin.chimevalue();
                plugin.flutevalue();
                plugin.harpvalue();
                plugin.hatvalue();
                plugin.snareDrumvalue();
                plugin.xylophonevalue();
                player.sendMessage(ChatColor.GREEN + "Instrument items have been updated!");
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Failed to update instrument items: " + e.getMessage());
            }
        }

        if (successCount > 0) {
            player.sendMessage(ChatColor.GREEN + "Successfully reloaded " + successCount + " config files!");
        }
        
        if (failCount > 0) {
            player.sendMessage(ChatColor.RED + "Failed to reload " + failCount + " config files!");
        }

        if (successCount == configFiles.size()) {
            player.sendMessage(ChatColor.GREEN + "All instrument configs have been reloaded successfully!");
        } else if (successCount > 0) {
            player.sendMessage(ChatColor.YELLOW + "Some configs were reloaded successfully, but some failed.");
        } else {
            player.sendMessage(ChatColor.RED + "No config files could be reloaded!");
        }
    }
}
