package events;

import ability.Ability;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AbilityUseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Ability ability;

    public AbilityUseEvent(Ability ability) {
        this.ability = ability;
    }

    public Ability getAbility() {
        return ability;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
