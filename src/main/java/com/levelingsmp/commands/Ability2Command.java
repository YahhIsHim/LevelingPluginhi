package com.levelingsmp.commands;

import com.levelingsmp.LevelingPluginSMP;
import com.levelingsmp.utils.StatAbilityManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ability2Command implements CommandExecutor {
    private final LevelingPluginSMP plugin;
    private final StatAbilityManager manager;

    public Ability2Command(LevelingPluginSMP plugin, StatAbilityManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("Only players.");
            return true;
        }
        manager.activateSpeedAbility(p);
        return true;
    }
}
