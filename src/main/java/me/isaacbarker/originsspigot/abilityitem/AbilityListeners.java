package me.isaacbarker.originsspigot.abilityitem;

import me.isaacbarker.originsspigot.OriginsSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class AbilityListeners implements Listener {


    private final OriginsSpigot plugin;

    public AbilityListeners(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    // Locks down spell item

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        ItemStack dropped = e.getItemDrop().getItemStack();

        if (AbilitySystem.isSpellItem(dropped)) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onPickUp(EntityPickupItemEvent e) {
        ItemStack picked = e.getItem().getItemStack();

        if (AbilitySystem.isSpellItem(picked)) {
            e.setCancelled(true);
            e.getItem().remove();
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.getDrops().remove(AbilitySystem.spellItem(plugin.getPlayerConfig(e.getEntity().getUniqueId())));
    }


    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        ItemStack item = AbilitySystem.spellItem(plugin.getPlayerConfig(e.getPlayer().getUniqueId()));

        if (!e.getPlayer().getInventory().contains(item)) {
            e.getPlayer().getInventory().setItem(4, item);
        }
    }

}
