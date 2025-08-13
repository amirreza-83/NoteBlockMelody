package me.nbm.noteblockmelody.notes.didgeridoo;

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

public class didgeridoo_event implements Listener {

    private final NoteBlockMelody plugin;

    public didgeridoo_event(NoteBlockMelody plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDidgeridooUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().isSimilar(plugin.didgeridooItem)) {
            openDidgeridooGUI(player);
        }
    }

    public void openDidgeridooGUI(Player player) {
        FileConfiguration didgeridooConfig = InstrumentConfigManager.get("Didgeridoo");
        if (didgeridooConfig == null) {
            player.sendMessage(ChatColor.RED + "Didgeridoo configuration not found!");
            return;
        }
        
        String guiTitle = ChatColor.translateAlternateColorCodes('&', didgeridooConfig.getString("gui-title", "Didgeridoo"));

        Inventory gui = Bukkit.createInventory(null, 27, guiTitle);

        List<String> itemTypes = didgeridooConfig.getStringList("strings");
        List<String> itemNames = didgeridooConfig.getStringList("string-names");
        List<Integer> positions = didgeridooConfig.getIntegerList("string-item-positions");

        for (int i = 0; i < itemTypes.size(); i++) {
            if (i >= positions.size()) break;

            ItemStack item = new ItemStack(Material.valueOf(itemTypes.get(i).toUpperCase()));
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemNames.get(i)));
                item.setItemMeta(meta);
            }

            gui.setItem(positions.get(i), item);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        FileConfiguration didgeridooConfig = InstrumentConfigManager.get("Didgeridoo");
        if (didgeridooConfig == null) return;
        
        String guiTitle = ChatColor.translateAlternateColorCodes('&', didgeridooConfig.getString("gui-title", "Didgeridoo"));
        if (!event.getView().getTitle().equals(guiTitle)) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        
        if (CooldownManager.isOnCooldown(player)) {
            return;
        }
        
        int slot = event.getRawSlot();

        List<Double> pitches = didgeridooConfig.getDoubleList("pitches");
        List<String> sounds = didgeridooConfig.getStringList("sounds");
        List<Integer> positions = didgeridooConfig.getIntegerList("string-item-positions");

        int soundIndex = -1;
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i) == slot) {
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
                plugin.getLogger().warning("[NBM] Invalid sound in Didgeridoo.yml at index " + soundIndex + ": " + sounds.get(soundIndex));
            }
        }
    }
}
