package me.isaacbarker.originsspigot.creeperorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class CreeperSleepListener implements Listener {

    private final OriginsSpigot plugin;

    public CreeperSleepListener (OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent e) {
        Player p = e.getPlayer();

        String playerOrigin = plugin.getPlayerConfig(p.getUniqueId());

        if (playerOrigin == null) { return; }

        if (playerOrigin.equals("creeper")) {
            e.setCancelled(true);
        }
    }

}
