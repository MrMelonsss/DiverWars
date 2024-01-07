package org.mrmelon__.diverwars.game;

public class Team {

    private Game game;

    private String name;
    private int players;
    private String color;

    private int[] teamSpawn;
    private int[] teamGenerator;
    private int[] teamEngine;

    private int[] pos1RangeEngine;
    private int[] pos2RangeEngine;

    public Team(String name, int players, String color, Game game) {
        this.name = name;
        this.players = players;
        this.color = color;
        this.game = game;
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
}
