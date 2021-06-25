package boss;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class BossPlayerManager {
    private static ArrayList<BossPlayer> bossPlayers = new ArrayList<BossPlayer>();

    public static void addBossPlayer(BossPlayer bossPlayer) {
        bossPlayers.add(bossPlayer);
    }

    public static ArrayList<BossPlayer> getBossPlayers() {
        return bossPlayers;
    }

    public static void removeBossPlayer(BossPlayer bossPlayer) {
        bossPlayers.remove(bossPlayer);
    }

    public static BossPlayer getBossPlayerByPlayer(Player player) {
        for (BossPlayer bossPlayer : bossPlayers) {
            if (bossPlayer.getPlayer() == player)
                return bossPlayer;
        }
        return null;
    }

    public static Player getRandomPlayer() {
        ArrayList<BossPlayer> players = getBossPlayers();
        Random r = new Random();
        int low = 0;
        int high = players.size();
        int playerCount = r.nextInt(high - low) + low;
        return players.get(playerCount).getPlayer();
    }
}
