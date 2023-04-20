package com.jesse.cordsaver;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CordMenuListener implements Listener {
    private ItemStack clickedCord;

    @EventHandler
    public void onClick(InventoryClickEvent e){
        // Checking if the player is in the Cords menu GUI
        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.RED + ConfigManager.getCordsMenuName()) && e.getCurrentItem() != null){
            e.setCancelled(true);

            // Getting the needed variables
            int clickedSlot = e.getRawSlot();
            World.Environment dimension;
            Player player = (Player) e.getWhoClicked();
            int cordCount = YmlFileManager.getCordCount(player);
            GUI cordsGUI;

            // Checking if the player clicked on a coordinate and if yes it opens the Action menu
            clickedCord = e.getCurrentItem();
            if (Main.cordDictionary.containsKey(clickedCord)){
                ActionMenu(player);
            }

            // Checking if the player clicked on one of the dimension tab and creating the corresponding GUI using the GUI class
            switch(clickedSlot){
                case 3:
                    dimension = World.Environment.NORMAL;
                    cordsGUI = new GUI(player, dimension, cordCount);
                    player.openInventory(cordsGUI.createGUI());
                    break;
                case 4:
                    dimension = World.Environment.NETHER;
                    cordsGUI = new GUI(player, dimension, cordCount);
                    player.openInventory(cordsGUI.createGUI());
                    break;
                case 5:
                    dimension = World.Environment.THE_END;
                    cordsGUI = new GUI(player, dimension, cordCount);
                    player.openInventory(cordsGUI.createGUI());
                    break;
            }
        }

        // Checks if the player is in the action menu
        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.DARK_RED + ConfigManager.getActionMenuName()) && e.getCurrentItem() != null){
            e.setCancelled(true);

            // Getting needed variables
            Player player = (Player) e.getWhoClicked();
            int clickedCordNumber = Main.cordDictionary.get(clickedCord);

            // Checking if the player clicked on delete coordinate or broadcast coordinate
            switch(e.getRawSlot()){
                case 20:
                    deleteCord(player, clickedCordNumber);
                    Main.cordDictionary.clear(); // Needs to be cleared here otherwise this dictionary will keep growing (memory leak)
                    break;
                case 24:
                    broadcastCord(player, clickedCordNumber);
                    Main.cordDictionary.clear(); // Needs to be cleared here otherwise this dictionary will keep growing (memory leak)
                    break;
            }
        }
    }

    private void ActionMenu(Player player){
        // Creates the actionmenu and opens it (I plan to intergrate this menu in the GUI class)
        Inventory inv = Bukkit.createInventory(player, 45, ChatColor.DARK_RED + ConfigManager.getActionMenuName());

        ItemStack border = new ItemStack(Material.LIME_STAINED_GLASS_PANE); // Border
        for (int i : new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44}){
            inv.setItem(i, border);
        }

        ItemStack deleteCord = new ItemStack(Material.BARRIER);
        ItemMeta deleteCordMeta = deleteCord.getItemMeta();
        deleteCordMeta.setDisplayName(ChatColor.RED + ConfigManager.getActionMenuDelete());
        deleteCord.setItemMeta(deleteCordMeta);
        inv.setItem(20, deleteCord);

        ItemStack printCords = new ItemStack(Material.DISPENSER);
        ItemMeta printCordsMeta = printCords.getItemMeta();
        printCordsMeta.setDisplayName(ChatColor.GREEN + ConfigManager.getActionMenuPrint());
        printCords.setItemMeta(printCordsMeta);
        inv.setItem(24, printCords);

        player.openInventory(inv);
    }


    private void deleteCord(Player player, int clickedCord){
        // This method deletes a coordinate by copying all the coordinates below the chosen coordinates and putting one coordinate higher
        // After coping/pasting it deletes the last coordinate entirly from the yml file
        int cordCount = YmlFileManager.getCordCount(player);
        int cordToCopy = clickedCord + 1;
        int cordToPaste = clickedCord;
        for (int i = 0; i < (cordCount - clickedCord); i++){
            // Get the information to copy to the coordinate above it
            String tmpName = YmlFileManager.getCordName(player, cordToCopy);
            Location tmpLocation = YmlFileManager.getCordLocation(player, cordToCopy);
            cordToCopy++;

            // Puts the copied information one coordinate higher
            YmlFileManager.setCordName(player, cordToPaste, tmpName);
            YmlFileManager.setCordLocation(player, cordToPaste, tmpLocation, null);
            cordToPaste++;
        }

        YmlFileManager.deleteCordPath(player, cordCount, ChatColor.RED + ConfigManager.getDeleteCoordinateMessage());
        cordCount--;
        YmlFileManager.setCordCount(player, cordCount);
        player.closeInventory();
    }

    private void broadcastCord(Player player, int clickedCord){
        boolean broadcastToggle = ConfigManager.getBroadcastToggle();
        String cordName = YmlFileManager.getCordName(player, clickedCord);
        Location cordLocation = YmlFileManager.getCordLocation(player, clickedCord);
        int[] cordXYZ = YmlFileManager.getLocationXYZ(cordLocation);

        if (broadcastToggle){
            Bukkit.broadcastMessage(ChatColor.GREEN + cordName + ConfigManager.getPrintCoordinateMessage() + " x: "+ cordXYZ[0] + " y: " + cordXYZ[1] + " z: " + cordXYZ[2]);
            player.closeInventory();
        } else {
            player.sendMessage(ChatColor.GREEN + cordName + ConfigManager.getPrintCoordinateMessage() + " x: "+ cordXYZ[0] + " y: " + cordXYZ[1] + " z: " + cordXYZ[2]);
            player.closeInventory();
        }
    }
}
