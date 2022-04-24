package me.isaacbarker.originsspigot.felineorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FelineDamageListener implements Listener {

    private final OriginsSpigot plugin;

    public  FelineDamageListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p) {
            if (plugin.getPlayerConfig(p.getUniqueId()).equals("feline")) {
                // if fall-damage is true then the player will not take fall damage
                if (plugin.getConfig().getBoolean("origins.feline.fall-damage")) {
                    if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
