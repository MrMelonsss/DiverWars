package org.mrmelon__.diverwars;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mrmelon__.diverwars.commands.GameSettingsCMD;
import org.mrmelon__.diverwars.game.GameManager;
import org.mrmelon__.diverwars.game.Team;
import org.mrmelon__.diverwars.game.events.BlockActionEvent;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

public final class Main extends JavaPlugin {

    private static Main instance;
    private GameManager gameManager;
    public static Main getInstance() {
        return instance;
    }
    public GameManager getGameManager() {
        return gameManager;
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

        getCommand("diverwars").setExecutor(new GameSettingsCMD());
        Bukkit.getPluginManager().registerEvents(new BlockActionEvent(),this);

        gameManager = new GameManager();

        for (Team team : (gameManager.getGameByName("guga").getTeams())) {
            System.out.println(team.getName());
        }

        //

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("DiverWars is disable");

        //

    }
}
