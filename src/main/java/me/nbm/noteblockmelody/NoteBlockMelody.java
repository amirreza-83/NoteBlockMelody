package me.nbm.noteblockmelody;

import me.nbm.noteblockmelody.config.InstrumentConfigManager;
import me.nbm.noteblockmelody.NBMCommand;
import me.nbm.noteblockmelody.notes.banjo.banjo_command;
import me.nbm.noteblockmelody.notes.banjo.banjo_event;
import me.nbm.noteblockmelody.notes.base_drum.base_drum_command;
import me.nbm.noteblockmelody.notes.base_drum.base_drum_event;
import me.nbm.noteblockmelody.notes.bass.bass_command;
import me.nbm.noteblockmelody.notes.bass.bass_event;
import me.nbm.noteblockmelody.notes.bell.bell_command;
import me.nbm.noteblockmelody.notes.bell.bell_event;
import me.nbm.noteblockmelody.notes.bit.bit_command;
import me.nbm.noteblockmelody.notes.bit.bit_event;
import me.nbm.noteblockmelody.notes.chime.chime_command;
import me.nbm.noteblockmelody.notes.chime.chime_event;
import me.nbm.noteblockmelody.notes.cow_bell.cow_bell_command;
import me.nbm.noteblockmelody.notes.cow_bell.cow_bell_event;
import me.nbm.noteblockmelody.notes.didgeridoo.didgeridoo_command;
import me.nbm.noteblockmelody.notes.didgeridoo.didgeridoo_event;
import me.nbm.noteblockmelody.notes.flute.flute_command;
import me.nbm.noteblockmelody.notes.flute.flute_event;
import me.nbm.noteblockmelody.notes.guitar.guitar_command;
import me.nbm.noteblockmelody.notes.guitar.guitar_event;
import me.nbm.noteblockmelody.notes.harp.harp_command;
import me.nbm.noteblockmelody.notes.harp.harp_event;
import me.nbm.noteblockmelody.notes.hat.hat_command;
import me.nbm.noteblockmelody.notes.hat.hat_event;
import me.nbm.noteblockmelody.notes.iron_xylophone.iron_xylophone_command;
import me.nbm.noteblockmelody.notes.iron_xylophone.iron_xylophone_event;
import me.nbm.noteblockmelody.notes.pling.pling_command;
import me.nbm.noteblockmelody.notes.pling.pling_event;
import me.nbm.noteblockmelody.notes.snare_drum.snare_drum_command;
import me.nbm.noteblockmelody.notes.snare_drum.snare_drum_event;
import me.nbm.noteblockmelody.notes.xylophone.xylophone_command;
import me.nbm.noteblockmelody.notes.xylophone.xylophone_event;
import me.nbm.noteblockmelody.notes.extra.extra_command;
import me.nbm.noteblockmelody.notes.extra.extra_event;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Map;

public class NoteBlockMelody extends JavaPlugin {

    public FileConfiguration config;
    public FileConfiguration guitarConfig;

    public Map<String, FileConfiguration> instrumentConfigs = new HashMap<>();

    public String guiTitle;
    public List<String> stringItems;
    public List<Double> pitches;
    public List<String> sounds;
    public List<Integer> stringItemPositions;
    public List<String> stringNames;
    public ItemStack extraItem;
    public ItemStack snareDrumItem;
    public ItemStack xylophoneItem;
    public ItemStack plingItem;
    public ItemStack ironXylophoneItem;
    public ItemStack hatItem;
    public ItemStack harpItem;
    public ItemStack guitarItem;
    public ItemStack fluteItem;
    public ItemStack didgeridooItem;
    public ItemStack cowBellItem;
    public ItemStack chimeItem;
    public ItemStack bitItem;
    public ItemStack bellItem;
    public ItemStack bassItem;
    public ItemStack baseDrumItem;
    public ItemStack banjoItem;

