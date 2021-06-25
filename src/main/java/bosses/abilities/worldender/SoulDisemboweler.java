package bosses.abilities.worldender;

import ability.Ability;
import boss.BossPlayer;
import boss.BossPlayerManager;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

public class SoulDisemboweler extends Ability {
    @Override
    public String getName() {
        return "Soul Disemboweler";
    }

    @Override
    public void activate() {
        ArrayList<BossPlayer> bossPlayers = BossPlayerManager.getBossPlayers();

        if (bossPlayers.isEmpty())
            return;

        Random r = new Random();
        int high = bossPlayers.size();
        int low = 0;
        int randomPlayer = r.nextInt(high - low) + low;
        BossPlayer bossPlayer = bossPlayers.get(randomPlayer);
        Player player = bossPlayer.getPlayer();
        Location playerLocation = player.getLocation();
        Location newBossLocation = new Location(getBoss().getWorld(), playerLocation.getBlockX(), playerLocation.getBlockY() + 2, playerLocation.getBlockZ());
        getBoss().getBossEntity().teleport(newBossLocation);
        getBoss().getWorld().strikeLightning(playerLocation);
        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 3 * 20, 1));
        player.playSound(playerLocation, Sound.ENDERMAN_HIT, 4, 2);
    }

    @Override
    public long getCooldown() {
        return 15;
    }
}
