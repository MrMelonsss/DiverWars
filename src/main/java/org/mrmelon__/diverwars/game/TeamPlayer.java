package org.mrmelon__.diverwars.game;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mrmelon__.diverwars.game.GameItems.Armor;

import java.util.UUID;

public class TeamPlayer {

    private Team team;

    private Player player;
    private UUID uuid;

    private Armor armor;

    private int tempForMultiply;
    private int currentAirCount;


    public TeamPlayer(Team team, Player player) {
        this.team = team;
        this.player = player;
        this.uuid = player.getUniqueId();

        currentAirCount=300;
        tempForMultiply=0;
        armor = Armor.LEATHER_DIVING_SUIT;
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

    public int getTempForMultiply() {
        return tempForMultiply;
    }

    public void setTempForMultiply(int tempForMultiply) {
        this.tempForMultiply = tempForMultiply;
    }

    public int getCurrentAirCount() {
        return currentAirCount;
    }

    public void setCurrentAirCount(int currentAirCount) {
        this.currentAirCount = currentAirCount;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
