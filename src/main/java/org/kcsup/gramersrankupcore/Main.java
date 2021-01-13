package org.kcsup.gramersrankupcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.kcsup.gramersrankupcore.commands.LobbyCommand;
import org.kcsup.gramersrankupcore.commands.PracticeCommand;
import org.kcsup.gramersrankupcore.commands.RankCommand;
import org.kcsup.gramersrankupcore.commands.UnPracticeCommand;
import org.kcsup.gramersrankupcore.ranks.Ranks;
import org.kcsup.gramersrankupcore.util.PracticeManager;
import org.kcsup.gramersrankupcore.util.RankManager;
import org.kcsup.gramersrankupcore.util.SignManager;
import org.kcsup.gramersrankupcore.util.TeamManager;

public final class Main extends JavaPlugin {

    private RankManager rankManager;
    private TeamManager teamManager;
    private SignManager signManager;

    public Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getMainScoreboard();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new ListenerClass(this), this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        rankManager = new RankManager(this);
        teamManager = new TeamManager(this);
        signManager = new SignManager(this);

        loadCommands();

        for(Player player : Bukkit.getOnlinePlayers()) {
            Ranks rank = rankManager.getRank(player);
            player.setPlayerListName(rank.getPrefix() + " " + rank.getMiddleChatColor() + player.getName());
        }

    }

    public RankManager getRankManager() { return rankManager; }
    public SignManager getSignManager() { return signManager; }

    private void loadCommands() {
        getCommand("practice").setExecutor(new PracticeCommand(this));
        getCommand("unpractice").setExecutor(new UnPracticeCommand(this));
        getCommand("rank").setExecutor(new RankCommand(this));

        getCommand("lobby").setExecutor(new LobbyCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        PracticeManager.practicePurge();
        for(Player player : PracticeManager.practicingOriginalLocation.keySet()) {
            player.sendMessage(ChatColor.RED + "You were forced out of Practice Mode due to a reload.");
        }
    }
}
