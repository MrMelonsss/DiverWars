package org.mrmelon__.diverwars.game.events;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.mrmelon__.diverwars.Main;
import org.mrmelon__.diverwars.game.Game;

public class BlockActionEvent implements Listener {

    @EventHandler
    public void breakBlock(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();
        World world = location.getWorld();
        Game game = Main.getInstance().getGameManager().getGameByWorld(world.getName());

        if (checkRegion(game,location)) {
            if (!game.getBlocksForReplace().containsKey(location)) {
                game.getBlocksForReplace().put(location, block.getType());
            }
        }

    }

    @EventHandler
    public void placeBlock(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();
        World world = location.getWorld();
        Game game = Main.getInstance().getGameManager().getGameByWorld(world.getName());

        if (checkRegion(game,location)) {
            if (!game.getBlocksForReplace().containsKey(location)) {
                game.getBlocksForReplace().put(location, Material.AIR);
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
