package com.example.treeevent;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TreeManager {
    private final TreeEventPlugin plugin;
    private final Map<UUID, Integer> exp = new HashMap<>();
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private FileConfiguration levelsConfig;
    private FileConfiguration rewardsConfig;

    public TreeManager(TreeEventPlugin plugin) {
        this.plugin = plugin;
        loadConfigs();
    }

    public void loadConfigs() {
        File levelsFile = new File(plugin.getDataFolder(), "exp_levels.yml");
        if (!levelsFile.exists()) plugin.saveResource("exp_levels.yml", false);
        levelsConfig = YamlConfiguration.loadConfiguration(levelsFile);

        File rewardsFile = new File(plugin.getDataFolder(), "rewards.yml");
        if (!rewardsFile.exists()) plugin.saveResource("rewards.yml", false);
        rewardsConfig = YamlConfiguration.loadConfiguration(rewardsFile);
    }

    public void addExpAsync(Player player, int amount) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int newExp = exp.getOrDefault(player.getUniqueId(), 0) + amount;
                exp.put(player.getUniqueId(), newExp);
                checkLevelUp(player, newExp);
            }
        }.runTaskAsynchronously(plugin);
    }

    public boolean onCooldown(UUID uuid) {
        long now = System.currentTimeMillis();
        return cooldowns.getOrDefault(uuid, 0L) > now;
    }

    public void setCooldown(UUID uuid, long millis) {
        cooldowns.put(uuid, System.currentTimeMillis() + millis);
    }

    public int getExp(UUID uuid) {
        return exp.getOrDefault(uuid, 0);
    }

    public int getLevel(UUID uuid) {
        int playerExp = getExp(uuid);
        int lvl = 1;
        for (String key : levelsConfig.getConfigurationSection("levels").getKeys(false)) {
            int required = levelsConfig.getInt("levels." + key);
            if (playerExp >= required) {
                lvl = Integer.parseInt(key);
            } else {
                break;
            }
        }
        return lvl;
    }

    private void checkLevelUp(Player player, int newExp) {
        int newLevel = getLevel(player.getUniqueId());
        if (rewardsConfig.contains("rewards." + newLevel)) {
            for (String cmd : rewardsConfig.getStringList("rewards." + newLevel)) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        cmd.replace("%player%", player.getName()));
            }
        }
    }

    public void saveData() {
        File dataFile = new File(plugin.getDataFolder(), "progress.yml");
        YamlConfiguration data = new YamlConfiguration();
        for (Map.Entry<UUID, Integer> e : exp.entrySet()) {
            data.set(e.getKey().toString(), e.getValue());
        }
        try {
            data.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        File dataFile = new File(plugin.getDataFolder(), "progress.yml");
        if (!dataFile.exists()) return;
        YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
        for (String key : data.getKeys(false)) {
            exp.put(UUID.fromString(key), data.getInt(key));
        }
    }

    public void reset() {
        exp.clear();
        cooldowns.clear();
    }
}
