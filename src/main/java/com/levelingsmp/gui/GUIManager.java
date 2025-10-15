package com.levelingsmp.gui;

import com.levelingsmp.utils.StatManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUIManager {

    private final StatManager statManager;

    public GUIManager(StatManager statManager) {
        this.statManager = statManager;
    }

    // -----------------------------
    // OPEN MAIN GUI
    // -----------------------------
    public void openMainGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "Leveling Menu");

        int strength = statManager.getStat(player, "strength");
        int speed = statManager.getStat(player, "speed");
        int vitality = statManager.getStat(player, "vitality");
        int points = statManager.getStatPoints(player);

        // Strength Icon
        ItemStack strengthItem = createItem(Material.NETHERITE_SWORD, ChatColor.RED + "Strength",
                List.of(ChatColor.GRAY + "Increase melee damage.",
                        ChatColor.YELLOW + "Current Level: " + ChatColor.GOLD + strength,
                        ChatColor.YELLOW + "Next Bonus:",
                        ChatColor.GREEN + getStrengthBonusText(strength),
                        ChatColor.AQUA + "Click to upgrade (Cost: 1 point)"));

        // Speed Icon
        ItemStack speedItem = createItem(Material.SUGAR, ChatColor.AQUA + "Agility",
                List.of(ChatColor.GRAY + "Increase movement speed.",
                        ChatColor.YELLOW + "Current Level: " + ChatColor.GOLD + speed,
                        ChatColor.YELLOW + "Next Bonus:",
                        ChatColor.GREEN + getSpeedBonusText(speed),
                        ChatColor.AQUA + "Click to upgrade (Cost: 1 point)"));

        // Vitality Icon
        ItemStack vitalityItem = createItem(Material.GOLDEN_APPLE, ChatColor.GREEN + "Vitality",
                List.of(ChatColor.GRAY + "Increase max health.",
                        ChatColor.YELLOW + "Current Level: " + ChatColor.GOLD + vitality,
                        ChatColor.YELLOW + "Next Bonus:",
                        ChatColor.GREEN + getVitalityBonusText(vitality),
                        ChatColor.AQUA + "Click to upgrade (Cost: 1 point)"));

        // Stat points item
        ItemStack pointsItem = createItem(Material.EXPERIENCE_BOTTLE, ChatColor.GOLD + "Available Points: " + points,
                List.of(ChatColor.GRAY + "Earn points by killing players.",
                        ChatColor.GRAY + "Lose one when you die."));

        gui.setItem(11, strengthItem);
        gui.setItem(13, speedItem);
        gui.setItem(15, vitalityItem);
        gui.setItem(22, pointsItem);

        player.openInventory(gui);
    }

    private ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(new ArrayList<>(lore));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    private String getStrengthBonusText(int level) {
        return switch (level) {
            case 0 -> "+Strength I";
            case 1 -> "+Strength II";
            case 2 -> "Unlocks Ground Slam Ability!";
            default -> "MAXED";
        };
    }

    private String getSpeedBonusText(int level) {
        return switch (level) {
            case 0 -> "+Speed I";
            case 1 -> "+Speed II";
            case 2 -> "Unlocks Dash Ability!";
            default -> "MAXED";
        };
    }

    private String getVitalityBonusText(int level) {
        return switch (level) {
            case 0 -> "+2 Permanent Hearts";
            case 1 -> "+5 Permanent Hearts";
            case 2 -> "Unlocks Vitality Heal Ability!";
            default -> "MAXED";
        };
    }

    // -----------------------------
    // HANDLE CLICK EVENTS
    // -----------------------------
    public void handleGUIClick(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Leveling Menu")) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            int slot = e.getRawSlot();
            if (slot == 11) {
                upgradeStat(player, "strength");
            } else if (slot == 13) {
                upgradeStat(player, "speed");
            } else if (slot == 15) {
                upgradeStat(player, "vitality");
            }
        }
    }

    private void upgradeStat(Player player, String stat) {
        int points = statManager.getStatPoints(player);
        int currentLevel = statManager.getStat(player, stat);

        if (points <= 0) {
            player.sendMessage(ChatColor.RED + "You don’t have enough stat points!");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 0.5f);
            return;
        }

        if (currentLevel >= 3) {
            player.sendMessage(ChatColor.YELLOW + "That stat is already maxed out!");
            return;
        }

        statManager.increaseStat(player, stat);
        player.sendMessage(ChatColor.GREEN + "You upgraded " + stat + " to level " + (currentLevel + 1) + "!");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.2f);

        // Apply bonuses immediately
        applyStatBonuses(player);

        // Reopen GUI
        openMainGUI(player);
    }

    public void applyStatBonuses(Player player) {
        int strength = statManager.getStat(player, "strength");
        int speed = statManager.getStat(player, "speed");
        int vitality = statManager.getStat(player, "vitality");

        // Clear potion effects first
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        // Strength
        if (strength >= 1)
            player.addPotionEffect(org.bukkit.potion.PotionEffectType.INCREASE_DAMAGE.createEffect(Integer.MAX_VALUE, strength - 1));

        // Speed
        if (speed >= 1)
            player.addPotionEffect(org.bukkit.potion.PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, speed - 1));

        // Vitality (extra health)
        double baseHealth = 20.0;
        if (vitality == 1)
            player.setMaxHealth(baseHealth + 4);
        else if (vitality == 2)
            player.setMaxHealth(baseHealth + 10);
        else
            player.setMaxHealth(baseHealth);
    }
}
