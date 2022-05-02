package me.isaacbarker.originsspigot;

import me.isaacbarker.originsspigot.abilityitem.AbilitySystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class originsSwitchingSystem implements Listener {

    private static HashMap<UUID, String> guiSessions = new HashMap<>();

    private static ItemStack creeper = createItem(AbilitySystem.spellItem("creeper"),
            "Creeper Origin",
            "§aAdvantages",
            "§a✓ You can summon explosions at will",
            "§a✓ You are immune to explosions",
            "§cWeaknesses",
            "§c✖ You do less damage the felines",
            "§c✖ You cannot sleep");

    private static ItemStack feline = createItem(AbilitySystem.spellItem("feline"),
            "Feline Origin",
            "§aAdvantages",
            "§a✓ You take no fall damage",
            "§a✓ You can double jump",
            "§a✓ You do more damage to creepers",
            "§cWeaknesses",
            "§c✖ You have 9 lives (9 hearts)");

    private static ItemStack blaze = createItem(AbilitySystem.spellItem("blaze"),
            "Blaze Origin",
            "§aAdvantages",
            "§a✓ Permanent fire resistance",
            "§a✓ Stronger when on fire",
            "§a✓ You can use your blaze knockback ability",
            "§cWeaknesses",
            "§c✖ You cannot breather underwater",
            "§c✖ You recieve damage in rain");

    private static ItemStack vampire = createItem(AbilitySystem.spellItem("vampire"),
            "Vampire Origin",
            "§aAdvantages",
            "§a✓ Can turn into a bat and fly",
            "§a✓ Increased strength and speed",
            "§cWeaknesses",
            "§c✖ You loose hunger more quickly",
            "§c✖ You catch fire in daylight in less you are holding",
            "§c   your origins ring");

    private static ItemStack enderman = createItem(AbilitySystem.spellItem("enderman"),
            "Enderman Origin",
            "§aAdvantages",
            "§a✓ You can use enderpearls without having them",
            "§a✓ You break blocks fast",
            "§cWeaknesses",
            "§c✖ You cannot breather underwater",
            "§c✖ You recieve damage in rain");

    private static ItemStack axolotl = createItem(AbilitySystem.spellItem("fish"),
            "Axolotl Origin",
            "§aAdvantages",
            "§a✓ You can swim faster underwater",
            "§a✓ You can see underwater",
            "§a✓ You can launch yourself with a trident",
            "§cWeaknesses",
            "§c✖ You cannot live above water",
            "§c   (somewhere where the highest block isn't water.)");



    private static ItemStack human = createItem(AbilitySystem.genericRing(),
            "§eJust a bog standard person.");


    private final OriginsSpigot plugin;

    public originsSwitchingSystem(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    public static void originChange(Player p) {
        Inventory inv = originGUI();

        p.openInventory(inv);
    }

    public static Inventory originGUI() {
        Inventory inv = Bukkit.createInventory(null, 9, "Origins Selection");


        inv.addItem(creeper, feline, blaze, vampire, enderman, axolotl, human);
        inv.setItem(8, selectItem());

        return inv;

    }

    private static ItemStack createItem(ItemStack itemStack, String name, String... lore) {
        ItemStack item = itemStack;
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack selectItem() {
        ItemStack select = new ItemStack(Material.GREEN_DYE, 1);
        ItemMeta selectMeta = select.getItemMeta();
        selectMeta.setDisplayName("§aConfirm");
        selectMeta.setLore(Arrays.asList("§7Click and origin and then click here to confirm!"));
        select.setItemMeta(selectMeta);

        return select;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws IOException {
        if (e.getView().getTitle().equals("Origins Selection")) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            UUID uuid = p.getUniqueId();
            ItemStack item = e.getCurrentItem();

            guiSessions.putIfAbsent(uuid, null);

            if (item == null) { return; }

            if (item.equals(creeper)) {
                guiSessions.replace(uuid, "creeper");
            } else if (item.equals(feline)) {
                guiSessions.replace(uuid, "feline");
            } else if (item.equals(blaze)) {
                guiSessions.replace(uuid, "blaze");
            } else if (item.equals(vampire)) {
                guiSessions.replace(uuid, "vampire");
            } else if (item.equals(enderman)) {
                guiSessions.replace(uuid, "enderman");
            } else if (item.equals(axolotl)) {
                guiSessions.replace(uuid, "fish");
            } else if (item.equals(human)) {
                guiSessions.replace(uuid, "human");
            } else if (item.equals(selectItem())) {
                if (guiSessions.containsKey(uuid) && guiSessions.get(uuid) != null) {
                    String origin = guiSessions.get(uuid);

                    // reset player
                    p.setMaximumAir(300);
                    p.setRemainingAir(300);
                    p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);

                    plugin.getOriginsConfig().set(uuid.toString(), origin);
                    plugin.getOriginsConfig().save(plugin.getOriginsFile());
                    p.closeInventory();


                    String playerOrigin = plugin.getPlayerConfig(uuid);

                    ItemStack spellItem = AbilitySystem.spellItem(playerOrigin);

                    for (int i = 0; i < p.getInventory().getContents().length; i++) {
                        ItemStack[] items = p.getInventory().getContents();

                        if (items[i] != null && AbilitySystem.isSpellItem(items[i])) {
                            p.getInventory().setItem(i, spellItem);
                            return;
                        }
                    }


                    p.getInventory().setItem(4, spellItem);
                    p.sendMessage(ChatColor.GREEN + "Welcome to Origins!", ChatColor.YELLOW + "Right click your origin ring to use your ability!", ChatColor.YELLOW + "You can change your origin if you have the permission with " + ChatColor.GRAY + "/origin");

                    return;
                } else {
                    return;
                }
            }

            e.getCurrentItem().addEnchantment(Enchantment.DAMAGE_ALL, 1);

            for (ItemStack i : e.getInventory().getContents()) {
                if (i != null && !i.equals(item)) {
                    i.removeEnchantment(Enchantment.DAMAGE_ALL);
                }
            }

        }
    }

}
