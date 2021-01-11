package org.kcsup.gramersrankupcore.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.kcsup.gramersrankupcore.Main;
import org.kcsup.gramersrankupcore.ranks.Ranks;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

        // ------------------------------ NMS Testing

//        changeName(player, rank.getPrefix() + " " + rank.getMiddleChatColor() + player.getName());

        // ------------------------------

        Team team = main.scoreboard.getTeam(rank.getTeamName());
        team.addPlayer(player);
    }

    public void rankUp(Player player) {
        Ranks currentRank = getRank(player);
        int indexForCurrentRank = ArrayUtils.indexOf(Ranks.values(), currentRank);
        Ranks nextRank = Ranks.values()[indexForCurrentRank + 1];
        setRank(player, nextRank);

        for(Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(ChatColor.GREEN + player.getName() + " just Ranked Up to rank " + nextRank.getMiddleChatColor() + nextRank.name() + ChatColor.GREEN + "!");
        }
    }

    public Ranks getRank(Player player) {
        return Ranks.valueOf(yaml.getString("Players." + player.getUniqueId().toString()));
    }
    public Ranks getRank(UUID uuid) {
        return Ranks.valueOf(yaml.getString("Players." + uuid.toString()));
    }

    public boolean isHigherRank(Ranks rank1, Ranks rank2) {
        int indexOfRank1 = ArrayUtils.indexOf(Ranks.values(), rank1);
        int indexOfRank2 = ArrayUtils.indexOf(Ranks.values(), rank2);
        return indexOfRank1 > indexOfRank2;
    }

    // ------------------------------------

//    public void changeName(Player p, String newName){
//        for(Player pl : Bukkit.getOnlinePlayers()){
//            //REMOVES THE PLAYER
//            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)p).getHandle()));
//            //CHANGES THE PLAYER'S GAME PROFILE
//            GameProfile gp = ((CraftPlayer)p).getProfile();
//            try {
//                Field nameField = GameProfile.class.getDeclaredField("name");
//                nameField.setAccessible(true);
//
//                Field modifiersField = Field.class.getDeclaredField("modifiers");
//                modifiersField.setAccessible(true);
//                modifiersField.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL);
//
//                nameField.set(gp, newName);
//            } catch (IllegalAccessException | NoSuchFieldException ex) {
//                throw new IllegalStateException(ex);
//            }
//            //ADDS THE PLAYER
//            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)p).getHandle()));
//            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(p.getEntityId()));
//            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(((CraftPlayer)p).getHandle()));
//        }
//    }

}
