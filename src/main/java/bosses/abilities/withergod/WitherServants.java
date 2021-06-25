package bosses.abilities.withergod;

import ability.Ability;
import boss.BossPlayer;
import boss.BossPlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;

import java.util.ArrayList;

public class WitherServants extends Ability {
    @Override
    public String getName() {
        return "Wither Servants";
    }

    @Override
    public void activate() {

        ArrayList<BossPlayer> bossPlayers =  BossPlayerManager.getBossPlayers();
        for (int i = 0; i < bossPlayers.size(); i++) {
            Player player = bossPlayers.get(i).getPlayer();
            player.playSound(player.getLocation(), Sound.IRONGOLEM_DEATH, 4, 2);
            for (int w = 0; w < 3; w++) {
                Skeleton skeleton = (Skeleton) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
                skeleton.setSkeletonType(Skeleton.SkeletonType.WITHER);
                skeleton.setTarget(player);
            }
        }
    }

    @Override
    public long getCooldown() {
        return 60;
    }
}
