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

        // Check if a player has disabled the resourcepack
        if (plugin.getConfig().getBoolean("packconfig." + e.getPlayer().getDisplayName()) != true) {
            e.getPlayer().setResourcePack("https://www.dropbox.com/s/3271lvlb844lzgb/OriginsSpigotResourcePack.zip?dl=1");
        }
        // is the player already configured or not
        if (playerOrigin == null) {
            OriginsSwitchingSystem.originChange(e.getPlayer());
        }
    }

}
