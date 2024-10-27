package org.cordell.com.anizottizombie.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.cordell.com.anizottizombie.AnizottiZombie;


public class ZombieDied implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        try {
            var entity = event.getEntity();
            for (var horde : AnizottiZombie.hordes) {
                for (var zombie : horde.getSpawnedZombies()) {
                    if (zombie.getBody().equals(entity)) {
                        zombie.onDied();
                        horde.getSpawnedZombies().remove(zombie);
                        horde.damage(1);
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("exception" + e.getMessage());
        }
    }
}
