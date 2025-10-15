package com.levelingsmp.utils;

import com.levelingsmp.LevelingPluginSMP;
import org.bukkit.*;
import com.levelingsmp.managers.StatManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class StatAbilityManager {

    private final LevelingPluginSMP plugin;
    private final StatManager statManager;
    private final HashMap<UUID, Long> groundSlamCd = new HashMap<>();
    private final HashMap<UUID, Long> dashCd = new HashMap<>();
    private final HashMap<UUID, Long> vitalityCd = new HashMap<>();

    public StatAbilityManager(LevelingPluginSMP plugin, StatManager statManager) {
        this.plugin = plugin;
        this.statManager = statManager;
    }

    private long now() { return System.currentTimeMillis(); }

    public void activateStrengthAbility(Player p) {
        int strength = statManager.getStrength(p);
        int max = plugin.getConfig().getInt("max_stat", 3);
        if (strength < max) { p.sendMessage("You need max Strength to use this ability."); return; }
        long cdSeconds = plugin.getConfig().getLong("abilities.ground_slam.cooldown",20);
        long last = groundSlamCd.getOrDefault(p.getUniqueId(), 0L);
        if (now() - last < cdSeconds*1000) {
            p.sendMessage("Ground slam on cooldown.");
            return;
        }
        groundSlamCd.put(p.getUniqueId(), now());
        int damageHearts = plugin.getConfig().getInt("abilities.ground_slam.damage_hearts",4);
        // play effect
        p.getWorld().spawnParticle(Particle.EXPLOSION, p.getLocation(), 2);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
        // damage nearby entities for fixed 'damageHearts' hearts
        Location loc = p.getLocation();
        for (Entity e : loc.getWorld().getNearbyEntities(loc, 4, 2, 4)) {
            if (e instanceof org.bukkit.entity.LivingEntity le && !le.equals(p)) {
                double dmg = damageHearts * 2.0;
                // apply true damage: reduce health ignoring armor by setting damage via setHealth (clamped)
                double newHp = Math.max(0.0, le.getHealth() - dmg);
                le.setHealth(Math.min(le.getMaxHealth(), newHp));
            }
        }
        p.sendMessage("You used Ground Slam!");
    }

    public void activateSpeedAbility(Player p) {
        int speed = statManager.getSpeed(p);
        int max = plugin.getConfig().getInt("max_stat", 3);
        if (speed < max) { p.sendMessage("You need max Speed to use this ability."); return; }
        long cdSeconds = plugin.getConfig().getLong("abilities.dash.cooldown",10);
        long last = dashCd.getOrDefault(p.getUniqueId(), 0L);
        if (now() - last < cdSeconds*1000) {
            p.sendMessage("Dash on cooldown.");
            return;
        }
        dashCd.put(p.getUniqueId(), now());
        int distance = plugin.getConfig().getInt("abilities.dash.distance",6);
        Location to = p.getLocation().add(p.getLocation().getDirection().normalize().multiply(distance));
        p.teleport(to);
        p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 20, 0.5,0.5,0.5);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_SPLASH, 1f, 1f);
        p.sendMessage("You dashed forward!");
    }

    public void activateVitalityAbility(Player p) {
        int vit = statManager.getVitality(p);
        int max = plugin.getConfig().getInt("max_stat", 3);
        if (vit < max) { p.sendMessage("You need max Vitality to use this ability."); return; }
        long cdSeconds = plugin.getConfig().getLong("abilities.vitality_surge.cooldown",60);
        long last = vitalityCd.getOrDefault(p.getUniqueId(), 0L);
        if (now() - last < cdSeconds*1000) {
            p.sendMessage("Vitality ability on cooldown.");
            return;
        }
        vitalityCd.put(p.getUniqueId(), now());
        int extraAbs = plugin.getConfig().getInt("abilities.vitality_surge.extra_absorption",5);
        int regenSec = plugin.getConfig().getInt("abilities.vitality_surge.regen_seconds", 2);
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, regenSec*20, 1));
        p.setAbsorptionAmount(extraAbs * 2.0f);
        p.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, p.getLocation(), 20);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        p.sendMessage("Vitality Surge activated!");
    }
}
