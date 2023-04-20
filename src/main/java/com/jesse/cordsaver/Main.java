package com.jesse.cordsaver;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Main extends JavaPlugin implements Listener {

    public static HashMap<ItemStack, Integer> cordDictionary = new HashMap<>();
    @Override
    public void onEnable() {
        new ConfigManager(this);
        new YmlFileManager(this);

        getCommand("save").setExecutor(new SaveCommand());
        getCommand("save").setTabCompleter(new saveTabCompleter());
        getCommand("cordsmenu").setExecutor(new CordMenuCommand());
        Bukkit.getPluginManager().registerEvents(new CordMenuListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onCommandSend(PlayerCommandSendEvent e) {
        // Removes all the plugin:command commands from the chat
        e.getCommands().removeIf(command -> command.contains(":"));
    }
}
