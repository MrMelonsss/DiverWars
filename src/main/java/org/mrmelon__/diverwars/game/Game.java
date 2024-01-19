package org.mrmelon__.diverwars.game;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrmelon__.diverwars.Main;

import java.io.File;
import java.util.*;

public class Game {

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

    public void activateGenerators(Team team) {
        World world = Bukkit.getWorld(this.world);
        int[] locXYZ = team.getTeamGenerator();
        Location location = new Location(world, locXYZ[0], locXYZ[1], locXYZ[2]);
        ItemStack itemStack = new ItemStack(Material.PRISMARINE_SHARD);

        new BukkitRunnable() {

            @Override
            public void run() {

                if (gameStatement) {
                    world.dropItem(location, itemStack); //ПОКА ЧТО ТАК РЕАЛИЗУЕМ
                }

            }
        }.runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    public void startGame() {
        gameStatement=true;

        sendMessageSessionPlayers(ChatColor.GOLD + "Game is starting...");

        //join in team all peoples

        for (Player player : playersInSession) {
            if (!checkPlayerInAnyTeam(player)) {
                for (Team team : teams) {
                    if (team.getPlayers()!=team.getPlayersInTeam().size()) {
                        team.addPlayersInTeam(player);
                        player.sendMessage(ChatColor.AQUA + "U r was added in "+ChatColor.getByChar(team.getColor())+team.getName()+ ChatColor.AQUA +" team");
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,132142,0,true));   //временно!!!!!!!!!!!!!!!!!!
                        Main.getInstance().tpPlayer(new Location(Bukkit.getWorld(world), team.getTeamSpawn()[0],team.getTeamSpawn()[1],team.getTeamSpawn()[2]),player);
                        regenPlayer(player);
                        player.setInvulnerable(false);
                        break;
                    }
                }
            }
        }

        for (Team team : teams) {
            activateGenerators(team);
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

        sendMessageSessionPlayers(ChatColor.GOLD + "Game is ending...");

        playersInSession.clear();
        countOfPlayersInSession=0;
    }

    public void joinGame(Player player) {
        if (gameStatement) return; // сделать сообщение об ошибке
        if (countOfPlayersInSession<countOfPlayers) {
            if (playersInSession.contains(player)) {
                player.sendMessage("U already in session");
                return;
            }
            countOfPlayersInSession++;
            playersInSession.add(player);
            player.setInvulnerable(true);
            Main.getInstance().tpPlayer(new Location(Bukkit.getWorld(world),lobby[0],lobby[1],lobby[2]),player);
            regenPlayer(player);
            // сделать норм сообщение
            sendMessageSessionPlayers(player.getName()+" join in the game ["+countOfPlayersInSession+"/"+countOfPlayers+"]");
            if (countOfPlayersInSession==countOfPlayers) {

                final int[] timer = {10}; // потом связать с конфигом
                sendMessageSessionPlayers(ChatColor.YELLOW + "Game will be starting after " + ChatColor.RED + timer[0] + ChatColor.YELLOW + " sec"); //ОТСЫЛАТЬ ВСЕМ В ИГРЕ
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (countOfPlayersInSession==countOfPlayers) {
                            timer[0]--;
                            if (timer[0] == 0) {
                                cancel();
                                startGame();
                            } else {
                                sendMessageSessionPlayers(ChatColor.YELLOW + "Game will be starting after " + ChatColor.RED + timer[0] + ChatColor.YELLOW + " sec");
                            }
                        } else {
                            cancel();
                        }
                    }
                }.runTaskTimer(Main.getInstance(),20L,20L);
                // реализовать таймер и прочую ересь


            }
        }
    }

    public void leaveGame(Player player) {
        if (gameStatement) return;
        countOfPlayersInSession--;
        playersInSession.remove(player);
        player.setInvulnerable(true);
        List<Integer> spawnLocation = Main.getInstance().getConfigMain().getIntegerList("spawnLocation");
        Main.getInstance().tpPlayer(new Location(Bukkit.getWorld(Main.getInstance().getConfigMain().getString("spawnWorld")),spawnLocation.get(0),spawnLocation.get(1),spawnLocation.get(2)),player); // сделать локацию на спавн
        regenPlayer(player);
        // сделать норм сообщение
        sendMessageSessionPlayers(player.getName()+" leave from the game ["+countOfPlayersInSession+"/"+countOfPlayers+"]");
        if (countOfPlayersInSession+1==countOfPlayers) {
            sendMessageSessionPlayers(ChatColor.YELLOW + "Game will not be starting");
        }
    }

    public void regenPlayer(Player player) {
        PlayerInventory inventory = player.getInventory();
        //надо очистку инвентаря реализовать
        player.setHealth(player.getMaxHealth());
        player.setLevel(0);
        player.setExp(0); // dobavit партиклы
        player.setFoodLevel(20);
        player.setRemainingAir(295);
        for (PotionEffectType potionEffectType : PotionEffectType.values()) {
            player.removePotionEffect(potionEffectType);
        }
    }

    public void sendMessageSessionPlayers(String string) {
        for (Player player : playersInSession) {
            player.sendMessage(string);
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

    public Team getTeamByEngineLocation(Location location) {
        int[] loc = {location.getBlockX(), location.getBlockY(), location.getBlockZ()};
        for (Team team : teams) {
            if (Arrays.equals(team.getTeamEngine(), loc)) {
                return team;
            }
        }
        return null;
    }

    //
}
