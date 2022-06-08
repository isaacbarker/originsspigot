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
            String playerOrigin = plugin.getPlayerConfig(p.getUniqueId());

            if (playerOrigin == null) { return; }

            if (playerOrigin.equals("creeper")) {
                if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION
                        || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
                ) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
