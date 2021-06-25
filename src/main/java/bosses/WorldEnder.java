package bosses;

import ability.Ability;
import boss.Boss;
import boss.BossManager;
import bosses.abilities.worldender.*;
import events.BossDealthEvent;
import events.BossSpawnEvent;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.EulerAngle;

public class WorldEnder extends Boss {
    public WorldEnder() {
        super(EntityType.ARMOR_STAND, "World Ender", 5000, new AbilitySelect(), WorldsWrath.class, BossSummoner.class, Scorch.class, SoulStealer.class, SoulDisemboweler.class);
        ArmorStand bossArmor =  ((ArmorStand) getBossEntity());
        bossArmor.setGravity(false);
        bossArmor.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
        bossArmor.setBasePlate(false);
        bossArmor.setCanPickupItems(false);
        bossArmor.setArms(true);
        bossArmor.setLeftLegPose(new EulerAngle(120f, 0f, 0f));
        bossArmor.setLeftArmPose(new EulerAngle(0f, 0f, 0f));
        bossArmor.setRightArmPose(new EulerAngle(30f, 0f, 0f));

        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(Color.fromBGR(19, 19, 44));
        chestplate.setItemMeta(chestplateMeta);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(Color.fromBGR(12, 12, 22));
        leggings.setItemMeta(leggingsMeta);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.BLACK);
        boots.setItemMeta(bootsMeta);

        //Wither Skull
        bossArmor.setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (byte) 1));
        bossArmor.setChestplate(chestplate);
        bossArmor.setLeggings(leggings);
        bossArmor.setBoots(boots);

    }

    @Override
    public void spawnBoss(Location location) {
        World world = location.getWorld();
        Entity entity = world.spawnEntity(location, getType());
        setBossEntity(entity);
        BossManager.addBoss(this);
        Bukkit.getPluginManager().callEvent(new BossSpawnEvent(this));
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 4, 1);
            world.strikeLightningEffect(location);
            world.strikeLightningEffect(location);
            player.sendMessage(String.format("%sWorlder Ender> %sPray fool, you face the %s%s%s!",
                    ChatColor.DARK_RED, ChatColor.RED, ChatColor.DARK_RED, getName().toUpperCase(), ChatColor.RED));
        }
    }

    @Override
    public void killBoss(Player player) {
        for (Ability ability: getAbilities()) {
            ability.destroy();
        }
        getBossEntity().remove();
        BossManager.removeBoss(this);
        Bukkit.getPluginManager().callEvent(new BossDealthEvent(this));
        Bukkit.broadcastMessage(String.format("%sWorld Ender> %sI will have my revenge %s%s%s...",
                ChatColor.DARK_RED, ChatColor.RED, ChatColor.GREEN, player.getName(), ChatColor.RED));
        announceMultipleLines(String.format("%s****************************\n\n%s\n\n%s****************************\n%sCustomBosses> %sThe %s%s%s has been defeated by %s%s%s!",
                ChatColor.GREEN, getPlayerDamage(), ChatColor.GREEN, ChatColor.RED, ChatColor.GRAY, ChatColor.GREEN, getName(), ChatColor.GRAY, ChatColor.GREEN, player.getName(), ChatColor.GRAY));
    }
}
