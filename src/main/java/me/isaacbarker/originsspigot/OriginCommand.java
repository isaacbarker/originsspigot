package me.isaacbarker.originsspigot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OriginCommand implements CommandExecutor {

    private final OriginsSpigot plugin;

    public OriginCommand(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            OriginsSwitchingSystem.originChange(p);
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
        }

        return true;
    }
}
