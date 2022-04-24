package me.isaacbarker.originsspigot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.imageio.IIOException;
import java.io.IOException;

public class onJoinListener implements Listener {

    private final OriginsSpigot plugin;

    public onJoinListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException {
        String playerOrigin = plugin.getPlayerConfig(e.getPlayer().getUniqueId());
        String uuid = e.getPlayer().getUniqueId().toString();
        // is the player already configured or not
        if (playerOrigin == null) {
            plugin.getOriginsConfig().set(uuid, "human");
            plugin.getOriginsConfig().save(plugin.getOriginsFile());
        }
    }

}
