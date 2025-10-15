package com.levelingsmp.managers;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class StatManager {

    private final Map<UUID, PlayerStats> data = new HashMap<>();
    private final Random random = new Random();

    public void ensurePlayerData(Player player) {
        data.computeIfAbsent(player.getUniqueId(), k -> new PlayerStats());
    }

    public PlayerStats getStats(Player player) {
        ensurePlayerData(player);
        return data.get(player.getUniqueId());
    }

    public int getStrength(Player player) {
        return getStats(player).getStrength();
    }

    public int getSpeed(Player player) {
        return getStats(player).getSpeed();
    }

    public int getVitality(Player player) {
        return getStats(player).getVitality();
    }

    public void addStrength(Player player, int amount) {
        getStats(player).addStrength(amount);
    }

    public void addSpeed(Player player, int amount) {
        getStats(player).addSpeed(amount);
    }

    public void addVitality(Player player, int amount) {
        getStats(player).addVitality(amount);
    }

    public void removePointOnDeath(Player player) {
        PlayerStats stats = getStats(player);
        int total = stats.getStrength() + stats.getSpeed() + stats.getVitality();
        if (total <= 0) return;

        int rand = random.nextInt(total);
        if (rand < stats.getStrength() && stats.getStrength() > 0) {
            stats.addStrength(-1);
        } else if (rand < stats.getStrength() + stats.getSpeed() && stats.getSpeed() > 0) {
            stats.addSpeed(-1);
        } else if (stats.getVitality() > 0) {
            stats.addVitality(-1);
        }
    }

    public int totalInvested(Player player) {
        PlayerStats stats = getStats(player);
        return stats.getStrength() + stats.getSpeed() + stats.getVitality();
    }

    // Optional if you track skill points separately
    public void addSkillPoint(Player player) {
        getStats(player).addSkillPoint(1);
    }

    public boolean removeSkillPoint(Player player) {
        PlayerStats stats = getStats(player);
        if (stats.getSkillPoints() > 0) {
            stats.addSkillPoint(-1);
            return true;
        }
        return false;
    }
}
