package me.isaacbarker.originsspigot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class OnJoinListener implements Listener {

    private final OriginsSpigot plugin;

    public OnJoinListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException {
        String playerOrigin = plugin.getPlayerConfig(e.getPlayer().getUniqueId());
        System.out.println(playerOrigin);

        // Check if a player has disabled the resourcepack
        if (plugin.getConfig().getBoolean("packconfig." + e.getPlayer().getDisplayName()) != true) {
            e.getPlayer().setResourcePack("https://isaacbarker.me/resourcepack");
        }
        // is the player already configured or not
        if (playerOrigin == null) {
            OriginsSwitchingSystem.originChange(e.getPlayer());
        }
    }

}
