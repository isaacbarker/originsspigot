package me.isaacbarker.originsspigot.endermanorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import me.isaacbarker.originsspigot.blazeorigin.BlazeRunnable;
import me.isaacbarker.originsspigot.felineorigin.FelineRunnable;
import me.isaacbarker.originsspigot.fishorigin.FishRunnable;
import me.isaacbarker.originsspigot.vampireorigin.VampireRunnable;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EndermanSpell {

    public static void endermanSpell(Player p, FileConfiguration config, OriginsSpigot plugin) {
        if (config.getBoolean("origins.enderman.spell.enabled")) {
            EnderPearl enderPearl = (EnderPearl) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.ENDER_PEARL);
            enderPearl.setShooter(p);
            enderPearl.setVelocity(p.getLocation().getDirection().multiply(1).setY(1));
            enderPearl.addPassenger(p);
            // Runnable - registering origin's runnable
            Long timeInTicks = 2L;

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!enderPearl.isDead()) {
                        Location location = enderPearl.getLocation();
                        location.setY(location.getY() + 1);
                        enderPearl.getWorld().spawnParticle(Particle.DRAGON_BREATH, location, 2);
                    } else {
                        this.cancel();
                    }
                }

            }.runTaskTimer(plugin, timeInTicks, timeInTicks);
        }
    }

}
