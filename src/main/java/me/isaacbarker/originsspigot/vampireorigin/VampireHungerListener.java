package me.isaacbarker.originsspigot.vampireorigin;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class VampireHungerListener implements Listener {

    private final OriginsSpigot plugin;

    public VampireHungerListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player p) {
            if (plugin.getPlayerConfig(p.getUniqueId()).equals("vampire")) {
                // See if the food level is a decrease
                int suggestedFood = e.getFoodLevel();
                int currFood = p.getFoodLevel();

                if (suggestedFood < currFood) { // Food is a decrease
                    int decrease = currFood - suggestedFood;
                    decrease = decrease * 2;
                    e.setFoodLevel(currFood - decrease);
                }

            }
        }
    }

}
