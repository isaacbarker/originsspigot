package me.isaacbarker.originsspigot.blazeorigin;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;

public class BlazeSpell {

    public static void blazeSpell(Player p, FileConfiguration config) {
        if (config.getBoolean("origins.blaze.spell.enabled")) {
            Location loc = p.getLocation();
            // Iterate over entites
            for (Entity e : loc.getWorld().getNearbyEntities(loc, 4, 4, 4)) {
                // Apply knockback to entites
                if (e.getUniqueId() != p.getUniqueId()) {
                    e.setFireTicks(100);
                    e.setVelocity(p.getLocation().getDirection().multiply(4));
                }
            }
        }
    }

}
