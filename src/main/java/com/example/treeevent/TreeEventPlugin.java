package com.example.treeevent;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class TreeEventPlugin extends JavaPlugin {
    private TreeManager treeManager;
    private TreeVisualizer visualizer;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResource("exp_levels.yml", false);
        saveResource("rewards.yml", false);

        treeManager = new TreeManager(this);
        treeManager.loadData();

        visualizer = new TreeVisualizer(this);
        Bukkit.getPluginManager().registerEvents(new TreeListener(treeManager, visualizer), this);
        getCommand("treeevent").setExecutor(new TreeCommand(treeManager));

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new TreeExpansion().register();
        }
    }

    @Override
    public void onDisable() {
        treeManager.saveData();
    }

    private class TreeExpansion extends PlaceholderExpansion {
        @Override
        public @NotNull String getIdentifier() {
            return "treeevent";
        }

        @Override
        public @NotNull String getAuthor() {
            return "Codex";
        }

        @Override
        public @NotNull String getVersion() {
            return getDescription().getVersion();
        }

        @Override
        public String onPlaceholderRequest(Player player, String params) {
            if (player == null) return "";
            if (params.equalsIgnoreCase("level")) {
                return String.valueOf(treeManager.getLevel(player.getUniqueId()));
            }
            return null;
        }
    }
}
