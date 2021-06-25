package bosses.abilities.zombieking;

import ability.Ability;
import boss.BossPlayer;
import boss.BossPlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Hoard extends Ability {
    @Override
    public String getName() {
        return "Zombie Hoard";
    }

    @Override
    public void activate() {
        getBoss().setInvulnerable(5);
        ArrayList<BossPlayer> bossPlayers = BossPlayerManager.getBossPlayers();
        for (BossPlayer bossPlayer : bossPlayers) {
            Player player = bossPlayer.getPlayer();
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_HIT, 4, 2);
            for (int i = 1; i < 8; i++) {
                Zombie zombie = (Zombie) getBoss().getWorld().spawnEntity(getBoss().getBossEntity().getLocation(), EntityType.ZOMBIE);
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) Double.POSITIVE_INFINITY, 1));
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, (int) Double.POSITIVE_INFINITY, 1));
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int) Double.POSITIVE_INFINITY, 0));
                zombie.setBaby(false);
                zombie.setCanPickupItems(false);
                zombie.setTarget(player);
            }
        }
    }

    @Override
    public long getCooldown() {
        return 45;
    }
}
