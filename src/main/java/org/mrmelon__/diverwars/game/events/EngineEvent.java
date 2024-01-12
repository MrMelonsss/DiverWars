package org.mrmelon__.diverwars.game.events;

import org.bukkit.Location;
import org.bukkit.Material;
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
                    long mult = teamPlayer.getArmor().getMultiplySecond();
                    int temp = teamPlayer.getTempForMultiply();
                    int regen = (int) (mult * 4);
                    int curr = teamPlayer.getCurrentAirCount();
                    Team team = getTeamByEngineRegion(game,player);
                    //Bukkit Scheduler
                    //вывод колва воздуха в балонах

                    if (temp == mult - 1) {
                        teamPlayer.setTempForMultiply(0);

                        System.out.println("event" + player.getMaximumAir());

                        if (player.getEyeLocation().getBlock().getType() == Material.WATER) {
                            if (curr == -21) {
                                event.setAmount(-17);
                                teamPlayer.setCurrentAirCount(-17);
                            } else {
                                event.setAmount(curr - 1);
                            }
                            if (player.isDead()) {
                                System.out.println("CANCEL Death");
                                teamPlayer.setCurrentAirCount(300);
                            }
                        } else {
                            System.out.println("reg");
                            if (curr >= 300) {
                                System.out.println("CANCEL Regen");
                            } else if (team!=null) { //fasfas
                                // вывод колва воздуха в куполе
                                if (teamPlayer.getTeam().isRegenerationAir) {
                                    if (300 - curr < regen) {
                                        event.setAmount(300);
                                    } else {
                                        event.setAmount(curr + regen);
                                    }
                                } else {
                                    if (300 - curr < regen) {
                                        if (300 - curr >= teamPlayer.getTeam().getAirPercent()) {
                                            event.setAmount(curr + teamPlayer.getTeam().getAirPercent());
                                            teamPlayer.getTeam().setAirPercent(0);
                                            // mess zero perc of air
                                        } else {
                                            event.setAmount(300);
                                            teamPlayer.getTeam().setAirPercent(teamPlayer.getTeam().getAirPercent() - (300 - curr));
                                        }
                                    } else {
                                        if (regen > teamPlayer.getTeam().getAirPercent()) {
                                            event.setAmount(curr + teamPlayer.getTeam().getAirPercent());
                                            teamPlayer.getTeam().setAirPercent(0);
                                            // mess zero perc of air
                                        } else {
                                            event.setAmount(curr + regen);
                                            teamPlayer.getTeam().setAirPercent(teamPlayer.getTeam().getAirPercent() - regen);
                                        }
                                    }
                                }
                            }
                        }

                        teamPlayer.setCurrentAirCount(event.getAmount());

                        System.out.println("-----------------------------------");
                        System.out.println(curr);
                        System.out.println(event.getAmount());
                        System.out.println(teamPlayer.getTeam().getAirPercent());
                        System.out.println("-----------------------------------");

                    } else {
                        teamPlayer.setTempForMultiply(temp + 1);
                        event.setAmount(temp % 2 == 0 ? curr - 1 : curr);
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
