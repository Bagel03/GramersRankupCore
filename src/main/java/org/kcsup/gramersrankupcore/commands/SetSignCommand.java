package org.kcsup.gramersrankupcore.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kcsup.gramersrankupcore.Main;
import org.kcsup.gramersrankupcore.ranks.Ranks;

public class SetSignCommand implements CommandExecutor {

    private Main main;

    public SetSignCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                for (Ranks rank : Ranks.values()) {
                    if (rank.name().equals(args[0].toUpperCase())) {
                        main.getSignManager().setSignLocation(rank, new Location(player.getWorld(),
                                -642,
                                22,
                                -39));
                    }
                }
            }
        }
        return false;
    }
}
