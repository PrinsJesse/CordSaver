package com.jesse.cordsaver.GUI;

import com.jesse.cordsaver.Main;
import com.jesse.cordsaver.Utils.ConfigManager;
import com.jesse.cordsaver.Utils.YmlFileManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI {

    public Inventory createCoordGUI(Player player, World.Environment clickedDimension, int coordCount){
        Inventory coordsGUI = Bukkit.createInventory(player, 54, ChatColor.RED + ConfigManager.getCoordsMenuName());

        // Putting the border in the GUI
        ItemStack border = createGuiItem(YmlFileManager.getBorderMaterial(player), ChatColor.GRAY + "", null);
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
            String coordName = YmlFileManager.getCoordName(player, coordNumber);
            Location coordLocation = YmlFileManager.getCoordLocation(player, coordNumber);
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

    public Inventory createBorderCustomizationGUI(Player player){
        Material[] glassPainList = new Material[]{Material.BLACK_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE, Material.GRAY_STAINED_GLASS_PANE,
                Material.GREEN_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE, Material.BROWN_STAINED_GLASS_PANE, Material.CYAN_STAINED_GLASS_PANE,
                Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.LIGHT_GRAY_STAINED_GLASS_PANE, Material.MAGENTA_STAINED_GLASS_PANE,
                Material.ORANGE_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, Material.WHITE_STAINED_GLASS_PANE,
                Material.YELLOW_STAINED_GLASS_PANE, Material.PURPLE_STAINED_GLASS_PANE};
        Inventory customizationGUI = Bukkit.createInventory(player, 18, ChatColor.BLUE + ConfigManager.getBorderCustomizationGuiName());

        List<ItemStack> customizeBorderGuiItems = new ArrayList<>();
        for (int i = 0; i < 16; i++){
            ItemStack guiItem = createGuiItem(glassPainList[i],  "Press the color you want", null);
            customizationGUI.setItem(i, guiItem);
            customizeBorderGuiItems.add(guiItem);
        }
        GuiListener.customizeBorderGuiItems = customizeBorderGuiItems;

        return customizationGUI;
    }

    private ItemStack createGuiItem(Material item, String displayName, String lore){
        // Creates an item taking a material, displayname and lore (set lore to null for no lore)
        ItemStack guiItem = new ItemStack(item);
        ItemMeta guiItemMeta = guiItem.getItemMeta();
        guiItemMeta.setDisplayName(displayName);
        if (lore != null){
            guiItemMeta.setLore(Arrays.asList(lore));
        }
        guiItem.setItemMeta(guiItemMeta);
        return guiItem;
    }
}
