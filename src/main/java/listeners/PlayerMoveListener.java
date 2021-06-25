package listeners;

import boss.BossPlayer;
import boss.BossPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerMoveListener extends ListenerRegister {
    public PlayerMoveListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        BossPlayer bossPlayer = BossPlayerManager.getBossPlayerByPlayer(e.getPlayer());
        if (bossPlayer == null)
            return;

        if (bossPlayer.isFrozen())
            e.setCancelled(true);
    }
}
