package org.mrmelon__.diverwars.game;

public class Team {

    private String name;
    private int players;
    private String color;

    public Team(String name, int players, String color) {
        this.name = name;
        this.players = players;
        this.color = color;
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
}
