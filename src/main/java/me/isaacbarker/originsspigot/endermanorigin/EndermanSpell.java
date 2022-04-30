package me.isaacbarker.originsspigot.endermanorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EndermanSpell {

    public static void endermanSpell(Player p, FileConfiguration config, OriginsSpigot plugin) {
        if (config.getBoolean("origins.enderman.spell.enabled")) {
            EnderPearl enderPearl = (EnderPearl) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.ENDER_PEARL);
            enderPearl.setShooter(p);
            enderPearl.setVelocity(p.getLocation().getDirection().multiply(1).setY(1));
            enderPearl.addPassenger(p);
        }
    }

}
