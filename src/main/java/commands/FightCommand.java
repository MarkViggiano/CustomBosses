package commands;

import boss.BossPlayer;
import boss.BossPlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import scanner.WorldScanner;

public class FightCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (BossPlayerManager.getBossPlayerByPlayer(player) != null)
                player.sendMessage(String.format("%sCustom Bosses>%s You are already registered as a fighter!",
                        ChatColor.RED, ChatColor.GRAY));
            else {
                player.teleport(WorldScanner.getBossPlayerSpawn());
                player.getInventory().clear();
                player.getEquipment().setArmorContents(null);
                player.updateInventory();
                player.setGameMode(GameMode.SURVIVAL);
                BossPlayer bossPlayer = new BossPlayer(player);
                bossPlayer.equip();
            }
        }

        return true;
    }
}
