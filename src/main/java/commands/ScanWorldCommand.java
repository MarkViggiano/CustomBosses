package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import scanner.WorldScanner;

public class ScanWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && sender.hasPermission("custombosses.developer")) {
            Player player = (Player) sender;
            WorldScanner.scanWorld(player);

        } else {
            sender.sendMessage("No permission!");
        }

        return true;
    }
}
