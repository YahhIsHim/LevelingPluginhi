package com.levelingsmp;

import com.levelingsmp.commands.*;
import com.levelingsmp.events.PlayerEvents;
import com.levelingsmp.gui.LevelingGUI;
import com.levelingsmp.utils.ItemFactory;
import com.levelingsmp.utils.StatManager;
import com.levelingsmp.utils.StatAbilityManager;
import com.levelingsmp.utils.WeaponAbilityManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LevelingPluginSMP extends JavaPlugin {

    private static LevelingPluginSMP instance;
    private StatManager statManager;
    private ItemFactory itemFactory;
    private StatAbilityManager statAbilityManager;
    private WeaponAbilityManager weaponAbilityManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        // create managers
        statManager = new StatManager(this);
        itemFactory = new ItemFactory(this);
        statAbilityManager = new StatAbilityManager(this, statManager);
        weaponAbilityManager = new WeaponAbilityManager(this);

        // load data
        statManager.load();

        // register commands
        getCommand("leveling").setExecutor(new LevelingCommand(this, statManager, itemFactory));
        getCommand("levelinggive").setExecutor(new LevelingGiveCommand(this, itemFactory, statManager));
        getCommand("ability1").setExecutor(new Ability1Command(this, statAbilityManager));
        getCommand("ability2").setExecutor(new Ability2Command(this, statAbilityManager));
        getCommand("ability3").setExecutor(new Ability3Command(this, statAbilityManager));
        getCommand("ability4").setExecutor(new Ability4Command(this, weaponAbilityManager));
        getCommand("ability5").setExecutor(new Ability5Command(this, weaponAbilityManager));
        getCommand("ability6").setExecutor(new Ability6Command(this, weaponAbilityManager));

        // register events
        getServer().getPluginManager().registerEvents(new PlayerEvents(this, statManager, itemFactory, statAbilityManager, weaponAbilityManager), this);
        getServer().getPluginManager().registerEvents(new LevelingGUI(this, statManager, itemFactory), this);

        getLogger().info("LevelingPluginSMP enabled.");
    }

    @Override
    public void onDisable() {
        statManager.save();
        getLogger().info("LevelingPluginSMP disabled.");
    }

    public static LevelingPluginSMP getInstance() {
        return instance;
    }

    public StatManager getStatManager() { return statManager; }
    public ItemFactory getItemFactory() { return itemFactory; }
    public StatAbilityManager getStatAbilityManager() { return statAbilityManager; }
    public WeaponAbilityManager getWeaponAbilityManager() { return weaponAbilityManager; }
}

