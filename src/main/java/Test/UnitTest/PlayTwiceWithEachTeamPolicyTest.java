package UnitTest;

import Data.Database;
import Domain.*;
import Service.FootballManagementSystem;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayTwiceWithEachTeamPolicyTest {

    @Test
    public void assignGames() {
        FootballManagementSystem system;
        PlayTwiceWithEachTeamPolicy one = new PlayTwiceWithEachTeamPolicy();
        system = new FootballManagementSystem();
        system.systemInit(true);
        LeagueInSeason haal =system.dataReboot();
        List <Team> teams = haal.getTeams();
        List<Date> dates = system.getDates();
        List<Game> games = one.assignGames( dates, haal);
        assertNotNull(games);
        assertEquals((teams.size())*(teams.size()-1), games.size());
    }
}