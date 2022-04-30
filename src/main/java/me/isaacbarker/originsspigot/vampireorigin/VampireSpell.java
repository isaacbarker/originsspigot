package me.isaacbarker.originsspigot.vampireorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
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

            // Stop after 5 seconds
            long timeInSeconds = config.getLong("origins.vampire.spell.length");
            long timeInTicks = 20 * timeInSeconds;
            new BukkitRunnable() {

                @Override
                public void run() {
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.setInvisible(false);
                    bat.remove();
                }
            }.runTaskLater(plugin, timeInTicks);

        }
    }

}
