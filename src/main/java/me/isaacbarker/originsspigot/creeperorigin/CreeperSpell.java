package me.isaacbarker.originsspigot.creeperorigin;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CreeperSpell {

    public static void creeperSpell(Player p, FileConfiguration config) {
        if (config.getBoolean("origins.creeper.spell.enabled")) {
            Location loc = p.getLocation();
            // get config
            float power = (float) config.getInt("origins.creeper.spell.power");
            boolean fire = config.getBoolean("origins.creeper.spell.fire-damage");
            boolean breakBlocks = config.getBoolean("origins.creeper.spell.destroy-blocks");
            // create explosion
            loc.getWorld().createExplosion(loc, power, fire, breakBlocks);
        }
    }

}
