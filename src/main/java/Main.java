import commands.CustomBossCommand;
import commands.FightCommand;
import commands.ScanWorldCommand;
import commands.SpawnBossCommand;
import listeners.*;
import org.bukkit.plugin.java.JavaPlugin;
import scanner.WorldScanner;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        registerListeners();
        registerCommands();
        loadConfig();
        WorldScanner.setWorldScannerPlugin(this);

    }

    @Override
    public void onDisable() {

    }

    private void registerListeners() {
        new BossAbilityToggleListener(this);
        new BossHitListener(this);
        new PlayerDeathListener(this);
        new PlayerHitListener(this);
        new PlayerMoveListener(this);
        new ExplosionListener(this);
        new WebListener(this);
    }

    private void registerCommands() {
        getCommand("customboss").setExecutor(new CustomBossCommand());
        getCommand("fight").setExecutor(new FightCommand());
        getCommand("spawnboss").setExecutor(new SpawnBossCommand());
        getCommand("scanworld").setExecutor(new ScanWorldCommand());
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
