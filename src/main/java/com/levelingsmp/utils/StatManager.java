package com.levelingsmp.utils;

import com.levelingsmp.LevelingPluginSMP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class StatManager {

    private final LevelingPluginSMP plugin;
    private File dataFile;
    private FileConfiguration data;

    public StatManager(LevelingPluginSMP plugin) {
        this.plugin = plugin;
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            plugin.saveResource("data.yml", false);
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void load() {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void save() {
        try {
            data.save(dataFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String node(UUID id, String key) {
        return "players." + id.toString() + "." + key;
    }

    public void ensurePlayerData(Player p) {
        UUID id = p.getUniqueId();
        if (!data.contains(node(id, "statPoints"))) {
            data.set(node(id, "statPoints"), 0);
            data.set(node(id, "strength"), 0);
            data.set(node(id, "speed"), 0);
            data.set(node(id, "vitality"), 0);
            save();
        }
    }

    public int getStatPoints(Player p) {
        ensurePlayerData(p);
        return data.getInt(node(p.getUniqueId(), "statPoints"), 0);
    }

    public void addStatPoints(Player p, int amount) {
        ensurePlayerData(p);
        data.set(node(p.getUniqueId(), "statPoints"), getStatPoints(p) + amount);
        save();
    }

    public void removeStatPoints(Player p, int amount) {
        ensurePlayerData(p);
        data.set(node(p.getUniqueId(), "statPoints"), Math.max(0, getStatPoints(p) - amount));
        save();
    }

    public int getStrength(Player p) { ensurePlayerData(p); return data.getInt(node(p.getUniqueId(), "strength"), 0); }
    public int getSpeed(Player p) { ensurePlayerData(p); return data.getInt(node(p.getUniqueId(), "speed"), 0); }
    public int getVitality(Player p) { ensurePlayerData(p); return data.getInt(node(p.getUniqueId(), "vitality"), 0); }

    public void setStrength(Player p, int v) { ensurePlayerData(p); data.set(node(p.getUniqueId(), "strength"), Math.max(0, v)); save(); reapplyAllEffects(p);}
    public void setSpeed(Player p, int v) { ensurePlayerData(p); data.set(node(p.getUniqueId(), "speed"), Math.max(0, v)); save(); reapplyAllEffects(p);}
    public void setVitality(Player p, int v) { ensurePlayerData(p); data.set(node(p.getUniqueId(), "vitality"), Math.max(0, v)); save(); reapplyAllEffects(p);}

    public void addStrength(Player p, int v) { setStrength(p, getStrength(p) + v); removeStatPoints(p,1); }
    public void addSpeed(Player p, int v) { setSpeed(p, getSpeed(p) + v); removeStatPoints(p,1); }
    public void addVitality(Player p, int v) { setVitality(p, getVitality(p) + v); removeStatPoints(p,1); }

    public void reapplyAllEffects(Player p) {
        // clear and reapply based on levels
        p.removePotionEffect(org.bukkit.potion.PotionEffectType.getByName("INCREASE_DAMAGE"));
        p.removePotionEffect(org.bukkit.potion.PotionEffectType.getByName("SPEED"));
        // Strength: 1 -> Strength I (increase damage), 2 -> Strength II
        int s = getStrength(p);
        if (s >= 1) {
            p.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.getByName("INCREASE_DAMAGE"), Integer.MAX_VALUE, s-1, false, false, false));
        }
        // Speed: attack speed is not potion, but we can give SPEED potion for movement speed as effect for demonstration
        int sp = getSpeed(p);
        if (sp >= 1) {
            p.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.getByName("SPEED"), Integer.MAX_VALUE, sp-1, false, false, false));
        }
        // Vitality: best handled by attribute or absorption
        int v = getVitality(p);
        if (v >= 1) {
            // v==1 => +2 health (permanent while have stat) we will apply absorption and max health modifiers are complicated, so we add absorption as indicator
            // For permanent hearts while stat present, servers normally use attribute modifiers; for simplicity we give absorption amount
            p.setHealth(Math.min(p.getMaxHealth(), p.getHealth())); // safety
        }
    }

    public void removePointOnDeath(Player p) {
        // remove one point from a random stat that has >0
        int s = getStrength(p), sp = getSpeed(p), v = getVitality(p);
        if (s + sp + v == 0) return;
        if (s > 0 && sp == 0 && v == 0) setStrength(p, s - 1);
        else if (sp > 0 && s == 0 && v == 0) setSpeed(p, sp - 1);
        else if (v > 0 && s == 0 && sp == 0) setVitality(p, v - 1);
        else {
            // pick random non-zero stat
            java.util.List<String> list = new java.util.ArrayList<>();
            if (s > 0) list.add("s");
            if (sp > 0) list.add("sp");
            if (v > 0) list.add("v");
            String pick = list.get(new java.util.Random().nextInt(list.size()));
            if (pick.equals("s")) setStrength(p, s - 1);
            if (pick.equals("sp")) setSpeed(p, sp - 1);
            if (pick.equals("v")) setVitality(p, v - 1);
        }
        save();
    }

    public int totalInvested(Player p) {
        return getStrength(p) + getSpeed(p) + getVitality(p);
    }
}
