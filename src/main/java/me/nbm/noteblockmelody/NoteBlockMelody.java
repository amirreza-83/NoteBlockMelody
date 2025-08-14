package me.nbm.noteblockmelody;

import me.nbm.noteblockmelody.config.InstrumentConfigManager;
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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Map;

public class NoteBlockMelody extends JavaPlugin {

    public FileConfiguration config;
    public File langFile;
    public YamlConfiguration dictionaly;
    private File defLangFile;
    private YamlConfiguration defDictionaly;

    public Map<String, FileConfiguration> instrumentConfigs = new HashMap<>();

    public String guiTitle;
    public List<String> stringItems;
    public List<Double> pitches;
    public List<String> sounds;
    public List<Integer> stringItemPositions;
    public List<String> stringNames;

    public ItemStack banjoItem;
    public List<String> banjoPattern;
    public Map<String,String> banjoIngredients;

    public ItemStack baseDrumItem;
    public List<String> baseDrumPattern;
    public Map<String,String> baseDrumIngredients;

    public ItemStack bassItem;
    public List<String> bassPattern;
    public Map<String,String> bassIngredients;

    public ItemStack bellItem;
    public List<String> bellPattern;
    public Map<String,String> bellIngredients;

    public ItemStack bitItem;
    public List<String> bitPattern;
    public Map<String,String> bitIngredients;

    public ItemStack chimeItem;
    public List<String> chimePattern;
    public Map<String,String> chimeIngredients;

    public ItemStack cowBellItem;
    public List<String> cowBellPattern;
    public Map<String,String> cowBellIngredients;

    public ItemStack didgeridooItem;
    public List<String> didgeridooPattern;
    public Map<String,String> didgeridooIngredients;

    public ItemStack fluteItem;
    public List<String> flutePattern;
    public Map<String,String> fluteIngredients;

    public ItemStack guitarItem;
    public List<String> guitarPattern;
    public Map<String,String> guitarIngredients;

    public ItemStack harpItem;
    public List<String> harpPattern;
    public Map<String,String> harpIngredients;

    public ItemStack hatItem;
    public List<String> hatPattern;
    public Map<String,String> hatIngredients;

    public ItemStack ironXylophoneItem;
    public List<String> ironXylophonePattern;
    public Map<String,String> ironXylophoneIngredients;

    public ItemStack plingItem;
    public List<String> plingPattern;
    public Map<String,String> plingIngredients;

    public ItemStack snareDrumItem;
    public List<String> snareDrumPattern;
    public Map<String,String> snareDrumIngredients;

    public ItemStack xylophoneItem;
    public List<String> xylophonePattern;
    public Map<String,String> xylophoneIngredients;

    public ItemStack extraItem;
    public List<String> extraPattern;
    public Map<String,String> extraIngredients;

    public ConsoleCommandSender message;

    public String versionNumber;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        config = getConfig();
        message = Bukkit.getConsoleSender();
        saveDefaultInstrumentConfigs();
        InstrumentConfigManager.init(this);

        detectServerVersionAndUpdateConfigs();
        
        langLoad();

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
        message.sendMessage("§6           §bContributed by Jekyll             ");
        message.sendMessage("§6                                               ");
        message.sendMessage("§6===============================================");
        message.sendMessage("§6 §aBanjo  §bBaseDrum   §cBass    §dBell    §eBit");
        message.sendMessage("§6 §fChime  §1CowBell    §2Didgeridoo §3Flute");
        message.sendMessage("§6 §4Guitar §5Harp       §6Hat     §7IronXylophone");
        message.sendMessage("§6 §8Pling  §9SnareDrum  §aXylophone  §bExtra");
        message.sendMessage("§6===============================================");
        message.sendMessage("§6        §2All instruments loaded!              ");
        message.sendMessage("§6===============================================");

        banjovalue();
        baseDrumvalue();
        bassvalue();
        bellvalue();
        bitvalue();
        chimevalue();
        cowBellvalue();
        didgeridoovalue();
        flutevalue();
        guitarvalue();
        harpvalue();
        hatvalue();
        ironXylophonevalue();
        plingvalue();
        snareDrumvalue();
        xylophonevalue();
        extravalue();

