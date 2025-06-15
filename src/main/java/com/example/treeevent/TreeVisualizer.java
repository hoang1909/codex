package com.example.treeevent;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TreeVisualizer {
    private final TreeEventPlugin plugin;
    private final Set<ArmorStand> decorated = new HashSet<>();

    public TreeVisualizer(TreeEventPlugin plugin) {
        this.plugin = plugin;
    }

    public void decorate(ArmorStand base) {
        if (decorated.contains(base)) return;
        FileConfiguration cfg = plugin.getConfig();
        ConfigurationSection section = cfg.getConfigurationSection("tree-visual");
        if (section == null || !section.getBoolean("enabled", true)) return;

        List<?> layers = section.getList("layers");
        if (layers != null) {
            for (Object obj : layers) {
                if (!(obj instanceof ConfigurationSection layer)) continue;
                String matName = layer.getString("material", "AIR");
                Material mat = Material.matchMaterial(matName);
                double y = layer.getDouble("y-offset", 0);
                ArmorStand part = base.getWorld().spawn(base.getLocation().clone().add(0, y, 0), ArmorStand.class);
                part.setVisible(false);
                part.setGravity(false);
                part.setMarker(true);
                if (mat != null) {
                    part.getEquipment().setHelmet(new ItemStack(mat));
                }
                decorated.add(part);
            }
        }

        if (section.getBoolean("glow", false)) {
            base.setGlowing(true);
        }

        ConfigurationSection particle = section.getConfigurationSection("particle-effect");
        if (particle != null && particle.getBoolean("enabled", false)) {
            String typeName = particle.getString("type", "VILLAGER_HAPPY");
            Particle type;
            try {
                type = Particle.valueOf(typeName.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                type = Particle.VILLAGER_HAPPY;
            }
            int interval = particle.getInt("interval-ticks", 200);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (base.isDead()) {
                        cancel();
                        return;
                    }
                    base.getWorld().spawnParticle(type, base.getLocation().add(0, 1, 0), 1);
                }
            }.runTaskTimer(plugin, interval, interval);
        }

        decorated.add(base);
    }
}
