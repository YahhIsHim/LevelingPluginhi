package com.levelingsmp.listeners;

import com.levelingsmp.utils.WeaponAbilityManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class AbilityListener implements Listener {
    private final WeaponAbilityManager abilityManager;

    public AbilityListener(WeaponAbilityManager abilityManager) {
        this.abilityManager = abilityManager;
    }

    @EventHandler
    public void onAbilityUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand() == null) return;

        abilityManager.tryActivateAbility(player, player.getInventory().getItemInMainHand());
    }
}
