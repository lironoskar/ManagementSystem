package UnitTest;

import Domain.*;
import Service.FootballManagementSystem;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class PlayerTest {
    FootballManagementSystem system;
    User mesiU;
    Player mesi;

    @Before
    public void init() {
        system = new FootballManagementSystem();
        system.systemInit(true);
        LeagueInSeason league = system.dataReboot();
        Admin admin = (Admin) system.getAdmin().checkUserRole("Admin");
        mesiU = admin.addNewPlayer("mesi", "mesi", "mesi@mail.com", new Date(30 / 5 / 93), Player.RolePlayer.goalkeeper, 200000);
        mesi=(Player) mesiU.checkUserRole("Player");

    }

    @Test
    public void getBirthDate() {
        assertEquals(mesi.getBirthDate(),new Date(30 / 5 / 93));
    }

    @Test
    public void getRole() {
        assertEquals(mesi.getRole(),"goalkeeper");
    }

    @Test
    public void setRole() {
        mesi.setRole(Player.RolePlayer.attackingPlayer);
        assertEquals(mesi.getRole(),"attackingPlayer");

    }

    @Test
    public void getUser() {
        assertEquals(mesi.getUser().getID(),mesi.getID());
    }
}