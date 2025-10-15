package com.levelingsmp.gui;

import com.levelingsmp.utils.ItemFactory;
import com.levelingsmp.utils.StatManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LevelingGUI {

    public static final String GUI_TITLE = "§8Player Leveling";

    public static void open(Player player, StatManager statManager) {
        Inventory inv = Bukkit.createInventory(null, 27, GUI_TITLE);

        int strength = statManager.getStat(player, "strength");
        int speed = statManager.getStat(player, "speed");
        int vitality = statManager.getStat(player, "vitality");
        int points = statManager.getStatPoints(player);

        inv.setItem(11, createStatItem(Material.NETHERITE_SWORD, "§cStrength", strength,
                "§7Increases attack power.",
                "§eAbility: Ground Slam"));

        inv.setItem(13, createStatItem(Material.NETHERITE_AXE, "§bAgility", speed,
                "§7Increases movement speed.",
                "§eAbility: Dash"));

        inv.setItem(15, createStatItem(Material.TRIDENT, "§aVitality", vitality,
                "§7Increases health and grants regen.",
                "§eAbility: Survival Burst"));

        inv.setItem(22, createInfoItem(points));

        player.openInventory(inv);
    }

    private static ItemStack createStatItem(Material material, String name, int level, String... loreLines) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName(name + " §7(§e" + level + "§7)");
        List<String> lore = new ArrayList<>();
        for (String line : loreLines) lore.add(line);
        lore.add("");
        lore.add("§7Click to upgrade §f(§e1 point§f)");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack createInfoItem(int points) {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName("§6Your Stats");
        List<String> lore = new ArrayList<>();
        lore.add("§7You have §e" + points + " §7unspent stat points.");
        lore.add("");
        lore.add("§fEach upgrade enhances your power.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
