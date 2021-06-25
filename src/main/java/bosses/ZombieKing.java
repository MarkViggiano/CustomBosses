package bosses;

import boss.Boss;
import bosses.abilities.zombieking.GroundSlam;
import bosses.abilities.zombieking.Hoard;
import bosses.abilities.zombieking.UnDeadHealing;
import bosses.abilities.zombieking.UndyingRage;
import org.bukkit.entity.EntityType;

public class ZombieKing extends Boss {
    public ZombieKing() {
        super(EntityType.GIANT, "Zombie King", 2500, new UnDeadHealing(), Hoard.class, GroundSlam.class, UndyingRage.class);
    }
}
