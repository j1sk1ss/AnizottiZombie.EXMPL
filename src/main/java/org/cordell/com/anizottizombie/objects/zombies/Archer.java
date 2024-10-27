package org.cordell.com.anizottizombie.objects.zombies;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.cordell.com.anizottizombie.objects.BaseZombie;


public class Archer extends BaseZombie {
    public Archer(int hp, double size, double speed) {
        super(hp, size, speed);
    }

    @Override
    protected EntityType getType() {
        return EntityType.SKELETON;
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
