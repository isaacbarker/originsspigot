package me.isaacbarker.originsspigot;

import me.isaacbarker.originsspigot.blazeorigin.BlazeAttackListener;
import me.isaacbarker.originsspigot.blazeorigin.BlazeRunnable;
import me.isaacbarker.originsspigot.creeperorigin.CreeperAttackListener;
import me.isaacbarker.originsspigot.creeperorigin.CreeperDamageListener;
import me.isaacbarker.originsspigot.creeperorigin.CreeperSleepListener;
import me.isaacbarker.originsspigot.felineorigin.FelineAttackListener;
import me.isaacbarker.originsspigot.felineorigin.FelineDamageListener;
import me.isaacbarker.originsspigot.felineorigin.FelineRunnable;
import me.isaacbarker.originsspigot.vampireorigin.VampireHungerListener;
import me.isaacbarker.originsspigot.vampireorigin.VampireRunnable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.UUID;

public final class OriginsSpigot extends JavaPlugin {

    // origins.yml loader
    private File originsFile = new File(getDataFolder(), "origins.yml");
    private FileConfiguration originsConfig = YamlConfiguration.loadConfiguration(originsFile);

    // allows origins classes to access a user's origin
    public FileConfiguration getOriginsConfig() { return originsConfig; }

    public File getOriginsFile() { return originsFile; }

    public String getPlayerConfig(UUID uuid) {
      String stringUUID = uuid.toString();
      return getOriginsConfig().getString(stringUUID);
    }

    @Override
    public void onEnable() {
        // Load in config.yml
        saveDefaultConfig();
        // Load origins.yml
        if (!originsFile.exists()) {
            saveResource("origins.yml", false);
        }
        // Switch origin command
        getCommand("origin").setExecutor(new originCommand(this));
        // Spell System
        getServer().getPluginManager().registerEvents(new AbilitySystem(this), this);
        // OriginsMc - Registering origins listeners.
        getServer().getPluginManager().registerEvents(new onJoinListener(this), this);
        // Creeper
        if (getConfig().getBoolean("origins.creeper.enabled")) {
            getServer().getPluginManager().registerEvents(new CreeperDamageListener(this), this);
            getServer().getPluginManager().registerEvents(new CreeperAttackListener(this), this);
            getServer().getPluginManager().registerEvents(new CreeperSleepListener(this), this);
        }

        // Feline
        if (getConfig().getBoolean("origins.feline.enabled")) {
            getServer().getPluginManager().registerEvents(new FelineDamageListener(this), this);
            getServer().getPluginManager().registerEvents(new FelineAttackListener(this), this);
        }

        // Blaze
        if (getConfig().getBoolean("origins.blaze.enabled")) {
            getServer().getPluginManager().registerEvents(new BlazeAttackListener(this), this);
        }

        // Vampire
        if (getConfig().getBoolean("origins.vampire.enabled")) {
            getServer().getPluginManager().registerEvents(new VampireHungerListener(this), this);
        }

        // Runnable - registering origin's runnable
        Long timeInSeconds  = 5L;
        Long timeInTicks = 20L * timeInSeconds;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    String playerOrigin = originsConfig.getString(p.getUniqueId().toString());
                    p.setAllowFlight(true);

                    if (playerOrigin == null) {
                        return;
                    } else if (playerOrigin.equals("feline")) { // Feline
                        FelineRunnable.felineRunnable(p);
                    } else if (playerOrigin.equals("blaze")) { // Blaze
                        BlazeRunnable.blazeRunnable(p);
                    } else if (playerOrigin.equals("vampire")) { // Vampire
                        VampireRunnable.vampireRunnable(p);
                    }
                }
            }
        }.runTaskTimer(this, timeInTicks, timeInTicks);
    }
}
