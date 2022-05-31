package me.isaacbarker.originsspigot.abilityitem;

import me.isaacbarker.originsspigot.OriginsSpigot;
import me.isaacbarker.originsspigot.blazeorigin.BlazeSpell;
import me.isaacbarker.originsspigot.creeperorigin.CreeperSpell;
import me.isaacbarker.originsspigot.endermanorigin.EndermanSpell;
import me.isaacbarker.originsspigot.felineorigin.FelineSpell;

import me.isaacbarker.originsspigot.fishorigin.FishSpell;
import me.isaacbarker.originsspigot.vampireorigin.VampireSpell;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
    private static HashMap<String, Integer> originToModelMap = new HashMap<>(){{
       put("creeper", 200);
       put("feline", 300);
       put("blaze", 400);
       put("vampire", 500);
       put("enderman", 600);
       put("fish", 700);
    }};

    private final OriginsSpigot plugin;

    public AbilitySystem(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    // Spell system
    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack spellItem = spellItem(plugin.getPlayerConfig(p.getUniqueId()));
        ItemStack clickedItem = e.getItem();

        if (clickedItem == null) { return; }

        if ((a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) && clickedItem.equals(spellItem)) {
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
        } else if (pOrigin.equals("blaze")) {
            BlazeSpell.blazeSpell(p, plugin.getConfig());
        } else if (pOrigin.equals("vampire")) {
            VampireSpell.vampireSpell(p, plugin.getConfig(), plugin);
        } else if (pOrigin.equals("enderman")) {
            EndermanSpell.endermanSpell(p, plugin.getConfig(), plugin);
        } else if (pOrigin.equals("fish")) {
            FishSpell.fishSpell(p, plugin.getConfig(), plugin);
        }

        int vampireLength = plugin.getConfig().getInt("origins.vampire.spell.length");
        int abilityCooldown = plugin.getConfig().getInt("origins.spell-cooldown");

        // Custom vampire cooldown
        if (pOrigin.equals("vampire")) {
            cooldown.put(p.getUniqueId(), (System.currentTimeMillis() / 1000) + abilityCooldown + vampireLength);
            return;
        }

        cooldown.put(p.getUniqueId(), (System.currentTimeMillis() / 1000) + abilityCooldown);
    }

    // Spell item
    public static ItemStack spellItem(String origin) {

        ItemStack customItem = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "☀ Origins Ring *");

        if (origin == null || origin.equals("human")) {
            return null;
        } else {
            // Convert player origin to custom model
            meta.setCustomModelData(originToModelMap.get(origin));
        }

        customItem.setItemMeta(meta);

        return customItem;
    }

    public static ItemStack genericRing() {
        ItemStack customItem = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        meta.setDisplayName("Origins Ring");
        meta.setCustomModelData(100);
        customItem.setItemMeta(meta);
        return customItem;
    }

    public static boolean isSpellItem(ItemStack item) {

        if (!item.hasItemMeta()) { return false; }

        int customModelData = item.getItemMeta().getCustomModelData();

        if (customModelData >= 100 || customModelData <= 800) {
            return true;
        }

        return false;
    }

    public static boolean isSpellItemHeld(Player p) {
        ItemStack offHand = p.getInventory().getItemInOffHand();
        ItemStack mainHand = p.getInventory().getItemInMainHand();

        if (offHand == null || mainHand == null) { return false; }

        if (isSpellItem(offHand) || isSpellItem(mainHand)) { return true; }

        return false;
    }


}
