package com.jesse.cordsaver.GUI;

import com.jesse.cordsaver.Main;
import com.jesse.cordsaver.Utils.ConfigManager;
import com.jesse.cordsaver.Utils.YmlFileManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiListener implements Listener {
    private ItemStack clickedCoord;
    public static List<ItemStack> customizeBorderGuiItems = new ArrayList<>();

    @EventHandler
    public void onClick(InventoryClickEvent e){
        // Checking if the player is in the Coords menu GUI
        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.RED + ConfigManager.getCoordsMenuName()) && e.getCurrentItem() != null){
            e.setCancelled(true);

            // Getting the needed variables
            int clickedSlot = e.getRawSlot();
            World.Environment dimension;
            Player player = (Player) e.getWhoClicked();
            int coordCount = YmlFileManager.getCoordCount(player);
            GUI coordsGUI;

            // Checking if the player clicked on a coordinate and if yes it opens the Action menu
            clickedCoord = e.getCurrentItem();
            if (Main.coordDictionary.containsKey(clickedCoord)){
                GUI actionMenu = new GUI();
                player.openInventory(actionMenu.createActionGUI(player));
            }

            // Checking if the player clicked on one of the dimension tab and creating the corresponding GUI using the GUI class
            switch(clickedSlot){
                case 3:
                    dimension = World.Environment.NORMAL;
                    coordsGUI = new GUI();
                    player.openInventory(coordsGUI.createCoordGUI(player, dimension, coordCount));
                    break;
                case 4:
                    dimension = World.Environment.NETHER;
                    coordsGUI = new GUI();
                    player.openInventory(coordsGUI.createCoordGUI(player, dimension, coordCount));
                    break;
                case 5:
                    dimension = World.Environment.THE_END;
                    coordsGUI = new GUI();
                    player.openInventory(coordsGUI.createCoordGUI(player, dimension, coordCount));
                    break;
            }
        }

        // Checks if the player is in the action menu
        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.DARK_RED + ConfigManager.getActionMenuName()) && e.getCurrentItem() != null){
            e.setCancelled(true);

            // Getting needed variables
            Player player = (Player) e.getWhoClicked();
            int clickedCoordNumber = Main.coordDictionary.get(clickedCoord);

            // Checking if the player clicked on delete coordinate or broadcast coordinate
            switch(e.getRawSlot()){
                case 20:
                    deleteCoord(player, clickedCoordNumber);
                    Main.coordDictionary.clear(); // Needs to be cleared here otherwise this dictionary will keep growing (memory leak)
                    break;
                case 24:
                    broadcastCoord(player, clickedCoordNumber);
                    Main.coordDictionary.clear(); // Needs to be cleared here otherwise this dictionary will keep growing (memory leak)
                    break;
            }
        }

        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.BLUE + ConfigManager.getBorderCustomizationGuiName()) && e.getCurrentItem() != null){
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);
            ItemStack clickedItem = e.getCurrentItem();
            if (customizeBorderGuiItems.contains(clickedItem)){
                YmlFileManager.setBorderMaterial(player, clickedItem.getType(), ChatColor.GREEN + "The border has been changed!");
                player.closeInventory();
            }
        }
    }

    private void deleteCoord(Player player, int clickedCoord){
        // This method deletes a coordinate by copying all the coordinates below the chosen coordinates and putting one coordinate higher
        // After coping/pasting it deletes the last coordinate entirly from the yml file
        int coordCount = YmlFileManager.getCoordCount(player);
        int coordToCopy = clickedCoord + 1;
        int coordToPaste = clickedCoord;
        for (int i = 0; i < (coordCount - clickedCoord); i++){
            // Get the information to copy to the coordinate above it
            String tmpName = YmlFileManager.getCoordName(player, coordToCopy);
            Location tmpLocation = YmlFileManager.getCoordLocation(player, coordToCopy);
            coordToCopy++;

            // Puts the copied information one coordinate higher
            YmlFileManager.setCoordName(player, coordToPaste, tmpName);
            YmlFileManager.setCoordLocation(player, coordToPaste, tmpLocation, null);
            coordToPaste++;
        }

        YmlFileManager.deleteCoordPath(player, coordCount, ChatColor.RED + ConfigManager.getDeleteCoordinateMessage());
        coordCount--;
        YmlFileManager.setCoordCount(player, coordCount);
        player.closeInventory();
    }

    private void broadcastCoord(Player player, int clickedCoord){
        boolean broadcastToggle = ConfigManager.getBroadcastToggle();
        String coordName = YmlFileManager.getCoordName(player, clickedCoord);
        Location coordLocation = YmlFileManager.getCoordLocation(player, clickedCoord);
        int[] coordXYZ = YmlFileManager.getLocationXYZ(coordLocation);

        if (broadcastToggle){
            Bukkit.broadcastMessage(ChatColor.GREEN + coordName + ConfigManager.getPrintCoordinateMessage() + " x: "+ coordXYZ[0] + " y: " + coordXYZ[1] + " z: " + coordXYZ[2]);
            player.closeInventory();
        } else {
            player.sendMessage(ChatColor.GREEN + coordName + ConfigManager.getPrintCoordinateMessage() + " x: "+ coordXYZ[0] + " y: " + coordXYZ[1] + " z: " + coordXYZ[2]);
            player.closeInventory();
        }
    }
}
