package me.isaacbarker.originsspigot;

import me.isaacbarker.originsspigot.abilityitem.AbilitySystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class onJoinListener implements Listener {

    private final OriginsSpigot plugin;

    public onJoinListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException {
        String playerOrigin = plugin.getPlayerConfig(e.getPlayer().getUniqueId());

        e.getPlayer().setResourcePack("https://firebasestorage.googleapis.com/v0/b/isaac-barker.appspot.com/o/OriginsSpigotResourcePack.zip?alt=media&token=f8eeb2fe-129f-4289-b104-1f6b6573a556");
        // is the player already configured or not
        if (playerOrigin == null || !e.getPlayer().hasPlayedBefore()) {
            originsSwitchingSystem.originChange(e.getPlayer());
        } else {

            ItemStack item = AbilitySystem.spellItem(playerOrigin);

            if (!e.getPlayer().getInventory().contains(item)) {
                e.getPlayer().getInventory().setItem(4, item);
            }

        }
    }

}
