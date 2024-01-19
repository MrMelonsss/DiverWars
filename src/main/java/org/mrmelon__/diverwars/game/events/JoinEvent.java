package org.mrmelon__.diverwars.game.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mrmelon__.diverwars.Main;
import org.mrmelon__.diverwars.game.Game;
import org.mrmelon__.diverwars.game.Team;
import org.mrmelon__.diverwars.game.TeamPlayer;

import java.util.List;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) { //leave event for join
        Player player = event.getPlayer();

        Game game = Main.getInstance().getGameManager().getGameByWorld(player.getWorld().getName());

        FileConfiguration configuration = Main.getInstance().getConfigMain();
        List<Integer> list = configuration.getIntegerList("spawnLocation");

        Location spawn = new Location(Bukkit.getWorld(configuration.getString("spawnWorld")),list.get(0),list.get(1),list.get(2));

        player.setInvulnerable(true);

        if (game!=null) {
            for (Team team : game.getTeams()) {
                for (TeamPlayer teamPlayer : team.getPlayersInTeam()) {
                    if (player.getUniqueId().equals(teamPlayer.getUuid())) {
                        teamPlayer.setPlayer(player);
                        spawn = player.getLocation();
                        player.setInvulnerable(false);
                    }
                }
            }
        }

        Main.getInstance().tpPlayer(spawn,player);

    }

}
