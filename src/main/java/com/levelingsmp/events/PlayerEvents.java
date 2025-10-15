package com.levelingsmp.events;

import com.levelingsmp.LevelingPluginSMP;
import com.levelingsmp.utils.ItemFactory;
import com.levelingsmp.utils.StatAbilityManager;
import com.levelingsmp.managers.StatManager;
import com.levelingsmp.utils.WeaponAbilityManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlayerEvents implements Listener {

    private final LevelingPluginSMP plugin;
    private final StatManager statManager;
    private final ItemFactory itemFactory;
    private final StatAbilityManager statAbilityManager;
    private final WeaponAbilityManager weaponAbilityManager;
    private final Random random = new Random();

    public PlayerEvents(LevelingPluginSMP plugin, StatManager statManager, ItemFactory itemFactory, StatAbilityManager statAbilityManager, WeaponAbilityManager weaponAbilityManager) {
        this.plugin = plugin;
        this.statManager = statManager;
        this.itemFactory = itemFactory;
        this.statAbilityManager = statAbilityManager;
        this.weaponAbilityManager = weaponAbilityManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        // ensure data exists
        statManager.ensurePlayerData(p);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player dead = e.getEntity();
        // if player has any stat leveled, drop a stat core (stat point item)
        if (statManager.totalInvested(dead) > 0 && plugin.getConfig().getBoolean("drop_on_death_if_has_stat", true)) {
            ItemStack core = itemFactory.createStatPoint();
            dead.getWorld().dropItemNaturally(dead.getLocation(), core);
            // remove one stat point from random stat (or from inventory when they pick up reset token—we remove here)
            statManager.removePointOnDeath(dead);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player attacker)) return;
        // weapon lifesteal handled in WeaponAbilityManager via checks; it also listens for entity damage events if needed
        weaponAbilityManager.handleDamageEvent(e);
    }
}
