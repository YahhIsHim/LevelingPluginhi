package com.levelingsmp.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WeaponAbilityManager {
    public void tryActivateAbility(Player player, ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;
        String name = item.getItemMeta().getDisplayName();

        if (name.contains("§cPower Sword")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
            player.sendMessage("§cPower Surge activated!");
        } else if (name.contains("§bSwift Blade")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
            player.sendMessage("§bSpeed Burst activated!");
        } else if (name.contains("§aLife Staff")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
            player.sendMessage("§aHealing Aura activated!");
        }
    }
}
