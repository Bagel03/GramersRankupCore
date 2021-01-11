package org.kcsup.gramersrankupcore;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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

        if(e.getAction() == Action.PHYSICAL) {
            if(e.getClickedBlock().getType() == Material.SOIL) {
                e.setCancelled(true);
            }
        }

        if(e.getClickedBlock() != null) {
            Block block = e.getClickedBlock();
            if(block.getType() == Material.SIGN_POST ||
            block.getType() == Material.WALL_SIGN) {
                Ranks rank = main.getRankManager().getRank(player);
                if(main.getSignManager().rankLevelSignLocations.containsKey(rank)) {
                    int rankIndex = ArrayUtils.indexOf(Ranks.values(), rank);
                    Location rankLocation = main.getSignManager().rankLevelSignLocations.get(rank);
                    if(block.getLocation().getWorld() == rankLocation.getWorld() &&
                    block.getLocation().getX() == rankLocation.getX() &&
                    block.getLocation().getY() == rankLocation.getY() &&
                    block.getLocation().getZ() == rankLocation.getZ()) {
                        Ranks nextRank = Ranks.values()[rankIndex + 1];
                        player.teleport(main.getSignManager().rankSpawnLocations.get(nextRank));
                        main.getRankManager().rankUp(player);
                    } else {
                        if(main.getSignManager().rankLevelSignLocations.containsValue(block.getLocation())) {
                            player.sendMessage("pog");
                            int locationIndex = ArrayUtils.indexOf(main.getSignManager().rankLevelSignLocations.values().toArray(),
                                    block.getLocation());
                            Ranks rankFromLocation = Ranks.values()[locationIndex];
                            Ranks nextRankFromLocation = (Ranks) main.getSignManager().rankLevelSignLocations.keySet().toArray()[locationIndex + 1];
                            if(main.getRankManager().isHigherRank(Ranks.III, Ranks.I)) {
                                player.sendMessage("works");
                                player.teleport(main.getSignManager().rankSpawnLocations.get(nextRankFromLocation));
                            }
                        }
                    }
                }
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
            main.getRankManager().setRank(player, Ranks.I);
        }

        Ranks rank = main.getRankManager().getRank(player);
        player.setPlayerListName(rank.getPrefix() + " " + rank.getMiddleChatColor() + player.getName());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        Ranks rank = main.getRankManager().getRank(player.getUniqueId());

        String message = ChatColor.translateAlternateColorCodes('&', e.getMessage());

        for(Player online : e.getRecipients()) {
            if(message.contains("@" + online.getName())) {
                String newMessage = message.replace("@" + online.getName(), ChatColor.YELLOW + "@" + online.getName() + ChatColor.WHITE);
                online.playSound(online.getLocation(), Sound.NOTE_PLING, 10f, 1f);
                online.sendMessage(rank.getPrefix() + " " + rank.getMiddleChatColor() + player.getName() + ChatColor.WHITE + ": " + newMessage);
            } else if(message.toLowerCase().contains("@" + online.getName().toLowerCase())) {
                String newMessage = message.replace("@" + online.getName().toLowerCase(), ChatColor.YELLOW + "@" + online.getName() + ChatColor.WHITE);
                online.playSound(online.getLocation(), Sound.NOTE_PLING, 10f, 1f);
                online.sendMessage(rank.getPrefix() + " " + rank.getMiddleChatColor() + player.getName() + ChatColor.WHITE + ": " + newMessage);
            } else {
                online.sendMessage(rank.getPrefix() + " " + rank.getMiddleChatColor() + player.getName() + ChatColor.WHITE + ": " + message);
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if(e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
            } else if(e.getCause() == EntityDamageEvent.DamageCause.FIRE ||
                    e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                player.setFireTicks(0);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockChange(BlockFromToEvent e) {
        if(e.getBlock().getType() == Material.WATER ||
        e.getBlock().getType() == Material.STATIONARY_WATER) {
            e.setCancelled(true);
        }
    }

}
