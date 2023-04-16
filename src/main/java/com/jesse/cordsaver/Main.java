package com.jesse.cordsaver;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin implements Listener {

    static File file;
    @Override
    public void onEnable() {
        getCommand("save").setExecutor(new SaveCommand());
        getCommand("cordsmenu").setExecutor(new CordMenuCommand());
        Bukkit.getPluginManager().registerEvents(new CordMenuListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this);

        file = new File(getDataFolder(), "cords.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Can't load file!");
            }
        }

        YmlFileManager.startFileManager(file);
    }

    public static File getCordsFile(){
        return file;
    }

    @EventHandler
    public void onCommandSend(PlayerCommandSendEvent e) {
        e.getCommands().removeIf(command -> command.contains(":"));
    }
}
