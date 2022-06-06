package me.isaacbarker.originsspigot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleResourcePack implements CommandExecutor {

    private final OriginsSpigot plugin;

    public ToggleResourcePack(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            Boolean isToggled = plugin.getConfig().getBoolean("packconfig." + ((Player) sender).getDisplayName());

            if (isToggled == false || isToggled == null) {
                plugin.getConfig().set("packconfig." + ((Player) sender).getDisplayName(), true);
                sender.sendMessage(ChatColor.GREEN + "Disabled default resource loading.");
            } else {
                plugin.getConfig().set("packconfig." + ((Player) sender).getDisplayName(), false);
                sender.sendMessage(ChatColor.GREEN + "Enabled default resource loading.");
            }

            plugin.saveConfig();

        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
        }

        return true;
    }
}
