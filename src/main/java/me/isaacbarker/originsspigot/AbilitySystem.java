package me.isaacbarker.originsspigot;

import me.isaacbarker.originsspigot.blazeorigin.BlazeSpell;
import me.isaacbarker.originsspigot.creeperorigin.CreeperSpell;
import me.isaacbarker.originsspigot.felineorigin.FelineSpell;

import me.isaacbarker.originsspigot.vampireorigin.VampireSpell;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public class AbilitySystem implements Listener {

    private static HashMap<UUID, Long> cooldown = new HashMap<>();
    private final OriginsSpigot plugin;

    public AbilitySystem(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    // Spell system
    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack spellItem = spellItem();

        if ((a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) && e.getItem().equals(spellItem)) {
            castSpell(p);
        }
    }

    // Spell Caster
    private void castSpell(Player p) {
        String pOrigin = plugin.getPlayerConfig(p.getUniqueId());

        // Ignore if player is a human origin
        if (pOrigin.equals("human")) { return; }

        // Check cooldown if valid
        if (cooldown.containsKey(p.getUniqueId()) && cooldown.get(p.getUniqueId()) >= (System.currentTimeMillis() / 1000)) {
            Long difference = cooldown.get(p.getUniqueId()) - (System.currentTimeMillis() / 1000);
            String message = ChatColor.RED + "You can't use your ability yet! Please wait " + difference +"s.";
            p.sendMessage(message);
            return;
        }

        // Delete user cooldown
        cooldown.remove(p.getUniqueId());

        // Check for different origins
        if (pOrigin.equals("creeper")) {
            CreeperSpell.creeperSpell(p, plugin.getConfig());
        } else if (pOrigin.equals("feline")) {
            FelineSpell.felineSpell(p, plugin.getConfig());
        } else if (pOrigin.equals("blaze")) {
            BlazeSpell.blazeSpell(p, plugin.getConfig());
        } else if (pOrigin.equals("vampire")) {
            VampireSpell.vampireSpell(p, plugin.getConfig(), plugin);
        }

        // Custom vampire cooldown
        if (pOrigin.equals("vampire")) {
            cooldown.put(p.getUniqueId(), (System.currentTimeMillis() / 1000) + 30);
            return;
        }

        cooldown.put(p.getUniqueId(), (System.currentTimeMillis() / 1000) + 10);
    }

    // Spell item
    public static ItemStack spellItem() {
        ItemStack customItem = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        meta.setCustomModelData(100);
        customItem.setItemMeta(meta);

        return customItem;
    }


}
