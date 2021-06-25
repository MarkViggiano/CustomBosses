package bosses;

import boss.Boss;
import bosses.abilities.broodmother.ExplosiveSpiderling;
import bosses.abilities.broodmother.MarkDash;
import bosses.abilities.broodmother.Spiderling;
import bosses.abilities.broodmother.WebSling;
import org.bukkit.entity.EntityType;

public class BroodMother extends Boss {

    public BroodMother() {
        super(EntityType.SPIDER, "BroodMother", 1000, new ExplosiveSpiderling(), WebSling.class, MarkDash.class, Spiderling.class);
    }
}
