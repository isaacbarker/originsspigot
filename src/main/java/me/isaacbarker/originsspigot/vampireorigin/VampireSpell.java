package me.isaacbarker.originsspigot.vampireorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class VampireSpell {

    public static void vampireSpell(Player p, FileConfiguration config, OriginsSpigot plugin) {
        if (config.getBoolean("origins.vampire.spell.enabled")) {
            p.setInvisible(true);
            p.setAllowFlight(true);
            p.setFlying(true);
            // Summon following bat
            Bat bat = (Bat) p.getWorld().spawnEntity(p.getLocation(), EntityType.BAT);
            bat.setInvulnerable(true);
            p.addPassenger(bat);

            for (Entity e : p.getLocation().getWorld().getNearbyEntities(p.getLocation(), 4, 4, 4)) {
                if (e.getUniqueId() != p.getUniqueId()) {
                    if (e instanceof Player target) {
                        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2 * 20, 0, false, false));
                    }
                }
            }

            long timeInTicks = 2L;

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!bat.isDead()) {
                        Location location = bat.getLocation();
                        location.setY(location.getY() + 1);
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 67, 54), 1.0F);
                        bat.getWorld().spawnParticle(Particle.REDSTONE, location, 2, dustOptions);
                    } else {
                        this.cancel();
                    }
                }

            }.runTaskTimer(plugin, timeInTicks, timeInTicks);

            // Stop after 5 seconds
            long timeInSeconds = config.getLong("origins.vampire.spell.length");
            long timeInTicksCountdown = 20 * timeInSeconds;
            new BukkitRunnable() {

                @Override
                public void run() {
                    // Only disallow flight if in survival or adventure
                    if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                        p.setAllowFlight(false);
                    }
                    p.setFlying(false);
                    p.setInvisible(false);
                    bat.remove();
                }
            }.runTaskLater(plugin, timeInTicksCountdown);

        }
    }

}
