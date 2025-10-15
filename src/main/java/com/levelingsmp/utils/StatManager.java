package com.levelingsmp.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class StatManager {

    private final File dataFile;
    private final FileConfiguration data;

    public StatManager(File dataFolder) {
        this.dataFile = new File(dataFolder, "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void save() {
        try {
            data.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -----------------------------
    //     PLAYER STAT FUNCTIONS
    // -----------------------------

    public void createPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        if (!data.contains("players." + uuid)) {
            data.set("players." + uuid + ".strength", 0);
            data.set("players." + uuid + ".speed", 0);
            data.set("players." + uuid + ".vitality", 0);
            data.set("players." + uuid + ".points", 0);
            save();
        }
    }

    public int getStat(Player player, String stat) {
        UUID uuid = player.getUniqueId();
        return data.getInt("players." + uuid + "." + stat, 0);
    }

    public void setStat(Player player, String stat, int value) {
        UUID uuid = player.getUniqueId();
        data.set("players." + uuid + "." + stat, value);
        save();
    }

    public int getStatPoints(Player player) {
        UUID uuid = player.getUniqueId();
        return data.getInt("players." + uuid + ".points", 0);
    }

    public void setStatPoints(Player player, int points) {
        UUID uuid = player.getUniqueId();
        data.set("players." + uuid + ".points", points);
        save();
    }

    public void addStatPoint(Player player) {
        setStatPoints(player, getStatPoints(player) + 1);
    }

    public void removeStatPoint(Player player) {
        setStatPoints(player, Math.max(0, getStatPoints(player) - 1));
    }

    public void increaseStat(Player player, String stat) {
        int current = getStat(player, stat);
        int points = getStatPoints(player);

        if (points > 0) {
            setStat(player, stat, current + 1);
            removeStatPoint(player);
        }
    }

    // -----------------------------
    //     SKILL DROP ON DEATH
    // -----------------------------

    public boolean dropSkillPoint(Player player) {
        UUID uuid = player.getUniqueId();
        int strength = getStat(player, "strength");
        int speed = getStat(player, "speed");
        int vitality = getStat(player, "vitality");

        if (strength + speed + vitality <= 0) {
            return false; // No stats → no drop
        }

        // randomly remove one stat
        String[] stats = {"strength", "speed", "vitality"};
        String random = stats[(int) (Math.random() * stats.length)];

        int statValue = getStat(player, random);
        if (statValue > 0) {
            setStat(player, random, statValue - 1);
            save();
            return true;
        }
        return false;
    }
}
