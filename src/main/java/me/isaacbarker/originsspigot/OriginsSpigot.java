package me.isaacbarker.originsspigot;

import me.isaacbarker.originsspigot.blazeorigin.BlazeRunnable;
import me.isaacbarker.originsspigot.creeperorigin.CreeperAttackDamager;
import me.isaacbarker.originsspigot.creeperorigin.CreeperDamageListener;
import me.isaacbarker.originsspigot.creeperorigin.CreeperDeathListener;
import me.isaacbarker.originsspigot.creeperorigin.CreeperRunnable;
import me.isaacbarker.originsspigot.felineorigin.FelineAttackDamage;
import me.isaacbarker.originsspigot.felineorigin.FelineDamageListener;
import me.isaacbarker.originsspigot.felineorigin.FelineRunnable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Blaze;
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
        // OriginsMc - Registering origins listeners.
        getServer().getPluginManager().registerEvents(new onJoinListener(this), this);
        // Creeper
        if (getConfig().getBoolean("origins.creeper.enabled")) {
            getServer().getPluginManager().registerEvents(new CreeperDeathListener(this), this);
            getServer().getPluginManager().registerEvents(new CreeperDamageListener(this), this);
            getServer().getPluginManager().registerEvents(new CreeperAttackDamager(this), this);
        }

        // Feline
        if (getConfig().getBoolean("origins.feline.enabled")) {
            getServer().getPluginManager().registerEvents(new FelineDamageListener(this), this);
            getServer().getPluginManager().registerEvents(new FelineAttackDamage(this), this);
        }

        // Runnable - registering origin's runnable
        Long timeInSeconds  = 5L;
        Long timeInTicks = 20L * timeInSeconds;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    String playerOrigin = originsConfig.getString(p.getUniqueId().toString());

                    if (playerOrigin == null) {
                        return;
                    } else if (playerOrigin.equals("creeper")) { // Creeper
                        CreeperRunnable.creeperRunnable(p);
                    } else if (playerOrigin.equals("feline")) { // Feline
                        FelineRunnable.felineRunnable(p, getConfig());
                    } else if (playerOrigin.equals("blaze")) { // Blaze
                        BlazeRunnable.blazeRunnable(p, getConfig());
                    }
                }
            }
        }.runTaskTimer(this, timeInTicks, timeInTicks);
    }
}
