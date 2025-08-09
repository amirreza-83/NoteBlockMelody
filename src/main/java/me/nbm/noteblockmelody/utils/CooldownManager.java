package me.nbm.noteblockmelody.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    
    private static final Map<UUID, Long> playerCooldowns = new HashMap<>();
    private static final long COOLDOWN_DURATION = 100;
    
    public static boolean isOnCooldown(Player player) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        
        if (playerCooldowns.containsKey(playerId)) {
            long lastClickTime = playerCooldowns.get(playerId);
            if (currentTime - lastClickTime < COOLDOWN_DURATION) {
                return true;
            }
        }
        
        return false;
    }
    
    public static void setCooldown(Player player) {
        playerCooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }
    
    public static void removeCooldown(Player player) {
        playerCooldowns.remove(player.getUniqueId());
    }
    
    public static void clearAllCooldowns() {
        playerCooldowns.clear();
    }
}
