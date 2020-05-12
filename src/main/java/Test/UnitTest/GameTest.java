package UnitTest;

import Domain.*;
import Service.FootballManagementSystem;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {
    Game game;
    FootballManagementSystem system;
    User user;
    Fan fan;
    ReceiveAlerts receiveAlerts;
    Team team0;
    Team team1;

    @Before
public void init(){
    system = new FootballManagementSystem();
    system.systemInit(true);
    LeagueInSeason league = system.dataReboot();
    team0 = league.getTeams().get(0);
    team1 = league.getTeams().get(1);
    Field field = new Field("Tel-Aviv","Bloomfield", 10000, 150000);
    Referee mainReferee = league.getReferees().get(0);
    List<Referee> sideReferees = new LinkedList<>();
    sideReferees.add(league.getReferees().get(1));
    sideReferees.add(league.getReferees().get(2));
    game = new Game(new Date(120, 4, 25, 20, 0), field, mainReferee, sideReferees, team0, team1, league);
        Guest guest = new Guest();
        user = guest.register("fan@gmail.com", "Aa1234", "fan", "fan", "0500001234", "yosef23");
        fan = (Fan) user.checkUserRole("Fan");
receiveAlerts=new ReceiveAlerts(true,false);
    }

    @Test
    public void addFanForNotifications() {
        assertTrue(game.addFanForNotifications(fan,receiveAlerts));
    }

    @Test
    public void getEventReportString() {
        assertNotNull(game.getEventReportString());
    }

    @Test
    public void setNews() {
        game.setNews("update news to observers");
        assertEquals(1, game.getMainReferee().getMessageBox().size(), 0);
    }

    @Test
    public void getId() {
        assertNotNull(game.getId());
    }

    @Test
    public void getDate() {
        assertEquals(game.getDate(),new Date(120, 4, 25, 20, 0));
    }


    @Test
    public void hostScore() {
        game.setHostScore(2);
        assertEquals(game.hostScore(),2);
    }

    @Test
    public void guestScore() {
        game.setGuestScore(3);
        assertEquals(game.guestScore(),3);
    }

    @Test
    public void setHostScore() {
        game.setHostScore(2);
        assertEquals(game.hostScore(),2);
    }

    @Test
    public void setGuestScore() {
        game.setGuestScore(3);
        assertEquals(game.guestScore(),3);
    }

    @Test
    public void getField() {
        assertEquals(game.getField().getName(),"Bloomfield");
    }

    @Test
    public void getMainReferee() {
        assertNotNull(game.getMainReferee());
    }

    @Test
    public void getSideReferees() {
        assertEquals(game.getSideReferees().size(),2);
    }

    @Test
    public void getHostTeam() {
        assertEquals(game.getHostTeam().getName(),team0.getName());
    }

    @Test
    public void getGuestTeam() {
        assertEquals(game.getGuestTeam().getName(),team1.getName());

    }

    @Test
    public void getName(){
        assertEquals(game.getName(), game.getHostTeam().getName() + " and "+ game.getGuestTeam().getName());
    }

    @Test
    public void getEventReport() {
        assertEquals(game.getEventReport().getEvents().size(),0);
    }

    @Test
    public void setDate() {
        game.setDate(new Date(2020, 5, 25, 20, 0));
        assertEquals(game.getDate(),new Date(2020, 5, 25, 20, 0));
    }

    @Test
    public void setField() {
        Field field1 = new Field("beer sheva","terner", 10000, 150000);
        game.setField(field1);
        assertEquals(game.getField().getName(),field1.getName());
    }

    @Test
    public void setNewsFromReferee() {
        List<Game> gameList = new LinkedList<>();
        gameList.add(game);
        fan.followGames(gameList, new ReceiveAlerts(true, true));
        assertEquals(fan.getMessageBox().size(), 0);
        Event event = new Event(Event.EventType.RedCard, 70, "description");
        game.setNewsFromReferee(event);
        assertEquals(fan.getMessageBox().size(), 1);
    }
}