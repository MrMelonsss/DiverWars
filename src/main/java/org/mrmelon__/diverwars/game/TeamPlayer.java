package org.mrmelon__.diverwars.game;

import org.bukkit.entity.Player;
import org.mrmelon__.diverwars.game.GameItems.Armor;

public class TeamPlayer {

    private Team team;

    public boolean isRegenerationAir;

    private Player player;

    private Armor armor;


    public TeamPlayer(Team team, Player player) {
        this.team = team;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
