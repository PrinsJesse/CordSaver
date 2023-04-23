package com.jesse.cordsaver;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Main extends JavaPlugin implements Listener {

    public static HashMap<ItemStack, Integer> coordDictionary = new HashMap<>();
    @Override
    public void onEnable() {
        ConfigManager.startConfigManager(this);
        YmlFileManager.startYmlFileManager(this);


        getCommand("savecoords").setExecutor(new SaveCommand());
        getCommand("savecoords").setTabCompleter(new saveTabCompleter());
        getCommand("customizegui").setExecutor(new CustomizeCommand());
        getCommand("customizegui").setTabCompleter(new CustomizeTabCompleter());
        getCommand("coordsmenu").setExecutor(new CoordMenuCommand());
        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onCommandSend(PlayerCommandSendEvent e) {
        // Removes all the plugin:command commands from the chat
        e.getCommands().removeIf(command -> command.contains(":"));
    }
}
