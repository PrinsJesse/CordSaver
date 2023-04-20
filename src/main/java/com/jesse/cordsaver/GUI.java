package com.jesse.cordsaver;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GUI {
    private Player player;
    private int cordCount;
    private World.Environment clickedDimension;

    public GUI(Player player, World.Environment clickedDimension, int cordCount){
        this.player = player;
        this.clickedDimension = clickedDimension;
        this.cordCount = cordCount;
    }

    public Inventory createGUI(){
        Inventory cordsGUI = Bukkit.createInventory(player, 54, ChatColor.RED + ConfigManager.getCordsMenuName());

        // Putting the border in the GUI
        ItemStack border = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        for (int i : new int[]{0,1,2,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53}){
            cordsGUI.setItem(i, border);
        }

        // Putting the dimension selectors in the GUI
        cordsGUI.setItem(3, createGuiItem(Material.GRASS_BLOCK, ConfigManager.getOverworldSelectorName(), null));
        cordsGUI.setItem(4, createGuiItem(Material.NETHERRACK, ConfigManager.getNetherSelectorName(), null));
        cordsGUI.setItem(5, createGuiItem(Material.END_STONE, ConfigManager.getEndSelectorName(), null));

        for (int cordNumber = 1, slotNumber = 0; cordNumber <= cordCount; cordNumber++){
            // Getting all the needed variables
            ItemStack cordDisplayItem = null;
            String cordName = YmlFileManager.getCordName(player, cordNumber);
            Location cordLocation = YmlFileManager.getCordLocation(player, cordNumber);
            int[] cordXYZ = YmlFileManager.getLocationXYZ(cordLocation);
            String cordLore = ChatColor.AQUA + "x: " + cordXYZ[0] + " y: " + cordXYZ[1] + " z: " + cordXYZ[2];
            World.Environment cordDimension = cordLocation.getWorld().getEnvironment();
            int[] slotList = new int[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43}; // List of slots where I can put a cord

            // Checks if the dimension of the coordinate is the same as the dimension tab the player clicked on
            if (cordDimension == clickedDimension){

                // Creates the coordinate display item depending on the dimension the coordinate was saved in
                switch(clickedDimension){
                    case NORMAL:
                        cordDisplayItem = createGuiItem(Material.GRASS_BLOCK, cordName, cordLore);
                        cordsGUI.setItem(slotList[slotNumber], cordDisplayItem);
                        slotNumber++;
                        break;
                    case NETHER:
                        cordDisplayItem = createGuiItem(Material.NETHERRACK, cordName, cordLore);
                        cordsGUI.setItem(slotList[slotNumber], cordDisplayItem);
                        slotNumber++;
                        break;
                    case THE_END:
                        cordDisplayItem = createGuiItem(Material.END_STONE, cordName, cordLore);
                        cordsGUI.setItem(slotList[slotNumber], cordDisplayItem);
                        slotNumber++;
                        break;
                }
                Main.cordDictionary.put(cordDisplayItem, cordNumber);
            }
        }
        return cordsGUI;
    }

    private ItemStack createGuiItem(Material item, String displayName, String lore){
        // Creates an item taking a material, displayname and lore (lore null for no lore)
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
