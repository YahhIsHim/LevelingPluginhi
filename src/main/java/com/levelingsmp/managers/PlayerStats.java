package com.levelingsmp.managers;

import java.util.UUID;

public class PlayerStats {
    private final UUID playerId;
    private int strength;
    private int speed;
    private int vitality;
    private int skillPoints;

    public PlayerStats(UUID playerId) {
        this.playerId = playerId;
        this.strength = 0;
        this.speed = 0;
        this.vitality = 0;
        this.skillPoints = 0;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getStrength() {
        return strength;
    }

    public int getSpeed() {
        return speed;
    }

    public int getVitality() {
        return vitality;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public void addStrength(int amount) {
        this.strength += amount;
    }

    public void addSpeed(int amount) {
        this.speed += amount;
    }

    public void addVitality(int amount) {
        this.vitality += amount;
    }

    public void addSkillPoint(int amount) {
        this.skillPoints += amount;
    }

    public void setStrength(int value) {
        this.strength = value;
    }

    public void setSpeed(int value) {
        this.speed = value;
    }

    public void setVitality(int value) {
        this.vitality = value;
    }

    public void setSkillPoints(int value) {
        this.skillPoints = value;
    }
}
