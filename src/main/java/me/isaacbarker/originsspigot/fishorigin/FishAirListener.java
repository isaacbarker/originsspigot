package me.isaacbarker.originsspigot.fishorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FishAirListener implements Listener {

    private final OriginsSpigot plugin;

    public FishAirListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onAirChange(EntityAirChangeEvent e) {
        if (e.getEntity() instanceof Player p) {
            String playerOrigin = plugin.getPlayerConfig(p.getUniqueId());

            if (playerOrigin == null) { return; }

            if (playerOrigin.equals("fish")) {
                e.setCancelled(true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10 * 20, 0, false, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 10 * 20, 0, false, false));
            }
        }
    }

}
