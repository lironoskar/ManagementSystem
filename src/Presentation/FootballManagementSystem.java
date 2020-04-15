package Presentation;

import Data.Database;
import Domain.*;
import Service.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class FootballManagementSystem {

    public static void main(String[] args) {
        //***data***//
        Database database = new Database();
        //***domain***//
        AssetManagement assetManagement = new AssetManagement(database);
        ComplaintManager complaintManager = new ComplaintManager(database);
        EditPersonalInfo editPersonalInfo = new EditPersonalInfo(database);
        EventReportManagement eventReportManagement = new EventReportManagement(database);
        FinanceTransactionsManagement financeTransactionsManagement = new FinanceTransactionsManagement(database);
        LeagueAndGameManagement leagueAndGameManagement = new LeagueAndGameManagement(database);
        PersonalPageManagement personalPageManagement = new PersonalPageManagement(database);
        RefereeManagement refereeManagement = new RefereeManagement(database);
        Searcher searcher = new Searcher(database);
        UserManagement userManagement = new UserManagement(database);
        //***service***//
        AssetSystem assetSystem = new AssetSystem(assetManagement);
        NotificationSystem notificationSystem = new NotificationSystem(leagueAndGameManagement, refereeManagement, assetManagement);
        FinanceTransactionsSystem financeTransactionsSystem = new FinanceTransactionsSystem(financeTransactionsManagement, notificationSystem);
        GuestSystem guestSystem = new GuestSystem(searcher, userManagement);
        PersonalPageSystem personalPageSystem = new PersonalPageSystem(personalPageManagement);
        RefereeSystem refereeSystem = new RefereeSystem(leagueAndGameManagement, refereeManagement, eventReportManagement);
        SearchSystem searchSystem = new SearchSystem(searcher);
        UnionRepresentativeSystem unionRepresentativeSystem = new UnionRepresentativeSystem(financeTransactionsManagement, leagueAndGameManagement, refereeManagement);
        UserSystem userSystem = new UserSystem(searcher, complaintManager, editPersonalInfo, personalPageManagement, userManagement, leagueAndGameManagement);
        //***presentation***//
        int id = IdGenerator.getNewId();
        Admin systemAdmin = new Admin("adminush","", "example@gmail.com");
        userSystem.addUser(systemAdmin.getID(), "Adminush1", systemAdmin);

        //**UnitTests!-NotificationSystem**//
        TeamOwner owner = new TeamOwner("Team","Owner", "a@gmail.com");
        List<TeamOwner> owners = new LinkedList<>();
        owners.add(owner);
        Field field = new Field( "jerusalem", 550);
        Team hapoel = new Team("Hapoel", null, owners, database.createPlayers(), database.createCoaches(),field);

        database.addAsset(field);
        database.addTeam(hapoel);
        PersonalPage hapoelsPage = new PersonalPage("",hapoel.getPlayers().get(0));
        database.addPage(hapoelsPage);//expected : true
        Field field2 = new Field( "TelAviv", 550);
        Team macabi = new Team ("Macabi", null,owners, database.createPlayers(),database.createCoaches(),field2);
        database.addTeam(macabi);
        database.addAsset(field2);
        Game game = new Game("G"+IdGenerator.getNewId(),new Date(120,4,25, 20,0,0), field, database.mainReferee(), database.sideReferees(),hapoel, macabi);
        Game game2 = new Game("G"+IdGenerator.getNewId(),new Date(120,4,25, 20,0,0), field, database.mainReferee(), database.sideReferees(),hapoel, macabi);
        database.addGame(game);
        database.addGame(game2);

        Fan fan = new Fan("AviLevi@gmail.com", "Avi", "Levi", "0500004544", "Yuda123");
        ReceiveAlerts receiveAlerts = new ReceiveAlerts(true, true);
        List<Game> games = new LinkedList<>();
        games.add(game);
        games.add(game2);
        System.out.println(notificationSystem.openORCloseTeam("closed", hapoel, false)); //expected : false
        System.out.println(notificationSystem.openORCloseTeam("open", hapoel, false)); //expected : true
        hapoel.setActive(false);
        System.out.println(notificationSystem.openORCloseTeam("closed", hapoel, true)); //expected : true
        notificationSystem.refereeAlertsChangeDate(game, game.getDate());
        notificationSystem.refereeAlertsChangeGameLocation(game, game.getField());
        System.out.println(notificationSystem.UserRemovalNotification(fan));//expected : false
        fan.deactivate();
        System.out.println(notificationSystem.UserRemovalNotification(fan));//expected : true
        System.out.println();
        System.out.println(financeTransactionsSystem.reportNewIncome(macabi.getTeamOwners().get(0), macabi, 200));//expected : true
        System.out.println(financeTransactionsSystem.reportNewExpanse(macabi.getTeamOwners().get(0), macabi, 300));//expected : false
    }
}