    public ConsoleCommandSender message;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        message = Bukkit.getConsoleSender();
        saveDefaultInstrumentConfigs();
        InstrumentConfigManager.init(this);
        
        detectServerVersionAndUpdateConfigs();

        getCommand("nbm").setExecutor(new NBMCommand(this));

        getCommand("banjo").setExecutor(new banjo_command(this));
        Bukkit.getPluginManager().registerEvents(new banjo_event(this), this);

        getCommand("base_drum").setExecutor(new base_drum_command(this));
        Bukkit.getPluginManager().registerEvents(new base_drum_event(this), this);

        getCommand("bass").setExecutor(new bass_command(this));
        Bukkit.getPluginManager().registerEvents(new bass_event(this), this);

        getCommand("bell").setExecutor(new bell_command(this));
        Bukkit.getPluginManager().registerEvents(new bell_event(this), this);

        getCommand("bit").setExecutor(new bit_command(this));
        Bukkit.getPluginManager().registerEvents(new bit_event(this), this);

        getCommand("chime").setExecutor(new chime_command(this));
        Bukkit.getPluginManager().registerEvents(new chime_event(this), this);

        getCommand("cow_bell").setExecutor(new cow_bell_command(this));
        Bukkit.getPluginManager().registerEvents(new cow_bell_event(this), this);

        getCommand("didgeridoo").setExecutor(new didgeridoo_command(this));
        Bukkit.getPluginManager().registerEvents(new didgeridoo_event(this), this);

        getCommand("flute").setExecutor(new flute_command(this));
        Bukkit.getPluginManager().registerEvents(new flute_event(this), this);

        getCommand("guitar").setExecutor(new guitar_command(this));
        Bukkit.getPluginManager().registerEvents(new guitar_event(this), this);

        getCommand("harp").setExecutor(new harp_command(this));
        Bukkit.getPluginManager().registerEvents(new harp_event(this), this);

        getCommand("hat").setExecutor(new hat_command(this));
        Bukkit.getPluginManager().registerEvents(new hat_event(this), this);

        getCommand("iron_xylophone").setExecutor(new iron_xylophone_command(this));
        Bukkit.getPluginManager().registerEvents(new iron_xylophone_event(this), this);

        getCommand("pling").setExecutor(new pling_command(this));
        Bukkit.getPluginManager().registerEvents(new pling_event(this), this);

        getCommand("snare_drum").setExecutor(new snare_drum_command(this));
        Bukkit.getPluginManager().registerEvents(new snare_drum_event(this), this);

        getCommand("xylophone").setExecutor(new xylophone_command(this));
        Bukkit.getPluginManager().registerEvents(new xylophone_event(this), this);

        getCommand("extra").setExecutor(new extra_command(this));
        Bukkit.getPluginManager().registerEvents(new extra_event(this), this);

        message.sendMessage("§6===============================================");
        message.sendMessage("§6                                               ");
        message.sendMessage("§6    §eNoteBlockMelody v1.7-α                   ");
        message.sendMessage("§6                                               ");
        message.sendMessage("§6           §bMade by Minestar                  ");
        message.sendMessage("§6                                               ");
        message.sendMessage("§6===============================================");
        message.sendMessage("§6 §aBanjo  §bBaseDrum   §cBass    §dBell    §eBit");
        message.sendMessage("§6 §fChime  §1CowBell    §2Didgeridoo §3Flute");
        message.sendMessage("§6 §4Guitar §5Harp       §6Hat     §7IronXylophone");
        message.sendMessage("§6 §8Pling  §9SnareDrum  §aXylophone  §bExtra");
        message.sendMessage("§6===============================================");
        message.sendMessage("§6        §2All instruments loaded!              ");
        message.sendMessage("§6===============================================");

        extravalue();
        xylophonevalue();
        snareDrumvalue();
        plingvalue();
        ironXylophonevalue();
        hatvalue();
        harpvalue();
        guitarvalue();
        flutevalue();
        didgeridoovalue();
        cowBellvalue();
        chimevalue();
        bitvalue();
        bellvalue();
        bassvalue();
        baseDrumvalue();
        banjovalue();

