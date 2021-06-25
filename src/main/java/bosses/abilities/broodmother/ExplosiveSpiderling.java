package bosses.abilities.broodmother;

import ability.Ultimate;
import boss.BossPlayer;
import boss.BossPlayerManager;
import org.bukkit.entity.Player;

public class ExplosiveSpiderling extends Ultimate {
    @Override
    public String getName() {
        return "Explosive Revenge";
    }

    @Override
    public void activate() {
        for (BossPlayer bossPlayer : BossPlayerManager.getBossPlayers()) {
            Player player = bossPlayer.getPlayer();

            player.getWorld().createExplosion(player.getLocation(), 2);
        }
    }

    @Override
    public long getCooldown() {
        return 10;
    }
}
