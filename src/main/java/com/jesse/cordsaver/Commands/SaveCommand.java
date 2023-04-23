package com.jesse.cordsaver.Commands;

import com.jesse.cordsaver.Utils.ConfigManager;
import com.jesse.cordsaver.Utils.YmlFileManager;
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
        // /save Name_for_coords

        if (commandSender instanceof Player && args.length > 0){
            // Getting the needed variables
            Player player = (Player) commandSender;
            Location playerLocation = player.getLocation();

            // Builds the location name from everything user types after /save
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg).append(" ");
            }
            String coordName = builder.toString();

            //save the executors coords, the name of the coords and the dimension to a yml file
            int coordCount = YmlFileManager.getCoordCount(player) + 1;
            YmlFileManager.setCoordCount(player, coordCount);
            YmlFileManager.setCoordName(player, coordCount, coordName);
            YmlFileManager.setCoordLocation(player, coordCount, playerLocation, ChatColor.GREEN + ConfigManager.getSaveCoordinateMessage());
        }
        return false;
    }
}
