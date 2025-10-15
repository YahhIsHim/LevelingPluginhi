package com.levelingsmp.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {

    public static ItemStack createStatPoint() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aSkill Point");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createResetToken() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cReset Token");
        item.setItemMeta(meta);
        return item;
    }

    public static boolean isStatPoint(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return "§aSkill Point".equals(item.getItemMeta().getDisplayName());
    }

    public static boolean isResetToken(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return "§cReset Token".equals(item.getItemMeta().getDisplayName());
    }
}
