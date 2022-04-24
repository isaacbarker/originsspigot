package me.isaacbarker.originsspigot.creeperorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CreeperAttackDamager implements Listener {

    private final OriginsSpigot plugin;

    public CreeperAttackDamager(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player damager && e.getEntity() instanceof Player p) {
            String damagerOrigin = plugin.getPlayerConfig(damager.getUniqueId());
            String playerOrigin = plugin.getPlayerConfig(p.getUniqueId());

            // Check if damager is a creeper and player is a feline
            if (damagerOrigin == "feline" && playerOrigin == "creeper") {
                double amp = plugin.getConfig().getDouble("origins.creeper.feline-damage-amplifier");
                double damage = e.getDamage();
                e.setDamage(damage * amp);
            }
        }
    }

}
