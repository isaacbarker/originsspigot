package me.isaacbarker.originsspigot.blazeorigin;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlazeRunnable {

    public static void blazeRunnable(Player p) {
        // fire resistance
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 10*20, 0, false, false));
        // limit air
        p.setMaximumAir(0);
    }

}
