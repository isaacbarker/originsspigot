package me.isaacbarker.originsspigot.vampireorigin;

import me.isaacbarker.originsspigot.abilityitem.AbilitySystem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VampireRunnable {

    public static void vampireRunnable(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*20, 0, false, false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10*20, 0, false, false));

        if ((p.getPlayerTime() >= 1000 && p.getPlayerTime() <= 12000) && !AbilitySystem.isSpellItemHeld(p) && !p.getLocation().getWorld().hasStorm()) {
            // Check if they are exposed to rain
            Block b = p.getLocation().getWorld().getHighestBlockAt(p.getLocation());

            if (b.getType() == Material.AIR || b.getY() < p.getLocation().getY()) {
                p.setFireTicks(2*20);
            }
        }

    }

}
