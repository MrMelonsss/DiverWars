package org.mrmelon__.diverwars.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private Game game;

    private String name;
    private int players;
    private String color;

    private List<TeamPlayer> playersInTeam;

    private int[] teamSpawn;
    private int[] teamGenerator;
    private int[] teamEngine;


    public boolean isRegenerationAir;
    private int airPercent; // 100% = 2000 1% = 20

    private int[] pos1RangeEngine;
    private int[] pos2RangeEngine;

    public Team(String name, int players, String color, Game game) {
        this.name = name;
        this.players = players;
        this.color = color;
        this.game = game;

        isRegenerationAir=true;
        airPercent = 2000;

        playersInTeam = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int[] getTeamSpawn() {
        return teamSpawn;
    }

    public void setTeamSpawn(int x,int y,int z) {
        this.teamSpawn = new int[]{x, y, z};
    }

    public int[] getTeamGenerator() {
        return teamGenerator;
    }

    public void setTeamGenerator(int x,int y,int z) {
        this.teamGenerator = new int[]{x, y, z};
    }

    public int[] getTeamEngine() {
        return teamEngine;
    }

    public void setTeamEngine(int x,int y,int z) {
        this.teamEngine = new int[]{x, y, z};
    }

    public List<TeamPlayer> getPlayersInTeam() {
        return playersInTeam;
    }

    public void setPlayersInTeam(List<TeamPlayer> playersInTeam) {
        this.playersInTeam = playersInTeam;
    }

    public void addPlayersInTeam(Player player) {
        playersInTeam.add(new TeamPlayer(this,player));
    }

    public int[] getPos1RangeEngine() {
        return pos1RangeEngine;
    }

    public void setPos1RangeEngine(int x,int y,int z) {
        this.pos1RangeEngine = new int[]{x, y, z};
    }

    public int[] getPos2RangeEngine() {
        return pos2RangeEngine;
    }

    public void setPos2RangeEngine(int x,int y,int z) {
        this.pos2RangeEngine = new int[]{x, y, z};
    }

    public int getAirPercent() {
        return airPercent;
    }

    public void setAirPercent(int airPercent) {
        this.airPercent = airPercent;
    }
}
