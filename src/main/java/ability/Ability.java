package ability;

import boss.Boss;
import org.bukkit.ChatColor;

public abstract class Ability {
    private long lastUsed;
    private Boss boss;
    private boolean destroyOnUse = false;

    public Ability() {
    }

    public abstract String getName();
    public abstract void activate();
    public abstract long getCooldown();

    public long getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(long lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public boolean isOnCooldown() {
        return (System.currentTimeMillis() - getLastUsed()) < getCooldown() * 1000L;
    }

    public String getUsedMessage(String abilityName) {
        return String.format("%sCustomBosses>%s %s %s used %s%s%s!",
                ChatColor.RED, ChatColor.GREEN, boss.getName(), ChatColor.GRAY, ChatColor.GREEN, abilityName, ChatColor.GRAY);
    }

    public void destroy() {
        try {
            getBoss().removeAbility(this);
            finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void setDestroyOnUse(boolean destroyOnUse) {
        this.destroyOnUse = destroyOnUse;
    }

    public boolean destroysOnUse() {
        return destroyOnUse;
    }

}
