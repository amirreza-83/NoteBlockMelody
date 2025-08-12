package me.nbm.noteblockmelody.notes.cow_bell;

import me.nbm.noteblockmelody.NoteBlockMelody;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cow_bell_command implements CommandExecutor {

    private final NoteBlockMelody plugin;

    public cow_bell_command(NoteBlockMelody plugin) {
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
            cow_bell_event event = new cow_bell_event(plugin);
            event.openCowBellGUI(player);
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permission to use instruments.");
        }

        return true;
    }
}
