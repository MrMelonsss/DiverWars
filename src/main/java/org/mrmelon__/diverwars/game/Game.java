package org.mrmelon__.diverwars.game;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.mrmelon__.diverwars.Main;

import java.io.File;
import java.util.*;

public class Game {
    public static int ds=0;

    private String name;
    private String mapName;
    private List<Team> teams;
    private int countOfPlayers;

    private String world;

    private int[] lobby;

    private int[] pos1ForBorderOfReplace;
    private int[] pos2ForBorderOfReplace;
    private HashMap<Location, Material> blocksForReplace;

    private File file;
    private FileConfiguration gameConfig;


    public boolean gameStatement;
    private List<Player> playersInSession;
    private int countOfPlayersInSession;




    public Game(String name) {
        this.name = name;
        this.countOfPlayers=0;
        gameStatement=false;

        file = new File(Main.getInstance().getDataFolder()+File.separator+"games", name+".yml");
        gameConfig = YamlConfiguration.loadConfiguration(file);

        teams = new ArrayList<>();
        blocksForReplace = new HashMap<>();
        playersInSession = new ArrayList<>();

        reloadValueInConfig();

        Main.getInstance().getGameManager().addGameInGameManagerList(this);
    }

    public Game(FileConfiguration gameConfig, File file) {
        this.gameConfig = gameConfig;
        this.file = file;
        gameStatement=false;

        teams = new ArrayList<>();
        blocksForReplace = new HashMap<>();
        playersInSession = new ArrayList<>();

        if (gameConfig.contains("world")) {
            Bukkit.createWorld(new WorldCreator(gameConfig.getString("world")));
        }

        getAllFromConfig();

    }

    public void saveConfig() {
        try {
            gameConfig.save(file);
            reloadConfig();
        } catch (Exception e) {
            System.out.println("Config of "+file.getName()+" game doesn't save. ERROR: "+e);
        }
    }

