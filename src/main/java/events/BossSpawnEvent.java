package events;

import boss.Boss;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BossSpawnEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Boss boss;

    public BossSpawnEvent(Boss boss) {
        this.boss = boss;
    }

    public Boss getBoss() {
        return boss;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
