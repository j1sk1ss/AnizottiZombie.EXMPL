package org.cordell.com.anizottizombie.objects;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.cordell.com.anizottizombie.objects.zombies.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class ZombieHorde {
    public ZombieHorde(int zombieCount, Location spawn) {
        hpBar = Bukkit.createBossBar("Horde", BarColor.RED, BarStyle.SOLID);
        hpBar.setProgress(1.0);

        maxHordeHp = zombieCount;
        hordeHp = zombieCount;

        spawnLocation = spawn.toHighestLocation();

        var random = new Random();
        zombies = new ArrayList<>();
        spawnedZombies = new ArrayList<>();
        for (int i = 0; i < zombieCount; i++) {
            var option = random.nextInt(100);
            if (option < 50) {
                if (option < 40) zombies.add(new DefaultZombie(100, 1, .2));
                else zombies.add(new Archer(10, 1, .3));
            }
            else if (option < 60) {
                if (option < 55) zombies.add(new ArmoredZombie(250, 2, .2));
                else zombies.add(new ArmoredArcher(100, 1, .2));
            }
            else if (option < 70) zombies.add(new FatmanZombie(500, 2, .2));
            else if (option < 99) zombies.add(new LightZombie(5, 1, .35));
        }

        spawnZombies(zombieCount / 5);
        healthHorde();
        for (var player : Bukkit.getOnlinePlayers()) {
            hpBar.addPlayer(player);
        }
    }

    @Getter
    private final BossBar hpBar;
    private final double maxHordeHp;
    private double hordeHp;

    private final Location spawnLocation;
    private final List<BaseZombie> zombies;

    @Getter @Setter
    private List<BaseZombie> spawnedZombies;

    public void damage(int damage) {
        hordeHp -= damage;
        hpBar.setProgress(hordeHp / maxHordeHp);
        if (hordeHp > maxHordeHp) hordeHp = maxHordeHp;
        else if (hordeHp <= 0) {
            hpBar.removeAll();
            hordeHp = 0;
        }
    }

    public boolean spawnPart(int offset, int count) {
        if (offset == zombies.size()) return false;
        for (int i = Math.max(0, offset); i < Math.min(offset + count, zombies.size()); i++) {
            var world = Bukkit.getServer().getWorlds().get(0);

            var xOffset = (new Random().nextDouble() - 0.5) * 20;
            var zOffset = (new Random().nextDouble() - 0.5) * 20;
            var randomLoc = new Location(world, spawnLocation.getX() + xOffset, 100, spawnLocation.getZ() + zOffset);
            var loc = new Location(world, randomLoc.getX(), randomLoc.toHighestLocation().getY() + 1, randomLoc.getZ());

            assert world != null;
            zombies.get(i).spawn(loc);
            spawnedZombies.add(zombies.get(i));
        }

        return true;
    }

    private void spawnZombies(int partSize) {
        new BukkitRunnable() {
            private int offset = 0;

            @Override
            public void run() {
                System.out.println("Horde part: " + offset + " size: " + partSize);
                if (!spawnPart(offset, partSize)) this.cancel();
                offset += partSize;
            }
        }.runTaskTimer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("AnizottiZombie")), 0, 500);
    }

    private void healthHorde() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (spawnedZombies.isEmpty()) {
                    this.cancel();
                    return;
                }

                for (var entity : spawnedZombies) {
                    if (entity.getBody() == null) continue;
                    entity.getBody().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000, 1));
                }
            }
        }.runTaskTimer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("AnizottiZombie")), 0, 10000);
    }
}
