package com.jesse.cordsaver;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class YmlFileManager {
    // Manager to set/get all the different fields in the yml file
    private static YamlConfiguration modifyFile;
    private static File coordsFile;

    public static void startYmlFileManager(Main main){
        YmlFileManager.coordsFile = new File(main.getDataFolder(), "cords.yml");
        if (!YmlFileManager.coordsFile.exists()) {
            try {
                YmlFileManager.coordsFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Can't load file!");
            }
        }
        YmlFileManager.modifyFile = YamlConfiguration.loadConfiguration(YmlFileManager.coordsFile);
    }

    public static void setCoordCount(Player player, int CoordCount){
        String playerName = player.getName();
        modifyFile.set(playerName + ".CordCount", CoordCount);
        saveFile(coordsFile, null, player);
    }

    public static int getCoordCount(Player player){
        String playerName = player.getName();
        return modifyFile.getInt(playerName + ".CordCount");
    }

    public static void setCoordLocation(Player player, int coordNumber, Location location, String message){
        String playerName = player.getName();
        modifyFile.set(playerName + ".cord" + coordNumber + ".Location", location);
        saveFile(coordsFile, message, player);
    }

    public static Location getCoordLocation(Player player, int coordNumber){
        String playerName = player.getName();
        return modifyFile.getLocation(playerName + ".cord" + coordNumber + ".Location");
    }

    public static void setCoordName(Player player, int coordNumber, String coordName){
        String playerName = player.getName();
        modifyFile.set(playerName + ".cord" + coordNumber + ".name", coordName);
        saveFile(coordsFile, null, player);
    }

    public static String getCoordName(Player player, int coordNumber){
        String playerName = player.getName();
        return modifyFile.getString(playerName + ".cord" + coordNumber + ".name");
    }

    public static Material getBorderMaterial(Player player){
        String playerName = player.getName();
        String borderMaterialString = modifyFile.getString(playerName + ".borderMaterial");
        if (borderMaterialString != null){
            return Material.valueOf(borderMaterialString);
        } else {
            return Material.LIME_STAINED_GLASS_PANE;
        }
    }

    public static void setBorderMaterial(Player player, Material material, String message){
        String playerName = player.getName();
        modifyFile.set(playerName + ".borderMaterial", material.toString());
        saveFile(coordsFile, message, player);
    }

    public static void deleteCoordPath(Player player, int coordNumber, String message){
        String playerName = player.getName();
        modifyFile.set(playerName + ".cord" + coordNumber, null);
        saveFile(coordsFile, message, player);
    }

    public static int[] getLocationXYZ(Location coordLocation){
        int[] locationXYZ = new int[3];
        locationXYZ[0] = (int) coordLocation.getX();
        locationXYZ[1] = (int) coordLocation.getY();
        locationXYZ[2] = (int) coordLocation.getZ();
        return locationXYZ;
    }

    private static void saveFile(File file, String message, Player player){
        try {
            modifyFile.save(file);
            if (message != null){
                player.sendMessage(message);
            }
        } catch (IOException error) {
            System.out.println("Couldn't save file");
        }
    }
}
