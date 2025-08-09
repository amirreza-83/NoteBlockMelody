package me.nbm.noteblockmelody;

import me.nbm.noteblockmelody.config.InstrumentConfigManager;
import me.nbm.noteblockmelody.NBMCommand;
import me.nbm.noteblockmelody.notes.bass.bass_command;
import me.nbm.noteblockmelody.notes.bass.bass_event;
import me.nbm.noteblockmelody.notes.bell.bell_command;
import me.nbm.noteblockmelody.notes.bell.bell_event;
import me.nbm.noteblockmelody.notes.chime.chime_command;
import me.nbm.noteblockmelody.notes.chime.chime_event;
import me.nbm.noteblockmelody.notes.flute.flute_command;
import me.nbm.noteblockmelody.notes.flute.flute_event;
import me.nbm.noteblockmelody.notes.guitar.guitar_command;
import me.nbm.noteblockmelody.notes.guitar.guitar_event;
import me.nbm.noteblockmelody.notes.harp.harp_command;
import me.nbm.noteblockmelody.notes.harp.harp_event;
import me.nbm.noteblockmelody.notes.hat.hat_command;
import me.nbm.noteblockmelody.notes.hat.hat_event;
import me.nbm.noteblockmelody.notes.snare_drum.snare_drum_command;
import me.nbm.noteblockmelody.notes.snare_drum.snare_drum_event;
import me.nbm.noteblockmelody.notes.xylophone.xylophone_command;
import me.nbm.noteblockmelody.notes.xylophone.xylophone_event;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Map;

public class NoteBlockMelody extends JavaPlugin {

    public FileConfiguration config;
    public FileConfiguration guitarConfig;
    public ItemStack snareDrumItem;
    public ItemStack xylophoneItem;

    public Map<String, FileConfiguration> instrumentConfigs = new HashMap<>();

    public ItemStack guitarItem;
    public String guiTitle;
    public List<String> stringItems;
    public List<Double> pitches;
    public List<String> sounds;
    public List<Integer> stringItemPositions;
    public List<String> stringNames;
    public ItemStack hatItem;
    public ItemStack harpItem;
    public ItemStack fluteItem;
    public ItemStack chimeItem;
    public ItemStack bellItem;
    public ItemStack bassItem;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        
        saveDefaultInstrumentConfigs();
        InstrumentConfigManager.init(this);
        
        detectServerVersionAndUpdateConfigs();

        getCommand("nbm").setExecutor(new NBMCommand(this));
        getCommand("guitar").setExecutor(new guitar_command(this));
        Bukkit.getPluginManager().registerEvents(new guitar_event(this), this);

        getCommand("bass").setExecutor(new bass_command(this));
        Bukkit.getPluginManager().registerEvents(new bass_event(this), this);

        getCommand("bell").setExecutor(new bell_command(this));
        Bukkit.getPluginManager().registerEvents(new bell_event(this), this);

        getCommand("chime").setExecutor(new chime_command(this));
        Bukkit.getPluginManager().registerEvents(new chime_event(this), this);

        getCommand("flute").setExecutor(new flute_command(this));
        Bukkit.getPluginManager().registerEvents(new flute_event(this), this);

        getCommand("hat").setExecutor(new hat_command(this));
        Bukkit.getPluginManager().registerEvents(new hat_event(this), this);

        getCommand("snare_drum").setExecutor(new snare_drum_command(this));
        Bukkit.getPluginManager().registerEvents(new snare_drum_event(this), this);

        getCommand("harp").setExecutor(new harp_command(this));
        Bukkit.getPluginManager().registerEvents(new harp_event(this), this);

        getCommand("xylophone").setExecutor(new xylophone_command(this));
        Bukkit.getPluginManager().registerEvents(new xylophone_event(this), this);

        getLogger().info("§6===============================================");
        getLogger().info("§6                                               ");
        getLogger().info("§6    §eNoteBlockMelody v1.7-α                   ");
        getLogger().info("§6                                               ");
        getLogger().info("§6           §bMade by Minestar                  ");
        getLogger().info("§6                                               ");
        getLogger().info("§6===============================================");
        getLogger().info("§6 §aGuitar  §bBass    §cBell   §dChime           ");
        getLogger().info("§6 §eFlute   §fHarp    §9Hat    §2Snare           ");
        getLogger().info("§6 §3Xylophone                                   ");
        getLogger().info("§6===============================================");
        getLogger().info("§6        §2All instruments loaded!              ");
        getLogger().info("§6===============================================");

        xylophonevalue();
        snareDrumvalue();
        hatvalue();
        harpvalue();
        flutevalue();
        chimevalue();
        bellvalue();
        bassvalue();
        guitarvalue();

        createGuitarRecipe();
        createXylophoneRecipe();
        createSnareDrumRecipe();
        createHatRecipe();
        createHarpRecipe();
        createFluteRecipe();
        createChimeRecipe();
        createBellRecipe();
        createBassRecipe();

