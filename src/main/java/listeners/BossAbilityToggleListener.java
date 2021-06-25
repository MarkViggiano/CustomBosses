package listeners;

import boss.BossPlayerManager;
import bosses.BroodMother;
import events.BossDealthEvent;
import events.BossSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class BossAbilityToggleListener extends ListenerRegister {
    public BossAbilityToggleListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void bossSpawn(BossSpawnEvent e) {
        //Run the task 2 seconds after this is called, then wait 15 seconds between each call
        int taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), new Runnable() {
            public void run() {

                e.getBoss().activateAbilities();

                if (e.getBoss().getBossEntity() instanceof Monster)
                    ((Monster) e.getBoss().getBossEntity()).setTarget(BossPlayerManager.getRandomPlayer());
            }
        }, 10, 20);

        e.getBoss().setTaskId(taskid);
        Bukkit.getLogger().info(String.format("[INFO] Task id for boss: %s", taskid));
    }

    @EventHandler
    public void bossDeath(BossDealthEvent e) {
        Bukkit.getScheduler().cancelTask(e.getBoss().getTaskId());

        if (e.getBoss() instanceof BroodMother)
            e.getBoss().activateUltimate();

    }
}
