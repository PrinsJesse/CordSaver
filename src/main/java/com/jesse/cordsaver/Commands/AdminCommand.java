package com.jesse.cordsaver.Commands;

import com.jesse.cordsaver.Utils.GuiManager;
import com.jesse.cordsaver.Utils.YmlFileManager;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player){
            Player admin = (Player) commandSender;
            if (admin.hasPermission("coordSaver.admin")){
                if (args.length == 1){
                    String target = args[0];
                    GuiManager.admin_target.put(admin.getName(), target);
                    admin.openInventory(GuiManager.adminCoordsGUI.createAdminCoordGUI(admin, target, World.Environment.NORMAL, YmlFileManager.getCoordCount(target)));
                    return true;
                } else {
                    return false;
                }

            } else {
                admin.sendMessage(ChatColor.RED + "You don't have permission to use this!");
                return true;
            }
        }
        return false;
    }
}
