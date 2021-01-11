package org.kcsup.gramersrankupcore.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.kcsup.gramersrankupcore.Main;
import org.kcsup.gramersrankupcore.ranks.Ranks;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class RankManager {

    public File file;
    public YamlConfiguration yaml;

    private Main main;

    public RankManager(Main main) {
        this.main = main;

        file = new File(main.getDataFolder(), "rankData.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        yaml = YamlConfiguration.loadConfiguration(file);

        if(!yaml.contains("Players")) {
            yaml.createSection("Players");

            try {
                yaml.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setRank(Player player, Ranks rank) {
        yaml.set("Players." + player.getUniqueId().toString(), rank.name());

        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.setDisplayName(rank.getPrefix() + " " + rank.getMiddleChatColor() + player.getName());
        player.setPlayerListName(rank.getPrefix() + " " + rank.getMiddleChatColor() + player.getName());

        Team team = main.scoreboard.getTeam(rank.getTeamName());
        team.addPlayer(player);
    }

    public Ranks getRank(Player player) {
        return Ranks.valueOf(yaml.getString("Players." + player.getUniqueId().toString()));
    }
    public Ranks getRank(UUID uuid) {
        return Ranks.valueOf(yaml.getString("Players." + uuid.toString()));
    }


}
