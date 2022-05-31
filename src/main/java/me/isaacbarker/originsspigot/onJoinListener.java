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

        e.getPlayer().setResourcePack("https://www.dropbox.com/s/3271lvlb844lzgb/OriginsSpigotResourcePack.zip?dl=0");
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
