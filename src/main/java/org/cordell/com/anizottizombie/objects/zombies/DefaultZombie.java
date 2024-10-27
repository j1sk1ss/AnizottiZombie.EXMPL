package org.cordell.com.anizottizombie.objects.zombies;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.cordell.com.anizottizombie.objects.BaseZombie;


public class DefaultZombie extends BaseZombie {
    public DefaultZombie(int hp, double size, double speed) {
        super(hp, size, speed);
    }

    @Override
    protected EntityType getType() {
        return EntityType.ZOMBIE;
    }

    @Override
    protected ItemStack getHelmet() {
        return new ItemStack(Material.LEATHER_HELMET);
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
