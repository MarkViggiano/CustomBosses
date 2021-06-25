package scanner;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class WorldScanner {

    private static JavaPlugin plugin;

    public static void setWorldScannerPlugin(JavaPlugin newplugin) {
        plugin = newplugin;
    }

    public static void scanWorld(Player player) {
        Bukkit.broadcastMessage(String.format("%sCustomBosses>%s World scanning has started, note this will cause LAG!",
                ChatColor.RED, ChatColor.GRAY));
        World world = player.getWorld();
        Chunk[] chunks = world.getLoadedChunks();
        for (Chunk chunk: chunks) {
            int chunkX = chunk.getX() << 4;
            int chunkZ = chunk.getZ() << 4;
            for (int x = chunkX; x < chunkX + 16; x++) {
                for (int y = 0; y < world.getMaxHeight(); y++) {
                    for (int z = chunkZ; z < chunkZ + 16; z++) {
                        Block block = world.getBlockAt(x, y, z);
                        processBlock(world, block);
                    }
                }
            }
        }
    }

    private static void processBlock(World world, Block block) {
        if (block.getType().equals(Material.DIAMOND_BLOCK)) {
            plugin.getConfig().set("BossPlayerSpawnLocation.worldName", world.getName());
            plugin.getConfig().set("BossPlayerSpawnLocation.x", block.getX());
            plugin.getConfig().set("BossPlayerSpawnLocation.y", block.getY() + 1);
            plugin.getConfig().set("BossPlayerSpawnLocation.z", block.getZ());
            Bukkit.broadcastMessage(String.format("%sWorld Scanner> %sFound boss player spawn at: %s%s, %s, %s",
                    ChatColor.YELLOW, ChatColor.GRAY, ChatColor.GREEN, block.getX(), block.getY() + 1, block.getZ()));
            plugin.saveConfig();
        }

        if(block.getType().equals(Material.SPONGE)) {
            plugin.getConfig().set("BossSpawnLocation.worldName", world.getName());
            plugin.getConfig().set("BossSpawnLocation.x", block.getX());
            plugin.getConfig().set("BossSpawnLocation.y", block.getY() + 5);
            plugin.getConfig().set("BossSpawnLocation.z", block.getZ());
            Bukkit.broadcastMessage(String.format("%sWorld Scanner> %sFound boss spawn at: %s%s, %s, %s",
                    ChatColor.YELLOW, ChatColor.GRAY, ChatColor.GREEN, block.getX(), block.getY() + 5, block.getZ()));
            plugin.saveConfig();
            plugin.getLogger().info(String.format("%s", plugin.getConfig().getConfigurationSection("BossSpawnLocation").getKeys(false)));

            //TESTING
            ConfigurationSection worldData = plugin.getConfig().getConfigurationSection("Maps.Volcanic");
            String middleData = worldData.get("middle").toString();
            String[] cords = middleData.split(",");
            plugin.getLogger().info(String.format("%s, %s, %s", cords[0], cords[1], cords[2]));
            //Point middlePoint = new Point();
            //middlePoint.setX(Double.parseDouble(cords[0]));
            //middlePoint.setY(Double.parseDouble(cords[1]));
            //middlePoint.setZ(Double.parseDouble(cords[2]));
            //setMiddle(middlePoint);

            List<String> redSpawnPoints = worldData.getStringList("RedSpawns");
            for (String redSpawnPoint : redSpawnPoints) {
                String[] spawnPointParts = redSpawnPoint.split(",");
                plugin.getLogger().info(String.format("%s, %s, %s, %s", spawnPointParts[0], spawnPointParts[1], spawnPointParts[2], spawnPointParts[3]));
            }
        }
    }

    public static Location getBossPlayerSpawn() {
        String worldName = plugin.getConfig().getString("BossPlayerSpawnLocation.worldName");
        double x = plugin.getConfig().getDouble("BossPlayerSpawnLocation.x");
        double y = plugin.getConfig().getDouble("BossPlayerSpawnLocation.y");
        double z = plugin.getConfig().getDouble("BossPlayerSpawnLocation.z");
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }

    public static Location getBossSpawn() {
        String worldName = plugin.getConfig().getString("BossSpawnLocation.worldName");
        double x = plugin.getConfig().getDouble("BossSpawnLocation.x");
        double y = plugin.getConfig().getDouble("BossSpawnLocation.y");
        double z = plugin.getConfig().getDouble("BossSpawnLocation.z");
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }

}
