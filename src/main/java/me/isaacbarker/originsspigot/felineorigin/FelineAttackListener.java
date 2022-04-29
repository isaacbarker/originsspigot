package me.isaacbarker.originsspigot.felineorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FelineAttackListener implements Listener {

    private final OriginsSpigot plugin;

    public FelineAttackListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player damager && e.getEntity() instanceof Player p) {
            String damagerOrigin = plugin.getPlayerConfig(damager.getUniqueId());
            String playerOrigin = plugin.getPlayerConfig(p.getUniqueId());

            // Check if damager is a creeper and player is a feline
            if (damagerOrigin == "creeper" && playerOrigin == "feline") {
                double amp = 0.75;
                double damage = e.getDamage();
                e.setDamage(damage * amp);
            }
        }
    }
}
