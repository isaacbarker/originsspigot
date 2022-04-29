package me.isaacbarker.originsspigot.vampireorigin;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VampireRunnable {

    public static void vampireRunnable(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*20, 0, false, false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10*20, 0, false, false));
    }

}
