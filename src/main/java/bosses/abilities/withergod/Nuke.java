package bosses.abilities.withergod;

import ability.Ability;
import boss.BossPlayer;
import boss.BossPlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Nuke extends Ability {
    @Override
    public String getName() {
        return "Nuke";
    }

    @Override
    public void activate() {
        ArrayList<BossPlayer> bossPlayers =  BossPlayerManager.getBossPlayers();
        for (int i = 0; i < bossPlayers.size(); i++) {
            Player player = bossPlayers.get(i).getPlayer();
            player.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 6, 2);
            player.getWorld().createExplosion(player.getLocation(), 2);
        }
    }

    @Override
    public long getCooldown() {
        return 100;
    }
}
