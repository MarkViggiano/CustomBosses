package bosses.abilities.broodmother;

import ability.Ability;
import boss.BossPlayerManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

public class WebSling extends Ability {

    public WebSling() {

    }

    @Override
    public String getName() {
        return "WebSling";
    }

    @Override
    public void activate() {
        Location centerPoint = getBoss().getLocation();
        for (int i = 0; i < BossPlayerManager.getBossPlayers().size() * 4; i++) {
            float x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
            float y = (float) -5 + (float)(Math.random() * ((5 - -5) + 1));
            float z = (float) -0.3 + (float)(Math.random() * ((0.3 - -0.3) + 1));

            FallingBlock web = getBoss().getWorld().spawnFallingBlock(centerPoint, Material.WEB, (byte) 0);
            web.setVelocity(new Vector(x, y, z));
        }
    }

    @Override
    public long getCooldown() {
        return 60;
    }
}
