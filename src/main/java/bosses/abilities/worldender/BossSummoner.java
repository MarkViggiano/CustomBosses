package bosses.abilities.worldender;

import ability.Ability;
import boss.BossManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

import java.util.Random;

public class BossSummoner extends Ability {

    private String summonedBossName;

    @Override
    public String getName() {
        return "Boss Summoner";
    }

    @Override
    public void activate() {
        String[] bosses = {"broodmother", "zombieking", "withergod"};
        Random r = new Random();
        int low = 0;
        int high = bosses.length - 1;
        int boss = r.nextInt(high - low) + low;
        BossManager.spawnBoss(bosses[boss]);
        getBoss().getWorld().playSound(getBoss().getLocation(), Sound.AMBIENCE_THUNDER, 4, 4);
    }

    @Override
    public long getCooldown() {
        return 420;
    }

    @Override
    public String getUsedMessage(String name) {

        return String.format("%sWorld Ender> %sRise minion... RISE!",
                ChatColor.DARK_RED, ChatColor.RED);

    }
}
