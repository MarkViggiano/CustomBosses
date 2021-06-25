package listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ListenerRegister implements Listener {

    private Plugin plugin;

    public ListenerRegister (JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getLogger().info(String.format("[LISTENER] Registering listener: %s", this.getClass().getSimpleName()));
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Plugin getPlugin() {
        return plugin;
    }

}
