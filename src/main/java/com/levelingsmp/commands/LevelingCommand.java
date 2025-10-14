package com.levelingsmp.commands;

import com.levelingsmp.LevelingPluginSMP;
import com.levelingsmp.gui.LevelingGUI;
import com.levelingsmp.utils.ItemFactory;
import com.levelingsmp.utils.StatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelingCommand implements CommandExecutor {

    private final LevelingPluginSMP plugin;
    private final StatManager statManager;
    private final ItemFactory itemFactory;

    public LevelingCommand(LevelingPluginSMP plugin, StatManager statManager, ItemFactory itemFactory) {
        this.plugin = plugin;
        this.statManager = statManager;
        this.itemFactory = itemFactory;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("Only players can use this.");
            return true;
        }
        LevelingGUI.open(p);
        return true;
    }
}
