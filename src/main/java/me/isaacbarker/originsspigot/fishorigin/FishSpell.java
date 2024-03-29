package me.isaacbarker.originsspigot.fishorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.scheduler.BukkitRunnable;

public class FishSpell {

    public static void fishSpell(Player p, FileConfiguration config, OriginsSpigot plugin) {
        if (config.getBoolean("origins.fish.spell.enabled")) {
            Location tridentLocation = p.getLocation();
            tridentLocation.setY(tridentLocation.getY() + 1);
            Trident trident = (Trident) p.getLocation().getWorld().spawnEntity(tridentLocation, EntityType.TRIDENT);
            trident.setShooter(p);
            trident.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            trident.addPassenger(p);
            trident.setVelocity(p.getLocation().getDirection().multiply(2).setY(1));


            long timeInTicks = 2L;

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!trident.isInBlock()) {
                        Location location = trident.getLocation();
                        location.setY(location.getY() + 1);
                        trident.getWorld().spawnParticle(Particle.WATER_BUBBLE, location, 2);
                    } else {
                        trident.remove();
                        this.cancel();
                    }
                }

            }.runTaskTimer(plugin, timeInTicks, timeInTicks);
        }
    }

}
