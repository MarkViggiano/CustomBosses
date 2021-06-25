package bosses.abilities.worldender;

import ability.Ability;
import boss.BossPlayer;
import boss.BossPlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Scorch extends Ability {
    @Override
    public String getName() {
        return "Scorch";
    }

    @Override
    public void activate() {
        ArrayList<BossPlayer> bossPlayers = BossPlayerManager.getBossPlayers();
        for (BossPlayer bossPlayer : bossPlayers) {
            Player player = bossPlayer.getPlayer();
            player.playSound(player.getLocation(), Sound.GHAST_SCREAM, 4, 2);
            player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 5 * 20, 2));

        }
    }

    @Override
    public long getCooldown() {
        return 30;
    }
}
