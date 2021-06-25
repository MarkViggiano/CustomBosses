package bosses.abilities.zombieking;

import ability.Ability;

public class UndyingRage extends Ability {
    @Override
    public String getName() {
        return "Undying Rage";
    }

    @Override
    public void activate() {
        getBoss().setInvulnerable(15);
    }

    @Override
    public long getCooldown() {
        return 80;
    }
}
