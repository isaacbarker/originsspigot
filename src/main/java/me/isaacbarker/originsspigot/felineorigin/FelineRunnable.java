package me.isaacbarker.originsspigot.felineorigin;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class FelineRunnable {

    public static void felineRunnable(Player p) {
        // limited health
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(18.0);
    }

}
