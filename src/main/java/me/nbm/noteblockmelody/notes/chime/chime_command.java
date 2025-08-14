package me.nbm.noteblockmelody.notes.chime;

import me.nbm.noteblockmelody.NoteBlockMelody;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class chime_command implements CommandExecutor {

    private final NoteBlockMelody plugin;

    public chime_command(NoteBlockMelody plugin) {
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
            chime_event event = new chime_event(plugin);
            event.openChimeGUI(player);
        } else {
            player.sendMessage(ChatColor.RED + plugin.translate("dont-have-permission-to-use-instruments"));
        }

        return true;
    }
}
