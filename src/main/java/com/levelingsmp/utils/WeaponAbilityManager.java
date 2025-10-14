package com.levelingsmp.utils;

import com.levelingsmp.LevelingPluginSMP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class WeaponAbilityManager {

    private final LevelingPluginSMP plugin;
    private final HashMap<UUID, Long> swordCooldown = new HashMap<>();
    private final HashMap<UUID, Long> axeCooldown = new HashMap<>();
    private final HashMap<UUID, Long> tridentCooldown = new HashMap<>();
    private final HashMap<UUID, Long> swordEnd = new HashMap<>();
    private final HashMap<UUID, Long> axeEnd = new HashMap<>();
    private final HashMap<UUID, Long> tridentEnd = new HashMap<>();

    public WeaponAbilityManager(LevelingPluginSMP plugin) {
        this.plugin = plugin;
    }

    private long now() { return System.currentTimeMillis(); }

    public void activateSwordAbility(Player p) {
        // require holding sword
        if (p.getInventory().getItemInMainHand().getType() != Material.NETHERITE_SWORD) {
            p.sendMessage("You must hold the Strength Sword to use this ability.");
            return;
        }
        long cd = plugin.getConfig().getLong("weapon_abilities.sword.cooldown", 30);
        if (now() - swordCooldown.getOrDefault(p.getUniqueId(), 0L) < cd*1000) {
            p.sendMessage("Sword ability on cooldown.");
            return;
        }
        swordCooldown.put(p.getUniqueId(), now());
        long dur = plugin.getConfig().getLong("weapon_abilities.sword.duration_seconds", 8);
        swordEnd.put(p.getUniqueId(), now() + dur*1000);
        p.sendMessage("Sword ability active: auto-crit for " + dur + "s");
        // no special potion: we treat auto-crit in handleDamageEvent
    }

    public void activateAxeAbility(Player p) {
        if (p.getInventory().getItemInMainHand().getType() != Material.NETHERITE_AXE) {
            p.sendMessage("You must hold the Speed Axe to use this ability.");
            return;
        }
        long cd = plugin.getConfig().getLong("weapon_abilities.axe.cooldown", 30);
        if (now() - axeCooldown.getOrDefault(p.getUniqueId(), 0L) < cd*1000) {
            p.sendMessage("Axe ability on cooldown.");
            return;
        }
        axeCooldown.put(p.getUniqueId(), now());
        long dur = plugin.getConfig().getLong("weapon_abilities.axe.duration_seconds", 8);
        axeEnd.put(p.getUniqueId(), now() + dur*1000);
        int haste = plugin.getConfig().getInt("weapon_abilities.axe.haste_level", 3);
        p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, (int)dur*20, haste-1));
        p.sendMessage("Axe ability active: haste for " + dur + "s");
    }

    public void activateTridentAbility(Player p) {
        if (p.getInventory().getItemInMainHand().getType() != Material.TRIDENT) {
            p.sendMessage("You must hold the Vitality Trident to use this ability.");
            return;
        }
        long cd = plugin.getConfig().getLong("weapon_abilities.trident.cooldown", 30);
        if (now() - tridentCooldown.getOrDefault(p.getUniqueId(), 0L) < cd*1000) {
            p.sendMessage("Trident ability on cooldown.");
            return;
        }
        tridentCooldown.put(p.getUniqueId(), now());
        long dur = plugin.getConfig().getLong("weapon_abilities.trident.duration_seconds", 12);
        tridentEnd.put(p.getUniqueId(), now() + dur*1000);
        p.sendMessage("Trident ability active: lifesteal for " + dur + "s");
    }

    public void handleDamageEvent(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player p)) return;
        UUID id = p.getUniqueId();
        // Sword auto-crit: when active and holding netherite sword
        if (p.getInventory().getItemInMainHand().getType() == Material.NETHERITE_SWORD) {
            Long end = swordEnd.get(id);
            if (end != null && now() < end) {
                // Force critical-like behaviour: increase damage
                e.setDamage(e.getDamage() * 1.8); // arbitrary crit multiplier
            }
        }
        // Axe: already gave haste potion; no per-hit logic required
        // Trident lifesteal: if trident ability active and holding trident, heal on hit
        if (p.getInventory().getItemInMainHand().getType() == Material.TRIDENT) {
            Long end = tridentEnd.get(id);
            if (end != null && now() < end) {
                double lifesteal = plugin.getConfig().getDouble("weapon_abilities.trident.lifesteal_percent", 0.25);
                double heal = e.getDamage() * lifesteal;
                p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + heal));
            }
        }
    }
}
