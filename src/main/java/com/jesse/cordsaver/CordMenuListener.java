package com.jesse.cordsaver;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CordMenuListener implements Listener {
    int clickedSlot = 0;

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.RED + "Cords menu") && e.getCurrentItem() != null){
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();
            ItemStack currentItem = e.getCurrentItem();
            if (CordMenuCommand.cordItemstacks.contains(currentItem)){
                clickedSlot = e.getRawSlot();
                ActionMenu(player);
            }
        }

        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.RED + "Action menu") && e.getCurrentItem() != null){
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);
            int[] translateClickedSlotToCord =
                    new int[]{0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,6,7,0,0,8,9,10,11,12,13,14,0,0,15,16,17,18,19,20,21,0,0,22,23,24,25,26,27,28};
            int clickedCord = translateClickedSlotToCord[clickedSlot];

            switch(e.getRawSlot()){
                case 20:
                    deleteCord(player, clickedCord);
                    break;
                case 24:
                    broadCastCord(player, clickedCord);
                    break;
            }
        }
    }

    private void ActionMenu(Player player){
        Inventory inv = Bukkit.createInventory(player, 45, ChatColor.RED + "Action menu");

        ItemStack border = new ItemStack(Material.LIME_STAINED_GLASS_PANE); // Border
        for (int i : new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44}){
            inv.setItem(i, border);
        }

        ItemStack deleteCord = new ItemStack(Material.BARRIER);
        ItemMeta deleteCordMeta = deleteCord.getItemMeta();
        deleteCordMeta.setDisplayName(ChatColor.RED + "Delete this coordinate");
        deleteCord.setItemMeta(deleteCordMeta);
        inv.setItem(20, deleteCord);

        ItemStack printCords = new ItemStack(Material.DISPENSER);
        ItemMeta printCordsMeta = printCords.getItemMeta();
        printCordsMeta.setDisplayName(ChatColor.GREEN + "Print the coordinates to the chat");
        printCords.setItemMeta(printCordsMeta);
        inv.setItem(24, printCords);

        player.openInventory(inv);
    }

    private void deleteCord(Player player, int clickedCord){
        int cordCount = YmlFileManager.getCordCount(player);
        int cordToCopy = clickedCord + 1;
        int cordToPaste = clickedCord;
        for (int i = 0; i < (cordCount - clickedCord); i++){
            // Get the information to copy
            String tmpName = YmlFileManager.getCordName(player, cordToCopy);
            Location tmpLocation = YmlFileManager.getCordLocation(player, cordToCopy);
            cordToCopy++;

            // Set the information
            YmlFileManager.setCordName(player, cordToPaste, tmpName);
            YmlFileManager.setCordLocation(player, cordToPaste, tmpLocation);
            cordToPaste++;
        }

        YmlFileManager.deleteCordPath(player, cordCount);
        cordCount--;
        YmlFileManager.setCordCount(player, cordCount);
        player.closeInventory();
    }

    private void broadCastCord(Player player, int clickedCord){
        String cordName = YmlFileManager.getCordName(player, clickedCord);
        Location cordLocation = YmlFileManager.getCordLocation(player, clickedCord);
        int[] cordXYZ = YmlFileManager.getLocationXYZ(cordLocation);
        Bukkit.broadcastMessage(ChatColor.GREEN + cordName + "is at" + " x: "+ cordXYZ[0] + " y: " + cordXYZ[1] + " z: " + cordXYZ[2]);
        player.closeInventory();
    }
}
