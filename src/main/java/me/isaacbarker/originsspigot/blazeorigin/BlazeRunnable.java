package me.isaacbarker.originsspigot.blazeorigin;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlazeRunnable {

    public static void blazeRunnable(Player p, FileConfiguration config) {
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
        // strength
        if (config.getBoolean("origins.blaze.strength")) {
            int amp = config.getInt("origins.blaze.strength-level");
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10*20, amp, false, false));
        }
        // resistance
        if (config.getBoolean("origins.blaze.resistance")) {
            int amp = config.getInt("origins.blaze.resistance-level");
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10*20, amp, false, false));
        }
        // fire resistance
        if (config.getBoolean("origins.blaze.fire-resistance")) {
            int amp = config.getInt("origins.blaze.fire-resistance-level");
            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 10*20, amp, false, false));
        }
    }

}
