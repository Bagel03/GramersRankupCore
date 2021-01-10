package org.kcsup.gramersrankupcore.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class PracticeManager {

    public static HashMap<Player, Location> practicingOriginalLocation = new HashMap<>();

    public static void practice(Player player) {
        if(!isPracticing(player)) {
            ItemStack practiceExitItem = new ItemStack(Material.SLIME_BALL);
            ItemMeta itemMeta = practiceExitItem.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GREEN + "Reset");
            practiceExitItem.setItemMeta(itemMeta);

            practicingOriginalLocation.put(player, player.getLocation());
            player.getInventory().setItem(8, practiceExitItem);

            player.sendMessage(ChatColor.GREEN + "Now entered Practice Mode!");
        } else {
            player.sendMessage(ChatColor.RED + "You are already in Practice Mode!");
        }
    }

    public static void unPractice(Player player) {
        if(isPracticing(player)) {
            player.teleport(practicingOriginalLocation.get(player));
            player.getInventory().setItem(8, null);
            practicingOriginalLocation.remove(player);
            player.sendMessage(ChatColor.GREEN + "Removed from Practice Mode!");
        } else {
            player.sendMessage(ChatColor.RED + "You aren't currently practicing!");
        }
    }

    public static void practicePurge() {
        for(Player player : practicingOriginalLocation.keySet()) {
            Location location = PracticeManager.practicingOriginalLocation.get(player);
            player.teleport(location);
            player.getInventory().setItem(8, null);
            PracticeManager.practicingOriginalLocation.remove(player);
        }
    }

    public static boolean isPracticing(Player player) {
        return practicingOriginalLocation.containsKey(player);
    }

}