        if(config.getBoolean("craftable")) {
          createRecipe(banjoItem, banjoIngredients, banjoPattern, "Banjo");
          createRecipe(baseDrumItem, baseDrumIngredients, baseDrumPattern, "BaseDrum");
          createRecipe(baseDrumItem, baseDrumIngredients, baseDrumPattern, "Bass");
          createRecipe(bellItem, bellIngredients, bellPattern, "Bell");
          createRecipe(bitItem, bitIngredients, bitPattern, "Bit");
          createRecipe(chimeItem, chimeIngredients, chimePattern, "Chime");
          createRecipe(cowBellItem, cowBellIngredients, cowBellPattern, "CowBell");
          createRecipe(didgeridooItem, didgeridooIngredients, didgeridooPattern, "Didgeridoo");
          createRecipe(fluteItem, fluteIngredients, flutePattern, "Flute");
          createRecipe(guitarItem, guitarIngredients, guitarPattern, "Guitar");
          createRecipe(harpItem, harpIngredients, harpPattern, "Harp");
          createRecipe(hatItem, hatIngredients, hatPattern, "Hat");
          createRecipe(ironXylophoneItem, ironXylophoneIngredients, ironXylophonePattern, "IronXylophone");
          createRecipe(snareDrumItem, snareDrumIngredients, snareDrumPattern, "SnareDrum");
          createRecipe(plingItem, plingIngredients, plingPattern, "Pling");
          createRecipe(xylophoneItem, xylophoneIngredients, xylophonePattern, "Xylophone");
          createRecipe(extraItem, extraIngredients, extraPattern, "Extra");
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
    
    public void removeAllRecipes() {
        try {
            java.util.Iterator<org.bukkit.inventory.Recipe> iterator = Bukkit.recipeIterator();
            while (iterator.hasNext()) {
                org.bukkit.inventory.Recipe recipe = iterator.next();
                if (recipe instanceof org.bukkit.inventory.ShapedRecipe) {
                    org.bukkit.inventory.ShapedRecipe shapedRecipe = (org.bukkit.inventory.ShapedRecipe) recipe;
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
            message.sendMessage("§6 Source: instruments/ folder");
            message.sendMessage("§b----------------------------------------------");
            int loadedCount = 0;
            for (String configFile : configFiles) {
                File targetFile = new File(getDataFolder(), "instruments" + File.separator + configFile);
                if (!targetFile.exists()) {
                    saveResource("instruments/" + configFile, false);
                    File sourceFile = new File(getDataFolder(), "instruments/" + configFile);
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
            File newFolder = new File(getDataFolder(), "instruments");
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
                if (!new File(getDataFolder(), "instruments" + File.separator + configFile).exists()) {
                    saveResource("instruments/" + configFile, false);
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
        
        versionNumber = extractVersionNumber(bukkitVersion);
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

    private boolean isVersionAtLeast(String current, String target) {
        int currentMajor = Integer.parseInt(current.split("\\.")[1]);
        int targetMajor = Integer.parseInt(target.split("\\.")[1]);
        return currentMajor >= targetMajor;
    }

    public void langLoad(){
        String lang = config.getString("lang");
        defLangFile = new File(getDataFolder(), "languages" + File.separator + "en.yml");
        langFile = new File(getDataFolder(), "languages" + File.separator + lang + ".yml");   
        if(langFile == null){
            langFile = defLangFile;
        }
        defDictionaly = YamlConfiguration.loadConfiguration(defLangFile);
        dictionaly = YamlConfiguration.loadConfiguration(langFile);
    }

    public String translate(String key){
        String mes = new String();
        if(dictionaly.getString(key) == null){
            mes = defDictionaly.getString(key);
        }else{
            mes = dictionaly.getString(key);
        }
        return mes; 
    }

    public void banjovalue() {
        String instName = "Banjo";
        banjoItem = getItem(instName);
        banjoIngredients = new HashMap<>();
        banjoIngredients.put("A", "STRING");
        banjoIngredients.put("B", "OAK_PLANKS");
        banjoIngredients.put("C", "STICK");
        banjoPattern = new ArrayList<>();
        banjoPattern.add("C  ");
        banjoPattern.add(" AB");
        banjoPattern.add(" BB");
        value(banjoItem,banjoIngredients,banjoPattern,instName);
    }

    public void baseDrumvalue() {
        String instName = "BaseDrum";
        baseDrumItem = getItem(instName);
        baseDrumIngredients = new HashMap<>();
        baseDrumIngredients.put("A", "STRING");
        baseDrumIngredients.put("B", "OAK_PLANKS");
        baseDrumIngredients.put("C", "IRON_INGOT");
        baseDrumPattern = new ArrayList<>();
        baseDrumPattern.add("BBB");
        baseDrumPattern.add(" C ");
        baseDrumPattern.add("BBB");
        value(baseDrumItem,baseDrumIngredients,baseDrumPattern,instName);
    }

    public void bassvalue() {
        String instName = "Bass";
        bassItem = getItem(instName);
        bassIngredients = new HashMap<>();
        bassIngredients.put("A", "STRING");
        bassIngredients.put("B", "OAK_PLANKS");
        bassIngredients.put("C", "IRON_INGOT");
        bassPattern = new ArrayList<>();
        bassPattern.add("CA ");
        bassPattern.add("BA ");
        bassPattern.add("B  ");
        value(bassItem,bassIngredients,bassPattern,instName);
    }

    public void bellvalue() {
        String instName = "Bell";
        bellItem = getItem(instName);
        bellIngredients = new HashMap<>();
        bellIngredients.put("A", "STICK");
        bellIngredients.put("B", "STRING");
        bellIngredients.put("C", "IRON_INGOT");
        bellPattern = new ArrayList<>();
        bellPattern.add(" A ");
        bellPattern.add(" A ");
        bellPattern.add("CBC");
        value(bellItem,bellIngredients,bellPattern,instName);
    }

    public void bitvalue() {
        String instName = "Bit";
        bitItem = getItem(instName);
        bitIngredients = new HashMap<>();
        bitIngredients.put("A", "STICK");
        bitIngredients.put("B", "OAK_PLANKS");
        bitIngredients.put("C", "IRON_INGOT");
        bitPattern = new ArrayList<>();
        bitPattern.add("CAC");
        bitPattern.add("CBC");
        bitPattern.add("CBC");
        value(bitItem,bitIngredients,bitPattern,instName);
    }

    public void chimevalue() {
        String instName = "Chime";
        chimeItem = getItem(instName);
        chimeIngredients = new HashMap<>();
        chimeIngredients.put("B", "OAK_PLANKS");
        chimeIngredients.put("C", "IRON_INGOT");
        chimePattern = new ArrayList<>();
        chimePattern.add("BBB");
        chimePattern.add("CCC");
        chimePattern.add(" CC");
        value(chimeItem,chimeIngredients,chimePattern,instName);
    }

    public void cowBellvalue() {
        String instName = "CowBell";
        cowBellItem = getItem(instName);
        cowBellIngredients = new HashMap<>();
        cowBellIngredients.put("A", "STICK");
        cowBellIngredients.put("B", "OAK_PLANKS");
        cowBellIngredients.put("C", "IRON_INGOT");
        cowBellPattern = new ArrayList<>();
        cowBellPattern.add(" A ");
        cowBellPattern.add("CCC");
        cowBellPattern.add("CBC");
        value(cowBellItem,cowBellIngredients,cowBellPattern,instName);
    }

    public void didgeridoovalue() {
        String instName = "Didgeridoo";
        didgeridooItem = getItem(instName);
        didgeridooIngredients = new HashMap<>();
        didgeridooIngredients.put("B", "OAK_PLANKS");
        didgeridooIngredients.put("C", "IRON_INGOT");
        didgeridooPattern = new ArrayList<>();
        didgeridooPattern.add("BCB");
        didgeridooPattern.add("B B");
        didgeridooPattern.add("BCB");
        value(didgeridooItem,didgeridooIngredients,didgeridooPattern,instName);
    }

    public void flutevalue() {
        String instName = "Flute";
        fluteItem = getItem(instName);
        fluteIngredients = new HashMap<>();
        fluteIngredients.put("A", "STICK");
        flutePattern = new ArrayList<>();
        flutePattern.add("AAA");
        value(fluteItem,fluteIngredients,flutePattern,instName);
    }

    public void guitarvalue() {
        String instName = "Guitar";
        guitarItem = getItem(instName);
        guitarIngredients = new HashMap<>();
        guitarIngredients.put("A", "STRING");
        guitarIngredients.put("B", "OAK_PLANKS");
        guitarPattern = new ArrayList<>();
        guitarPattern.add("B  ");
        guitarPattern.add(" AB");
        guitarPattern.add(" BB");
        value(guitarItem,guitarIngredients,guitarPattern,instName);
    }

    public void harpvalue() {
        String instName = "Harp";
        harpItem = getItem(instName);
        harpIngredients = new HashMap<>();
        harpIngredients.put("A", "STRING");
        harpIngredients.put("B", "OAK_PLANKS");
        harpPattern = new ArrayList<>();
        harpPattern.add(" AB");
        harpPattern.add(" AB");
        harpPattern.add("ABB");
        value(harpItem,harpIngredients,harpPattern,instName);
    }

    public void hatvalue() {
        String instName = "Hat";
        hatItem = getItem(instName);
        hatIngredients = new HashMap<>();
        hatIngredients.put("B", "OAK_PLANKS");
        hatIngredients.put("C", "IRON_INGOT");
        hatPattern = new ArrayList<>();
        hatPattern.add("CCC");
        hatPattern.add(" B ");
        hatPattern.add(" B ");
        value(hatItem,hatIngredients,hatPattern,instName);
    }

    public void ironXylophonevalue() {
        String instName = "IronXylophone";
        ironXylophoneItem = getItem(instName);
        ironXylophoneIngredients = new HashMap<>();
        ironXylophoneIngredients.put("B", "OAK_PLANKS");
        ironXylophoneIngredients.put("C", "IRON_INGOT");
        ironXylophonePattern = new ArrayList<>();
        ironXylophonePattern.add("CCC");
        ironXylophonePattern.add("BBB");
        value(ironXylophoneItem,ironXylophoneIngredients,ironXylophonePattern,instName);
    }

    public void plingvalue() {
        String instName = "Pling";
        plingItem = getItem(instName);
        plingIngredients = new HashMap<>();
        plingIngredients.put("B", "OAK_PLANKS");
        plingIngredients.put("C", "IRON_INGOT");
        plingPattern = new ArrayList<>();
        plingPattern.add("CCC");
        plingPattern.add("BBB");
        plingPattern.add("B B");
        value(plingItem,plingIngredients,plingPattern,instName);
    }

    public void snareDrumvalue() {
        String instName = "SnareDrum";
        snareDrumItem = getItem(instName);
        snareDrumIngredients = new HashMap<>();
        snareDrumIngredients.put("B", "OAK_PLANKS");
        snareDrumIngredients.put("C", "IRON_INGOT");
        snareDrumPattern = new ArrayList<>();
        snareDrumPattern.add("CCC");
        snareDrumPattern.add("   ");
        snareDrumPattern.add("BCB");
        value(snareDrumItem,snareDrumIngredients,snareDrumPattern,instName);
    }

    public void xylophonevalue() {
        String instName = "Xylophone";
        xylophoneItem = getItem(instName);
        xylophoneIngredients = new HashMap<>();
        xylophoneIngredients.put("B", "OAK_PLANKS");
        xylophoneIngredients.put("C", "IRON_INGOT");
        xylophonePattern = new ArrayList<>();
        xylophonePattern.add("BBB");
        xylophonePattern.add("CCC");
        value(xylophoneItem,xylophoneIngredients,xylophonePattern,instName);
    }

    public void extravalue() {
        String instName = "Extra";
        extraItem = getItem(instName);
        extraIngredients = new HashMap<>();
        extraIngredients.put("A", "STRING");        
        extraIngredients.put("B", "OAK_PLANKS");
        extraIngredients.put("C", "IRON_INGOT");
        extraPattern = new ArrayList<>();
        extraPattern.add("CBC");
        extraPattern.add("BAB");
        extraPattern.add("ACA");
        value(extraItem,extraIngredients,extraPattern,instName);
    }

    public void value(ItemStack item, Map<String,String> ingredients, List<String> pattern, String instName) {
        List<String> lore = new ArrayList<String>();
        Integer customModelData = -1;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            FileConfiguration config = InstrumentConfigManager.get(instName);
            String name = "&6Epic " + instName;
            if (config != null) {

                String withUnderscores = instName.replaceAll("(?<!^)([A-Z])", "_$1");
                String nameformated = config.getString(withUnderscores.toLowerCase() + "-name", name);
                if(nameformated != name){
                    name = nameformated;
                }

                try{
                    ConfigurationSection ingredientsSettings = config.getConfigurationSection("recipe.ingredients");

                        if(ingredientsSettings != null){
                            for(String key : ingredientsSettings.getKeys(false)){
                                ingredients.put(key, ingredientsSettings.getString(key));
                            }
                        }
                } catch (IllegalStateException e) {
                }

                try{
                    List<String> patternRecipe = config.getStringList("recipe.pattern");
                    if(patternRecipe != null){
                        for(int i = 0; i< patternRecipe.size(); i ++){
                            if(i>2)break;
                            pattern.set(i, patternRecipe.get(i));
                        }
                        if( patternRecipe.size() == 1){
                           if(pattern.size()== 3){
                               pattern.remove(2);
                           }
                           if(pattern.size()== 2){
                               pattern.remove(1);
                           }
                        }
                        if( patternRecipe.size() == 2){
                           if(pattern.size()== 3){
                            pattern.remove(2);
                           }
                        }
                    }
                } catch (IllegalStateException e) {
                }

                try{
                    lore = config.getStringList("lore");
                } catch (IllegalStateException e) {
                }
                try{
                    customModelData = config.getInt("custom_model_data");
                } catch (IllegalStateException e) {
                }

            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            if(lore !=null){
                for(int i = 0; i< lore.size(); i ++){
                    lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
                }
            meta.setLore(lore);
            }

            if (isVersionAtLeast(versionNumber, "1.16") && customModelData >= 0) {
                meta.setCustomModelData(customModelData);
            }   
            item.setItemMeta(meta);
        }
    }

    public ItemStack getItem(String instName){
        FileConfiguration config = InstrumentConfigManager.get(instName);
        if (config != null) {
            String itemOnConf = config.getString("item");
            if(itemOnConf != null){
                Material mat = checkMat(itemOnConf);
                return new ItemStack(mat);
            }
        }
        return new ItemStack(Material.STICK);
    }

    public void createRecipe(ItemStack item, Map<String,String> ingredients, List<String> pattern, String instName){
        String racipeName = instName + "_recipe";
        NamespacedKey key = new NamespacedKey(this, racipeName);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        List<String> patternA = new ArrayList<>();
        for(int i = 0; i < pattern.size(); i ++){
            String str = String.format("%-3s", pattern.get(i));
            patternA.add(str.substring(0,3));
        }
        String patternString;
        if(pattern.size() == 3){
            recipe.shape(patternA.get(0), patternA.get(1), patternA.get(2)); 
            patternString = patternA.get(0) + patternA.get(1) + patternA.get(2);
        } else if(pattern.size() == 2){
            recipe.shape(patternA.get(0), patternA.get(1));
            patternString = patternA.get(0) + patternA.get(1);
        } else {
            recipe.shape(patternA.get(0));     
            patternString = patternA.get(0);      
        }

        String[] str = patternString.replace(" ","").split("");

        for(String ingKey: str){
            if(ingredients.containsKey(ingKey)){
                Material mat;
                // compatible with old version
                if(ingredients.get(ingKey) == "OAK_PLANKS"){
                  mat = getWoodMaterial();
                }else if(ingredients.get(ingKey) == "IRON_INGOT"){
                  mat = getIronMaterial();
                }else if(ingredients.get(ingKey) == "STRING"){
                  mat = getStringMaterial();
                }else{
                  mat = Material.valueOf(ingredients.get(ingKey));
                }
                recipe.setIngredient(ingKey.toCharArray()[0], mat);
            }else{
                recipe.setIngredient(ingKey.toCharArray()[0], Material.valueOf("STICK"));                
            }
        }
        addRecipeSafely(key, recipe, instName);
    }

    private void addRecipeSafely(NamespacedKey key, ShapedRecipe recipe, String recipeName) {
        removeShapedRecipeByKey(key);

        try {
            Bukkit.addRecipe(recipe);
        } catch (IllegalStateException e) {
        }
    }

    private void removeShapedRecipeByKey(NamespacedKey targetKey) {
        java.util.Iterator<Recipe> iterator = Bukkit.recipeIterator();

        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();

            if (recipe instanceof ShapedRecipe) {
                ShapedRecipe shaped = (ShapedRecipe) recipe;
                NamespacedKey key = shaped.getKey();

                if (key != null && key.equals(targetKey)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    private Material checkMat(String mat){
        try {
                return Material.valueOf(mat);
            } catch (IllegalArgumentException e) {
                return Material.valueOf("OAK_PLANKS");
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

}
