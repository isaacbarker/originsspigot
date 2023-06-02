package me.isaacbarker.originsspigot.blazeorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BlazeAttackListener implements Listener {

    private final OriginsSpigot plugin;

    public BlazeAttackListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player p) {
            String playerOrigin = plugin.getPlayerConfig(p.getUniqueId());

            if (playerOrigin == null) { return; }

            if (playerOrigin.equals("blaze")) {
                if (p.isVisualFire() || p.getFireTicks() > 0) {
                    double damage = e.getDamage();
                    damage = damage * 1.25;
                    e.setDamage(damage);
                }
            }
        }
    }

}
