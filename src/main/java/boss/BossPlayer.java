package boss;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BossPlayer {

    private Player player;
    private boolean invincible = false;
    private int markedTime;
    private long playerMarkedAt;
    private int frozenTime;
    private long frozenAt;

    public BossPlayer(Player player) {
        this.player = player;

        BossPlayerManager.addBossPlayer(this);
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void equip() {
        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        List<String> lore = new ArrayList<>();

        ItemStack bossKiller = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta swordMeta = bossKiller.getItemMeta();
        swordMeta.setDisplayName(String.format("%sBoss Killer", ChatColor.RED));
        lore.add("Deals 10 damage to bosses.");
        swordMeta.setLore(lore);
        bossKiller.setItemMeta(swordMeta);

        ItemStack bossGunner = new ItemStack(Material.BOW, 1);
        ItemMeta bossGunnerMeta = bossGunner.getItemMeta();
        bossGunnerMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        bossGunnerMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        bossGunnerMeta.setDisplayName(String.format("%sBoss Gunner", ChatColor.RED));
        lore.clear();
        lore.add("Deals 12 damage to bosses");
        bossGunnerMeta.setLore(lore);
        bossGunner.setItemMeta(bossGunnerMeta);


        getPlayer().getEquipment().setHelmet(helmet);
        getPlayer().getEquipment().setChestplate(chestplate);
        getPlayer().getEquipment().setLeggings(leggings);
        getPlayer().getEquipment().setBoots(boots);

        getPlayer().getInventory().setItem(0, bossKiller);
        getPlayer().getInventory().setItem(1, bossGunner);
        getPlayer().getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 32));
        getPlayer().getInventory().setItem(3, new ItemStack(Material.GOLDEN_APPLE, 20));
        getPlayer().getInventory().setItem(7, new ItemStack(Material.MILK_BUCKET, 1));
        getPlayer().getInventory().setItem(8, new ItemStack(Material.ARROW, 1));

        getPlayer().sendMessage(ChatColor.RED + "Go save the world.");
        getPlayer().updateInventory();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isMarked() {
        return (System.currentTimeMillis() - this.playerMarkedAt) < this.markedTime * 1000L;
    }

    public void mark(int duration) {
        this.markedTime = duration;
        this.playerMarkedAt = System.currentTimeMillis();
    }

    public void freeze(int duration) {
        this.frozenTime = duration;
        this.frozenAt = System.currentTimeMillis();
    }

    public boolean isFrozen() {
        return (System.currentTimeMillis() - this.frozenAt) < this.frozenTime * 1000L;
    }

}
