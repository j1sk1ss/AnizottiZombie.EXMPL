package org.cordell.com.anizottizombie.weapons;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.j1sk1ss.itemmanager.manager.Item;
import org.j1sk1ss.itemmanager.manager.Manager;


public abstract class Weapon {
    public Weapon(int damage, Ammunition ammunition, String name) {
        this.damage     = damage;
        this.ammunition = ammunition;
        this.name       = name;
    }

    @Getter private String name;
    protected final Ammunition ammunition;
    protected final int damage;
    protected ItemStack body;

    public static Weapon getFromItemStack(ItemStack item) {
        var weaponType = Manager.getStringFromContainer(item, "weapon-type");
        if (weaponType == null) return null;

        return null;
    }

    public static void giveNew2players(Player player, Weapon weapon) {
        var body = new Item(weapon.getName());
        Manager.setString2Container(body, weapon.getName(), "weapon-type");
        Manager.giveItems(body, player);
    }

    public abstract void fire(Player player);

    public void reload(Player player) {
        var roundsFromInventory = ammunition.takeFromPlayer(player);
        if (roundsFromInventory != 0) {
            if (ammunition.getBullets() > 0) ammunition.give2player(player);
            if (body != null && body.getItemMeta() instanceof Damageable damageableMeta) {
                damageableMeta.setDamage(0);
                body.setItemMeta(damageableMeta);
            }

            ammunition.setBullets(roundsFromInventory);
        }
    }
}
