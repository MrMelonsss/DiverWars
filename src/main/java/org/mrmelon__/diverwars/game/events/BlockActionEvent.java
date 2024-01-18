package org.mrmelon__.diverwars.game.events;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.mrmelon__.diverwars.Main;
import org.mrmelon__.diverwars.game.Game;
import org.mrmelon__.diverwars.game.Team;
import org.mrmelon__.diverwars.game.TeamPlayer;

public class BlockActionEvent implements Listener {

    @EventHandler
    public void breakBlock(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();
        World world = location.getWorld();
        Game game = Main.getInstance().getGameManager().getGameByWorld(world.getName());
        Player player = event.getPlayer();

        if (game.gameStatement) {
            if (checkRegion(game, location)) {
                Team team = game.getTeamByEngineLocation(location);
                if (team != null) {
                    for (TeamPlayer teamPlayer : team.getPlayersInTeam()) {
                        if (teamPlayer.getPlayer().equals(player)) {
                            player.sendMessage("You can't break you bed");
                            event.setCancelled(true);
                            return;
                        }
                    }
                    player.sendMessage("You broke bed");
                    team.brokeRegeneration();
                } else {
                    if (game.getBlocksForReplace().containsKey(location)) {
                        location.getBlock().setType(game.getBlocksForReplace().get(location));
                        game.getBlocksForReplace().remove(location);
                    } else {
                        player.sendMessage("You can't break this block");
                        event.setCancelled(true);
                    }
                }
            }
        }

    }

    @EventHandler
    public void placeBlock(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();
        World world = location.getWorld();
        Game game = Main.getInstance().getGameManager().getGameByWorld(world.getName());
        Player player = event.getPlayer();

        if (game.gameStatement) {
            if (checkRegion(game, location)) {
                if (game.getTeamByEngineLocation(location) != null) {
                    player.sendMessage("You can't place block here");
                    event.setCancelled(true);
                    return;
                }
                game.getBlocksForReplace().put(location, block.getType()); // пофиксить тему с водой
            }
        }
    }

    private boolean checkRegion(Game game, Location location) {
        int x1 = game.getPos1ForBorderOfReplace()[0];
        int y1 = game.getPos1ForBorderOfReplace()[1];
        int z1 = game.getPos1ForBorderOfReplace()[2];
        int x2 = game.getPos2ForBorderOfReplace()[0];
        int y2 = game.getPos2ForBorderOfReplace()[1];
        int z2 = game.getPos2ForBorderOfReplace()[2];
        int xBlock = location.getBlockX();
        int yBlock = location.getBlockY();
        int zBlock = location.getBlockZ();

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
