package org.kcsup.gramersrankupcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.kcsup.gramersrankupcore.ranks.Ranks;
import org.kcsup.gramersrankupcore.util.PracticeManager;

public class ListenerClass implements Listener {

    private Main main;

    public ListenerClass(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent e) {
        if(e.getClickedBlock() != null) {
            Block block = e.getClickedBlock();
            if(block.getType() == Material.SIGN_POST ||
            block.getType() == Material.WALL_SIGN) {

            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(e.getItem() != null) {
            ItemStack itemStack = e.getItem();
            if(itemStack.getType() == Material.SLIME_BALL &&
            itemStack.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Reset") &&
            PracticeManager.isPracticing(player)) {
                Location tpBack = PracticeManager.practicingOriginalLocation.get(player);
                player.teleport(tpBack);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        Player player = (Player) e.getPlayer();
        if(PracticeManager.isPracticing(player)) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot open your inventory in Practice Mode!");
        }
    }

    @EventHandler
    public void onPLayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(!main.getRankManager().yaml.contains("Players." + player.getUniqueId().toString())) {
            main.getRankManager().setRank(player, Ranks.NA);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        Ranks rank = main.getRankManager().getRank(player.getUniqueId());

        for(Player online : e.getRecipients()) {
            online.sendMessage(rank.getPrefix() + " " + ChatColor.WHITE + player.getName() + ": " + e.getMessage());
        }

        e.setCancelled(true);
    }



}
