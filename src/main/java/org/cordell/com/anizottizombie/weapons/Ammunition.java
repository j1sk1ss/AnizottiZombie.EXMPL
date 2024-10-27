package org.cordell.com.anizottizombie.weapons;

import lombok.Getter;

import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;
import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.itemmanager.manager.Manager;


@Getter
public class Ammunition {
    public Ammunition(int bullets, String name, ItemStack body) {
        this.maxBullets = bullets;
        this.bullets = bullets;
        this.name = name;

        if (body != null) {
            if (Manager.getIntegerFromContainer(body, name) != -1) {
                this.bullets = Manager.getIntegerFromContainer(body, "bullets");
            }
        }
    }

    private final String name;
    private final int maxBullets;
    @Setter private int bullets;

    public void shoot(int amount) {
        bullets -= amount;
    }

    public void give2player(Player player) {
        giveNew2player(player, name, bullets);
    }

    public int takeFromPlayer(Player player) {
        for (var item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (Manager.getIntegerFromContainer(item, name) != -1) {
                var rounds = Manager.getIntegerFromContainer(item, "bullets");
                Manager.takeItems(item, player);
                return rounds;
            }
        }

        return 0;
    }

    public static void giveNew2player(Player player, String name, int bullets) {
        var ammo = new Item(name, "Rounds", Material.ARROW);

        Manager.setInteger2Container(ammo, 1, name);
        Manager.setInteger2Container(ammo, bullets, "bullets");

        Manager.giveItems(ammo, player);
    }
}
