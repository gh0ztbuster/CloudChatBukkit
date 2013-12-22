package net.cubespace.CloudChatBukkit.Manager;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import net.cubespace.CloudChatBukkit.Message.AFKMessage;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 15.12.13 15:15
 */
public class AFKManager {
    private final CloudChatBukkitPlugin plugin;
    private final HashMap<Player, Long> lastPlayerAction = new HashMap<Player, Long>();
    private final HashMap<Player, Boolean> afkStatus = new HashMap<Player, Boolean>();

    public AFKManager(final CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;

        if(plugin.getConfig().getInt("AutoAFK", 0) > 0) {
            plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
                @Override
                public void run() {
                    for(Map.Entry<Player, Long> playerLongEntry : lastPlayerAction.entrySet()) {
                        if(!afkStatus.get(playerLongEntry.getKey()) && System.currentTimeMillis() - playerLongEntry.getValue() > plugin.getConfig().getInt("AutoAFK") * 1000) {
                            AFKMessage.send(playerLongEntry.getKey(), true);
                            afkStatus.put(playerLongEntry.getKey(), true);
                        }
                    }
                }
            }, 20, 20);
        }
    }

    public void add(Player player) {
        if(plugin.getConfig().getBoolean("HandleAFK", false)) {
            afkStatus.put(player, false);

            AFKMessage.send(player, false);

            if(plugin.getConfig().getInt("AutoAFK", 0) > 0) {
                lastPlayerAction.put(player, System.currentTimeMillis());
            }
        }
    }

    public void reset(Player player) {
        if(plugin.getConfig().getBoolean("HandleAFK", false)) {
            if(plugin.getConfig().getInt("AutoAFK", 0) > 0) {
                lastPlayerAction.put(player, System.currentTimeMillis());
            }

            if(!afkStatus.get(player)) return;

            afkStatus.put(player, false);
            AFKMessage.send(player, false);
        }
    }

    public boolean isAFK(Player player) {
        if(!plugin.getConfig().getBoolean("HandleAFK", false)) {
            return false;
        }

        return afkStatus.get(player);
    }

    public void remove(Player player) {
        lastPlayerAction.remove(player);
        afkStatus.remove(player);

        AFKMessage.send(player, false);
    }
}