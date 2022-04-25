package me.isaacbarker.originsspigot;

import me.isaacbarker.originsspigot.creeperorigin.CreeperSpell;
import me.isaacbarker.originsspigot.felineorigin.FelineSpell;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashMap;
import java.util.UUID;

public class onAbilityListener implements Listener {

    private static HashMap<UUID, Long> cooldown = new HashMap<>();
    private final OriginsSpigot plugin;

    public onAbilityListener(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @EventHandler
    public void onDoubleSpace(PlayerToggleFlightEvent e) {
        // Ignore if player is in creative or spectator
        if (e.getPlayer().getGameMode() == GameMode.SPECTATOR ||
            e.getPlayer().getGameMode() == GameMode.CREATIVE) { return; }

        Player p = e.getPlayer();
        String pOrigin = plugin.getPlayerConfig(p.getUniqueId());
        e.setCancelled(true);

        // Ignore if player is a human origin
        if (pOrigin.equals("human")) { return; }

        // Check cooldown if valid
        if (cooldown.containsKey(p.getUniqueId()) && cooldown.get(p.getUniqueId()) >= (System.currentTimeMillis() / 1000)) {
            Long difference = cooldown.get(p.getUniqueId()) - (System.currentTimeMillis() / 1000);
            String message = ChatColor.RED + "You can't use your ability yet! Please wait " + difference +"s.";
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            return;
        }

        // Delete user cooldown
        cooldown.remove(p.getUniqueId());

        // Check for different origins
        if (pOrigin.equals("creeper")) {
            CreeperSpell.creeperSpell(p, plugin.getConfig());
        } else if (pOrigin.equals("feline")) {
            FelineSpell.felineSpell(p, plugin.getConfig());
        }

        cooldown.put(p.getUniqueId(), (System.currentTimeMillis() / 1000) + 10);

    }

}
