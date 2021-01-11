package org.kcsup.gramersrankupcore.ranks;

import org.bukkit.ChatColor;

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

    Ranks(String prefix, String teamName,ChatColor middleChatColor) {
        this.prefix = prefix;
        this.teamName = teamName;
        this.middleChatColor = middleChatColor;
    }

    public String getPrefix() { return prefix; }

    public String getTeamName() { return teamName; }

    public ChatColor getMiddleChatColor() { return middleChatColor; }
}
