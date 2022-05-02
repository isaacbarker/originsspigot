package me.isaacbarker.originsspigot.blazeorigin;

import org.bukkit.Material;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlazeRunnable {

    public static void blazeRunnable(Player p) {
        // fire resistance
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 10*20, 0, false, false));
        // limit air
        p.setMaximumAir(0);
        p.setRemainingAir(0);

        if (p.getLocation().getWorld().hasStorm()) {
            // Check if they are exposed to rain
            Block b = p.getLocation().getWorld().getHighestBlockAt(p.getLocation());

            if (b.getType() == Material.AIR || b.getY() < p.getLocation().getY()) {
                p.damage(2.0);
            }
        }

    }

}
