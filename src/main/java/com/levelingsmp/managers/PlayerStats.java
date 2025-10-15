package com.levelingsmp.managers;

import java.util.UUID;

public class PlayerStats {

    private final UUID uuid;
    private int strength;
    private int agility;
    private int vitality;
    private int statPoints;

    public PlayerStats(UUID uuid) {
        this.uuid = uuid;
        this.strength = 0;
        this.agility = 0;
        this.vitality = 0;
        this.statPoints = 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = Math.max(strength, 0);
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = Math.max(agility, 0);
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = Math.max(vitality, 0);
    }

    public int getStatPoints() {
        return statPoints;
    }

    public void setStatPoints(int statPoints) {
        this.statPoints = Math.max(statPoints, 0);
    }

    public void addStatPoint() {
        this.statPoints++;
    }

    public boolean spendStatPoint() {
        if (statPoints > 0) {
            statPoints--;
            return true;
        }
        return false;
    }

    public void resetStats() {
        this.strength = 0;
        this.agility = 0;
        this.vitality = 0;
        this.statPoints = 0;
    }

    @Override
    public String toString() {
        return "PlayerStats{" +
                "uuid=" + uuid +
                ", strength=" + strength +
                ", agility=" + agility +
                ", vitality=" + vitality +
                ", statPoints=" + statPoints +
                '}';
    }
}
