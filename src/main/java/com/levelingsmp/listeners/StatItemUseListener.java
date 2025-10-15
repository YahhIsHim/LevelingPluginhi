package com.levelingsmp.listeners;

import com.levelingsmp.LevelingPluginSMP;
import com.levelingsmp.utils.GUIManager;
import com.levelingsmp.utils.ItemFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class StatItemUseListener implements Listener {

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) return;

        // Stat point item opens GUI
        if (ItemFactory.isStatPoint(item)) {
            event.setCancelled(true);
            GUIManager.openLevelingGUI(player);
        }

        // Reset token resets stats
        if (ItemFactory.isResetToken(item)) {
            event.setCancelled(true);
            GUIManager.resetStats(player);
            player.sendMessage("§cAll your stats have been reset!");
        }
    }
}
