package listeners;

import boss.Boss;
import boss.BossManager;
import boss.BossPlayer;
import boss.BossPlayerManager;
import bosses.WorldEnder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerDeathListener extends ListenerRegister {
    public PlayerDeathListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void playerDieEvent(PlayerDeathEvent e) {
        BossPlayer bossPlayer = BossPlayerManager.getBossPlayerByPlayer(e.getEntity());
        if (bossPlayer == null)
            return;

        BossPlayerManager.removeBossPlayer(bossPlayer);
        e.setDeathMessage(String.format("%sCustomBosses> %sWarrior %s%s%s has fallen in battle!",
                ChatColor.RED, ChatColor.GRAY, ChatColor.GREEN, e.getEntity().getName(), ChatColor.GRAY));
        for (Boss boss : BossManager.getBosses()) {
            if (boss instanceof WorldEnder && boss.isRampaged()) {
                boss.addHP(300);
                Bukkit.broadcastMessage(String.format("%sCustomBosses> %sWorld Ender %shas healed for 300 hp!",
                        ChatColor.RED, ChatColor.DARK_RED,ChatColor.GRAY));
                Bukkit.broadcastMessage(String.format("%sWorld Ender> %sI brought you the one true gift %s%s%s, I brought you death.",
                        ChatColor.DARK_RED, ChatColor.RED, ChatColor.GREEN, e.getEntity().getName(), ChatColor.RED));
            }
        }
    }
}
