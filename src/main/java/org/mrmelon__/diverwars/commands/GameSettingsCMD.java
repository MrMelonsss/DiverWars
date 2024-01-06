package org.mrmelon__.diverwars.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mrmelon__.diverwars.Main;
import org.mrmelon__.diverwars.game.Game;

import java.util.Arrays;

public class GameSettingsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player;
        try {
            player = (Player) commandSender;
        } catch (Exception e) {
            System.out.println("sender is not player");
            return false;
        }

        if (strings.length==0) {
            commandSender.sendMessage("Please, use /game [arg]");
        } else {
            //
            if (strings[0].equals("tp")) {
                player.teleport(new Location(Bukkit.getWorld(strings[1]),0,100,0));
            }
            if (strings[0].equals("check")) {
                System.out.println(Main.getInstance().getGameManager().getGameByName(strings[1]).getBlocksForReplace());
            }
            //
            switch (strings[0]){
                case "create":
                    if (strings.length==2) {
                        if (!Main.getInstance().getGameManager().nameExist(strings[1])) {
                            new Game(strings[1]);
                            commandSender.sendMessage("Game "+strings[1]+" was created");
                            return true;
                        }
                        commandSender.sendMessage("This game is already exist");
                    } else commandSender.sendMessage("Please, use /game create [game]");
                    break;
                case "reload":
                    if (strings.length==2) {
                        if (Main.getInstance().getGameManager().nameExist(strings[1])) {
                            Main.getInstance().getGameManager().getGameByName(strings[1]).reloadValueInConfig();
                            commandSender.sendMessage("Game "+strings[1]+" was reloaded");
                            return true;
                        }
                        commandSender.sendMessage("This game is not defined");
                    } else commandSender.sendMessage("Please, use /game reload [game]");
                    break;
                case "delete":
                    if (strings.length==2) {
                        if (Main.getInstance().getGameManager().nameExist(strings[1])) {
                            Main.getInstance().getGameManager().deleteGameObject(Main.getInstance().getGameManager().getGameByName(strings[1]));
                            commandSender.sendMessage("Game "+strings[1]+" was deleted");
                            return true;
                        }
                        commandSender.sendMessage("This game is not defined");
                    } else commandSender.sendMessage("Please, use /game delete [game]");
                    break;
                case "team":// make lists of objects {game,team}
                    if (strings.length==1) {
                        commandSender.sendMessage("Please, use /game team [arg] [game] [values]");
                    } else {
                        switch (strings[1]) {
                            case "add":
                                if (strings.length >= 3) {
                                    if (strings.length >= 4) {
                                        if (Main.getInstance().getGameManager().nameExist(strings[2])) {
                                            Main.getInstance().getGameManager().getGameByName(strings[2]).addTeam(strings[3], Integer.parseInt(strings[4]), strings[5]);
                                            commandSender.sendMessage("Team of " + strings[2] + " game was added: " + strings[3]);
                                            return true;
                                        }
                                        commandSender.sendMessage("This game is not defined");
                                    } else
                                        commandSender.sendMessage("Please, use /game team add " + strings[2] + " [name] [playersCount] [color]");

                                } else
                                    commandSender.sendMessage("Please, use /game team add [game] [name] [playersCount] [color]");
                                break;
                            case "remove":
                                if (strings.length >= 3) {
                                    if (strings.length >= 4) {
                                        if (Main.getInstance().getGameManager().nameExist(strings[2])) {
                                            Main.getInstance().getGameManager().getGameByName(strings[2]).removeTeam(strings[3]);
                                            commandSender.sendMessage("Team of " + strings[2] + " game was removed: " + strings[3]);
                                            return true;
                                        }
                                        commandSender.sendMessage("This game is not defined");
                                    } else
                                        commandSender.sendMessage("Please, use /game team add " + strings[2] + " [name]");

                                } else
                                    commandSender.sendMessage("Please, use /game team add [game] [name]");
                                break;
                        }
                    }
                    break;
                case "set":
                    if (strings.length==1) {
                        commandSender.sendMessage("Please, use /game set [arg] [game] [value]");
                    } else {
                        switch (strings[1]) {
                            case "name":
                                if (strings.length>=3) {
                                    if (strings.length==4) {
                                        if (Main.getInstance().getGameManager().nameExist(strings[2])) {
                                            Main.getInstance().getGameManager().getGameByName(strings[2]).setName(strings[3]);
                                            commandSender.sendMessage("Name value of " + strings[2] + " game was changed on "+strings[3]);
                                            return true;
                                        }
                                        commandSender.sendMessage("This game is not defined");
                                    } else commandSender.sendMessage("Please, use /game set name "+strings[2]+" [value]");

                                } else commandSender.sendMessage("Please, use /game set name game [value]");
                                break;
                            case "mapName":
                                if (strings.length>=3) {
                                    if (strings.length==4) {
                                        if (Main.getInstance().getGameManager().nameExist(strings[2])) {
                                            Main.getInstance().getGameManager().getGameByName(strings[2]).setMapName(strings[3]);
                                            commandSender.sendMessage("mapName value of " + strings[2] + " game was changed on "+strings[3]);
                                            return true;
                                        }
                                        commandSender.sendMessage("This game is not defined");
                                    } else commandSender.sendMessage("Please, use /game set mapName "+strings[2]+" [value]");

                                } else commandSender.sendMessage("Please, use /game set mapName game [value]");
                                break;
                            case "countOfPlayers":
                                if (strings.length>=3) {
                                    if (strings.length==4) {
                                        if (Main.getInstance().getGameManager().nameExist(strings[2])) {
                                            Main.getInstance().getGameManager().getGameByName(strings[2]).setCountOfPlayers(Integer.parseInt(strings[3]));
                                            commandSender.sendMessage("countOfPlayers value of " + strings[2] + " game was changed on "+strings[3]);
                                            return true;
                                        }
                                        commandSender.sendMessage("This game is not defined");
                                    } else commandSender.sendMessage("Please, use /game set countOfPlayers "+strings[2]+" [value]");

                                } else commandSender.sendMessage("Please, use /game set countOfPlayers game [value]");
                                break;
                            case "world":
                                if (strings.length>=3) {
                                    if (strings.length==4) {
                                        if (Main.getInstance().getGameManager().nameExist(strings[2])) {
                                            Bukkit.createWorld(new WorldCreator(strings[3]));
                                            Main.getInstance().getGameManager().getGameByName(strings[2]).setWorld(strings[3]);
                                            commandSender.sendMessage("world folder of " + strings[2] + " game was changed on "+strings[3]);
                                            return true;
                                        }
                                        commandSender.sendMessage("This game is not defined");
                                    } else commandSender.sendMessage("Please, use /game set world "+strings[2]+" [world folder]");

                                } else commandSender.sendMessage("Please, use /game set world game [world folder]");
                                break;
                            case "pos1":
                                if (strings.length>=3) {
                                    if (strings.length==3) { // поменять иф
                                        if (Main.getInstance().getGameManager().nameExist(strings[2])) {
                                            Main.getInstance().getGameManager().getGameByName(strings[2]).setPos1ForBorderOfReplace(player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ());
                                            commandSender.sendMessage("pos1 location of " + strings[2] + " game was changed on "+ Arrays.toString(Main.getInstance().getGameManager().getGameByName(strings[2]).getPos1ForBorderOfReplace()));
                                            return true;
                                        }
                                        commandSender.sendMessage("This game is not defined");
                                    } else commandSender.sendMessage("Please, use /game set pos1 "+strings[2]);

                                } else commandSender.sendMessage("Please, use /game set pos1 game");
                                break;
                            case "pos2":
                                if (strings.length>=3) {
                                    if (strings.length==3) {
                                        if (Main.getInstance().getGameManager().nameExist(strings[2])) {
                                            Main.getInstance().getGameManager().getGameByName(strings[2]).setPos2ForBorderOfReplace(player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ());
                                            commandSender.sendMessage("pos2 location of " + strings[2] + " game was changed on "+Arrays.toString(Main.getInstance().getGameManager().getGameByName(strings[2]).getPos1ForBorderOfReplace()));
                                            return true;
                                        }
                                        commandSender.sendMessage("This game is not defined");
                                    } else commandSender.sendMessage("Please, use /game set pos2 "+strings[2]);

                                } else commandSender.sendMessage("Please, use /game set pos2 game");
                                break;
                        }
                    }
                    break;
            }
        }

        return false;
    }
}
