package listeners;

import boss.Boss;
import boss.BossManager;
import boss.BossPlayer;
import boss.BossPlayerManager;
import bosses.WitherGod;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerHitListener extends ListenerRegister {

    public PlayerHitListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void playerDamaged(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Player) {
            Boss boss = BossManager.getBoss(e.getDamager());

            if (boss != null)
                bossHitPlayer(e);

            //Spiderling
            if (e.getDamager() instanceof CaveSpider)
                spiderlingHitPlayer(e);

            //Wither Minion
            if (e.getDamager() instanceof Skeleton)
                skeletonHitPlayer(e);

        } else
            return;
    }

    @EventHandler
    public void playerShot(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE && e.getEntity() instanceof Player) {
            ((LivingEntity) e.getEntity()).damage(3);
        }

    }

    private void bossHitPlayer(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
        Player player = ((Player) e.getEntity());
        BossPlayer bossPlayer = BossPlayerManager.getBossPlayerByPlayer(player);
        if (bossPlayer == null)
            return;

        if (bossPlayer.isMarked())
            player.damage(3 * 1.1);
        else
            player.damage(3);
        return;
    }

    private void spiderlingHitPlayer(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
        Player player = ((Player) e.getEntity());
        BossPlayer bossPlayer = BossPlayerManager.getBossPlayerByPlayer(player);
        if (bossPlayer == null)
            return;

        if (bossPlayer.isMarked())
            player.damage(2 * 1.1);
        else
            player.damage(2);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, 2));
        return;
    }

    private void skeletonHitPlayer(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
        Skeleton skeleton = (Skeleton) e.getDamager();
        Player player = (Player) e.getEntity();
        if (skeleton.getSkeletonType() == Skeleton.SkeletonType.WITHER) {
            for (Boss boss : BossManager.getBosses()) {
                if (boss instanceof WitherGod) {
                    if (boss.isRampaged())
                        player.damage(2 * 1.9);
                    else
                        player.damage(2);
                    break;
                }
            }
        } else
            return;

    }

}
