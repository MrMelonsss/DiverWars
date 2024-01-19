package org.mrmelon__.diverwars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrmelon__.diverwars.commands.GameSettingsCMD;
import org.mrmelon__.diverwars.game.GameManager;
import org.mrmelon__.diverwars.game.Team;
import org.mrmelon__.diverwars.game.events.BlockActionEvent;
import org.mrmelon__.diverwars.game.events.EngineEvent;
import org.mrmelon__.diverwars.game.events.JoinEvent;
import org.mrmelon__.diverwars.game.events.LifeEvent;

import java.io.File;

public final class Main extends JavaPlugin {

    private static Main instance;
    private GameManager gameManager;

    private FileConfiguration configMain;
    public static Main getInstance() {
        return instance;
    }
    public GameManager getGameManager() {
        return gameManager;
    }

    public FileConfiguration getConfigMain() {
        return configMain;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("DiverWars is enable");

        //

        instance = this;

        File configFile = new File(getDataFolder(),"plugin.yml");
        if (!configFile.exists()) {
            saveResource("plugin.yml",false);
        }
        configMain = YamlConfiguration.loadConfiguration(configFile);


        getCommand("diverwars").setExecutor(new GameSettingsCMD());
        Bukkit.getPluginManager().registerEvents(new BlockActionEvent(),this);
        Bukkit.getPluginManager().registerEvents(new EngineEvent(),this);
        Bukkit.getPluginManager().registerEvents(new JoinEvent(),this);
        Bukkit.getPluginManager().registerEvents(new LifeEvent(),this);

        gameManager = new GameManager();

        //

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("DiverWars is disable");

        //

    }

// ебучие баги майна
    public void tpPlayer(Location location, Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(location);
            }
        }.runTaskLater(Main.getInstance(),1L);
    }

}
