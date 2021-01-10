package org.kcsup.gramersrankupcore.ranks;

import org.bukkit.ChatColor;

public enum Ranks {

    NA(ChatColor.translateAlternateColorCodes('&', "&f<&7NA&f>")),
    I(ChatColor.translateAlternateColorCodes('&', "&9[&2I&9]")),
    II(ChatColor.translateAlternateColorCodes('&', "&c[&6II&c]")),
    III(ChatColor.translateAlternateColorCodes('&', "&d[&5III&d]")),
    IV(ChatColor.translateAlternateColorCodes('&', "&3«[&BIV&3]»")),
    V(ChatColor.translateAlternateColorCodes('&', "&e«[&7V&e]»")),
    VI(ChatColor.translateAlternateColorCodes('&', "&8«[&4VI&8]»")),
    VII(ChatColor.translateAlternateColorCodes('&', "&a««[&eVII&a]»»")),
    VIII(ChatColor.translateAlternateColorCodes('&', "&5««[&0VIII&5]»»")),
    IX(ChatColor.translateAlternateColorCodes('&', "&6««[&7IX&6]»»")),
    X(ChatColor.translateAlternateColorCodes('&', "&0««&l[&f&lX&0&l]&0»»"));

    private String prefix;

    Ranks(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() { return prefix; }

}
