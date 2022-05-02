package me.isaacbarker.originsspigot.fishorigin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;

public class FishSpell {

    public static void fishSpell(Player p, FileConfiguration config) {
        if (config.getBoolean("origins.fish.spell.enabled")) {
            Trident trident = (Trident) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.TRIDENT);
            trident.setShooter(p);
            trident.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            trident.addPassenger(p);
            trident.setVelocity(p.getLocation().getDirection().multiply(5).setY(1));
        }
    }

}
