package me.isaacbarker.originsspigot.creeperorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CreeperDeathListener implements Listener {

    private final OriginsSpigot plugin;

    public CreeperDeathListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (plugin.getPlayerConfig(e.getEntity().getUniqueId()).equals("creeper")) {
            // keep inventory
            if (plugin.getConfig().getBoolean("origins.creeper.keep-inventory")) {
                e.setKeepInventory(true);
            }
            // explode on death
            if (plugin.getConfig().getBoolean("origins.creeper.death-explosion.enabled")) {
                Player p = e.getEntity();
                Location loc = p.getLocation();
                // get config
                float power = (float) plugin.getConfig().getInt("origins.creeper.death-explosion.power");
                boolean breakBlocks = plugin.getConfig().getBoolean("origins.creeper.death-explosion.destroy-blocks");
                // create explosion
                loc.getWorld().createExplosion(loc, power, false, breakBlocks);
            }
        }
    }
}
