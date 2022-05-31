package me.isaacbarker.originsspigot;

import me.isaacbarker.originsspigot.abilityitem.AbilityListeners;
import me.isaacbarker.originsspigot.abilityitem.AbilitySystem;
import me.isaacbarker.originsspigot.blazeorigin.BlazeAttackListener;
import me.isaacbarker.originsspigot.blazeorigin.BlazeRunnable;
import me.isaacbarker.originsspigot.creeperorigin.CreeperAttackListener;
import me.isaacbarker.originsspigot.creeperorigin.CreeperDamageListener;
import me.isaacbarker.originsspigot.creeperorigin.CreeperSleepListener;
import me.isaacbarker.originsspigot.endermanorigin.EndermanRunnable;
import me.isaacbarker.originsspigot.felineorigin.FelineAttackListener;
import me.isaacbarker.originsspigot.felineorigin.FelineDamageListener;
import me.isaacbarker.originsspigot.felineorigin.FelineRunnable;
import me.isaacbarker.originsspigot.fishorigin.FishAirListener;
import me.isaacbarker.originsspigot.fishorigin.FishRunnable;
import me.isaacbarker.originsspigot.vampireorigin.VampireHungerListener;
import me.isaacbarker.originsspigot.vampireorigin.VampireRunnable;
import org.bstats.bukkit.Metrics;
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
      String origin = getOriginsConfig().getString(stringUUID);

      if (origin != null) { return origin; }

      return "human";
    }

    @Override
    public void onEnable() {
        int pluginId = 15109;
        Metrics metrics = new Metrics(this, pluginId);
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
        getServer().getPluginManager().registerEvents(new AbilityListeners(this), this);
        // OriginsMc - Registering origins listeners.
        getServer().getPluginManager().registerEvents(new onJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new originsSwitchingSystem(this), this);

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

        // Fish
        if (getConfig().getBoolean("origins.fish.enabled")) {
            getServer().getPluginManager().registerEvents(new FishAirListener(this), this);
        }


        // Runnable - registering origin's runnable
        Long timeInSeconds  = 2L;
        Long timeInTicks = 20L * timeInSeconds;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    String playerOrigin = originsConfig.getString(p.getUniqueId().toString());

                    if (playerOrigin == null) {
                        return;
                    } else if (playerOrigin.equals("feline") && getConfig().getBoolean("origins.feline.enabled")) { // Feline
                        FelineRunnable.felineRunnable(p);
                    } else if (playerOrigin.equals("blaze") && getConfig().getBoolean("origins.blaze.enabled")) { // Blaze
                        BlazeRunnable.blazeRunnable(p);
                    } else if (playerOrigin.equals("vampire") && getConfig().getBoolean("origins.vampire.enabled")) { // Vampire
                        VampireRunnable.vampireRunnable(p);
                    } else if (playerOrigin.equals("enderman") && getConfig().getBoolean("origins.enderman.enabled")) { // Enderman
                        EndermanRunnable.endermanRunnable(p);
                    } else if (playerOrigin.equals("fish") && getConfig().getBoolean("origins.fish.enabled")) { // Fish
                        FishRunnable.fishRunnable(p);
                    }
                }
            }
        }.runTaskTimer(this, timeInTicks, timeInTicks);
    }
}
