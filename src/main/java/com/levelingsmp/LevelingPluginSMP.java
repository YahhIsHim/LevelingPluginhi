package com.levelingsmp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LevelingPluginSMP extends JavaPlugin {

    private static LevelingPluginSMP instance;

    @Override
    public void onEnable() {
        instance = this;

        // Save default configs if not exist
        saveDefaultConfig();
        saveResource("data.yml", false);

        // Register commands
        getCommand("leveling").setExecutor(new commands.LevelingCommand());
        getCommand("levelinggive").setExecutor(new commands.LevelingGiveCommand());
        getCommand("ability1").setExecutor(new commands.Ability1Command());
        getCommand("ability2").setExecutor(new commands.Ability2Command());
        getCommand("ability3").setExecutor(new commands.Ability3Command());
        getCommand("ability4").setExecutor(new commands.Ability4Command());

        // Register events
        getServer().getPluginManager().registerEvents(new listeners.PlayerKillListener(), this);
        getServer().getPluginManager().registerEvents(new listeners.StatItemUseListener(), this);
        getServer().getPluginManager().registerEvents(new listeners.AbilityListener(), this);

        getLogger().info("✅ LevelingPluginSMP has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("❌ LevelingPluginSMP has been disabled!");
    }

    public static LevelingPluginSMP getInstance() {
        return instance;
    }
}


