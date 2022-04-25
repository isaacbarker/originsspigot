package me.isaacbarker.originsspigot.felineorigin;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class FelineSpell {

    public static void felineSpell(Player p, FileConfiguration config) {
        if (config.getBoolean("origins.feline.spell.enabled")) {
            p.setVelocity(p.getLocation().getDirection().multiply(1.5).setY(1));
        }
    }

}
