package com.levelingsmp.listeners;

import com.levelingsmp.LevelingPluginSMP;
import com.levelingsmp.utils.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class PlayerKillListener implements Listener {

    private final Random random = new Random();

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer != null && killer != victim) {
            // Add 1 stat point to killer
            ItemStack point = ItemFactory.createStatPoint();
            killer.getInventory().addItem(point);
            killer.sendMessage("§aYou gained 1 Skill Point for killing " + victim.getName() + "!");

            // Handle victim losing 1 point (if they have any)
            List<ItemStack> inv = List.of(victim.getInventory().getContents());
            boolean removed = false;
            for (ItemStack item : inv) {
                if (item != null && ItemFactory.isStatPoint(item)) {
                    victim.getInventory().remove(item);
                    removed = true;
                    break;
                }
            }

            if (removed) {
                victim.sendMessage("§cYou lost 1 Skill Point!");
                victim.getWorld().dropItemNaturally(victim.getLocation(), ItemFactory.createStatPoint());
            }
        }
    }
}
