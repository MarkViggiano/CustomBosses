package listeners;

import boss.Boss;
import boss.BossManager;
import boss.BossPlayer;
import boss.BossPlayerManager;
import bosses.ZombieKing;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class BossHitListener extends ListenerRegister {
    public BossHitListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void bossDamageEvent(EntityDamageEvent e) {

        //This happens if an damage event happens as the entity's health is set to 0, it will create and NullPointerException if this is not here
        if (e.getEntity() == null)
            return;

        Boss boss = BossManager.getBoss(e.getEntity());
        EntityDamageEvent.DamageCause cause = e.getCause();

        //Prevents the rest of the code running if the entity is not a boss.
        if (boss == null)
            return;

        if (cause.equals(EntityDamageEvent.DamageCause.FALL))
            bossTakenFallDamage(e, boss);

        if (cause.equals(EntityDamageEvent.DamageCause.FIRE) || cause.equals(EntityDamageEvent.DamageCause.FIRE_TICK) || cause.equals(EntityDamageEvent.DamageCause.LAVA))
            e.setCancelled(true);

        if (cause.equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) || cause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION))
            e.setDamage(0);

    }

    @EventHandler
    public void bossHitEvent(EntityDamageByEntityEvent e) {
        Boss boss = BossManager.getBoss(e.getEntity());
        if (boss == null)
            return;

        if (e.getEntity() instanceof ArmorStand)
            e.setCancelled(true);

        if (e.getEntity().equals(boss.getBossEntity())) {

            if (boss.isInvulnerable()) {
                e.setCancelled(true);
                return;
            }

            if (e.getDamager() instanceof Arrow)
                arrowHitBoss(e, boss);

            if (e.getDamager() instanceof Player)
                playerHitBoss(e, boss);

            if (boss.getHP() <= boss.getMAXHP() * 0.1)
                boss.activateUltimate();

            if (boss.getHP() <= 0)
                boss.killBoss((Player) e.getDamager());

        }

    }

    private double distance(double x1, double z1, double x2, double z2) {
        // Calculating distance
        return Math.sqrt(Math.pow(x2 - x1, 2) +
                Math.pow(z2 - z1, 2) * 1.0);
    }

    private void pound(Location currentLoc, Player player, double multiplier, double damage) {
        player.playSound(currentLoc, Sound.IRONGOLEM_DEATH, 4, 2);
        if(multiplier > 1) multiplier = 1;
        Vector vector = fromAtoB(currentLoc, player.getLocation());
        vector.multiply(multiplier * 1.25D).setY(vector.getY() + 1);
        if(vector.getY() > 1D) vector.setY(1D);
        player.setVelocity(vector);
        player.damage(damage);
    }

    private Vector fromAtoB(Location a, Location b) {
        return fromAtoB(a.toVector(), b.toVector());
    }

    private Vector fromAtoB(Vector a, Vector b) {
        return b.subtract(a);
    }

    private void arrowHitBoss(EntityDamageByEntityEvent e, Boss boss) {
        ((LivingEntity) e.getEntity()).setHealth(((LivingEntity) e.getEntity()).getMaxHealth());
        Arrow arrow = (Arrow) e.getDamager();
        Player player = (Player) arrow.getShooter();

        boss.removeHP(8);
        boss.addDamage(player, 8);

    }

    private void playerHitBoss(EntityDamageByEntityEvent e, Boss boss) {
        ((LivingEntity) e.getEntity()).setHealth(((LivingEntity) e.getEntity()).getMaxHealth());

        if (((Player) e.getDamager()).getItemInHand().getType().equals(Material.DIAMOND_SWORD)) {
            boss.removeHP(10);
            boss.addDamage(((Player) e.getDamager()), 10);
        } else {
            boss.removeHP(1);
            boss.addDamage(((Player) e.getDamager()), 1);
        }
    }

    private void bossTakenFallDamage(EntityDamageEvent e, Boss boss) {
        e.setDamage(0);
        if (boss instanceof ZombieKing) {
            double bossX = boss.getLocation().getX();
            double bossZ = boss.getLocation().getZ();
            ArrayList<BossPlayer> bossPlayers = BossPlayerManager.getBossPlayers();
            if (bossPlayers.isEmpty())
                return;
            for (BossPlayer bossPlayer : bossPlayers) {
                Player player = bossPlayer.getPlayer();
                double playerX = player.getLocation().getX();
                double playerZ = player.getLocation().getZ();
                double distance = distance(bossX, bossZ, playerX, playerZ);
                if (distance <= 30)
                    pound(boss.getLocation(), player, 2, 6);

            }
        }
    }

}
