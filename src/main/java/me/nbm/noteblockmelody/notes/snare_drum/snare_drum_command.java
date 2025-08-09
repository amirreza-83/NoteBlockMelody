package me.nbm.noteblockmelody.notes.snare_drum;

import me.nbm.noteblockmelody.NoteBlockMelody;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class snare_drum_command implements CommandExecutor {

    private final NoteBlockMelody plugin;

    public snare_drum_command(NoteBlockMelody plugin) {
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
            snare_drum_event event = new snare_drum_event(plugin);
            event.openSnareDrumGUI(player);
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permission to use instruments.");
        }

        return true;
    }
}
