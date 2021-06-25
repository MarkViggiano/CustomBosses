package listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ExplosionListener extends ListenerRegister {
    public ExplosionListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void explosion(EntityExplodeEvent e) {

        if (e.getEntityType() == null)
            return;

        if (e.getEntityType() == EntityType.SKELETON) {
            Skeleton skeleton = (Skeleton) e.getEntity();
            if (skeleton.getSkeletonType() == Skeleton.SkeletonType.WITHER)
                skeleton.setMaxHealth(skeleton.getMaxHealth());
            else
                return;
        } else
            return;
    }

}
