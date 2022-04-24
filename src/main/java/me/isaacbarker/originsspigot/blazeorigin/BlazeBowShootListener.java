package me.isaacbarker.originsspigot.blazeorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class BlazeBowShootListener implements Listener {

    private final OriginsSpigot plugin;

    public BlazeBowShootListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player p) {
            String playerOrigin = plugin.getPlayerConfig(p.getUniqueId());

            if (playerOrigin.equals("blaze")) {
                if (plugin.getConfig().getBoolean("origins.blaze.fire-bow")) {
                    // add flame to all arrows
                    e.getProjectile().setFireTicks(1);
                }
            }
        }
    }

}
