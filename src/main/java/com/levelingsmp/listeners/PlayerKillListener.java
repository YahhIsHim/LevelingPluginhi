package com.levelingsmp.listeners;

import com.levelingsmp.managers.StatManager;
import com.levelingsmp.utils.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerKillListener implements Listener {
    private final StatManager statManager;

    public PlayerKillListener(StatManager statManager) {
        this.statManager = statManager;
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        statManager.addSkillPoint(killer, 1);

        ItemStack point = ItemManager.createStatPoint();
        killer.getInventory().addItem(point);
        killer.sendMessage("§aYou gained a Skill Point!");

        Player victim = event.getEntity();
        statManager.addSkillPoint(victim, -1);
    }
}
