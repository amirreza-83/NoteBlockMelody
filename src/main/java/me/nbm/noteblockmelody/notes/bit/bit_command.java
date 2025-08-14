package me.nbm.noteblockmelody.notes.bit;

import me.nbm.noteblockmelody.NoteBlockMelody;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class bit_command implements CommandExecutor {

    private final NoteBlockMelody plugin;

    public bit_command(NoteBlockMelody plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + plugin.translate("this-command-player-only"));
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("nbm.use")) {
            bit_event event = new bit_event(plugin);
            event.openBitGUI(player);
        } else {
            player.sendMessage(ChatColor.RED + plugin.translate("dont-have-permission-to-use-instruments"));
        }

        return true;
    }
}
