package bosses.abilities.broodmother;

import ability.Ability;
import boss.BossPlayer;
import boss.BossPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Spiderling extends Ability {

    public Spiderling() {

    }

    @Override
    public String getName() {
        return "Spiderling";
    }

    @Override
    public void activate() {
        int count = Bukkit.getOnlinePlayers().size();

        for (BossPlayer bossPlayer : BossPlayerManager.getBossPlayers()) {
            Player player = bossPlayer.getPlayer();
            for (int s = 1; s < 5; s++) {
                Entity minion = getBoss().getWorld().spawnEntity(getBoss().getLocation(), EntityType.CAVE_SPIDER);
                ((CaveSpider) minion).setTarget(player);
            }
        }
    }

    @Override
    public long getCooldown() {
        return 85;
    }
}
