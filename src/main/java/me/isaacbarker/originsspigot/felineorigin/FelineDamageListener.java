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
            String playerOrigin = plugin.getPlayerConfig(p.getUniqueId());

            if (playerOrigin == null) { return; }

            if (playerOrigin.equals("feline")) {
                if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
