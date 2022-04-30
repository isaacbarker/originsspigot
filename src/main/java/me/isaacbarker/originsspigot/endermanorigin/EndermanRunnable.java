package me.isaacbarker.originsspigot.endermanorigin;

import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EndermanRunnable {

    public static void endermanRunnable(Player p) {
        p.setMaximumAir(0);
        p.setRemainingAir(0);

        if (p.getLocation().getWorld().hasStorm()) {
           // Check if they are exposed to rain
           Block b = p.getLocation().getWorld().getHighestBlockAt(p.getLocation());

           if (b.getType() == Material.AIR || b.getY() < p.getLocation().getY()) {
               p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2*20, 0, false, false));
           }
        }

    }

}
