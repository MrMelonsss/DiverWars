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
            commandSender.sendMessage("Please, use /diverwars [arg]");
        } else {


            // BLOCK DEL

            if (strings[0].equals("tp")) {
                player.teleport(new Location(Bukkit.getWorld(strings[1]),0,100,0));
            }
            if (strings[0].equals("check")) {
                System.out.println(Main.getInstance().getGameManager().getGameByName(strings[1]).getBlocksForReplace());
            }
            if (strings[0].equals("teamadd")) {
                Main.getInstance().getGameManager().getGameByName(strings[1]).getTeamByName(strings[2]).addPlayersInTeam(player);
                System.out.println(Main.getInstance().getGameManager().getGameByName(strings[1]).getTeamPlayerByPlayer(player));
                Main.getInstance().getGameManager().getGameByName(strings[1]).joinGame(player);
            }
            if (strings[0].equals("switchTrue")) {
                Main.getInstance().getGameManager().getGameByName(strings[1]).getTeamByName(strings[2]).isRegenerationAir=true;
                System.out.println(Main.getInstance().getGameManager().getGameByName(strings[1]).getTeamByName(strings[2]).isRegenerationAir);
            }
            if (strings[0].equals("switchFalse")) {
                Main.getInstance().getGameManager().getGameByName(strings[1]).getTeamByName(strings[2]).isRegenerationAir=false;
                System.out.println(Main.getInstance().getGameManager().getGameByName(strings[1]).getTeamByName(strings[2]).isRegenerationAir);
            }

            // BLOCK DEL


            switch (strings[0].toLowerCase()){
                case "create":
                    if (strings.length==2) {
                        if (!Main.getInstance().getGameManager().nameExist(strings[1])) {
                            new Game(strings[1]);
                            commandSender.sendMessage("Game "+strings[1]+" was created");
                            return true;
                        }
                        commandSender.sendMessage("This game is already exist");
                    } else commandSender.sendMessage("Please, use /diverwars create [game]");
                    break;
                case "reloadin":
                    if (strings.length==2) {
                        if (Main.getInstance().getGameManager().nameExist(strings[1])) {
                            Main.getInstance().getGameManager().getGameByName(strings[1]).reloadValueInConfig();
                            commandSender.sendMessage("Game "+strings[1]+" was reloaded IN");
                            return true;
                        }
                        commandSender.sendMessage("This game is not defined");
                    } else commandSender.sendMessage("Please, use /diverwars reloadIN [game]");
                    break;
                case "reloadout":
                    if (strings.length==2) {
                        if (Main.getInstance().getGameManager().nameExist(strings[1])) {
                            Main.getInstance().getGameManager().getGameByName(strings[1]).getAllFromConfig();
                            commandSender.sendMessage("Game "+strings[1]+" was reloaded OUT");
                            return true;
                        }
                        commandSender.sendMessage("This game is not defined");
                    } else commandSender.sendMessage("Please, use /diverwars reloadOUT [game]");
                    break;
                case "join":
                    if (strings.length==2) {
                        if (Main.getInstance().getGameManager().nameExist(strings[1])) {
                            Main.getInstance().getGameManager().getGameByName(strings[1]).joinGame(player);
                            return true;
                        }
                        commandSender.sendMessage("This game is not defined");
                    } else commandSender.sendMessage("Please, use /diverwars join [game]");
                    break;
                case "leave":
                    if (strings.length==2) {
                        if (Main.getInstance().getGameManager().nameExist(strings[1])) {
                            Main.getInstance().getGameManager().getGameByName(strings[1]).leaveGame(player);
                            return true;
                        }
                        commandSender.sendMessage("This game is not defined");
                    } else commandSender.sendMessage("Please, use /diverwars leave [game]");
                    break;
                case "delete":
                    if (strings.length==2) {
                        if (Main.getInstance().getGameManager().nameExist(strings[1])) {
                            Main.getInstance().getGameManager().deleteGameObject(Main.getInstance().getGameManager().getGameByName(strings[1]));
                            commandSender.sendMessage("Game "+strings[1]+" was deleted");
                            return true;
                        }
                        commandSender.sendMessage("This game is not defined");
                    } else commandSender.sendMessage("Please, use /diverwars delete [game]");
                    break;
                case "team":// make lists of objects {game,team}
                    if (strings.length==1) {
                        commandSender.sendMessage("Please, use /diverwars team [arg] [game] [values]");
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
                                        commandSender.sendMessage("Please, use /diverwars team add " + strings[2] + " [name] [playersCount] [color]");

                                } else
                                    commandSender.sendMessage("Please, use /diverwars team add [game] [name] [playersCount] [color]");
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
                                        commandSender.sendMessage("Please, use /diverwars team remove " + strings[2] + " [name]");

                                } else
                                    commandSender.sendMessage("Please, use /diverwars team remove [game] [name]");
                                break;
                            case "set":
                                if (strings.length==2) {
                                    commandSender.sendMessage("Please, use /diverwars team set [arg] [game] [value]");
                                } else {
                                    switch (strings[2]) {
                                        case "teamSpawn":
                                            if (strings.length >= 3) {
                                                if (strings.length == 5) {
                                                    if (Main.getInstance().getGameManager().nameExist(strings[3])) {
                                                        if (Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4])==null) {
                                                            commandSender.sendMessage("this team is not defined");
                                                            return false;
                                                        }
                                                        Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4]).setTeamSpawn(player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ());
                                                        Main.getInstance().getGameManager().getGameByName(strings[3]).reloadValueInConfig();
                                                        commandSender.sendMessage("teamSpawn location of " + strings[3] + " game was changed on "+Arrays.toString(Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4]).getTeamSpawn()));
                                                        return true;
                                                    }
                                                    commandSender.sendMessage("This game is not defined");
                                                } else commandSender.sendMessage("Please, use /diverwars team set teamSpawn [game] [team name]");

                                            } else commandSender.sendMessage("Please, use /diverwars team set teamSpawn [game]");
                                            break;
                                        case "teamGenerator":
                                            if (strings.length >= 3) {
                                                if (strings.length == 5) {
                                                    if (Main.getInstance().getGameManager().nameExist(strings[3])) {
                                                        if (Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4])==null) {
                                                            commandSender.sendMessage("this team is not defined");
                                                            return false;
                                                        }
                                                        Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4]).setTeamGenerator(player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ());
                                                        Main.getInstance().getGameManager().getGameByName(strings[3]).reloadValueInConfig();
                                                        commandSender.sendMessage("teamGenerator location of " + strings[3] + " game was changed on "+Arrays.toString(Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4]).getTeamGenerator()));
                                                        return true;
                                                    }
                                                    commandSender.sendMessage("This game is not defined");
                                                } else commandSender.sendMessage("Please, use /diverwars team set teamGenerator [game] [team name]");

                                            } else commandSender.sendMessage("Please, use /diverwars team set teamGenerator [game]");
                                            break;
                                        case "teamEngine":
                                            if (strings.length >= 3) {
                                                if (strings.length == 5) {
                                                    if (Main.getInstance().getGameManager().nameExist(strings[3])) {
                                                        if (Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4])==null) {
                                                            commandSender.sendMessage("this team is not defined");
                                                            return false;
                                                        }
                                                        Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4]).setTeamEngine(player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ());
                                                        Main.getInstance().getGameManager().getGameByName(strings[3]).reloadValueInConfig();
                                                        commandSender.sendMessage("teamEngine location of " + strings[3] + " game was changed on "+Arrays.toString(Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4]).getTeamEngine()));
                                                        return true;
                                                    }
                                                    commandSender.sendMessage("This game is not defined");
                                                } else commandSender.sendMessage("Please, use /diverwars team set teamEngine [game] [team name]");

                                            } else commandSender.sendMessage("Please, use /diverwars team set teamEngine [game]");
                                            break;
                                        case "pos1RangeEngine":
                                            if (strings.length >= 3) {
                                                if (strings.length == 5) {
                                                    if (Main.getInstance().getGameManager().nameExist(strings[3])) {
                                                        if (Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4])==null) {
                                                            commandSender.sendMessage("this team is not defined");
                                                            return false;
                                                        }
                                                        Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4]).setPos1RangeEngine(player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ());
                                                        Main.getInstance().getGameManager().getGameByName(strings[3]).reloadValueInConfig();
                                                        commandSender.sendMessage("pos1RangeEngine location of " + strings[3] + " game was changed on "+Arrays.toString(Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4]).getPos1RangeEngine()));
                                                        return true;
                                                    }
                                                    commandSender.sendMessage("This game is not defined");
                                                } else commandSender.sendMessage("Please, use /diverwars team set pos1RangeEngine [game] [team name]");

                                            } else commandSender.sendMessage("Please, use /diverwars team set pos1RangeEngine [game]");
                                            break;
                                        case "pos2RangeEngine":
                                            if (strings.length >= 3) {
                                                if (strings.length == 5) {
                                                    if (Main.getInstance().getGameManager().nameExist(strings[3])) {
                                                        if (Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4])==null) {
                                                            commandSender.sendMessage("this team is not defined");
                                                            return false;
                                                        }
                                                        Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4]).setPos2RangeEngine(player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ());
                                                        Main.getInstance().getGameManager().getGameByName(strings[3]).reloadValueInConfig();
                                                        commandSender.sendMessage("pos2RangeEngine location of " + strings[3] + " game was changed on "+Arrays.toString(Main.getInstance().getGameManager().getGameByName(strings[3]).getTeamByName(strings[4]).getPos2RangeEngine()));
                                                        return true;
                                                    }
                                                    commandSender.sendMessage("This game is not defined");
                                                } else commandSender.sendMessage("Please, use /diverwars team set pos2RangeEngine [game] [team name]");

                                            } else commandSender.sendMessage("Please, use /diverwars team set pos2RangeEngine [game]");
                                            break;
                                    }
                                }
                        }
                    }
                    break;
                case "set":
                    if (strings.length==1) {
                        commandSender.sendMessage("Please, use /diverwars set [arg] [game] [value]");
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
                                    } else commandSender.sendMessage("Please, use /diverwars set name "+strings[2]+" [value]");

                                } else commandSender.sendMessage("Please, use /diverwars set name game [value]");
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
                                    } else commandSender.sendMessage("Please, use /diverwars set mapName "+strings[2]+" [value]");

                                } else commandSender.sendMessage("Please, use /diverwars set mapName game [value]");
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
                                    } else commandSender.sendMessage("Please, use /diverwars set countOfPlayers "+strings[2]+" [value]");

                                } else commandSender.sendMessage("Please, use /diverwars set countOfPlayers game [value]");
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
                                    } else commandSender.sendMessage("Please, use /diverwars set world "+strings[2]+" [world folder]");

                                } else commandSender.sendMessage("Please, use /diverwars set world game [world folder]");
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
                                    } else commandSender.sendMessage("Please, use /diverwars set pos1 "+strings[2]);

                                } else commandSender.sendMessage("Please, use /diverwars set pos1 game");
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
                                    } else commandSender.sendMessage("Please, use /diverwars set pos2 "+strings[2]);

                                } else commandSender.sendMessage("Please, use /diverwars set pos2 game");
                                break;
                            case "lobby":
                                if (strings.length>=3) {
                                    if (strings.length==3) {
                                        if (Main.getInstance().getGameManager().nameExist(strings[2])) {
                                            Main.getInstance().getGameManager().getGameByName(strings[2]).setLobby(player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ());
                                            commandSender.sendMessage("lobby location of " + strings[2] + " game was changed on "+Arrays.toString(Main.getInstance().getGameManager().getGameByName(strings[2]).getLobby()));
                                            return true;
                                        }
                                        commandSender.sendMessage("This game is not defined");
                                    } else commandSender.sendMessage("Please, use /diverwars set lobby "+strings[2]);

                                } else commandSender.sendMessage("Please, use /diverwars set lobby game");
                                break;
                        }
                    }
                    break;
            }
        }

        return false;
    }
}
