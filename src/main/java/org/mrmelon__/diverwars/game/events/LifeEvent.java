package org.mrmelon__.diverwars.game.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrmelon__.diverwars.Main;
import org.mrmelon__.diverwars.game.Game;

import java.util.List;

public class LifeEvent implements Listener {

    @EventHandler
    public void onDeath(EntityDamageEvent event) {
        System.out.println("call");
        if (event.getEntity() instanceof Player) {
            System.out.println("pl");
            Player player = (Player) event.getEntity();
            Game game = Main.getInstance().getGameManager().getGameByWorld(player.getWorld().getName());

            if (game != null) {
                System.out.println("gm");
                if (game.gameStatement) {
                    System.out.println("ac");
                    System.out.println(player.getHealth() + " " + event.getDamage());
                    if (player.getHealth()-event.getDamage()<=0) {
                        event.setCancelled(true);
                        System.out.println(event.getCause().toString());
                        System.out.println(event.getCause());
                        game.sendMessageSessionPlayers(ChatColor.GREEN + player.getName()+" was killed by "+ "CAUSE");
                        player.setGameMode(GameMode.SPECTATOR);
                        Main.getInstance().tpPlayer(new Location(Bukkit.getWorld(game.getWorld()),game.getLobby()[0],game.getLobby()[1],game.getLobby()[2]),player);
                        game.regenPlayer(player);
                        //перевод в спекты

                        int[] timer = {5+1};
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                timer[0]--;
                                player.sendMessage(ChatColor.AQUA + String.valueOf(timer[0]) + " remaining");
                                if (timer[0]==0) {
                                    int[] loc = game.getTeamPlayerByPlayer(player).getTeam().getTeamSpawn();
                                    Main.getInstance().tpPlayer(new Location(Bukkit.getWorld(game.getWorld()),loc[0],loc[1],loc[2]),player);
                                    player.setGameMode(GameMode.SURVIVAL);
                                    game.regenPlayer(player);
                                    cancel();
                                }
                            }

                        }.runTaskTimer(Main.getInstance(),0L,20L);


                    }
                }
            }
        }
    }

}