        getLogger().info("§d===============================================");
        getLogger().info("§d                                               ");
        getLogger().info("§d        §aNoteBlockMelody is Ready!             ");
        getLogger().info("§d                                               ");
        getLogger().info("§d    §6Let's make some beautiful music!         ");
        getLogger().info("§d                                               ");
        getLogger().info("§d===============================================");
    }

    @Override
    public void onDisable() {
        removeAllRecipes();
        getLogger().info("§c===============================================");
        getLogger().info("§c                                               ");
        getLogger().info("§c      §4NoteBlockMelody Disabled                ");
        getLogger().info("§c                                               ");
        getLogger().info("§c        §6Thanks for using our plugin!         ");
        getLogger().info("§c                                               ");
        getLogger().info("§c===============================================");
    }
    
    private void removeAllRecipes() {
        try {
            java.util.Iterator<org.bukkit.inventory.Recipe> iterator = Bukkit.recipeIterator();
            while (iterator.hasNext()) {
                org.bukkit.inventory.Recipe recipe = iterator.next();
                if (recipe instanceof org.bukkit.inventory.ShapedRecipe) {
                    org.bukkit.inventory.ShapedRecipe shapedRecipe = (org.bukkit.inventory.ShapedRecipe) recipe;
                    if (shapedRecipe.getResult().isSimilar(guitarItem) ||
                        shapedRecipe.getResult().isSimilar(bassItem) ||
                        shapedRecipe.getResult().isSimilar(bellItem) ||
                        shapedRecipe.getResult().isSimilar(chimeItem) ||
                        shapedRecipe.getResult().isSimilar(fluteItem) ||
                        shapedRecipe.getResult().isSimilar(harpItem) ||
                        shapedRecipe.getResult().isSimilar(hatItem) ||
                        shapedRecipe.getResult().isSimilar(snareDrumItem) ||
                        shapedRecipe.getResult().isSimilar(xylophoneItem)) {
                        iterator.remove();
                    }
                }
            }
        } catch (Exception e) {
        }
    }
    
    private void saveDefaultInstrumentConfigs() {
        String[] configFiles = {"Guitar.yml", "Bass.yml", "Bell.yml", "Chime.yml", "Flute.yml", 
                               "Harp.yml", "Hat.yml", "SnareDrum.yml", "Xylophone.yml"};
        
        String version = extractVersionNumber(Bukkit.getBukkitVersion());
        boolean useModernConfigs = !isVersion1_12_or_below(version);
        
        getLogger().info("§b==============================================");
        if (useModernConfigs) {
            getLogger().info("§a Loading Modern Configs (1.13+)");
            getLogger().info("§6 Source: new/ folder");
            getLogger().info("§b----------------------------------------------");
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
                            getLogger().info("§a ✓ " + fileName);
                        } catch (Exception e) {
                            getLogger().info("§c ✗ " + configFile);
                        }
                    }
                }
            }
            File newFolder = new File(getDataFolder(), "new");
            if (newFolder.exists() && newFolder.isDirectory()) {
                newFolder.delete();
            }
            getLogger().info("§b----------------------------------------------");
            getLogger().info("§2 Loaded " + loadedCount + " modern configs successfully!");
        } else {
            getLogger().info("§a Loading Legacy Configs (1.12-)");
            getLogger().info("§6 Source: default resources");
            getLogger().info("§b----------------------------------------------");
            int loadedCount = 0;
            for (String configFile : configFiles) {
                if (!new File(getDataFolder(), configFile).exists()) {
                    saveResource(configFile, false);
                    loadedCount++;
                    String fileName = configFile.replace(".yml", "");
                    getLogger().info("§a ✓ " + fileName);
                }
            }
            getLogger().info("§b----------------------------------------------");
            getLogger().info("§2 Loaded " + loadedCount + " legacy configs successfully!");
        }
        getLogger().info("§b==============================================");
    }

    private void detectServerVersionAndUpdateConfigs() {
        String version = Bukkit.getVersion();
        String bukkitVersion = Bukkit.getBukkitVersion();
        
        getLogger().info("§e╔══════════════════════════════════════════════╗");
        getLogger().info("§e║          §6NoteBlockMelody v1.7-α             §e║");
        getLogger().info("§e║              §bVersion Detection              §e║");
        getLogger().info("§e╠══════════════════════════════════════════════╣");
        getLogger().info("§e║ §aServer: §f" + String.format("%-32s", version) + " §e║");
        getLogger().info("§e║ §aBukkit: §f" + String.format("%-32s", bukkitVersion) + " §e║");
        
        String versionNumber = extractVersionNumber(bukkitVersion);
        getLogger().info("§e║ §aVersion: §f" + String.format("%-31s", versionNumber) + " §e║");
        getLogger().info("§e╠══════════════════════════════════════════════╣");
        
        if (isVersion1_12_or_below(versionNumber)) {
            getLogger().info("§e║ §bStatus: §6Legacy Mode §7(1.12 & below)       §e║");
            getLogger().info("§e║ §dSounds: §fClassic Note Block Sounds        §e║");
        } else {
            getLogger().info("§e║ §bStatus: §6Modern Mode §7(1.13+)             §e║");
            getLogger().info("§e║ §dSounds: §fAdvanced Note Block Sounds       §e║");
        }
        getLogger().info("§e║ §2Status: §aReady to Rock!                   §e║");
        getLogger().info("§e╚══════════════════════════════════════════════╝");
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

    private void createGuitarRecipe() {
        NamespacedKey key = new NamespacedKey(this, "guitar_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, guitarItem);
        recipe.shape("W  ", " SW", " WW");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('S', getStringMaterial());
        addRecipeSafely(key, recipe, "Guitar");
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
    private void createChimeRecipe() {
        NamespacedKey key = new NamespacedKey(this, "chime_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, chimeItem);
        recipe.shape("WWW", "III", " II");
        recipe.setIngredient('W', getWoodMaterial());
        recipe.setIngredient('I', getIronMaterial());
        addRecipeSafely(key, recipe, "Chime");
    }

    private void createFluteRecipe() {
        NamespacedKey key = new NamespacedKey(this, "flute_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, fluteItem);
        recipe.shape("WWW", "   ", "   ");
        recipe.setIngredient('W', Material.STICK);
        addRecipeSafely(key, recipe, "Flute");
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

}
