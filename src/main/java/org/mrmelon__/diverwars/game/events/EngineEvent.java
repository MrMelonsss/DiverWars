package org.mrmelon__.diverwars.game.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.mrmelon__.diverwars.Main;
import org.mrmelon__.diverwars.game.Game;
import org.mrmelon__.diverwars.game.TeamPlayer;

public class EngineEvent implements Listener {

    public static boolean switcher = true;

    public void airEvent(TeamPlayer teamPlayer) {
        int mult = teamPlayer.getArmor().getMultiplySecond();
        //Bukkit Scheduler
    }
    @EventHandler
    public void onBubble(EntityAirChangeEvent event) {

        if (event.getEntityType()== EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            Game game = Main.getInstance().getGameManager().getGameByName(player.getWorld().getName());
            if (game!=null) {
                TeamPlayer teamPlayer = game.getTeamPlayerByPlayer(player);
                if (teamPlayer != null) {
                    airEvent(teamPlayer);
                }
            }
        }

    }

}
