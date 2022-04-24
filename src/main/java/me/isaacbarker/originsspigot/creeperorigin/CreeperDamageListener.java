package me.isaacbarker.originsspigot.creeperorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class CreeperDamageListener implements Listener {

    private final OriginsSpigot plugin;

    public CreeperDamageListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p) {
            if (plugin.getPlayerConfig(p.getUniqueId()).equals("creeper")) {
                // cancels damage if tnt-immunity is triggered and it is block explosion
                if (plugin.getConfig().getBoolean("origins.creeper.tnt-immunity")) {
                    if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION
                            || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
                    ) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
