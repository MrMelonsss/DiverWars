package org.mrmelon__.diverwars.game;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.mrmelon__.diverwars.Main;

import java.io.File;
import java.util.ArrayList;

public class GameManager {

    private ArrayList<Game> games;

    public GameManager() {
        // load from yml if dont exist
        games=new ArrayList<>();

        File gamesDirectory = new File(Main.getInstance().getDataFolder(), "games");
        if (gamesDirectory.exists()) {
            if (gamesDirectory.isDirectory()) {
                String[] gamesConfigs = gamesDirectory.list();
                for (String gameConfigFile : gamesConfigs) {
                    FileConfiguration gameConfig = YamlConfiguration.loadConfiguration(new File(gamesDirectory, gameConfigFile));
                    Game game = new Game(gameConfig,new File(gamesDirectory, gameConfigFile));
                    addGameInGameManagerList(game);
                }
            } else {
                gamesDirectory.delete();
                gamesDirectory.mkdir();
            }
        } else {
            gamesDirectory.mkdir();

        }

    }

    public void addGameInGameManagerList(Game game) {
        games.add(game);
    }

    public void deleteGameObject(Game game) {
        games.remove(game);
        game.getFile().delete();
        game=null;
    }

    public boolean nameExist(String name) {
        for (Game game : games) {
            String iterateName = game.getName();
            if (iterateName.equals(name)){
                return true;
            }
        }
        return false;
    }


    public Game getGameByName(String name) {
        for (Game game : games) {
            if (game.getName().equals(name)) {
                return game;
            }
        }
        return null;
    }

    public Game getGameByWorld(String world) {
        for (Game game : games) {
            if (game.getWorld().equals(world)) {
                return game;
            }
        }
        return null;
    }

}