        if(config.getBoolean("craftable")) {
          createExtraRecipe();
          createXylophoneRecipe();
          createSnareDrumRecipe();
          createPlingRecipe();
          createIronXylophoneRecipe();
          createHatRecipe();
          createHarpRecipe();
          createGuitarRecipe();
          createFluteRecipe();
          createDidgeridooRecipe();
          createCowBellRecipe();
          createChimeRecipe();
          createBitRecipe();
          createBellRecipe();
          createBassRecipe();
          createBaseDrumRecipe();
          createBanjoRecipe();
        }

        message.sendMessage("§d===============================================");
        message.sendMessage("§d                                               ");
        message.sendMessage("§d        §aNoteBlockMelody is Ready!             ");
        message.sendMessage("§d                                               ");
        message.sendMessage("§d    §6Let's make some beautiful music!         ");
        message.sendMessage("§d                                               ");
        message.sendMessage("§d===============================================");
    }

    @Override
    public void onDisable() {
        removeAllRecipes();
        message.sendMessage("§c===============================================");
        message.sendMessage("§c                                               ");
        message.sendMessage("§c      §4NoteBlockMelody Disabled                ");
        message.sendMessage("§c                                               ");
        message.sendMessage("§c        §6Thanks for using our plugin!         ");
        message.sendMessage("§c                                               ");
        message.sendMessage("§c===============================================");
    }
    
    private void removeAllRecipes() {
        try {
            java.util.Iterator<org.bukkit.inventory.Recipe> iterator = Bukkit.recipeIterator();
            while (iterator.hasNext()) {
                org.bukkit.inventory.Recipe recipe = iterator.next();
                if (recipe instanceof org.bukkit.inventory.ShapedRecipe) {
                    org.bukkit.inventory.ShapedRecipe shapedRecipe = (org.bukkit.inventory.ShapedRecipe) recipe;
                    if(config.getBoolean("craftable")) {
                        if (shapedRecipe.getResult().isSimilar(guitarItem) ||
                            shapedRecipe.getResult().isSimilar(baseDrumItem) ||
                            shapedRecipe.getResult().isSimilar(bassItem) ||
                            shapedRecipe.getResult().isSimilar(bellItem) ||
                            shapedRecipe.getResult().isSimilar(chimeItem) ||
                            shapedRecipe.getResult().isSimilar(cowBellItem) ||
                            shapedRecipe.getResult().isSimilar(didgeridooItem) ||
                            shapedRecipe.getResult().isSimilar(fluteItem) ||
                            shapedRecipe.getResult().isSimilar(harpItem) ||
                            shapedRecipe.getResult().isSimilar(hatItem) ||
                            shapedRecipe.getResult().isSimilar(ironXylophoneItem) ||
                            shapedRecipe.getResult().isSimilar(plingItem) ||
                            shapedRecipe.getResult().isSimilar(snareDrumItem) ||
                            shapedRecipe.getResult().isSimilar(xylophoneItem) ||
                            shapedRecipe.getResult().isSimilar(extraItem) ) {
                            iterator.remove();
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }
    
    private void saveDefaultInstrumentConfigs() {
        String[] configFiles = {"Banjo.yml", "BaseDrum.yml", "Bass.yml", "Bell.yml",
                               "Bit.yml", "Chime.yml", "CowBell.yml", "Didgeridoo.yml",
                               "Flute.yml", "Guitar.yml", "Harp.yml", "Hat.yml",
                               "IronXylophone.yml", "Pling.yml", "SnareDrum.yml",
                               "Xylophone.yml", "Extra.yml"
                               };
        
        String version = extractVersionNumber(Bukkit.getBukkitVersion());
        boolean useModernConfigs = !isVersion1_12_or_below(version);
        
        message.sendMessage("§b===============================================");
        if (useModernConfigs) {
            message.sendMessage("§a Loading Modern Configs (1.13+)");
            message.sendMessage("§6 Source: new/ folder");
            message.sendMessage("§b----------------------------------------------");
            int loadedCount = 0;
            for (String configFile : configFiles) {
                File targetFile = new File(getDataFolder(), configFile);
                if (!targetFile.exists()) {
                    saveResource("new/" + configFile, false);
                    File sourceFile = new File(getDataFolder(), "new/" + configFile);
                    if (sourceFile.exists()) {
                        try {
                            java.nio.file.Files.copy(sourceFile.toPath(), targetFile.toPath());
                            sourceFile.delete();
                            loadedCount++;
                            String fileName = configFile.replace(".yml", "");
                            message.sendMessage("§a ✓ " + fileName);
                        } catch (Exception e) {
                            message.sendMessage("§c ✗ " + configFile);
                        }
                    }
                }
            }
            File newFolder = new File(getDataFolder(), "new");
            if (newFolder.exists() && newFolder.isDirectory()) {
                newFolder.delete();
            }
            message.sendMessage("§b-----------------------------------------------");
            message.sendMessage("§2 Loaded " + loadedCount + " modern configs successfully!");
        } else {
            message.sendMessage("§a Loading Legacy Configs (1.12-)");
            message.sendMessage("§6 Source: default resources");
            message.sendMessage("§b-----------------------------------------------");
            int loadedCount = 0;
            for (String configFile : configFiles) {
                if (!new File(getDataFolder(), configFile).exists()) {
                    saveResource(configFile, false);
                    loadedCount++;
                    String fileName = configFile.replace(".yml", "");
                    message.sendMessage("§a ✓ " + fileName);
                }
            }
            message.sendMessage("§b----------------------------------------------");
            message.sendMessage("§2 Loaded " + loadedCount + " legacy configs successfully!");
        }
        message.sendMessage("§b===============================================");
    }

    private void detectServerVersionAndUpdateConfigs() {
        String version = Bukkit.getVersion();
        String bukkitVersion = Bukkit.getBukkitVersion();
        
        message.sendMessage("§e╔═════════════════════════════════════════════╗");
        message.sendMessage("§e          §6NoteBlockMelody v1.7-α             ");
        message.sendMessage("§e              §bVersion Detection              ");
        message.sendMessage("§e╠═════════════════════════════════════════════╣");
        message.sendMessage("§e §aServer: §f" + String.format("%-32s", version));
        message.sendMessage("§e §aBukkit: §f" + String.format("%-32s", bukkitVersion));
        
        String versionNumber = extractVersionNumber(bukkitVersion);
        message.sendMessage("§e §aVersion: §f" + String.format("%-31s", versionNumber));
        message.sendMessage("§e╠═════════════════════════════════════════════╣");
        
        if (isVersion1_12_or_below(versionNumber)) {
            message.sendMessage("§e §bStatus: §6Legacy Mode §7(1.12 & below)");
            message.sendMessage("§e §dSounds: §fClassic Note Block Sounds");
        } else {
            message.sendMessage("§e §bStatus: §6Modern Mode §7(1.13+)");
            message.sendMessage("§e §dSounds: §fAdvanced Note Block Sounds");
        }
        message.sendMessage("§e §2Status: §aReady to Rock!");
        message.sendMessage("§e╚═════════════════════════════════════════════╝");
    }
    
    private String extractVersionNumber(String bukkitVersion) {
        if (bukkitVersion.contains("-")) {
            String[] parts = bukkitVersion.split("-");
            if (parts.length > 0) {
                return parts[0];
            }
        }
        return bukkitVersion;
    }
    
    private boolean isVersion1_12_or_below(String version) {
        return version.startsWith("1.12") || version.startsWith("1.11") || 
               version.startsWith("1.10") || version.startsWith("1.9") || 
               version.startsWith("1.8") || version.startsWith("1.7");
    }

    public void banjovalue() {
        banjoItem = new ItemStack(Material.STICK);
        ItemMeta meta = banjoItem.getItemMeta();
        if (meta != null) {
            FileConfiguration banjoConfig = InstrumentConfigManager.get("Banjo");
            String banjoName = "&6Epic Banjo";
            if (banjoConfig != null) {
                banjoName = banjoConfig.getString("banjo-name", banjoName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', banjoName));
            banjoItem.setItemMeta(meta);
        }
    }

    public void baseDrumvalue() {
        baseDrumItem = new ItemStack(Material.STICK);
        ItemMeta meta = baseDrumItem.getItemMeta();
        if (meta != null) {
            FileConfiguration BaseDrumConfig = InstrumentConfigManager.get("BaseDrum");
            String BaseDrumName = "&6Epic Base Drum";
            if (BaseDrumConfig != null) {
                BaseDrumName = BaseDrumConfig.getString("base_drum-name", BaseDrumName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', BaseDrumName));
            baseDrumItem.setItemMeta(meta);
        }
    }

    public void bassvalue() {
        bassItem = new ItemStack(Material.STICK);
        ItemMeta meta = bassItem.getItemMeta();
        if (meta != null) {
            FileConfiguration bassConfig = InstrumentConfigManager.get("Bass");
            String bassName = "&6Epic Bass";
            if (bassConfig != null) {
                bassName = bassConfig.getString("bass-name", bassName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', bassName));
            bassItem.setItemMeta(meta);
        }
    }

    public void bellvalue() {
        bellItem = new ItemStack(Material.STICK);
        ItemMeta meta = bellItem.getItemMeta();
        if (meta != null) {
            FileConfiguration bellConfig = InstrumentConfigManager.get("Bell");
            String bellName = "&6Epic Bell";
            if (bellConfig != null) {
                bellName = bellConfig.getString("bell-name", bellName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', bellName));
            bellItem.setItemMeta(meta);
        }
    }
    public void bitvalue() {
        bitItem = new ItemStack(Material.STICK);
        ItemMeta meta = bitItem.getItemMeta();
        if (meta != null) {
            FileConfiguration bitConfig = InstrumentConfigManager.get("Bit");
            String bitName = "&6Epic Bit";
            if (bitConfig != null) {
                bitName = bitConfig.getString("bit-name", bitName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', bitName));
            bitItem.setItemMeta(meta);
        }
    }
    public void chimevalue() {
        chimeItem = new ItemStack(Material.STICK);
        ItemMeta meta = chimeItem.getItemMeta();
        if (meta != null) {
            FileConfiguration chimeConfig = InstrumentConfigManager.get("Chime");
            String chimeName = "&6Epic Chime";
            if (chimeConfig != null) {
                chimeName = chimeConfig.getString("chime-name", chimeName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', chimeName));
            chimeItem.setItemMeta(meta);
        }
    }

        public void cowBellvalue() {
        cowBellItem = new ItemStack(Material.STICK);
        ItemMeta meta = cowBellItem.getItemMeta();
        if (meta != null) {
            FileConfiguration CowBellConfig = InstrumentConfigManager.get("CowBell");
            String CowBellName = "&6Epic Cow Bell";
            if (CowBellConfig != null) {
                CowBellName = CowBellConfig.getString("cow_bell-name", CowBellName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', CowBellName));
            cowBellItem.setItemMeta(meta);
        }
    }

    public void didgeridoovalue() {
        didgeridooItem = new ItemStack(Material.STICK);
        ItemMeta meta = didgeridooItem.getItemMeta();
        if (meta != null) {
            FileConfiguration didgeridooConfig = InstrumentConfigManager.get("Didgeridoo");
            String didgeridooName = "&6Epic Didgeridoo";
            if (didgeridooConfig != null) {
                didgeridooName = didgeridooConfig.getString("didgeridoo-name", didgeridooName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', didgeridooName));
            didgeridooItem.setItemMeta(meta);
        }
    }

    public void flutevalue() {
        fluteItem = new ItemStack(Material.STICK);
        ItemMeta meta = fluteItem.getItemMeta();
        if (meta != null) {
            FileConfiguration fluteConfig = InstrumentConfigManager.get("Flute");
            String fluteName = "&6Epic Flute";
            if (fluteConfig != null) {
                fluteName = fluteConfig.getString("flute-name", fluteName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', fluteName));
            fluteItem.setItemMeta(meta);
        }
    }

    public void guitarvalue() {
        guitarItem = new ItemStack(Material.STICK);
        ItemMeta meta = guitarItem.getItemMeta();
        if (meta != null) {
            FileConfiguration guitarConfig = InstrumentConfigManager.get("Guitar");
            String guitarName = "&6Epic Guitar";
            if (guitarConfig != null) {
                guitarName = guitarConfig.getString("guitar-name", guitarName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', guitarName));
            guitarItem.setItemMeta(meta);
        }
    }

    public void harpvalue() {
        harpItem = new ItemStack(Material.STICK);
        ItemMeta meta = harpItem.getItemMeta();
        if (meta != null) {
            FileConfiguration harpConfig = InstrumentConfigManager.get("Harp");
            String harpName = "&6Epic Harp";
            if (harpConfig != null) {
                harpName = harpConfig.getString("harp-name", harpName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', harpName));
            harpItem.setItemMeta(meta);
        }
    }

    public void hatvalue() {
        hatItem = new ItemStack(Material.STICK);
        ItemMeta meta = hatItem.getItemMeta();
        if (meta != null) {
            FileConfiguration hatConfig = InstrumentConfigManager.get("Hat");
            String hatName = "&6Epic Hat";
            if (hatConfig != null) {
                hatName = hatConfig.getString("hat-name", hatName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', hatName));
            hatItem.setItemMeta(meta);
        }
    }

    public void ironXylophonevalue() {
        ironXylophoneItem = new ItemStack(Material.STICK);
        ItemMeta meta = ironXylophoneItem.getItemMeta();
        if (meta != null) {
            FileConfiguration ironXylophoneConfig = InstrumentConfigManager.get("IronXylophone");
            String ironXylophoneName = "&6Epic Iron Xylophone";
            if (ironXylophoneConfig != null) {
                ironXylophoneName = ironXylophoneConfig.getString("iron_xylophone-name", ironXylophoneName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ironXylophoneName));
            ironXylophoneItem.setItemMeta(meta);
        }
    }

        public void plingvalue() {
        plingItem = new ItemStack(Material.STICK);
        ItemMeta meta = plingItem.getItemMeta();
        if (meta != null) {
            FileConfiguration plingConfig = InstrumentConfigManager.get("Pling");
            String plingName = "&6Epic Pling";
            if (plingConfig != null) {
                plingName = plingConfig.getString("pling-name", plingName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plingName));
            plingItem.setItemMeta(meta);
        }
    }

    public void snareDrumvalue() {
        snareDrumItem = new ItemStack(Material.STICK);
        ItemMeta meta = snareDrumItem.getItemMeta();
        if (meta != null) {
            FileConfiguration SnareDrumConfig = InstrumentConfigManager.get("SnareDrum");
            String SnareDrumName = "&6Epic Snare Drum";
            if (SnareDrumConfig != null) {
                SnareDrumName = SnareDrumConfig.getString("snare_drum-name", SnareDrumName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', SnareDrumName));
            snareDrumItem.setItemMeta(meta);
        }
    }


    public void xylophonevalue() {
        xylophoneItem = new ItemStack(Material.STICK);
        ItemMeta meta = xylophoneItem.getItemMeta();
        if (meta != null) {
            FileConfiguration xylophoneConfig = InstrumentConfigManager.get("Xylophone");
            String xylophoneName = "&6Epic Xylophone";
            if (xylophoneConfig != null) {
                xylophoneName = xylophoneConfig.getString("xylophone-name", xylophoneName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', xylophoneName));
            xylophoneItem.setItemMeta(meta);
        }
    }

    public void extravalue() {
        extraItem = new ItemStack(Material.STICK);
        ItemMeta meta = extraItem.getItemMeta();
        if (meta != null) {
            FileConfiguration extraConfig = InstrumentConfigManager.get("Extra");
            String extraName = "&6Epic Extra";
            if (extraConfig != null) {
                extraName = extraConfig.getString("extra-name", extraName);
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', extraName));
            extraItem.setItemMeta(meta);
        }
    }

    private void addRecipeSafely(NamespacedKey key, ShapedRecipe recipe, String recipeName) {
        removeSimilarRecipes(recipe.getResult());
        try {
            Bukkit.addRecipe(recipe);
        } catch (IllegalStateException e) {
        }
    }
    
    private void removeSimilarRecipes(ItemStack targetItem) {
        try {
            java.util.Iterator<org.bukkit.inventory.Recipe> iterator = Bukkit.recipeIterator();
            while (iterator.hasNext()) {
                org.bukkit.inventory.Recipe recipe = iterator.next();
                if (recipe instanceof org.bukkit.inventory.ShapedRecipe) {
                    org.bukkit.inventory.ShapedRecipe shapedRecipe = (org.bukkit.inventory.ShapedRecipe) recipe;
                    if (shapedRecipe.getResult().isSimilar(targetItem)) {
                        iterator.remove();
                    }
                }
            }
        } catch (Exception e) {
        }
    }
    
    private Material getWoodMaterial() {
        try {
            return Material.valueOf("OAK_PLANKS");
        } catch (IllegalArgumentException e) {
            try {
                return Material.valueOf("PLANKS");
            } catch (IllegalArgumentException e2) {
                return Material.valueOf("WOOD_PLANKS");
            }
        }
    }
    
    private Material getIronMaterial() {
        try {
            return Material.valueOf("IRON_INGOT");
        } catch (IllegalArgumentException e) {
            return Material.valueOf("IRON");
        }
    }
    
    private Material getStringMaterial() {
        try {
            return Material.valueOf("STRING");
        } catch (IllegalArgumentException e) {
            return Material.valueOf("SULPHUR");
        }
    }

    private void createBanjoRecipe() {
        NamespacedKey key = new NamespacedKey(this, "banjo_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, banjoItem);
        recipe.shape(" W ", " SW", "W W");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('S', getStringMaterial());
        addRecipeSafely(key, recipe, "Banjo");
    }

    private void createBaseDrumRecipe() {
        NamespacedKey key = new NamespacedKey(this, "basedrum_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, baseDrumItem);
        recipe.shape("LIL", "LWL", "LIL");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('I', getIronMaterial());
        recipe.setIngredient('L', Material.STRING);
        addRecipeSafely(key, recipe, "BaseDrum");
    }

    private void createBassRecipe() {
        NamespacedKey key = new NamespacedKey(this, "bass_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, bassItem);
        recipe.shape("IS ", "WS ", "W  ");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('S', getStringMaterial());
        recipe.setIngredient('I', getIronMaterial());
        addRecipeSafely(key, recipe, "Bass");
    }

    private void createBellRecipe() {
        NamespacedKey key = new NamespacedKey(this, "bell_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, bellItem);
        recipe.shape(" W ", " W ", "ISI");
        recipe.setIngredient('W', Material.STICK);
        recipe.setIngredient('S', getStringMaterial());
        recipe.setIngredient('I', getIronMaterial());
        addRecipeSafely(key, recipe, "Bell");
    }

    private void createBitRecipe() {
        NamespacedKey key = new NamespacedKey(this, "bit_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, guitarItem);
        recipe.shape("III", "   ", "IWI");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('I', getStringMaterial());
        addRecipeSafely(key, recipe, "Bit");
    }

    private void createChimeRecipe() {
        NamespacedKey key = new NamespacedKey(this, "chime_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, chimeItem);
        recipe.shape("WWW", "III", " II");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('I', getIronMaterial());
        addRecipeSafely(key, recipe, "Chime");
    }

    private void createCowBellRecipe() {
        NamespacedKey key = new NamespacedKey(this, "cow_bell_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, bellItem);
        recipe.shape(" W ", "IWI", "ISI");
        recipe.setIngredient('W', Material.STICK);
        recipe.setIngredient('S', getStringMaterial());
        recipe.setIngredient('I', getIronMaterial());
        addRecipeSafely(key, recipe, "CowBell");
    }

    private void createDidgeridooRecipe() {
        NamespacedKey key = new NamespacedKey(this, "didgeridoo_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, didgeridooItem);
        recipe.shape("WIW", "W W", "WIW");
        recipe.setIngredient('W', Material.STICK);
        recipe.setIngredient('I', getIronMaterial());
        addRecipeSafely(key, recipe, "Didgeridoo");
    }

    private void createFluteRecipe() {
        NamespacedKey key = new NamespacedKey(this, "flute_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, fluteItem);
        recipe.shape("WWW", "   ", "   ");
        recipe.setIngredient('W', Material.STICK);
        addRecipeSafely(key, recipe, "Flute");
    }

    private void createGuitarRecipe() {
        NamespacedKey key = new NamespacedKey(this, "guitar_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, guitarItem);
        recipe.shape("W  ", " SW", " WW");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('S', getStringMaterial());
        addRecipeSafely(key, recipe, "Guitar");
    }

    private void createHarpRecipe() {
        NamespacedKey key = new NamespacedKey(this, "harp_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, harpItem);
        recipe.shape(" SW", " SW", "SWW");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('S', getStringMaterial());
        addRecipeSafely(key, recipe, "Harp");
    }

    private void createHatRecipe() {
        NamespacedKey key = new NamespacedKey(this, "hat_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, hatItem);
        recipe.shape("III", " W ", " W ");
        recipe.setIngredient('W', Material.STICK);
        recipe.setIngredient('I', getIronMaterial());
        addRecipeSafely(key, recipe, "Hat");
    }

    private void createIronXylophoneRecipe() {
        NamespacedKey key = new NamespacedKey(this, "iron_xylophone_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, ironXylophoneItem);
        recipe.shape("   ", "WWW", "III");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('I', getIronMaterial());
        addRecipeSafely(key, recipe, "IronXylophone");
    }

    private void createPlingRecipe() {
        NamespacedKey key = new NamespacedKey(this, "pling_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, plingItem);
        recipe.shape("III", "   ", "WIW");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('I', getIronMaterial());
        addRecipeSafely(key, recipe, "Pling");
    }

    private void createSnareDrumRecipe() {
        NamespacedKey key = new NamespacedKey(this, "snaredrum_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, snareDrumItem);
        recipe.shape("LLL", "IWI", "LLL");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('I', getIronMaterial());
        recipe.setIngredient('L', Material.STRING);
        addRecipeSafely(key, recipe, "SnareDrum");
    }

    private void createXylophoneRecipe() {
        NamespacedKey key = new NamespacedKey(this, "xylophone_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, xylophoneItem);
        recipe.shape("   ", "III", "WWW");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('I', getIronMaterial());
        addRecipeSafely(key, recipe, "Xylophone");
    }

    private void createExtraRecipe() {
        NamespacedKey key = new NamespacedKey(this, "extra_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, guitarItem);
        recipe.shape("IWI", "WSW", "SIS");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('S', getStringMaterial());
        recipe.setIngredient('I', getIronMaterial());
        addRecipeSafely(key, recipe, "Extra");
    }

}
