package me.isaacbarker.originsspigot.felineorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class FelineAttackDamage implements Listener {

    private final OriginsSpigot plugin;

    public FelineAttackDamage(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player damager && e.getEntity() instanceof Player p) {
            String damagerOrigin = plugin.getPlayerConfig(damager.getUniqueId());
            String playerOrigin = plugin.getPlayerConfig(p.getUniqueId());

            // Check if damager is a creeper and player is a feline
            if (damagerOrigin == "creeper" && playerOrigin == "feline") {
                double amp = plugin.getConfig().getDouble("origins.feline.creeper-damage-amplifier");
                double damage = e.getDamage();
                e.setDamage(damage * amp);
            }
        }
    }
}
