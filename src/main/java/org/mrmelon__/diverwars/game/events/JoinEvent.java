package org.mrmelon__.diverwars.game.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mrmelon__.diverwars.Main;
import org.mrmelon__.diverwars.game.Game;
import org.mrmelon__.diverwars.game.Team;
import org.mrmelon__.diverwars.game.TeamPlayer;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Game game = Main.getInstance().getGameManager().getGameByWorld(player.getWorld().getName());

        for (Team team : game.getTeams()) {
            for (TeamPlayer teamPlayer : team.getPlayersInTeam()) {
                if (player.getUniqueId().equals(teamPlayer.getUuid())) {
                    teamPlayer.setPlayer(player);
                }
            }
        }
    }

}
