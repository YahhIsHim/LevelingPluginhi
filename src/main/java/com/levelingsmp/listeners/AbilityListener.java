package com.levelingsmp.listeners;

import com.levelingsmp.utils.WeaponAbilityManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class AbilityListener implements Listener {

    @EventHandler
    public void onAbilityActivate(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null) return;

        WeaponAbilityManager.tryActivateAbility(player, item);
    }
}
