package boss;

import ability.Ability;
import ability.Ultimate;
import events.AbilityUseEvent;
import events.BossDealthEvent;
import events.BossSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import scanner.WorldScanner;

import java.util.*;

public class Boss {

    private EntityType type;
    private String name;
    private Entity bossEntity;
    private double MAX_HP;
    private double hp;
    private ArrayList<Ability> abilities;
    private Ultimate ultimate;
    private int rampageDuration = 0;
    private long rampagedAt = 0;
    private int invulnerableDuration = 0;
    private long invulnerableAt = 0;
    private int taskId;
    HashMap<Player, Integer> damageDone = new HashMap<Player, Integer>();

    public Boss(EntityType type, String name, double MAX_HP, Ultimate ultimate, Class<? extends Ability>... abilities) {
        this.type = type;
        this.name = name;
        this.MAX_HP = MAX_HP;
        this.hp = MAX_HP;
        this.ultimate = ultimate;
        this.ultimate.setBoss(this);

        spawnBoss(WorldScanner.getBossSpawn());
        setUpAbilities(abilities);
    }

    public void spawnBoss(Location location) {
        World world = location.getWorld();
        Entity entity = world.spawnEntity(location, this.type);
        this.bossEntity = entity;
        BossManager.addBoss(this);
        Bukkit.getPluginManager().callEvent(new BossSpawnEvent(this));
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(getBossSpawnMessage());
        }
        this.bossEntity.setCustomNameVisible(true);
        this.bossEntity.setCustomName(String.format("%s%s%s/%s", ChatColor.BOLD, ChatColor.RED, (int) this.getHP(), (int) this.getMAXHP()));
    }

    public void killBoss(Player player) {
        BossManager.removeBoss(this);
        for (Ability ability: this.abilities) {
            this.abilities.remove(this);
            ability.destroy();
        }
        ((LivingEntity) getBossEntity()).setHealth(0);
        Bukkit.getPluginManager().callEvent(new BossDealthEvent(this));
        announceMultipleLines(String.format("%s****************************\n\n%s\n\n%s****************************\n%sCustomBosses> %sThe %s%s%s has been defeated by %s%s%s!",
                ChatColor.GREEN, this.getPlayerDamage(), ChatColor.GREEN, ChatColor.RED, ChatColor.GRAY, ChatColor.GREEN, this.getName(), ChatColor.GRAY, ChatColor.GREEN, player.getName(), ChatColor.GRAY));
    }

    protected void announceMultipleLines(String message) {
        String[] s = message.split("\n");
        for (String m : s) {
            Bukkit.broadcastMessage(m);
        }
    }

    private void setUpAbilities(Class<? extends Ability>... abilities) {
        ArrayList<Ability> newAbilities = new ArrayList<Ability>();
        for (Class<? extends Ability> ability : abilities) {
            try {
                Ability newAbility = ability.newInstance();
                newAbility.setBoss(this);
                newAbilities.add(newAbility);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        this.abilities = newAbilities;
    }

    public void addTemporaryAbility(Class<? extends Ability> ability) {
        try {
            Ability newAbility = ability.newInstance();
            newAbility.setDestroyOnUse(true);
            this.abilities.add(newAbility);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void removeAbility(Ability ability) {
        this.abilities.remove(ability);
    }

    public void activateAbilities() {
        ArrayList<Ability> abilities = this.abilities;
        for (Ability ability : abilities) {
            if (!ability.isOnCooldown()) {
                ability.activate();
                ability.setLastUsed(System.currentTimeMillis());
                Bukkit.broadcastMessage(ability.getUsedMessage(ability.getName()));
                Bukkit.getPluginManager().callEvent(new AbilityUseEvent(ability));
                if (ability.destroysOnUse())
                    ability.destroy();
            }
        }
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public void activateUltimate() {
        if (this.ultimate == null)
            return;

        if (this.ultimate.isOnCooldown())
            return;

        this.ultimate.activate();
        Bukkit.broadcastMessage(String.format("%sCustomBosses> %s%s%s used%s ultimate ability%s: %s%s%s!",
                ChatColor.RED, ChatColor.GREEN, getName(), ChatColor.GRAY, ChatColor.YELLOW, ChatColor.GRAY, ChatColor.GREEN, this.ultimate.getName(), ChatColor.GRAY));
        this.ultimate.setLastUsed(System.currentTimeMillis());
    }

    public String getBossSpawnMessage() {
        return String.format("%sCustomBosses> %sThe %s%s%s has spawned at: %s%s, %s, %s%s!",
                ChatColor.RED, ChatColor.GRAY, ChatColor.GREEN, this.name, ChatColor.GRAY, ChatColor.GREEN,
                this.getLocation().getBlockX(), this.getLocation().getBlockY(), this.getLocation().getBlockZ(), ChatColor.GRAY);
    }

    public void addHP(double hp) {
        this.hp += hp;
    }

    public void removeHP(double hp) {
        this.hp -= hp;
    }

    public void setHP(double hp) {
        this.hp = hp;
    }

    public double getHP() {
        return hp;
    }

    public double getMAXHP() {
        return MAX_HP;
    }

    public EntityType getType() {
        return type;
    }

    public World getWorld() {
        return bossEntity.getWorld();
    }

    public Location getLocation() {
        return bossEntity.getLocation();
    }

    public void setBossEntity(Entity entity) {
        this.bossEntity = entity;
    }

    public Entity getBossEntity() {
        return bossEntity;
    }

    public String getName() {
        return name;
    }

    public void addDamage(Player player, int damage) {

        if (this.damageDone.get(player) == null)
            this.damageDone.put(player, damage);
        else
            this.damageDone.replace(player, damage + this.damageDone.get(player));

        this.bossEntity.setCustomName(String.format("%s%s%s/%s", ChatColor.BOLD, ChatColor.RED, (int) this.getHP(), (int) this.getMAXHP()));
    }

    public String getPlayerDamage() {
        // Create a list from elements of HashMap
        List<Map.Entry<Player, Integer>> list = new ArrayList<>(this.damageDone.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Player, Integer> >() {
            public int compare(Map.Entry<Player, Integer> o1, Map.Entry<Player, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Player, Integer> temp = new LinkedHashMap<Player, Integer>();
        for (Map.Entry<Player, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }

        String playerChart = "";
        int i = Bukkit.getOnlinePlayers().size();

        for (Map.Entry<Player, Integer> playerData : temp.entrySet()) {
            Player player = playerData.getKey();
            int damage = playerData.getValue();
            playerChart += String.format("%s%s. %s %s || %s%s%s damage to %s%s%s!\n",
                    ChatColor.RED, i, ChatColor.YELLOW, player.getName(), ChatColor.GRAY, ChatColor.YELLOW, damage, ChatColor.GRAY, ChatColor.GREEN, getName(), ChatColor.GRAY);
            i--;
        }

        return playerChart;
    }

    public boolean isRampaged() {
        return (System.currentTimeMillis() - this.rampagedAt) < this.rampageDuration * 1000L;
    }

    public void rampage(int duration) {
        this.rampageDuration = duration;
        this.rampagedAt = System.currentTimeMillis();
    }

    public boolean isInvulnerable() {
        return (System.currentTimeMillis() - this.invulnerableAt) < this.invulnerableDuration * 1000L;
    }

    public void setInvulnerable(int duration) {
        this.invulnerableDuration = duration;
        this.invulnerableAt = System.currentTimeMillis();
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

}
