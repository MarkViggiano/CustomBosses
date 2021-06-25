package bosses.abilities.broodmother;

import ability.Ability;
import boss.BossPlayer;
import boss.BossPlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

public class MarkDash extends Ability {
    @Override
    public String getName() {
        return "Marked Dash";
    }

    @Override
    public void activate() {

        ArrayList<BossPlayer> bossPlayers = BossPlayerManager.getBossPlayers();
        Random r = new Random();
        int high = bossPlayers.size();
        int low = 0;
        int randomPlayer;
        if (high == 1)
            randomPlayer = 0;
        else
            randomPlayer = r.nextInt(high - low + 1) + low;
        BossPlayer bossPlayer = bossPlayers.get(randomPlayer);
        Player player = bossPlayer.getPlayer();
        player.playSound(bossPlayer.getPlayer().getLocation(), Sound.ANVIL_LAND, 3, 2);
        player.sendMessage(String.format("%sCustomMobs> %sYou have been marked by the %s%s%s! You will take 10 percent more damage for the next 10 seconds!",
                ChatColor.RED, ChatColor.GRAY, ChatColor.GREEN, getBoss().getName(), ChatColor.GRAY));
        bossPlayer.mark(10);
        Spider broodMother = ((Spider) getBoss().getBossEntity());
        broodMother.setTarget(player);
        broodMother.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2, 4));
    }

    @Override
    public long getCooldown() {
        return 45;
    }
}
