package com.levelingsmp.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.levelingsmp.managers.StatManager;

public class LevelingGUI {
    public void open(Player player, StatManager statManager) {
        Inventory gui = Bukkit.createInventory(null, 27, "§8Leveling Menu");

        int strength = statManager.getStat(player, "strength");
        int speed = statManager.getStat(player, "speed");
        int vitality = statManager.getStat(player, "vitality");
        int skillPoints = statManager.getStatPoints(player);

        gui.setItem(11, createItem(Material.IRON_SWORD, "§cStrength §7(" + strength + ")", "§7Increases attack damage"));
        gui.setItem(13, createItem(Material.FEATHER, "§bSpeed §7(" + speed + ")", "§7Increases movement speed"));
        gui.setItem(15, createItem(Material.APPLE, "§aVitality §7(" + vitality + ")", "§7Increases max health"));
        gui.setItem(26, createItem(Material.EXPERIENCE_BOTTLE, "§eSkill Points: " + skillPoints, "§7Earn by killing players"));

        player.openInventory(gui);
    }

    private ItemStack createItem(Material mat, String name, String lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(java.util.Collections.singletonList(lore));
        item.setItemMeta(meta);
        return item;
    }
}
