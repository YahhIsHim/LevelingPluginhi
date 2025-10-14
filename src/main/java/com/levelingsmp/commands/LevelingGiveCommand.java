package com.levelingsmp.commands;

import com.levelingsmp.LevelingPluginSMP;
import com.levelingsmp.utils.ItemFactory;
import com.levelingsmp.utils.StatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class LevelingGiveCommand implements CommandExecutor {

    private final LevelingPluginSMP plugin;
    private final ItemFactory itemFactory;
    private final StatManager statManager;

    public LevelingGiveCommand(LevelingPluginSMP plugin, ItemFactory itemFactory, StatManager statManager) {
        this.plugin = plugin;
        this.itemFactory = itemFactory;
        this.statManager = statManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("Only players.");
            return true;
        }
        if (!p.isOp()) {
            p.sendMessage("You must be OP to use this command.");
            return true;
        }
        p.getInventory().addItem(itemFactory.createStatPoint());
        p.getInventory().addItem(itemFactory.createResetToken());
        p.getInventory().addItem(itemFactory.createStrengthSword());
        p.getInventory().addItem(itemFactory.createSpeedAxe());
        p.getInventory().addItem(itemFactory.createVitalityTrident());
        p.sendMessage("Given test items.");
        return true;
    }
}
