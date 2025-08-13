package me.nbm.noteblockmelody.notes.base_drum;

import me.nbm.noteblockmelody.NoteBlockMelody;
import me.nbm.noteblockmelody.config.InstrumentConfigManager;
import me.nbm.noteblockmelody.utils.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class base_drum_event implements Listener {

    private final NoteBlockMelody plugin;

    public base_drum_event(NoteBlockMelody plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBase_DrumUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().isSimilar(plugin.baseDrumItem)) {
            openBaseDrumGUI(player);
        }
    }

    public void openBaseDrumGUI(Player player) {
        FileConfiguration baseDrumConfig = InstrumentConfigManager.get("BaseDrum");
        if (baseDrumConfig == null) {
            player.sendMessage(ChatColor.RED + "Base Drum configuration not found!");
            return;
        }
        
        String guiTitle = ChatColor.translateAlternateColorCodes('&', baseDrumConfig.getString("gui-title", "BaseDrum"));

        Inventory gui = Bukkit.createInventory(null, 27, guiTitle);

        List<String> stringItems = baseDrumConfig.getStringList("strings");
        List<String> stringNames = baseDrumConfig.getStringList("string-names");
        List<Integer> stringItemPositions = baseDrumConfig.getIntegerList("string-item-positions");

        for (int i = 0; i < stringItems.size(); i++) {
            if (i >= stringItemPositions.size()) break;

            ItemStack item = new ItemStack(Material.valueOf(stringItems.get(i).toUpperCase()));
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', stringNames.get(i)));
                item.setItemMeta(meta);
            }

            gui.setItem(stringItemPositions.get(i), item);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        FileConfiguration baseDrumConfig = InstrumentConfigManager.get("BaseDrum");
        if (baseDrumConfig == null) return;
        
        String guiTitle = ChatColor.translateAlternateColorCodes('&', baseDrumConfig.getString("gui-title", "BaseDrum"));
        if (!event.getView().getTitle().equals(guiTitle)) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        
        if (CooldownManager.isOnCooldown(player)) {
            return;
        }
        
        int slot = event.getRawSlot();

        List<Double> pitches = baseDrumConfig.getDoubleList("pitches");
        List<String> sounds = baseDrumConfig.getStringList("sounds");
        List<Integer> stringItemPositions = baseDrumConfig.getIntegerList("string-item-positions");

        int soundIndex = -1;
        for (int i = 0; i < stringItemPositions.size(); i++) {
            if (stringItemPositions.get(i) == slot) {
                soundIndex = i;
                break;
            }
        }

        if (soundIndex >= 0 && soundIndex < sounds.size() && soundIndex < pitches.size()) {
            try {
                Sound sound = Sound.valueOf(sounds.get(soundIndex));
                float pitch = pitches.get(soundIndex).floatValue();

                for (Player nearby : player.getWorld().getPlayers()) {
                    if (nearby.getLocation().distance(player.getLocation()) <= 50) {
                        nearby.playSound(player.getLocation(), sound, 1.0f, pitch);
                    }
                }
                
                CooldownManager.setCooldown(player);
            } catch (IllegalArgumentException ex) {
                plugin.getLogger().warning("[NBM] Invalid sound in BaseDrum.yml at index " + soundIndex + ": " + sounds.get(soundIndex));
            }
        }
    }
}
