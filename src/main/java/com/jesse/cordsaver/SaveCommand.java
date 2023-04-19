package com.jesse.cordsaver;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SaveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // /save Name_for_cords

        if (commandSender instanceof Player && args.length > 0){
            // Getting the needed variables
            Player player = (Player) commandSender;
            Location playerLocation = player.getLocation();

            // Builds the location name from everything user types after /save
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg).append(" ");
            }
            String cordName = builder.toString();

            //save the executors cords, the name of the cords and the dimension to a yml file
            int cordCount = YmlFileManager.getCordCount(player) + 1;
            YmlFileManager.setCordCount(player, cordCount);
            YmlFileManager.setCordName(player, cordCount, cordName);
            YmlFileManager.setCordLocation(player, cordCount, playerLocation, ChatColor.GREEN + "The location has been saved!");
        }
        return false;
    }
}
