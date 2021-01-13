package org.kcsup.gramersrankupcore.ranks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.kcsup.gramersrankupcore.Main;

public enum Ranks {

    I(ChatColor.translateAlternateColorCodes('&', "&9[&2I&9]"), "A", ChatColor.DARK_GREEN),
    II(ChatColor.translateAlternateColorCodes('&', "&a[&6II&a]"), "9", ChatColor.GOLD),
    III(ChatColor.translateAlternateColorCodes('&', "&d[&5III&d]"), "8", ChatColor.DARK_PURPLE),
    IV(ChatColor.translateAlternateColorCodes('&', "&3«[&BIV&3]»"), "7", ChatColor.AQUA),
    V(ChatColor.translateAlternateColorCodes('&', "&e«[&7V&e]»"), "6", ChatColor.GRAY),
    VI(ChatColor.translateAlternateColorCodes('&', "&8«[&4VI&8]»"), "5", ChatColor.DARK_RED),
    VII(ChatColor.translateAlternateColorCodes('&', "&a««[&eVII&a]»»"), "4", ChatColor.YELLOW),
    VIII(ChatColor.translateAlternateColorCodes('&', "&5««[&0VIII&5]»»"), "3", ChatColor.DARK_PURPLE),
    IX(ChatColor.translateAlternateColorCodes('&', "&6««[&8IX&6]»»"), "2", ChatColor.DARK_GRAY),
    X(ChatColor.translateAlternateColorCodes('&', "&0««&l[&f&lX&0&l]&0»»"), "1", ChatColor.WHITE),
    XI(ChatColor.translateAlternateColorCodes('&', "&e««&6&l[&c&lXI&6&l]&e»»"), "0", ChatColor.RED);

    private String prefix;
    private String teamName;
    private ChatColor middleChatColor;
    private Location spawn;
    private Location rankUpSignLoc;
    private Location rankUpLobbyLoc;

    Ranks(String prefix, String teamName,ChatColor middleChatColor) {
        this.prefix = prefix;
        this.teamName = teamName;
        this.middleChatColor = middleChatColor;

        FileConfiguration config = Bukkit.getPluginManager().getPlugin("GramersRankupCore").getConfig();
        if(config.contains("Ranks." + name())) {
            String spawnPath = "Ranks." + name() + ".Spawn";
            String signPath = "Ranks." + name() + ".Sign";
            this.spawn = new Location(Bukkit.getWorld(config.getString(spawnPath + ".World")),
                    config.getDouble(spawnPath + ".X"),
                    config.getDouble(spawnPath + ".Y"),
                    config.getDouble(spawnPath + ".Z"),
                    config.getInt(spawnPath + ".Yaw"),
                    config.getInt(spawnPath + ".Pitch"));
            this.rankUpSignLoc = new Location(Bukkit.getWorld(config.getString(signPath + ".World")),
                    config.getDouble(signPath + ".X"),
                    config.getDouble(signPath + ".Y"),
                    config.getDouble(signPath + ".Z"));
        }
        if(config.contains("Lobbies.RankUpLobby.Signs." + name())) {
            String lobbySignPath = "Lobbies.RankUpLobby.Signs." + name();
            this.rankUpLobbyLoc = new Location(Bukkit.getWorld(config.getString(lobbySignPath + ".World")),
                    config.getDouble(lobbySignPath + ".X"),
                    config.getDouble(lobbySignPath + ".Y"),
                    config.getDouble(lobbySignPath + ".Z"));
        }
    }

    public String getPrefix() { return prefix; }

    public String getTeamName() { return teamName; }

    public ChatColor getMiddleChatColor() { return middleChatColor; }

    public Location getSpawn() { return spawn; }
    public Location getRankUpSignLoc() { return rankUpSignLoc; }
    public Location getRankUpLobbyLoc() { return rankUpLobbyLoc; }
}
