package org.kcsup.gramersrankupcore.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.kcsup.gramersrankupcore.Main;
import org.kcsup.gramersrankupcore.ranks.Ranks;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SignManager {

    public HashMap<String, Location> tipSignLocations = new HashMap<>();

    public File file;
    public YamlConfiguration yaml;
    public Main main;

    public SignManager(Main main) {
        this.main = main;

        setupRankSigns();
        setupLobbySigns();

//        file = new File(main.getDataFolder(), "signData.yml");
//
//        if(!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        yaml = YamlConfiguration.loadConfiguration(file);
//
//        if(!yaml.contains("Signs")) {
//            yaml.createSection("Signs");
//
//            try {
//                yaml.save(file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        for(Ranks rank : Ranks.values()){
//            String rankName = rank.name();
//
//            if(!yaml.contains("Signs." + rankName)) {
//                yaml.createSection("Signs." + rankName);
//
//                try {
//                    yaml.save(file);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    public void setupLobbySigns() {
        for(String keys : main.getConfig().getKeys(true)) {
            if(keys.startsWith("Lobbies.Lobby.Signs") && StringUtils.countMatches(keys, ".") == 3) {
                Location signLocation = new Location(Bukkit.getWorld(main.getConfig().getString(keys + ".Sign.World")),
                        main.getConfig().getDouble(keys + ".Sign.X"),
                        main.getConfig().getDouble(keys + ".Sign.Y"),
                        main.getConfig().getDouble(keys + ".Sign.Z"));
                if(signLocation.getBlock().getType() == Material.SIGN_POST ||
                signLocation.getBlock().getType() == Material.WALL_SIGN) {
                    Sign sign = (Sign) signLocation.getBlock().getState();
                    sign.setLine(0, ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(keys + ".Sign.Line1")));
                    sign.setLine(1, ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(keys + ".Sign.Line2")));
                    sign.setLine(2, ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(keys + ".Sign.Line3")));
                    sign.setLine(3, ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(keys + ".Sign.Line4")));
                    sign.update();
                }
                for(Player player : Bukkit.getOnlinePlayers()) {
                    String newKey = keys.replace("Lobbies.Lobby.Signs.", "");
                    player.sendMessage(newKey);
                }
            }
        }
    }

    public void isLobbySignLocation(Location location) {

    }

    public void setupRankSigns() {
        for(Ranks rank : Ranks.values()) {
            if(main.getConfig().contains("Ranks." + rank.name())) {
                Location signLocation = rank.getRankUpSignLoc();
                if (signLocation.getBlock().getType() == Material.WALL_SIGN ||
                        signLocation.getBlock().getType() == Material.SIGN_POST) {
                    int index = ArrayUtils.indexOf(Ranks.values(), rank);
                    Ranks nextRank = Ranks.values()[index + 1];
                    Sign sign = (Sign) signLocation.getBlock().getState();
                    sign.setLine(0, ChatColor.GREEN + "You Completed");
                    sign.setLine(1, ChatColor.GREEN + "Rank " + rank.getMiddleChatColor() + rank.name() + ChatColor.GREEN + "!");
                    sign.setLine(2, ChatColor.AQUA + "Click this Sign to");
                    sign.setLine(3, ChatColor.AQUA + "Rank Up to " + nextRank.getMiddleChatColor() + nextRank.name() + ChatColor.AQUA + "!");
                    sign.update();
                }
            }

            if(main.getConfig().contains("Lobbies.RankUpLobby.Signs." + rank.name())) {
                Location rankUpLobbyLocation = rank.getRankUpLobbyLoc();
                if (rankUpLobbyLocation.getBlock().getType() == Material.WALL_SIGN ||
                        rankUpLobbyLocation.getBlock().getType() == Material.SIGN_POST) {
                    Sign rankSign = (Sign) rankUpLobbyLocation.getBlock().getState();
                    rankSign.setLine(1, "Warp To:");
                    rankSign.setLine(2, "Rank " + rank.getMiddleChatColor() + rank.name());
                    rankSign.update();
                }
            }
        }
    }

    public void setSignLocation(Ranks rank, Location location) {
        yaml.set("Signs." + rank.name(), location);

        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
