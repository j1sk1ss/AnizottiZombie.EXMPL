package org.cordell.com.anizottizombie.objects.zombies;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.cordell.com.anizottizombie.objects.BaseZombie;


public class LightZombie extends BaseZombie {
    public LightZombie(int hp, double size, double speed) {
        super(hp, size, speed);
    }

    @Override
    protected EntityType getType() {
        return EntityType.ZOMBIE;
    }

    @Override
    protected ItemStack getHelmet() {
        return null;
    }

    @Override
    protected ItemStack getChestplate() {
        return null;
    }

    @Override
    protected ItemStack getItemInMainHand() {
        return null;
    }

    @Override
    public void onDied() {}
}
