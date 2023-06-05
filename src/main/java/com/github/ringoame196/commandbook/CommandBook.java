package com.github.ringoame196.commandbook;

import com.github.ringoame196.commandbook.Commands.makecomb;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandBook extends JavaPlugin {

    private static JavaPlugin plugin;
    private Events events;
    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        getCommand("makecomb").setExecutor(new makecomb());
        this.events = new Events();
        Bukkit.getPluginManager().registerEvents(this.events,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        super.onDisable();
    }
    public static JavaPlugin getPlugin(){return plugin;}
}