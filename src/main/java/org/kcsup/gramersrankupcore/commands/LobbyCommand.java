package org.kcsup.gramersrankupcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kcsup.gramersrankupcore.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LobbyCommand implements CommandExecutor {

    private Main main;

    public LobbyCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                for(String keys : main.getConfig().getKeys(true)) {
                    if(keys.startsWith("Lobbies") && StringUtils.countMatches(keys, ".") == 1) {
                        String lobbyName = keys.replace("Lobbies.", "");
                        if(lobbyName.equalsIgnoreCase(args[0])) {
                            player.teleport(new Location(Bukkit.getWorld(main.getConfig().getString(keys + ".Spawn.World")),
                                    main.getConfig().getDouble(keys + ".Spawn.X"),
                                    main.getConfig().getDouble(keys + ".Spawn.Y"),
                                    main.getConfig().getDouble(keys + ".Spawn.Z"),
                                    main.getConfig().getInt(keys + ".Spawn.Yaw"),
                                    main.getConfig().getInt(keys + ".Spawn.Pitch")));
                            return false;
                        }
                    }
                }

                if(args[0].equalsIgnoreCase("list")) {
                    player.sendMessage(ChatColor.GREEN + "Lobbies:");
                    for(String keys : main.getConfig().getKeys(true)) {
                        if (keys.startsWith("Lobbies") && StringUtils.countMatches(keys, ".") == 1) {
                            String lobbyName = keys.replace("Lobbies.", "");
                            player.sendMessage(ChatColor.GREEN + lobbyName);
                        }
                    }
                } else if(args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(ChatColor.GREEN + "Usage:\n/lobby <optional: lobbyName>");
                }
            } else if(args.length == 0) {
                String lobbyPath = "Lobbies.Lobby";
                player.teleport(new Location(Bukkit.getWorld(main.getConfig().getString(lobbyPath + ".Spawn.World")),
                        main.getConfig().getDouble(lobbyPath + ".Spawn.X"),
                        main.getConfig().getDouble(lobbyPath + ".Spawn.Y"),
                        main.getConfig().getDouble(lobbyPath + ".Spawn.Z"),
                        main.getConfig().getInt(lobbyPath + ".Spawn.Yaw"),
                        main.getConfig().getInt(lobbyPath + ".Spawn.Pitch")));
            }
        }
        return false;
    }
}
