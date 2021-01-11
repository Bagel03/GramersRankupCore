package org.kcsup.gramersrankupcore.util;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kcsup.gramersrankupcore.Main;
import org.kcsup.gramersrankupcore.ranks.Ranks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignManager {

    public HashMap<String, Location> tipSignLocations = new HashMap<>();

    public File file;
    public YamlConfiguration yaml;
    public Main main;

    public SignManager(Main main) {
        this.main = main;

        setupLevelSigns();

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

    public void setupLevelSigns() {
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
