package listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WebListener extends ListenerRegister {
    public WebListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void playerInWeb(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!e.getFrom().getBlock().equals(e.getTo().getBlock())) {
            Material blockMaterial = e.getTo().getBlock().getType();
            if (blockMaterial.equals(Material.WEB)) {
                player.damage(2);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, 3));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5 * 20, 2));
                player.playSound(player.getLocation(), Sound.AMBIENCE_CAVE, 3, 2);
            }
        }
    }
}
