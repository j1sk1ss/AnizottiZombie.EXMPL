package org.cordell.com.anizottizombie;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.cordell.com.anizottizombie.events.ZombieDied;
import org.cordell.com.anizottizombie.events.ZombieFall;
import org.cordell.com.anizottizombie.link.CommandManager;
import org.cordell.com.anizottizombie.objects.ZombieHorde;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public final class AnizottiZombie extends JavaPlugin {
    public static List<ZombieHorde> hordes;

    @Override
    public void onEnable() {
        hordes = new ArrayList<>();
        var command_manager = new CommandManager();
        for (var command : List.of("create_horde"))
            Objects.requireNonNull(getCommand(command)).setExecutor(command_manager);

        Bukkit.getPluginManager().registerEvents(new ZombieDied(), this);
        Bukkit.getPluginManager().registerEvents(new ZombieFall(), this);

        System.out.println("AnizottiZombie has been enabled!");
    }

    @Override
    public void onDisable() {
        for (var horde : hordes)
            horde.getHpBar().removeAll();

        System.out.println("AnizottiZombie has been disabled!");
    }
}
