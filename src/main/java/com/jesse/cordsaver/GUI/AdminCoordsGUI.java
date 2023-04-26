package com.jesse.cordsaver.GUI;

import com.jesse.cordsaver.Main;
import com.jesse.cordsaver.Utils.ConfigManager;
import com.jesse.cordsaver.Utils.YmlFileManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.jesse.cordsaver.Utils.GuiManager.createGuiItem;

public class AdminCoordsGUI {
    public Inventory createAdminCoordGUI(Player admin, String target, World.Environment clickedDimension, int coordCount){
        Inventory coordsGUI = Bukkit.createInventory(admin, 54, ChatColor.LIGHT_PURPLE + ConfigManager.getAdminCoordsMenuName() + target);

        // Putting the border in the GUI
        ItemStack border = createGuiItem(YmlFileManager.getBorderMaterial(admin), ChatColor.GRAY + "", null);
        for (int i : new int[]{0,1,2,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53}){
            coordsGUI.setItem(i, border);
        }

        // Putting the dimension selectors in the GUI
        coordsGUI.setItem(3, createGuiItem(Material.GRASS_BLOCK, ConfigManager.getOverworldSelectorName(), null));
        coordsGUI.setItem(4, createGuiItem(Material.NETHERRACK, ConfigManager.getNetherSelectorName(), null));
        coordsGUI.setItem(5, createGuiItem(Material.END_STONE, ConfigManager.getEndSelectorName(), null));

        for (int coordNumber = 1, slotNumber = 0; coordNumber <= coordCount; coordNumber++){
            // Getting all the needed variables
            ItemStack coordDisplayItem = null;
            String coordName = YmlFileManager.getCoordName(target, coordNumber);
            Location coordLocation = YmlFileManager.getCoordLocation(target, coordNumber);
            int[] coordXYZ = YmlFileManager.getLocationXYZ(coordLocation);
            String coordLore = ChatColor.AQUA + "x: " + coordXYZ[0] + " y: " + coordXYZ[1] + " z: " + coordXYZ[2];
            World.Environment coordDimension = coordLocation.getWorld().getEnvironment();
            int[] slotList = new int[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43}; // List of slots where I can put a coord

            // Checks if the dimension of the coordinate is the same as the dimension tab the player clicked on
            if (coordDimension == clickedDimension){

                // Creates the coordinate display item depending on the dimension the coordinate was saved in
                switch(clickedDimension){
                    case NORMAL:
                        coordDisplayItem = createGuiItem(Material.GRASS_BLOCK, coordName, coordLore);
                        coordsGUI.setItem(slotList[slotNumber], coordDisplayItem);
                        slotNumber++;
                        break;
                    case NETHER:
                        coordDisplayItem = createGuiItem(Material.NETHERRACK, coordName, coordLore);
                        coordsGUI.setItem(slotList[slotNumber], coordDisplayItem);
                        slotNumber++;
                        break;
                    case THE_END:
                        coordDisplayItem = createGuiItem(Material.END_STONE, coordName, coordLore);
                        coordsGUI.setItem(slotList[slotNumber], coordDisplayItem);
                        slotNumber++;
                        break;
                }
                Main.coordDictionary.put(coordDisplayItem, coordNumber);
            }
        }
        return coordsGUI;
    }

}
