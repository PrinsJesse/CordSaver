package com.jesse.cordsaver.Commands;

import com.jesse.cordsaver.Utils.GuiManager;
import com.jesse.cordsaver.Utils.YmlFileManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class CoordMenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (commandSender instanceof Player){
            // Gets the needed variables for this command
            Player player = (Player) commandSender;
            int coordCount = YmlFileManager.getCoordCount(player.getName());
            World.Environment dimension = World.Environment.NORMAL;

            // Creates and opens the coordinate GUI
            player.openInventory(GuiManager.coordsGUI.createCoordGUI(player, dimension, coordCount));
            return true;
        }
        return false;
    }
}
