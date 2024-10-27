package org.cordell.com.anizottizombie.weapons;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum AmmunitionNames {
    Pistol("Pistol ammo"),
    Rifle("Rifle ammo"),
    Automate("Automate ammo"),
    MachineGun("MachineGun ammo"),
    AirSupport("AirSupport ammo"),
    RPG("RPG ammo");

    public final String name;
    AmmunitionNames(String name) {
        this.name = name;
    }

    private static final Map<String, AmmunitionNames> map;
    static {
        map = Arrays.stream(values()).collect(Collectors.toMap(e -> e.name, e -> e));
    }

    public static AmmunitionNames fromName(String value) {
        return Optional.ofNullable(map.get(value)).orElse(Pistol);
    }
}
