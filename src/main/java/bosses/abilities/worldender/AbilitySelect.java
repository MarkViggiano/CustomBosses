package bosses.abilities.worldender;

import ability.Ultimate;
import bosses.abilities.broodmother.Spiderling;
import bosses.abilities.broodmother.WebSling;
import bosses.abilities.withergod.Nuke;
import bosses.abilities.withergod.WitherServants;
import bosses.abilities.zombieking.UnDeadHealing;
import bosses.abilities.zombieking.UndyingRage;

import java.util.Random;

public class AbilitySelect extends Ultimate {

    @Override
    public String getName() {
        return "Ability Selector";
    }

    @Override
    public void activate() {
        Class[] abilities = {Spiderling.class, WebSling.class, Nuke.class, WitherServants.class, UnDeadHealing.class, UndyingRage.class};
        Random r = new Random();
        int low = 0;
        int high = abilities.length - 1;
        int randomInt = r.nextInt(high - low) + low;
        getBoss().addTemporaryAbility(abilities[randomInt]);
        getBoss().setInvulnerable(5);
    }

    @Override
    public long getCooldown() {
        return 120;
    }
}
