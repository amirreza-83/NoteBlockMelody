package me.nbm.noteblockmelody.notes.base_drum;

import me.nbm.noteblockmelody.NoteBlockMelody;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class base_drum_command implements CommandExecutor {

    private final NoteBlockMelody plugin;

    public base_drum_command(NoteBlockMelody plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("nbm.use")) {
            base_drum_event event = new base_drum_event(plugin);
            event.openBaseDrumGUI(player);
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permission to use instruments.");
        }

        return true;
    }
}
