package me.isaacbarker.originsspigot.fishorigin;

import me.isaacbarker.originsspigot.abilityitem.AbilitySystem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class FishRunnable {

    public static void fishRunnable(Player p) {
            Block b = p.getLocation().getWorld().getHighestBlockAt(p.getLocation());

            if ((b.getType() != Material.WATER || b.getY() < p.getLocation().getY()) && !AbilitySystem.isSpellItemHeld(p)) {
                p.damage(2.0);
            }
    }


}
