package org.cordell.com.anizottizombie.objects.zombies;

import org.bukkit.Bukkit;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.cordell.com.anizottizombie.objects.BaseZombie;


public class FatmanZombie extends BaseZombie {
    public FatmanZombie(int hp, double size, double speed) {
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
    public void onDied() {
        var world = Bukkit.getServer().getWorlds().get(0);
        var effectCloud = (AreaEffectCloud) world.spawn(getBody().getLocation(), AreaEffectCloud.class);
        effectCloud.setRadius(3.0F);
        effectCloud.setDuration(10000);
        effectCloud.setRadiusPerTick(-0.05F);
        var healingEffect = new PotionEffect(PotionEffectType.NAUSEA, 1, 1);
        effectCloud.addCustomEffect(healingEffect, true);
    }
}
