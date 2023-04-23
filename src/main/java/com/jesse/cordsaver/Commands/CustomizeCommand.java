package com.jesse.cordsaver.Commands;

import com.jesse.cordsaver.GUI.GUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CustomizeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            GUI customizationGUI = new GUI();

            switch (args[0]){
                case "border":
                    player.openInventory(customizationGUI.createBorderCustomizationGUI(player));
                    break;
                default:
                    player.sendMessage("Usage: /customizegui <argument>");
            }
        }

        return false;
    }
}
