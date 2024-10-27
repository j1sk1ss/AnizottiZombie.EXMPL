package org.cordell.com.anizottizombie.weapons.models;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.Damageable;
import org.cordell.com.anizottizombie.weapons.Ammunition;
import org.cordell.com.anizottizombie.weapons.AmmunitionNames;
import org.cordell.com.anizottizombie.weapons.Weapon;


public class Pistol extends Weapon {
    public Pistol(int damage, String name) {
        super(damage, new Ammunition(8, AmmunitionNames.Pistol.name, null), name);
    }

    @Override
    public void fire(Player player) {
        if (ammunition.getBullets() > 0) {
            ammunition.shoot(1);
            if (body != null && body.getItemMeta() instanceof Damageable damageableMeta) {
                int currentDamage = damageableMeta.getDamage();
                damageableMeta.setDamage(currentDamage + 1);
                body.setItemMeta(damageableMeta);
            }
        }
        else {
            // Need reload sound
        }
    }
}
