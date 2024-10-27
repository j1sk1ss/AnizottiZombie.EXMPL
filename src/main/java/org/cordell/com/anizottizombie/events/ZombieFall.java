package org.cordell.com.anizottizombie.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.cordell.com.anizottizombie.AnizottiZombie;


public class ZombieFall implements Listener {
    @EventHandler
    public void onZombieFall(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            for (var horde : AnizottiZombie.hordes) {
                for (var zombie : horde.getSpawnedZombies()) {
                    if (zombie.getBody().equals(entity) && event.getCause() == EntityDamageEvent.DamageCause.FALL) event.setCancelled(true);
                }
            }
        }
    }
}
