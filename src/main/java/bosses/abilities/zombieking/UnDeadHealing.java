package bosses.abilities.zombieking;

import ability.Ultimate;

public class UnDeadHealing extends Ultimate {
    @Override
    public String getName() {
        return "Undead Healing";
    }

    @Override
    public void activate() {
        if (getBoss().getHP() == getBoss().getMAXHP())
            return;

        double missingHP = getBoss().getMAXHP() - getBoss().getHP();
        getBoss().setHP(getBoss().getHP() + (missingHP * 0.2));
    }

    @Override
    public long getCooldown() {
        return 90;
    }

}
