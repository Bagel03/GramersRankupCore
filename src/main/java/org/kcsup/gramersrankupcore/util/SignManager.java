package org.kcsup.gramersrankupcore.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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

    public File file;
    public YamlConfiguration yaml;
    public Main main;

    public SignManager(Main main) {
        this.main = main;

        file = new File(main.getDataFolder(), "signData.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        yaml = YamlConfiguration.loadConfiguration(file);

        if(!yaml.contains("Signs")) {
            yaml.createSection("Signs");

            try {
                yaml.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(Ranks rank : Ranks.values()){
            String rankName = rank.name();

            if(!yaml.contains("Signs." + rankName)) {
                yaml.createSection("Signs." + rankName);

                try {
                    yaml.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
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
