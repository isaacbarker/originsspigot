package me.isaacbarker.originsspigot.creeperorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CreeperAttackListener implements Listener {

    private final OriginsSpigot plugin;

    public CreeperAttackListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player damager && e.getEntity() instanceof Player p) {
            String damagerOrigin = plugin.getPlayerConfig(damager.getUniqueId());
            String playerOrigin = plugin.getPlayerConfig(p.getUniqueId());

            if (damagerOrigin == null || playerOrigin == null) {
                return;
            }
            // Check if damager is a creeper and player is a feline
            if (damagerOrigin == "feline" && playerOrigin == "creeper") {
                double amp = 1.25;
                double damage = e.getDamage();
                e.setDamage(damage * amp);
            }
        }
    }

}
