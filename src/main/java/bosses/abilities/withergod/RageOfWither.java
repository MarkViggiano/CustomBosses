package bosses.abilities.withergod;

import ability.Ultimate;

public class RageOfWither extends Ultimate {
    @Override
    public String getName() {
        return "Rage of the WitherGod";
    }

    @Override
    public void activate() {
        getBoss().rampage(5);
        getBoss().setInvulnerable(5);
    }

    @Override
    public long getCooldown() {
        return 150;
    }
}
