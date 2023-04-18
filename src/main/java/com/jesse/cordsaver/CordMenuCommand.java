package com.jesse.cordsaver;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class CordMenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (commandSender instanceof Player){
            // Gets the needed variables for this command
            Player player = (Player) commandSender;
            int cordCount = YmlFileManager.getCordCount(player);
            World.Environment dimension = World.Environment.NORMAL;

            GUI cordsGUI = new GUI(player, dimension, cordCount);
            cordsGUI.openGUI(cordsGUI.createGUI());
        }
        return false;
    }
}
