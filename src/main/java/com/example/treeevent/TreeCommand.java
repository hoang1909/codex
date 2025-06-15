package com.example.treeevent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TreeCommand implements CommandExecutor {
    private final TreeManager manager;

    public TreeCommand(TreeManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "/treeevent <stats|reset|end>");
            return true;
        }
        String sub = args[0].toLowerCase();
        if (sub.equals("stats")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Must be player");
                return true;
            }
            int exp = manager.getExp(player.getUniqueId());
            int lvl = manager.getLevel(player.getUniqueId());
            player.sendMessage(ChatColor.YELLOW + "EXP: " + exp + " Level: " + lvl);
            return true;
        }
        if (sub.equals("reset")) {
            if (!sender.hasPermission("treeevent.admin")) {
                sender.sendMessage(ChatColor.RED + "No permission");
                return true;
            }
            manager.reset();
            sender.sendMessage(ChatColor.GREEN + "Tree event data reset");
            return true;
        }
        if (sub.equals("end")) {
            if (!sender.hasPermission("treeevent.admin")) {
                sender.sendMessage(ChatColor.RED + "No permission");
                return true;
            }
            Bukkit.broadcastMessage(ChatColor.GOLD + "Tree event ended!");
            return true;
        }
        return false;
    }
}
