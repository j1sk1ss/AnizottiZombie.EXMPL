package org.cordell.com.anizottizombie.link;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cordell.com.anizottizombie.AnizottiZombie;
import org.cordell.com.anizottizombie.objects.ZombieHorde;
import org.jetbrains.annotations.NotNull;


public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equals("create_horde")) {
            for (var world : Bukkit.getWorlds()) {
                world.setStorm(true);
                world.setThundering(true);
                world.setWeatherDuration(Integer.MAX_VALUE);
            }

            var count = Integer.parseInt(args[0]);
            AnizottiZombie.hordes.add(new ZombieHorde(count, ((Player) commandSender).getLocation()));
            return true;
        }

        return false;
    }
}
