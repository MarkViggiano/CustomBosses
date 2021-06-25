package boss;

import bosses.BroodMother;
import bosses.WitherGod;
import bosses.WorldEnder;
import bosses.ZombieKing;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

public class BossManager {
    private static ArrayList<Boss> bosses = new ArrayList<Boss>();

    public static boolean spawnBoss(String name) {
        switch (name.toLowerCase()) {
            case "broodmother":
                addBoss(new BroodMother());
                return true;
            case "withergod":
                addBoss(new WitherGod());
                return true;
            case "zombieking":
                addBoss(new ZombieKing());
                return true;
            case "worldender":
                addBoss(new WorldEnder());
                return true;
            default:
                return false;
        }
    }

    public static void addBoss(Boss boss) {
        bosses.add(boss);
    }

    public static void removeBoss(Boss boss) {
        bosses.remove(boss);
    }

    public static ArrayList<Boss> getBosses() {
        return bosses;

    }

    public static Boss getBoss(Entity e) {
        for (Boss boss: getBosses()) {
            if (boss.getBossEntity().equals(e))
                return boss;
        }

        return null;
    }
}
