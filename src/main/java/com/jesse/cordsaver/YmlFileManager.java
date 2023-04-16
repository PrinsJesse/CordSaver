package com.jesse.cordsaver;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class YmlFileManager {
    private static YamlConfiguration modifyFile;
    private final static File cordsFile = Main.getCordsFile();

    public static void startFileManager(File file){
        modifyFile = YamlConfiguration.loadConfiguration(file);
    }

    public static void setCordCount(Player player, int CordCount){
        String playerName = player.getName();
        modifyFile.set(playerName + ".CordCount", CordCount);
        saveFile(cordsFile, null, player);
    }

    public static int getCordCount(Player player){
        String playerName = player.getName();
        return modifyFile.getInt(playerName + ".CordCount");
    }

    public static void setCordLocation(Player player, int cordNumber, Location location){
        String playerName = player.getName();
        modifyFile.set(playerName + ".cord" + cordNumber + ".Location", location);
        saveFile(cordsFile, ChatColor.GREEN + "The location has been saved!", player);
    }

    public static Location getCordLocation(Player player, int cordNumber){
        String playerName = player.getName();
        return modifyFile.getLocation(playerName + ".cord" + cordNumber + ".Location");
    }

    public static void setCordName(Player player, int cordNumber, String cordName){
        String playerName = player.getName();
        modifyFile.set(playerName + ".cord" + cordNumber + ".name", cordName);
        saveFile(cordsFile, null, player);
    }

    public static String getCordName(Player player, int cordNumber){
        String playerName = player.getName();
        return modifyFile.getString(playerName + ".cord" + cordNumber + ".name");
    }

    public static void deleteCordPath(Player player, int cordNumber){
        String playerName = player.getName();
        modifyFile.set(playerName + ".cord" + cordNumber, null);
        saveFile(cordsFile, ChatColor.RED + "The coordinate has been deleted", player);
    }

    public static int[] getLocationXYZ(Location cordLocation){
        int[] locationXYZ = new int[3];
        locationXYZ[0] = (int) cordLocation.getX();
        locationXYZ[1] = (int) cordLocation.getY();
        locationXYZ[2] = (int) cordLocation.getZ();
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
