package me.isaacbarker.originsspigot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class originCommand implements CommandExecutor {

    private final OriginsSpigot plugin;

    public originCommand(OriginsSpigot originsSpigot) {
        plugin = originsSpigot;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            originsSwitchingSystem.originChange(p);
        }

        return true;
    }
}
