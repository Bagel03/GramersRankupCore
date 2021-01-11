package org.kcsup.gramersrankupcore.commands;

import org.apache.commons.lang.enums.EnumUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kcsup.gramersrankupcore.Main;
import org.kcsup.gramersrankupcore.ranks.Ranks;

import java.util.Arrays;
import java.util.Locale;

public class
RankCommand implements CommandExecutor {

    private Main main;

    public RankCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp()) {
            if(args.length == 2) {
                Player playerToModify = Bukkit.getOfflinePlayer(args[0]).getPlayer();
                for(Ranks rank : Ranks.values()) {
                    if(rank.name().equals(args[1].toUpperCase())) {
                        Ranks rankToChangeTo = Ranks.valueOf(args[1].toUpperCase());
                        main.getRankManager().setRank(playerToModify, rankToChangeTo);

                        sender.sendMessage(ChatColor.GREEN + "Changed " + playerToModify.getName() + "'s " +
                                "rank to " + rankToChangeTo.name() + "!");
                        if(playerToModify.isOnline()) {
                            playerToModify.sendMessage(ChatColor.GREEN + sender.getName() + " changed your rank to "
                                    + rankToChangeTo.name() + ".");
                        }
                    }
                }
            } else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("list")) {
                    sender.sendMessage("Current Ranks:");
                    for(Ranks rank : Ranks.values()) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',rank.name() + " ( " + rank.getPrefix() + " &f)"));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid usage! (/rank <player> <rank>)");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be OP to use this command!");
        }
        return false;
    }
}
