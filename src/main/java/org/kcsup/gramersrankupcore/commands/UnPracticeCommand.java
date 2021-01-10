package org.kcsup.gramersrankupcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kcsup.gramersrankupcore.Main;
import org.kcsup.gramersrankupcore.util.PracticeManager;

public class
UnPracticeCommand implements CommandExecutor {

    private Main main;

    public UnPracticeCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            PracticeManager.unPractice(player);
        }
        return false;
    }
}
