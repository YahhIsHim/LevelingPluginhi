package com.levelingsmp.listeners;

import com.levelingsmp.gui.GUIManager;
import com.levelingsmp.utils.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class StatItemUseListener implements Listener {
    private final GUIManager guiManager;

    public StatItemUseListener(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null) return;

        if (ItemManager.isStatPoint(event.getItem())) {
            guiManager.openLevelingGUI(player);
            event.setCancelled(true);
        }

        if (ItemManager.isResetToken(event.getItem())) {
            guiManager.openLevelingGUI(player);
            event.setCancelled(true);
        }
    }
}
