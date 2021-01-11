package org.kcsup.gramersrankupcore.util;

import org.bukkit.scoreboard.Team;
import org.kcsup.gramersrankupcore.Main;
import org.kcsup.gramersrankupcore.ranks.Ranks;

public class TeamManager {

    private Main main;

    public TeamManager(Main main) {
        this.main = main;

        for(Ranks rank : Ranks.values()) {
            Team team = main.scoreboard.getTeam(rank.getTeamName());
            if(team == null) {
                main.scoreboard.registerNewTeam(rank.getTeamName());
            }
        }
    }

}
