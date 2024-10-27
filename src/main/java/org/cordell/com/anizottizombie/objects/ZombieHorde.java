package org.cordell.com.anizottizombie.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;


public class ZombieHorde {
    public ZombieHorde(int zombieCount, Location spawn) {
        hpBar = Bukkit.createBossBar("Орда", BarColor.RED, BarStyle.SOLID);
        hpBar.setProgress(1.0);

        maxHordeHp = zombieCount;
        hordeHp = zombieCount;

        spawnLocation = spawn;
        zombies = new ArrayList<>();
    }

    private final BossBar hpBar;
    private final double maxHordeHp;
    private double hordeHp;

    private final Location spawnLocation;
    private final List<LivingEntity> zombies;

    public void damage(int damage) {
        hordeHp -= damage;
        hpBar.setProgress(hordeHp / maxHordeHp);
        if (hordeHp > maxHordeHp) hordeHp = maxHordeHp;
        else if (hordeHp <= 0) {
            hordeHp = 0;
        }
    }

    public void spawnPart(String type, int count) {
        for (int i = 0; i < count; i++) {
            var world = Bukkit.getServer().getWorlds().get(0);

            var center = getCenter();
            var xOffset = (new Random().nextDouble() - 0.5) * 10;
            var zOffset = (new Random().nextDouble() - 0.5) * 10;
            var randomLoc = new Location(world, center.getX() + xOffset, 100, center.getZ() + zOffset);
            var loc = new Location(world, randomLoc.getX(), randomLoc.toHighestLocation().getY() + 1, randomLoc.getZ());

            assert world != null;
            var spawnedEntity = (LivingEntity)world.spawnEntity(loc, entity);
            Objects.requireNonNull(spawnedEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(100);
            spawnedEntity.setHealth(100);

            switch (type) {
                case "armored":
                    Objects.requireNonNull(spawnedEntity.getEquipment()).setHelmet(new ItemStack(Material.IRON_HELMET));
                    break;
                case "elite":
                    Objects.requireNonNull(spawnedEntity.getEquipment()).setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                    spawnedEntity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                    spawnedEntity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
                    break;
                case "boss":
                    Objects.requireNonNull(spawnedEntity.getEquipment()).setHelmet(new ItemStack(Material.NETHERITE_HELMET));
                    spawnedEntity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                    spawnedEntity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
                    break;
            }
        }
    }
}
