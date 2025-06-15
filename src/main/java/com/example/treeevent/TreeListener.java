package com.example.treeevent;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TreeListener implements Listener {
    private final TreeManager manager;
    private final TreeVisualizer visualizer;

    public TreeListener(TreeManager manager, TreeVisualizer visualizer) {
        this.manager = manager;
        this.visualizer = visualizer;
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof ArmorStand stand)) return;
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getHand() == null ? EquipmentSlot.HAND : event.getHand());
        if (item == null || item.getType() != Material.WATER_BUCKET) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName() || !meta.getDisplayName().equalsIgnoreCase("Water Bucket")) return;
        event.setCancelled(true);
        visualizer.decorate(stand);
        if (manager.onCooldown(player.getUniqueId())) return;
        manager.setCooldown(player.getUniqueId(), 1000);
        manager.addExpAsync(player, 10);
    }
}
