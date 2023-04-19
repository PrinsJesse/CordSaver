package com.jesse.cordsaver;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class Main extends JavaPlugin implements Listener {

    static File cordsFile;
    public static HashMap<ItemStack, Integer> cordDictionary = new HashMap<>();
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("save").setExecutor(new SaveCommand());
        getCommand("save").setTabCompleter(new saveTabCompleter());
        getCommand("cordsmenu").setExecutor(new CordMenuCommand());
        Bukkit.getPluginManager().registerEvents(new CordMenuListener(this), this);
        Bukkit.getPluginManager().registerEvents(this, this);

        if (!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        cordsFile = new File(getDataFolder(), "cords.yml");
        if (!cordsFile.exists()) {
            try {
                cordsFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Can't load file!");
            }
        }

        YmlFileManager.startFileManager(cordsFile);
    }

    public static File getCordsFile(){
        // This methode is needed for the YmlFileManager
        return cordsFile;
    }

    @EventHandler
    public void onCommandSend(PlayerCommandSendEvent e) {
        // Removes all the plugin:command commands from the chat
        e.getCommands().removeIf(command -> command.contains(":"));
    }
}
