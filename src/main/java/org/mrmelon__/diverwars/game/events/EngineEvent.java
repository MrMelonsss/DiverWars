package org.mrmelon__.diverwars.game.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrmelon__.diverwars.Main;
import org.mrmelon__.diverwars.game.Game;
import org.mrmelon__.diverwars.game.TeamPlayer;

public class EngineEvent implements Listener {

    public void airEvent(TeamPlayer teamPlayer, EntityAirChangeEvent event) {
        long mult = teamPlayer.getArmor().getMultiplySecond();
        int regen = (int) (mult*4);
        final int[] curr = {event.getAmount()};
        teamPlayer.airEventIsWorking=true;
        //Bukkit Scheduler
        //вывод колва воздуха в балонах

        new BukkitRunnable() {
            @Override
            public void run() {
                if (Game.ds==1) {
                    event.setAmount(curr[0]);
                    Game.ds=0;
                } else {
                    event.setAmount(curr[0]-1);
                    Game.ds=1;
                }
            }
        }.runTaskTimer(Main.getInstance(),0L,1L);

        new BukkitRunnable(){
            @Override
            public void run() {

                Player player = teamPlayer.getPlayer();

                System.out.println("event"+player.getMaximumAir());

                if (player.getEyeLocation().getBlock().getType() == Material.WATER) {
                    System.out.println("water");
                    if (event.getAmount()==-21) {
                        System.out.println("-21");
                        event.setAmount(0);
                    } else {
                        System.out.println("--"+ curr[0]);
                        event.setAmount(curr[0] - 1);
                        System.out.println("--"+ curr[0]);
                    }
                    if (player.isDead()) {
                        System.out.println("CANCEL Death");
                        teamPlayer.airEventIsWorking=false;
                        this.cancel();
                    }
                } else {
                    System.out.println("reg");
                    if (event.getAmount()>=300) {
                        System.out.println("CANCEL Regen");
                        teamPlayer.airEventIsWorking=false;
                        this.cancel();
                    } else if (checkRegion(teamPlayer)) {
                        // вывод колва воздуха в куполе
                        if (teamPlayer.getTeam().isRegenerationAir) {
                            if (300-event.getAmount()<regen) {
                                event.setAmount(event.getAmount()+(300- event.getAmount()));
                            } else {
                                event.setAmount(event.getAmount() + regen);
                            }
                        } else {
                            if (300-player.getRemainingAir()<regen) {
                                if (300-player.getRemainingAir()<=teamPlayer.getTeam().getAirPercent()) {
                                    player.setRemainingAir(player.getRemainingAir() + (300 - teamPlayer.getTeam().getAirPercent()));
                                    teamPlayer.getTeam().setAirPercent(0);
                                    // mess zero perc of air
                                } else {
                                    player.setRemainingAir(player.getRemainingAir() + (300 - player.getRemainingAir()));
                                    teamPlayer.getTeam().setAirPercent(teamPlayer.getTeam().getAirPercent()-(300 - player.getRemainingAir()));
                                }
                            } else {
                                if (regen>teamPlayer.getTeam().getAirPercent()) {
                                    player.setRemainingAir(player.getRemainingAir() + teamPlayer.getTeam().getAirPercent());
                                    teamPlayer.getTeam().setAirPercent(0);
                                    // mess zero perc of air
                                } else {
                                    player.setRemainingAir(player.getRemainingAir() + regen);
                                    teamPlayer.getTeam().setAirPercent(teamPlayer.getTeam().getAirPercent()-regen);
                                }
                            }
                        }
                    }
                }

                curr[0] = event.getAmount();

                System.out.println("-----------------------------------");
                System.out.println(curr[0]);
                System.out.println(event.getAmount());
                System.out.println(teamPlayer.getTeam().getAirPercent());
                System.out.println("-----------------------------------");

            }

        }.runTaskTimer(Main.getInstance(),0L,mult);
    }

