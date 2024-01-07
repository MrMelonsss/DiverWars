package org.mrmelon__.diverwars.game.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;

public class EngineEvent implements Listener {

    public static boolean switcher = true;
    @EventHandler
    public void onBubble(EntityAirChangeEvent event) {
        if (event.getEntityType()== EntityType.PLAYER) {
            if (switcher) {
                switcher=false;
                event.setAmount(100);
            }
            if (event.getAmount()==-19) {
                event.setAmount(0);
            }
            System.out.println("-----------------");
            System.out.println(event.isCancelled());
            System.out.println(event.getAmount());
            System.out.println("-----------------");
        }
    }

}