    public void reloadConfig() {
        gameConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void setAllInConfig() {
        for (String title : gameConfig.getKeys(false)) {
            gameConfig.set(title,null);
        }
        gameConfig.set("name",name);
        gameConfig.set("mapName",mapName);
        gameConfig.set("countOfPlayers",countOfPlayers);
        for (Team team : teams) {
            gameConfig.set("teams."+team.getName()+".playersCount",team.getPlayers());
            gameConfig.set("teams."+team.getName()+".color",team.getColor());
            gameConfig.set("teams."+team.getName()+".teamSpawn",team.getTeamSpawn());
            gameConfig.set("teams."+team.getName()+".teamGenerator",team.getTeamGenerator());
            gameConfig.set("teams."+team.getName()+".teamEngine",team.getTeamEngine());
            gameConfig.set("teams."+team.getName()+".pos1RangeEngine",team.getPos1RangeEngine());
            gameConfig.set("teams."+team.getName()+".pos2RangeEngine",team.getPos2RangeEngine());
        }
        gameConfig.set("world",world);
        gameConfig.set("pos1ForBorderOfReplace",pos1ForBorderOfReplace);
        gameConfig.set("pos2ForBorderOfReplace",pos2ForBorderOfReplace);
        gameConfig.set("lobby",lobby);
    }

    public void getAllFromConfig() { // надо реализовать проверку на существование приколов
        reloadConfig();
        name=gameConfig.getString("name");
        mapName=gameConfig.getString("mapName");
        countOfPlayers=gameConfig.getInt("countOfPlayers");
        if (gameConfig.contains("teams")) {
            if (!teams.isEmpty()) {
                teams.clear();
            }
            for (String teamName : gameConfig.getConfigurationSection("teams").getKeys(false)) {
                String[] teamsConfig = gameConfig.getConfigurationSection("teams." + teamName).getKeys(false).toArray(new String[0]);
                Team team = new Team(teamName, gameConfig.getInt("teams."+teamName+"."+teamsConfig[0]), gameConfig.getString("teams."+teamName+"."+teamsConfig[1]),this);
                List<Integer> posList = gameConfig.getIntegerList("teams."+teamName+".teamSpawn");
                if (!posList.isEmpty())
                    team.setTeamSpawn(posList.get(0),posList.get(1),posList.get(2));
                posList = gameConfig.getIntegerList("teams."+teamName+".teamGenerator");
                if (!posList.isEmpty())
                    team.setTeamGenerator(posList.get(0),posList.get(1),posList.get(2));
                posList = gameConfig.getIntegerList("teams."+teamName+".teamEngine");
                if (!posList.isEmpty())
                    team.setTeamEngine(posList.get(0),posList.get(1),posList.get(2));
                posList = gameConfig.getIntegerList("teams."+teamName+".pos1RangeEngine");
                if (!posList.isEmpty())
                    team.setPos1RangeEngine(posList.get(0),posList.get(1),posList.get(2));
                posList = gameConfig.getIntegerList("teams."+teamName+".pos2RangeEngine");
                if (!posList.isEmpty())
                    team.setPos2RangeEngine(posList.get(0),posList.get(1),posList.get(2));
                teams.add(team);
            }
        }
        world=gameConfig.getString("world");
        List<Integer> posList = gameConfig.getIntegerList("pos1ForBorderOfReplace");
        if (!posList.isEmpty())
        pos1ForBorderOfReplace= new int[]{posList.get(0),posList.get(1),posList.get(2)};
        posList = gameConfig.getIntegerList("pos2ForBorderOfReplace");
        if (!posList.isEmpty())
        pos2ForBorderOfReplace= new int[]{posList.get(0),posList.get(1),posList.get(2)};
        posList = gameConfig.getIntegerList("lobby");
        if (!posList.isEmpty())
        lobby= new int[]{posList.get(0),posList.get(1),posList.get(2)};
    }

    public void reloadValueInConfig() {
        setAllInConfig();
        saveConfig();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileConfiguration getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(FileConfiguration gameConfig) {
        this.gameConfig = gameConfig;
    }

    public TeamPlayer getTeamPlayerByPlayer(Player player) {
        for (Team team : teams) {
            for (TeamPlayer teamPlayer : team.getPlayersInTeam()) {
                if (teamPlayer.getPlayer().equals(player)) {
                    return teamPlayer;
                }
            }
        }
        return null;
    }

    public void startGame() {
        gameStatement=true;
        Bukkit.broadcastMessage("game was started");

        //join in team all peoples

        for (Player player : playersInSession) {
            if (!checkPlayerInAnyTeam(player)) {
                for (Team team : teams) {
                    if (team.getPlayers()!=team.getPlayersInTeam().size()) {
                        team.addPlayersInTeam(player);
                        player.sendMessage("U r was added in "+ChatColor.getByChar(team.getColor())+team.getName()+"'s team");
                        break;
                    } else {
                        player.sendMessage("Error with adding in team");
                    }
                }
            }
        }

        //tp teamSpawn
    }

    public boolean checkPlayerInAnyTeam(Player player) {
        for (Team team : teams) {
            for (TeamPlayer teamPlayer : team.getPlayersInTeam()) {
                if (teamPlayer.getPlayer().equals(player)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void endGame() {
        gameStatement=false;
    }

    public void joinGame(Player player) {
        if (gameStatement) return; // сделать сообщение об ошибке
        if (countOfPlayersInSession<countOfPlayers) {
            countOfPlayersInSession++;
            playersInSession.add(player);
            player.teleport(new Location(Bukkit.getWorld(world),lobby[0],lobby[1],lobby[2]));
            // сделать норм сообщение
            Bukkit.broadcastMessage(player.getName()+" join in the game ["+countOfPlayersInSession+"/"+countOfPlayers+"]");
            if (countOfPlayersInSession==countOfPlayers) {
                Bukkit.broadcastMessage("Game will be starting after 10 sec"); //ОТСЫЛАТЬ ВСЕМ В ИГРЕ
                // реализовать таймер и прочую ересь
                Bukkit.broadcastMessage("Game is starting...");
                startGame();
            }
        }
    }

    public void leaveGame(Player player) {
        if (gameStatement) return;
        countOfPlayersInSession--;
        playersInSession.remove(player);
        // сделать норм сообщение
        Bukkit.broadcastMessage(player.getName()+" leave from the game ["+countOfPlayersInSession+"/"+countOfPlayers+"]");
        if (countOfPlayersInSession-1==countOfPlayers) {
            Bukkit.broadcastMessage("Game will not be starting");
        }
    }

    public void addTeam(String nameOfTeam, int countOfPlayersInTeam, String colorOfTeam) {


        teams.add(new Team(nameOfTeam,countOfPlayersInTeam,colorOfTeam,this));
        countOfPlayers += countOfPlayersInTeam;

        reloadValueInConfig();

    }
    public void removeTeam(String nameOfTeam) {


        for (Team team : teams.toArray(new Team[0])) {
            if ((team.getName()).equals(nameOfTeam)) {
                countOfPlayers -= team.getPlayers();
                teams.remove(team);
                team = null;
            }
        }

        reloadValueInConfig();

    }

    public List<Team> getTeams() {
        return teams;
    }

    public Team getTeamByName(String name) {
        for (Team team : teams.toArray(new Team[0])) {
            if (team.getName().equals(name)) return team;
        }
        return null;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

        reloadValueInConfig();
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;

        reloadValueInConfig();
    }

    public int getCountOfPlayers() {
        return countOfPlayers;
    }

    public void setCountOfPlayers(int countOfPlayers) {
        this.countOfPlayers = countOfPlayers;

        reloadValueInConfig();
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;

        reloadValueInConfig();
    }

    public int[] getPos1ForBorderOfReplace() {
        return pos1ForBorderOfReplace;
    }

    public void setPos1ForBorderOfReplace(int x,int y,int z) {
        this.pos1ForBorderOfReplace = new int[]{x, y, z};

        reloadValueInConfig();
    }

    public int[] getPos2ForBorderOfReplace() {
        return pos2ForBorderOfReplace;
    }

    public void setPos2ForBorderOfReplace(int x,int y,int z) {
        this.pos2ForBorderOfReplace = new int[]{x, y, z};

        reloadValueInConfig();
    }

    public int[] getLobby() {
        return lobby;
    }

    public void setLobby(int x,int y,int z) {
        this.lobby = new int[]{x, y, z};

        reloadValueInConfig();
    }

    public HashMap<Location, Material> getBlocksForReplace() {
        return blocksForReplace;
    }

    public List<Player> getPlayersInSession() {
        return playersInSession;
    }

    public void setPlayersInSession(List<Player> playersInSession) {
        this.playersInSession = playersInSession;
    }

    public int getCountOfPlayersInSession() {
        return countOfPlayersInSession;
    }

    public void setCountOfPlayersInSession(int countOfPlayersInSession) {
        this.countOfPlayersInSession = countOfPlayersInSession;
    }

    //
}
