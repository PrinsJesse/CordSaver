package com.jesse.cordsaver;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public class CordMenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (commandSender instanceof Player){
            // Gets the needed variables for this command
            Player player = (Player) commandSender;
            int[] slotList = new int[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43};
            ItemStack cordDisplay = null;
            int cordCount = YmlFileManager.getCordCount(player);

            // Create inventory for the menu
            Inventory inv = Bukkit.createInventory(player, 54, ChatColor.RED + "Cords menu");

            // Create items for the menu and put them in the inventory
            ItemStack border = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            for (int i : new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53}){
                inv.setItem(i, border);
            }

            for (int cordNumber = 1; cordNumber < cordCount + 1; cordNumber++){ // starts at one because naming of cords starts at 1
                String cordName = YmlFileManager.getCordName(player, cordNumber);
                Location cordLocation = YmlFileManager.getCordLocation(player, cordNumber);
                World.Environment dimension = cordLocation.getWorld().getEnvironment();
                int[] cordXYZ = YmlFileManager.getLocationXYZ(cordLocation);

                switch(dimension){
                    case NORMAL:
                        cordDisplay = new ItemStack(Material.GRASS_BLOCK);
                        break;
                    case NETHER:
                        cordDisplay = new ItemStack(Material.NETHERRACK);
                        break;
                    case THE_END:
                        cordDisplay = new ItemStack(Material.END_STONE);
                        break;
                    default:
                        System.out.println("Cord has no dimension field");
                }

                if (cordDisplay != null){
                    ItemMeta cordDisplayMeta = cordDisplay.getItemMeta();
                    cordDisplayMeta.setDisplayName(cordName);
                    cordDisplayMeta.setLore(Arrays.asList(ChatColor.AQUA + "x: " + cordXYZ[0] + " y: " + cordXYZ[1] + " z: " + cordXYZ[2]));
                    cordDisplay.setItemMeta(cordDisplayMeta);

                    inv.setItem(slotList[cordNumber - 1], cordDisplay);
                }
            }

            player.openInventory(inv);
        }
        return false;
    }
}
