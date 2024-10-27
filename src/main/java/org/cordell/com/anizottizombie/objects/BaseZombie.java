package org.cordell.com.anizottizombie.objects;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.cordell.com.anizottizombie.common.BreakBlocks;

import java.util.Objects;


public abstract class BaseZombie {
    public BaseZombie(int hp, double size, double speed) {
        this.hp = hp;
        this.size = size;
        this.speed = speed;
    }

    protected int hp;
    protected double size;
    protected double speed;

    @Getter
    private LivingEntity body;

    public void spawn(Location loc) {
        var world = Bukkit.getServer().getWorlds().get(0);
        var entity = (LivingEntity)world.spawnEntity(loc, getType());

        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(10);
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(speed);
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_SCALE)).setBaseValue(size);
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(hp);
        entity.setHealth(hp);

        Objects.requireNonNull(entity.getEquipment()).setHelmet(getHelmet());
        entity.getEquipment().setChestplate(getChestplate());
        entity.getEquipment().setItemInMainHand(getItemInMainHand());

        body = entity;
        behavior();
    }

    protected abstract EntityType getType();

    protected abstract ItemStack getHelmet();

    protected abstract ItemStack getChestplate();

    protected abstract ItemStack getItemInMainHand();

    public abstract void onDied();

    private void behavior() {
        if (getBody() instanceof Zombie zombie) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (body == null) {
                        this.cancel();
                        return;
                    }

                    for (var player : Bukkit.getOnlinePlayers()) {
                        if (followAndBreakBlocks(zombie, player)) return;
                    }
                }
            }.runTaskTimer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("AnizottiZombie")), 0L, 20L);
        }
    }

    private boolean followAndBreakBlocks(Zombie entity, Player target) {
        var playerLocation = target.getLocation();
        var zombieLocation = entity.getLocation();

        if (zombieLocation.distance(playerLocation) <= 400) {
            entity.setTarget(target);
            var direction = playerLocation.toVector().subtract(zombieLocation.toVector()).normalize();
            var blockLocation = zombieLocation.clone();

            if (playerLocation.getY() > zombieLocation.getY() + 2 && zombieLocation.distance(playerLocation) < 5) {
                if (entity.isOnGround()) {
                    entity.setVelocity(new Vector(0, (playerLocation.getY() - zombieLocation.getY()) / 3, 0));
                }
            }

            blockLocation.add(direction);
            if (!blockLocation.getBlock().getType().isAir())
                breakBlockWithDelay(entity, blockLocation.getBlock(), BreakBlocks.BLOCK_BREAK_DELAY.getOrDefault(blockLocation.getBlock().getType(), 100L));

            return true;
        }

        return false;
    }

    private void breakBlockWithDelay(Zombie zombie, Block block, long delay) {
        if (block.getType() != Material.AIR) {
            zombie.setAI(false);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (zombie.getHealth() > 0) {
                        block.setType(Material.AIR);
                        block.getWorld().playSound(block.getLocation(), Sound.BLOCK_STONE_BREAK, 1.0f, 1.0f);
                        block.getWorld().spawnParticle(
                                Particle.BLOCK, block.getLocation().add(0.5, 0.5, 0.5), 100, block.getBlockData()
                        );
                    }

                    zombie.setAI(true);
                }
            }.runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("AnizottiZombie")), delay);
        }
    }
}
