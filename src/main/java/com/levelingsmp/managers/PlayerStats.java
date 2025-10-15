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
        return strength
