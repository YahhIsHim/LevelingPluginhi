package com.levelingsmp.utils;

import com.levelingsmp.LevelingPluginSMP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemFactory {

    private final LevelingPluginSMP plugin;

    public ItemFactory(LevelingPluginSMP plugin) {
        this.plugin = plugin;
        registerRecipes();
    }

    public ItemStack createStatPoint() {
        ItemStack it = new ItemStack(Material.NETHER_STAR);
        ItemMeta m = it.getItemMeta();
        m.setDisplayName("§6Stat Point");
        m.setLore(Arrays.asList("Use /leveling or right click to spend"));
        it.setItemMeta(m);
        return it;
    }

    public ItemStack createResetToken() {
        ItemStack it = new ItemStack(Material.NETHER_STAR);
        ItemMeta m = it.getItemMeta();
        m.setDisplayName("§cReset Token");
        m.setLore(Arrays.asList("Right-click to reset your stats and return cores"));
        it.setItemMeta(m);
        return it;
    }

    public ItemStack createStrengthSword() {
        ItemStack s = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta m = s.getItemMeta();
        m.setDisplayName("§cStrength Sword");
        m.setLore(Arrays.asList("A powerful sword - grants ability when used."));
        m.addEnchant(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 5, true);
        m.addEnchant(org.bukkit.enchantments.Enchantment.SWEEPING_EDGE, 3, true);
        m.addEnchant(org.bukkit.enchantments.Enchantment.FIRE_ASPECT, 2, true);
        m.addEnchant(org.bukkit.enchantments.Enchantment.LOOT_BONUS_MOBS, 3, true);
        m.setUnbreakable(true);
        s.setItemMeta(m);
        return s;
    }

    public ItemStack createSpeedAxe() {
        ItemStack s = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta m = s.getItemMeta();
        m.setDisplayName("§6Speed Axe");
        m.setLore(Arrays.asList("Gives attack speed boost ability."));
        m.addEnchant(org.bukkit.enchantments.Enchantment.DIG_SPEED, 5, true);
        m.addEnchant(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 4, true);
        m.setUnbreakable(true);
        s.setItemMeta(m);
        return s;
    }

    public ItemStack createVitalityTrident() {
        ItemStack s = new ItemStack(Material.TRIDENT);
        ItemMeta m = s.getItemMeta();
        m.setDisplayName("§aVitality Trident");
        m.setLore(Arrays.asList("Grants lifesteal ability when active."));
        m.addEnchant(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 4, true);
        m.addEnchant(org.bukkit.enchantments.Enchantment.LOYALTY, 3, true);
        m.setUnbreakable(true);
        s.setItemMeta(m);
        return s;
    }

    private void registerRecipes() {
        // Simple defaults: stat point => gold around blaze powder
        try {
            NamespacedKey k = new NamespacedKey(plugin, "stat_point");
            ShapedRecipe statRecipe = new ShapedRecipe(k, createStatPoint());
            statRecipe.shape(" G ", "GBG", " G ");
            statRecipe.setIngredient('G', Material.GOLD_INGOT);
            statRecipe.setIngredient('B', Material.BLAZE_POWDER);
            Bukkit.addRecipe(statRecipe);

            NamespacedKey r = new NamespacedKey(plugin, "reset_token");
            ShapedRecipe resetRecipe = new ShapedRecipe(r, createResetToken());
            resetRecipe.shape(" R ", "RCR", " R ");
            resetRecipe.setIngredient('R', Material.REDSTONE);
            resetRecipe.setIngredient('C', Material.NETHER_STAR);
            Bukkit.addRecipe(resetRecipe);
        } catch (Exception ex) {
            plugin.getLogger().warning("Could not register recipes: " + ex.getMessage());
        }
    }

    public boolean isStatPoint(ItemStack it) {
        if (it == null) return false;
        if (!it.hasItemMeta()) return false;
        return "§6Stat Point".equals(it.getItemMeta().getDisplayName());
    }

    public boolean isResetToken(ItemStack it) {
        if (it == null) return false;
        if (!it.hasItemMeta()) return false;
        return "§cReset Token".equals(it.getItemMeta().getDisplayName());
    }
}
