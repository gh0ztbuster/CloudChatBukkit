package net.cubespace.CloudChatBukkit.Listener;

import net.cubespace.CloudChatBukkit.CloudChatBukkitPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @date Last changed: 29.11.13 12:54
 */
public class ChatListener implements Listener {
    private CloudChatBukkitPlugin plugin;

    public ChatListener(CloudChatBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        plugin.getManagers().getAfkManager().reset(event.getPlayer());
    }
}
