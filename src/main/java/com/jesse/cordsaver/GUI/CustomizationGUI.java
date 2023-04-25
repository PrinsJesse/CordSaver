package com.jesse.cordsaver.GUI;

import com.jesse.cordsaver.Utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.jesse.cordsaver.Utils.GuiManager.createGuiItem;

public class CustomizationGUI {

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

}