    @EventHandler
    public void onBubble(EntityAirChangeEvent event) {

        if (event.getEntityType()== EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            Game game = Main.getInstance().getGameManager().getGameByWorld(player.getWorld().getName());
            if (game!=null) {
                TeamPlayer teamPlayer = game.getTeamPlayerByPlayer(player);
                if (teamPlayer != null) {
                    if (!teamPlayer.airEventIsWorking) {
                        System.out.println("teamPlayer");
                        //airEvent(teamPlayer, event);

                        long mult = teamPlayer.getArmor().getMultiplySecond();
                        int regen = (int) (mult*4);
                        final int[] curr = {event.getAmount()};
                        teamPlayer.airEventIsWorking=true;
                        //Bukkit Scheduler
                        //вывод колва воздуха в балонах

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (Game.ds==1) {
                                    event.setAmount(curr[0]);
                                    Game.ds=0;
                                } else {
                                    event.setAmount(curr[0]-1);
                                    Game.ds=1;
                                }
                            }
                        }.runTaskTimer(Main.getInstance(),0L,1L);

                        new BukkitRunnable(){
                            @Override
                            public void run() {

                                Player player = teamPlayer.getPlayer();

                                System.out.println("event"+player.getMaximumAir());

                                if (player.getEyeLocation().getBlock().getType() == Material.WATER) {
                                    System.out.println("water");
                                    if (event.getAmount()==-21) {
                                        System.out.println("-21");
                                        event.setAmount(0);
                                    } else {
                                        System.out.println("--"+ curr[0]);
                                        event.setAmount(curr[0] - 1);
                                        System.out.println("--"+ curr[0]);
                                    }
                                    if (player.isDead()) {
                                        System.out.println("CANCEL Death");
                                        teamPlayer.airEventIsWorking=false;
                                        this.cancel();
                                    }
                                } else {
                                    System.out.println("reg");
                                    if (event.getAmount()>=300) {
                                        System.out.println("CANCEL Regen");
                                        teamPlayer.airEventIsWorking=false;
                                        this.cancel();
                                    } else if (checkRegion(teamPlayer)) {
                                        // вывод колва воздуха в куполе
                                        if (teamPlayer.getTeam().isRegenerationAir) {
                                            if (300-event.getAmount()<regen) {
                                                event.setAmount(event.getAmount()+(300- event.getAmount()));
                                            } else {
                                                event.setAmount(event.getAmount() + regen);
                                            }
                                        } else {
                                            if (300-player.getRemainingAir()<regen) {
                                                if (300-player.getRemainingAir()<=teamPlayer.getTeam().getAirPercent()) {
                                                    player.setRemainingAir(player.getRemainingAir() + (300 - teamPlayer.getTeam().getAirPercent()));
                                                    teamPlayer.getTeam().setAirPercent(0);
                                                    // mess zero perc of air
                                                } else {
                                                    player.setRemainingAir(player.getRemainingAir() + (300 - player.getRemainingAir()));
                                                    teamPlayer.getTeam().setAirPercent(teamPlayer.getTeam().getAirPercent()-(300 - player.getRemainingAir()));
                                                }
                                            } else {
                                                if (regen>teamPlayer.getTeam().getAirPercent()) {
                                                    player.setRemainingAir(player.getRemainingAir() + teamPlayer.getTeam().getAirPercent());
                                                    teamPlayer.getTeam().setAirPercent(0);
                                                    // mess zero perc of air
                                                } else {
                                                    player.setRemainingAir(player.getRemainingAir() + regen);
                                                    teamPlayer.getTeam().setAirPercent(teamPlayer.getTeam().getAirPercent()-regen);
                                                }
                                            }
                                        }
                                    }
                                }

                                curr[0] = event.getAmount();

                                System.out.println("-----------------------------------");
                                System.out.println(curr[0]);
                                System.out.println(event.getAmount());
                                System.out.println(teamPlayer.getTeam().getAirPercent());
                                System.out.println("-----------------------------------");

                            }

                        }.runTaskTimer(Main.getInstance(),0L,mult);
                    }
                    //event.setCancelled(true);

                }
            }
//            if (player.getEyeLocation().getBlock().getType() != Material.WATER) {
//                if (event.getAmount()<=140) event.setAmount(300);
//            } else
//                if (event.getAmount()>=299)  event.setAmount(200);
//                else if (event.getAmount()>200) {
//                    if (Game.ds==1) {
//                        Game.ds = 0;
//                        event.setAmount(209);
//                    } else {
//                        event.setAmount(210);
//                        Game.ds = Game.ds + 1;
//                    }
//                }
//            System.out.println(event.getAmount());

//            event.setCancelled(true);
//            new BukkitRunnable() {
//
//                @Override
//                public void run() {
//                    System.out.println("tasktimer");
//                    event.setCancelled(false);
//                    System.out.println(event.getAmount());
//                    this.cancel();
//                }
//
//            }.runTaskLater(Main.getInstance(),20L);

        }




    }

    private boolean checkRegion(TeamPlayer teamPlayer) {
        int x1 = teamPlayer.getTeam().getPos1RangeEngine()[0];
        int y1 =  teamPlayer.getTeam().getPos1RangeEngine()[1];
        int z1 =  teamPlayer.getTeam().getPos1RangeEngine()[2];
        int x2 =  teamPlayer.getTeam().getPos2RangeEngine()[0];
        int y2 =  teamPlayer.getTeam().getPos2RangeEngine()[1];
        int z2 =  teamPlayer.getTeam().getPos2RangeEngine()[2];
        int xBlock = teamPlayer.getPlayer().getLocation().getBlockX();
        int yBlock = teamPlayer.getPlayer().getLocation().getBlockY();
        int zBlock = teamPlayer.getPlayer().getLocation().getBlockZ();

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
