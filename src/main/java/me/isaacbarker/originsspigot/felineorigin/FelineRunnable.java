package me.isaacbarker.originsspigot.felineorigin;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FelineRunnable {

    public static void felineRunnable(Player p, FileConfiguration config) {
        // jump boost
        if (config.getBoolean("origins.feline.jump-boost.enabled")) {
            int amp = config.getInt("origins.feline.jump-boost.level");
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10*20, amp, false, false));
        }
        // limited health
        if (config.getBoolean("origins.feline.limited-health")) {
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(18.0);
        }
    }

}
