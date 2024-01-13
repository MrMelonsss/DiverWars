package org.mrmelon__.diverwars.game.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrmelon__.diverwars.Main;
import org.mrmelon__.diverwars.game.Game;
import org.mrmelon__.diverwars.game.Team;
import org.mrmelon__.diverwars.game.TeamPlayer;

public class EngineEvent implements Listener {

    @EventHandler
    public void onBubble(EntityAirChangeEvent event) {

        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            Game game = Main.getInstance().getGameManager().getGameByWorld(player.getWorld().getName());
            if (game != null) {
                TeamPlayer teamPlayer = game.getTeamPlayerByPlayer(player);
                if (teamPlayer != null) {
                    if (game.gameStatement) {
                        long mult = teamPlayer.getArmor().getMultiplySecond();
                        int temp = teamPlayer.getTempForMultiply();
                        int regen = (int) (mult * 4);
                        int curr = teamPlayer.getCurrentAirCount();
                        Team team = getTeamByEngineRegion(game, player);


                        //вывод колва воздуха в балонах

                        if (temp == mult - 1) {
                            teamPlayer.setTempForMultiply(0);

                            if (player.getEyeLocation().getBlock().getType() == Material.WATER) {
                                if (curr == -21) {
                                    event.setAmount(-10);
                                    teamPlayer.setCurrentAirCount(-10);
                                } else {
                                    event.setAmount(curr - 1);
                                }
                                if (player.isDead()) {
                                    teamPlayer.setCurrentAirCount(300);
                                    event.setAmount(300);
                                }
                            } else {
                                if (curr >= 300) {
                                } else if (team != null) { //fasfas
                                    // вывод колва воздуха в куполе
                                    if (team.isRegenerationAir) {
                                        if (300 - curr < regen) {
                                            event.setAmount(300);
                                        } else {
                                            event.setAmount(curr + regen);
                                        }
                                    } else {
                                        if (team.getAirPercent() == 0) {
                                            if (curr == -21) {
                                                player.damage(2);
                                                event.setAmount(-10);
                                                teamPlayer.setCurrentAirCount(-10);
                                            } else {
                                                event.setAmount(curr - 1);
                                            }
                                            if (player.isDead()) {
                                                teamPlayer.setCurrentAirCount(300);
                                                event.setAmount(300);
                                            }
                                        } else if (300 - curr < regen) {
                                            if (300 - curr >= team.getAirPercent()) {
                                                event.setAmount(curr + team.getAirPercent());
                                                team.setAirPercent(0);
                                                // mess zero perc of air
                                            } else {
                                                event.setAmount(300);
                                                team.setAirPercent(team.getAirPercent() - (300 - curr));
                                            }
                                        } else {
                                            if (regen > teamPlayer.getTeam().getAirPercent()) {
                                                event.setAmount(curr + team.getAirPercent());
                                                team.setAirPercent(0);
                                                // mess zero perc of air
                                            } else {
                                                event.setAmount(curr + regen);
                                                team.setAirPercent(team.getAirPercent() - regen);
                                            }
                                        }

                                        player.sendMessage(team.getAirPercent() / (team.getMaxAirPercent() / 100) + "% of oxygen on base of " + team.getName());

                                    }
                                }
                            }

                            teamPlayer.setCurrentAirCount(event.getAmount());

                            System.out.println("-----------------------------------");
                            System.out.println(event.getAmount());
                            System.out.println(team != null ? team.getAirPercent() : null);
                            System.out.println("-----------------------------------");

                        } else {
                            teamPlayer.setTempForMultiply(temp + 1);
                            event.setAmount(temp % 2 == 0 ? curr - 1 : curr);
                        }
                    }
                }
            }
        }
    }

    private Team getTeamByEngineRegion(Game game, Player player) {
        for (Team team : game.getTeams()) {
            if (checkRegion(team,player)) {
                return team;
            }
        }
        return null;
    }

    private boolean checkRegion(Team team, Player player) {
        int x1 = team.getPos1RangeEngine()[0];
        int y1 =  team.getPos1RangeEngine()[1];
        int z1 =  team.getPos1RangeEngine()[2];
        int x2 =  team.getPos2RangeEngine()[0];
        int y2 =  team.getPos2RangeEngine()[1];
        int z2 =  team.getPos2RangeEngine()[2];
        int xBlock = player.getLocation().getBlockX();
        int yBlock = player.getLocation().getBlockY();
        int zBlock = player.getLocation().getBlockZ();

        if (x1>x2) {
            int temp=x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1>y2) {
            int temp=y1;
            y1 = y2;
            y2 = temp;
        }
        if (z1>z2) {
            int temp=z1;
            z1 = z2;
            z2 = temp;
        }

        if (x1<=xBlock && xBlock<x2) {
            if (y1<=yBlock && yBlock<y2) {
                if (z1<=zBlock && zBlock<z2) {
                    return true;
                }
            }
        }
        return false;
    }

}
