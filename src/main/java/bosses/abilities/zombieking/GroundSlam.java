package bosses.abilities.zombieking;

import ability.Ability;
import org.bukkit.Sound;
import org.bukkit.util.Vector;

public class GroundSlam extends Ability {
    @Override
    public String getName() {
        return "Ground Slam";
    }

    @Override
    public void activate() {

        Vector v = new Vector(0, 5, 0);
        getBoss().getBossEntity().setVelocity(v);
        getBoss().getWorld().playSound(getBoss().getBossEntity().getLocation(), Sound.BLAZE_BREATH, 4, 2);

    }

    @Override
    public long getCooldown() {
        return 30;
    }
}
