package com.levelingsmp.managers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatManager {
    private final Map<UUID, PlayerStats> statsMap = new HashMap<>();

    public PlayerStats getPlayerStats(Player player) {
        return statsMap.computeIfAbsent(player.getUniqueId(), id -> new PlayerStats(id));
    }

    public int getStat(Player player, String statName) {
        PlayerStats stats = getPlayerStats(player);
        switch (statName.toLowerCase()) {
            case "strength": return stats.getStrength();
            case "speed": return stats.getSpeed();
            case "vitality": return stats.getVitality();
            default: return 0;
        }
    }

    public int getStatPoints(Player player) {
        return getPlayerStats(player).getSkillPoints();
    }

    public void addStat(Player player, String statName, int amount) {
        PlayerStats stats = getPlayerStats(player);
        switch (statName.toLowerCase()) {
            case "strength": stats.addStrength(amount); break;
            case "speed": stats.addSpeed(amount); break;
            case "vitality": stats.addVitality(amount); break;
        }
    }

    public void addSkillPoint(Player player, int amount) {
        PlayerStats stats = getPlayerStats(player);
        stats.addSkillPoint(amount);
    }

    public void resetStats(Player player) {
        PlayerStats stats = getPlayerStats(player);
        stats.setStrength(0);
        stats.setSpeed(0);
        stats.setVitality(0);
        stats.setSkillPoints(0);
    }
}
