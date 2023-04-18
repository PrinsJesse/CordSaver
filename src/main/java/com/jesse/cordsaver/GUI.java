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
        Inventory cordsGUI = Bukkit.createInventory(player, 54, ChatColor.RED + "Cords menu");
        ItemStack border = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        for (int i : new int[]{0,1,2,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53}){
            cordsGUI.setItem(i, border);
        }

        // Set the dimension selectors
        ItemStack overworldSelector = new ItemStack(Material.GRASS_BLOCK);
        ItemStack netherSelector = new ItemStack(Material.NETHERRACK);
        ItemStack endSelector = new ItemStack(Material.END_STONE);
        ItemMeta overworldSelectorMeta = overworldSelector.getItemMeta();
        ItemMeta netherSelectorMeta = netherSelector.getItemMeta();
        ItemMeta endSelectorMeta = endSelector.getItemMeta();
        overworldSelectorMeta.setDisplayName("Your overworld coordinates");
        netherSelectorMeta.setDisplayName("Your nether coordinates");
        endSelectorMeta.setDisplayName("Your end coordinates");
        overworldSelector.setItemMeta(overworldSelectorMeta);
        netherSelector.setItemMeta(netherSelectorMeta);
        endSelector.setItemMeta(endSelectorMeta);
        cordsGUI.setItem(3, overworldSelector);
        cordsGUI.setItem(4, netherSelector);
        cordsGUI.setItem(5, endSelector);

        int slotNumber = 0;
        for (int cordNumber = 1; cordNumber <= cordCount; cordNumber++){
            ItemStack cordDisplay = null;
            String cordName = YmlFileManager.getCordName(player, cordNumber);
            Location cordLocation = YmlFileManager.getCordLocation(player, cordNumber);
            World.Environment cordDimension = cordLocation.getWorld().getEnvironment();
            int[] cordXYZ = YmlFileManager.getLocationXYZ(cordLocation);
            int[] slotList = new int[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43};

            if (cordDimension == clickedDimension){

                switch(clickedDimension){
                    case NORMAL:
                        cordDisplay = new ItemStack(Material.GRASS_BLOCK);
                        break;
                    case NETHER:
                        cordDisplay = new ItemStack(Material.NETHERRACK);
                        break;
                    case THE_END:
                        cordDisplay = new ItemStack(Material.END_STONE);
                        break;
                }

                if (cordDisplay != null){
                    ItemMeta cordDisplayMeta = cordDisplay.getItemMeta();
                    cordDisplayMeta.setDisplayName(cordName);
                    cordDisplayMeta.setLore(Arrays.asList(ChatColor.AQUA + "x: " + cordXYZ[0] + " y: " + cordXYZ[1] + " z: " + cordXYZ[2]));
                    cordDisplay.setItemMeta(cordDisplayMeta);

                    cordsGUI.setItem(slotList[slotNumber], cordDisplay);
                    slotNumber++;
                }

                Main.cordDictionary.put(cordDisplay, cordNumber);
            }
        }

        return cordsGUI;
    }

    public void openGUI(Inventory cordsGUI){
        player.openInventory(cordsGUI);
    }
}
