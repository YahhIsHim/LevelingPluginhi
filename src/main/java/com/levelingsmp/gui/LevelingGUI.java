package com.levelingsmp.gui;

import com.levelingsmp.LevelingPluginSMP;
import com.levelingsmp.utils.ItemFactory;
import com.levelingsmp.utils.StatManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LevelingGUI implements Listener {

    private static LevelingPluginSMP plugin;
    private static StatManager statManager;
    private static ItemFactory itemFactory;

    public LevelingGUI(LevelingPluginSMP instance, StatManager sm, ItemFactory itf) {
        plugin = instance;
        statManager = sm;
        itemFactory = itf;
    }

    public static void open(Player p) {
        int size = plugin.getConfig().getInt("gui.size", 27);
        String title = plugin.getConfig().getString("gui.title", "Leveling");
        Inventory inv = Bukkit.createInventory(null, size, title);

        // Strength slot
        ItemStack strength = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta sm = strength.getItemMeta();
        sm.setDisplayName("§cStrength");
        sm.setLore(java.util.List.of("Level: " + statManager.getStrength(p) + "/" + plugin.getConfig().getInt("max_stat", 3),
                "Click to spend 1 point"));
        strength.setItemMeta(sm);

        // Speed slot
        ItemStack speed = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta spm = speed.getItemMeta();
        spm.setDisplayName("§6Speed");
        spm.setLore(java.util.List.of("Level: " + statManager.getSpeed(p) + "/" + plugin.getConfig().getInt("max_stat", 3),
                "Click to spend 1 point"));
        speed.setItemMeta(spm);

        // Vitality slot
        ItemStack vit = new ItemStack(Material.APPLE);
        ItemMeta vm = vit.getItemMeta();
        vm.setDisplayName("§aVitality");
        vm.setLore(java.util.List.of("Level: " + statManager.getVitality(p) + "/" + plugin.getConfig().getInt("max_stat", 3),
                "Click to spend 1 point"));
        vit.setItemMeta(vm);

        inv.setItem(11, strength);
        inv.setItem(13, speed);
        inv.setItem(15, vit);

        // Info and statpoint display
        ItemStack statPoint = itemFactory.createStatPoint();
        ItemMeta pm = statPoint.getItemMeta();
        pm.setDisplayName("§eYou have §6" + statManager.getStatPoints(p) + " §epoints");
        statPoint.setItemMeta(pm);
        inv.setItem(4, statPoint);

        p.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getView().getTitle().equals(plugin.getConfig().getString("gui.title", "Leveling"))) {
            e.setCancelled(true); // prevent moving items
            int slot = e.getRawSlot();
            if (slot == 11) {
                // Strength
                if (statManager.getStatPoints(p) <= 0) {
                    p.sendMessage("You have no stat points.");
                    return;
                }
                if (statManager.getStrength(p) >= plugin.getConfig().getInt("max_stat", 3)) {
                    p.sendMessage("Strength is maxed.");
                    return;
                }
                statManager.addStrength(p,1);
                p.sendMessage("Added +1 Strength.");
                statManager.save();
                open(p);
            } else if (slot == 13) {
                if (statManager.getStatPoints(p) <= 0) {
                    p.sendMessage("You have no stat points.");
                    return;
                }
                if (statManager.getSpeed(p) >= plugin.getConfig().getInt("max_stat", 3)) {
                    p.sendMessage("Speed is maxed.");
                    return;
                }
                statManager.addSpeed(p,1);
                p.sendMessage("Added +1 Speed.");
                statManager.save();
                open(p);
            } else if (slot == 15) {
                if (statManager.getStatPoints(p) <= 0) {
                    p.sendMessage("You have no stat points.");
                    return;
                }
                if (statManager.getVitality(p) >= plugin.getConfig().getInt("max_stat", 3)) {
                    p.sendMessage("Vitality is maxed.");
                    return;
                }
                statManager.addVitality(p,1);
                p.sendMessage("Added +1 Vitality.");
                statManager.save();
                open(p);
            }
        }
    }

    // Prevent taking items by drag etc
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getView().getTitle().equals(plugin.getConfig().getString("gui.title", "Leveling"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        // nothing
    }
}
