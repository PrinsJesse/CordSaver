package com.jesse.cordsaver.GUI;

import com.jesse.cordsaver.Utils.ConfigManager;
import com.jesse.cordsaver.Utils.YmlFileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.jesse.cordsaver.Utils.GuiManager.createGuiItem;

public class ActionGUI {

    public Inventory createActionGUI(Player player){
        Inventory actionMenu = Bukkit.createInventory(player, 45, ChatColor.DARK_RED + ConfigManager.getActionMenuName());

        ItemStack border = createGuiItem(YmlFileManager.getBorderMaterial(player), ChatColor.GRAY + "", null); // Border
        for (int i : new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44}){
            actionMenu.setItem(i, border);
        }

        actionMenu.setItem(20, createGuiItem(Material.BARRIER, ChatColor.RED + ConfigManager.getActionMenuDelete(), null));
        actionMenu.setItem(24, createGuiItem(Material.DISPENSER, ChatColor.GREEN + ConfigManager.getActionMenuPrint(), null));

        return actionMenu;
    }

}
