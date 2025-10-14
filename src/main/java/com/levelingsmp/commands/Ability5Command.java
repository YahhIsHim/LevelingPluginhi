package com.levelingsmp.commands;

import com.levelingsmp.LevelingPluginSMP;
import com.levelingsmp.utils.WeaponAbilityManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Ability5Command implements CommandExecutor {
    private final LevelingPluginSMP plugin;
    private final WeaponAbilityManager manager;

    public Ability5Command(LevelingPluginSMP plugin, WeaponAbilityManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("Only players.");
            return true;
        }
        manager.activateAxeAbility(p);
        return true;
    }
}
