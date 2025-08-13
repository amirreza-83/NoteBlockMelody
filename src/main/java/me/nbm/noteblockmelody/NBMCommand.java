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
            sender.sendMessage(ChatColor.RED +  plugin.translate("this-command-player-only"));
            return true;
        }

        Player player = (Player) sender;
        
        if (!player.hasPermission("nbm.use")) {
            player.sendMessage(ChatColor.RED + plugin.translate("dont-have-permission-to-use"));
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (player.hasPermission("nbm.admin")) {
                reloadAllConfigs(player);
            } else {
                player.sendMessage(ChatColor.RED + plugin.translate("dont-have-permission-to-use"));
            }
            return true;
        }

        player.sendMessage(ChatColor.GOLD + plugin.translate("command-help-header"));
        if (player.hasPermission("nbm.admin")) {
            player.sendMessage(ChatColor.YELLOW + "/nbm reload " + ChatColor.WHITE + "- Reload all instrument configs (Admin)");
        }
        player.sendMessage(ChatColor.YELLOW + "/banjo " + ChatColor.WHITE + plugin.translate("command-help-banjo"));
        player.sendMessage(ChatColor.YELLOW + "/base_drum " + ChatColor.WHITE + plugin.translate("command-help-base_drum"));
        player.sendMessage(ChatColor.YELLOW + "/bass " + ChatColor.WHITE + plugin.translate("command-help-bass"));
        player.sendMessage(ChatColor.YELLOW + "/bell " + ChatColor.WHITE + plugin.translate("command-help-bell"));
        player.sendMessage(ChatColor.YELLOW + "/bit " + ChatColor.WHITE + plugin.translate("command-help-bit"));
        player.sendMessage(ChatColor.YELLOW + "/chime " + ChatColor.WHITE + plugin.translate("command-help-chime"));
        player.sendMessage(ChatColor.YELLOW + "/cow_bell " + ChatColor.WHITE + plugin.translate("command-help-cow_bell"));
        player.sendMessage(ChatColor.YELLOW + "/didgeridoo " + ChatColor.WHITE + plugin.translate("command-help-didgeridoo"));
        player.sendMessage(ChatColor.YELLOW + "/flute " + ChatColor.WHITE + plugin.translate("command-help-flute"));
        player.sendMessage(ChatColor.YELLOW + "/guitar " + ChatColor.WHITE + plugin.translate("command-help-guiter"));
        player.sendMessage(ChatColor.YELLOW + "/harp " + ChatColor.WHITE + plugin.translate("command-help-harp"));
        player.sendMessage(ChatColor.YELLOW + "/hat " + ChatColor.WHITE + plugin.translate("command-help-hat"));
        player.sendMessage(ChatColor.YELLOW + "/iron_xylophone " + ChatColor.WHITE + plugin.translate("command-help-iron_xylophone"));
        player.sendMessage(ChatColor.YELLOW + "/pling " + ChatColor.WHITE + plugin.translate("command-help-pling"));
        player.sendMessage(ChatColor.YELLOW + "/snare_drum " + ChatColor.WHITE + plugin.translate("command-help-snare_drum"));
        player.sendMessage(ChatColor.YELLOW + "/xylophone " + ChatColor.WHITE + plugin.translate("command-help-xylophone"));
        player.sendMessage(ChatColor.YELLOW + "/extra " + ChatColor.WHITE + plugin.translate("command-help-extra"));

        return true;
    }

    private void reloadAllConfigs(Player player) {
        Map<String, String> configFiles = new HashMap<>();
        configFiles.put("Banjo", "Banjo.yml");
        configFiles.put("BaseDrum", "BaseDrum.yml");
        configFiles.put("Bass", "Bass.yml");
        configFiles.put("Bell", "Bell.yml");
        configFiles.put("Bit", "Bit.yml");
        configFiles.put("Chime", "Chime.yml");
        configFiles.put("CowBell", "CowBell.yml");
        configFiles.put("Didgeridoo", "Didgeridoo.yml");
        configFiles.put("Flute", "Flute.yml");
        configFiles.put("Guitar", "Guitar.yml");
        configFiles.put("Harp", "Harp.yml");
        configFiles.put("Hat", "Hat.yml");
        configFiles.put("IronXylophone", "IronXylophone.yml");
        configFiles.put("Pling", "Pling.yml");
        configFiles.put("SnareDrum", "SnareDrum.yml");
        configFiles.put("Xylophone", "Xylophone.yml");
        configFiles.put("Extra", "Extra.yml");

        int successCount = 0;
        int failCount = 0;

        player.sendMessage(ChatColor.YELLOW + plugin.translate("starting-to-reload"));

        for (Map.Entry<String, String> entry : configFiles.entrySet()) {
            String instrumentName = entry.getKey();
            String fileName = entry.getValue();
            
            File file = new File(plugin.getDataFolder(), "instruments" + File.separator + fileName);
            if (file.exists()) {
                try {
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    InstrumentConfigManager.getAll().put(instrumentName, config);
                    successCount++;
                    plugin.getLogger().info(plugin.translate("prefix") + " " + plugin.translate("successfully-reloaded").replaceAll("\\$1",fileName));
                } catch (Exception e) {
                    failCount++;
                    plugin.getLogger().warning(plugin.translate("prefix") + " " + plugin.translate("faild-to-reloaded").replaceAll("\\$1",fileName) + ": " + e.getMessage());
                    player.sendMessage(ChatColor.RED + plugin.translate("prefix") + " " + plugin.translate("faild-to-reloaded").replaceAll("\\$1",fileName) + ": " + e.getMessage());
                }
            } else {
                failCount++;
                plugin.getLogger().warning(plugin.translate("prefix") + " " + plugin.translate("config-file-not-found").replaceAll("\\$1",fileName));
                player.sendMessage(ChatColor.RED + plugin.translate("file-not-found").replaceAll("\\$1",fileName));
            }
        }

        // Reload main config
        try {
            plugin.reloadConfig();
            plugin.config = plugin.getConfig();
            player.sendMessage(ChatColor.GREEN + plugin.translate("reloaded-main-config"));
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + plugin.translate("failed-to-reloaded-main-config") + e.getMessage());
        }

        try {
            plugin.langLoad();
            player.sendMessage(ChatColor.GREEN + plugin.translate("loaded-lang-file"));
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + plugin.translate("faild-to-load-lang-file") + e.getMessage());
        }

        // Reload instrument items after config reload
        if (successCount > 0) {
            try {
                plugin.banjovalue();
                plugin.baseDrumvalue();
                plugin.bassvalue();
                plugin.bellvalue();
                plugin.bitvalue();
                plugin.chimevalue();
                plugin.cowBellvalue();
                plugin.didgeridoovalue();
                plugin.flutevalue();
                plugin.guitarvalue();
                plugin.harpvalue();
                plugin.hatvalue();
                plugin.ironXylophonevalue();
                plugin.plingvalue();
                plugin.snareDrumvalue();
                plugin.xylophonevalue();
                plugin.extravalue();
                player.sendMessage(ChatColor.GREEN + plugin.translate("updated-instrument-item"));
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + plugin.translate("failed-to-update-instrument-item") + e.getMessage());
            }
        }

        if (successCount > 0) {
            player.sendMessage(ChatColor.GREEN + plugin.translate("reloaded-config-files").replaceAll("\\$1",Integer.toString(successCount)));
        }
        
        if (failCount > 0) {
            player.sendMessage(ChatColor.RED + plugin.translate("failed-to-reloaded-config-files").replaceAll("\\$1",Integer.toString(failCount)));
        }

        if (successCount == configFiles.size()) {
            player.sendMessage(ChatColor.GREEN + plugin.translate("reloaded-all-instruments-configs"));
        } else if (successCount > 0) {
            player.sendMessage(ChatColor.YELLOW + plugin.translate("faild-to-reload-a-part-of-configs"));
        } else {
            player.sendMessage(ChatColor.RED + plugin.translate("no-reloaded-configs"));
        }

        try {
            //   plugin.removeAllRecipes();
          if(plugin.config.getBoolean("craftable")) {
              plugin.createRecipe(plugin.banjoItem, plugin.banjoIngredients, plugin.banjoPattern, "Banjo");
              plugin.createRecipe(plugin.baseDrumItem, plugin.baseDrumIngredients, plugin.baseDrumPattern, "BaseDrum");
              plugin.createRecipe(plugin.baseDrumItem, plugin.baseDrumIngredients, plugin.baseDrumPattern, "Bass");
              plugin.createRecipe(plugin.bellItem, plugin.bellIngredients, plugin.bellPattern, "Bell");
              plugin.createRecipe(plugin.bitItem, plugin.bitIngredients, plugin.bitPattern, "Bit");
              plugin.createRecipe(plugin.chimeItem, plugin.chimeIngredients, plugin.chimePattern, "Chime");
              plugin.createRecipe(plugin.cowBellItem, plugin.cowBellIngredients, plugin.cowBellPattern, "CowBell");
              plugin.createRecipe(plugin.didgeridooItem, plugin.didgeridooIngredients, plugin.didgeridooPattern, "Didgeridoo");
              plugin.createRecipe(plugin.fluteItem, plugin.fluteIngredients, plugin.flutePattern, "Flute");
              plugin.createRecipe(plugin.guitarItem, plugin.guitarIngredients, plugin.guitarPattern, "Guitar");
              plugin.createRecipe(plugin.harpItem, plugin.harpIngredients, plugin.harpPattern, "Harp");
              plugin.createRecipe(plugin.hatItem, plugin.hatIngredients, plugin.hatPattern, "Hat");
              plugin.createRecipe(plugin.ironXylophoneItem, plugin.ironXylophoneIngredients, plugin.ironXylophonePattern, "IronXylophone");
              plugin.createRecipe(plugin.snareDrumItem, plugin.snareDrumIngredients, plugin.snareDrumPattern, "SnareDrum");
              plugin.createRecipe(plugin.plingItem, plugin.plingIngredients, plugin.plingPattern, "Pling");
              plugin.createRecipe(plugin.xylophoneItem, plugin.xylophoneIngredients, plugin.xylophonePattern, "Xylophone");
              plugin.createRecipe(plugin.extraItem, plugin.extraIngredients, plugin.extraPattern, "Extra");
              player.sendMessage(ChatColor.GREEN + plugin.translate("reloaded-recipes"));
            }
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + plugin.translate("faild-to-reload-recipes") + e.getMessage());
        }
    }
}
