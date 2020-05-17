package Service;

import Data.Database;
import Domain.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FootballManagementSystem {
    private static Database database;
    //***service***//
    private static NotificationSystem notificationSystem;
    private static FinanceTransactionsSystem financeTransactionsSystem;
    private static GuestSystem guestSystem;
    private static PersonalPageSystem personalPageSystem ;
    private static RefereeSystem refereeSystem;
    public static UnionRepresentativeSystem unionRepresentativeSystem;
    private static UserSystem userSystem;
    private static AdminSystem adminSystem;
    private static TeamManagementSystem teamManagementSystem;
    //***presentation***//
    private static List<User> systemAdmins;
    //***External systems***//
    private static StubAccountingSystem accountingSystem;
    private static StubIsraeliTaxLawsSystem taxLawsSystem;

    public Database getDatabase()
    {
        return database;
    }

    public TeamManagementSystem getTeamManagementSystem() {
        return teamManagementSystem;
    }

    public AdminSystem getAdminSystem() {
        return adminSystem;
    }

    public UserSystem getUserSystem() {
        return userSystem;
    }

    public UnionRepresentativeSystem getUnionRepresentativeSystem() {
        return unionRepresentativeSystem;
    }

    public GuestSystem getGuestSystem() {
        return guestSystem;
    }

    public PersonalPageSystem getPersonalPageSystem() {
        return personalPageSystem;
    }

    public RefereeSystem getRefereeSystem() {
        return refereeSystem;
    }

    public NotificationSystem getNotificationSystem() {
        return notificationSystem;
    }

    public FinanceTransactionsSystem getFinanceTransactionsSystem() {
        return financeTransactionsSystem;
    }

    public static List<User> getSystemAdmins() {
        return systemAdmins;
    }

    public static StubAccountingSystem getAccountingSystem() {
        return accountingSystem;
    }

    public static StubIsraeliTaxLawsSystem getTaxLawsSystem() {
        return taxLawsSystem;
    }

    public static boolean systemInit(boolean firsTime){
        //***data***//
        database = new Database();
        if(!firsTime)
            database.loadDatabaseFromDisk("");
        //***service***//
        notificationSystem = new NotificationSystem();
        adminSystem = new AdminSystem(notificationSystem);
        financeTransactionsSystem = new FinanceTransactionsSystem(notificationSystem);
        guestSystem = new GuestSystem();
        personalPageSystem = new PersonalPageSystem();
        refereeSystem = new RefereeSystem();
        unionRepresentativeSystem = new UnionRepresentativeSystem();
        userSystem = new UserSystem();
        teamManagementSystem = new TeamManagementSystem(notificationSystem);
        //***presentation***//
        systemAdmins = new LinkedList<>();
        if(firsTime){
            User systemAdmin = UserFactory.getNewAdmin("Aa1234","adminush","","example@gmail.com");
            systemAdmins.add(systemAdmin);
        }
        else{
            systemAdmins.addAll(database.getSystemAdmins());
        }


        accountingSystem = new StubAccountingSystem();
        taxLawsSystem = new StubIsraeliTaxLawsSystem();

        return true;
    }

    public User getAdmin(){
        return systemAdmins.get(0);
    }

    public boolean connectToOuterSystems(boolean flag)
    {

        boolean con1 = accountingSystem.connect(flag);
        boolean con2 = taxLawsSystem.connect(flag);

        if (con1 && con2)
            return true;

        return false;

    }

    public static String dataReboot(){
        MailSender.setReallySend(false);
        User unionRep = UserFactory.getNewUnionRepresentative("", "","mail@mail.com");
        unionRepresentativeSystem.configureNewSeason(unionRep.getID(),2020, new Date(120, 4, 1));
        unionRepresentativeSystem.configureNewLeague(unionRep.getID(),"Haal", "3");
        String leagueInSeasonId = unionRepresentativeSystem.configureLeagueInSeason(unionRep.getID(),"Haal", "2020", "PlayTwiceWithEachTeamPolicy", "StandardScorePolicy", 300);
        Team team;
        for (int i = 0; i < 14; i++) {
            List<User> players = createPlayers();
            List<User> coaches = createCoaches();
            List<User> owners = new LinkedList<>();
            String ownerId = adminSystem.addNewTeamOwner(systemAdmins.get(0).getID(),"Team","Owner","to"+i+"@gmail.com" );
            User owner = UserFactory.getUser(ownerId);
            if(owner!=null){
                owners.add(owner);
                Field field = new Field("jerusalem","Teddy" ,550, 150000);
                //team = new Team("team"+i,owners,players,coaches, field);
                TeamOwner teamOwner = (TeamOwner)owner.checkUserRole("TeamOwner");
                teamOwner.createTeam(owner,"team"+i, players, coaches, field);
                team = teamOwner.getTeamsToManage().get(0);
                team.getBudget().addIncome(1000000000);
                unionRepresentativeSystem.addTeamToLeague(unionRep.getID(),leagueInSeasonId, team.getID());
            }

        }
        for (int i = 0; i <10 ; i++) {
            String refId = mainReferee(unionRep);
            unionRepresentativeSystem.assignRefToLeague(unionRep.getID(),leagueInSeasonId, refId);
        }
        return leagueInSeasonId;
    }


    public static String mainReferee(User unionRep) {
        return unionRepresentativeSystem.appointReferee(unionRep.getID(),"referee", "",+IdGenerator.getNewId()+"@gmail.com", "referees");
    }
    public static List<User> createCoaches() {
        String CoachId = adminSystem.addNewCoach(systemAdmins.get(0).getID(),"coach1", "coach",+IdGenerator.getNewId()+"@gmail.com", "UEFA_A", "main", 1500);
        User Coach = UserFactory.getUser(CoachId);
        List<User> coaches = new LinkedList<>();
        coaches.add(Coach);
        return coaches;
    }
    public static List<User> createPlayers() {
        List<User> players = new LinkedList<>();
        User player;
        for (int i = 0; i <12 ; i++) {
            String playerId = adminSystem.addNewPlayer(systemAdmins.get(0).getID(), "player"+i, "...", "mail"+IdGenerator.getNewId()+"@gmail.com", new Date(99, 1, 1), "attackingPlayer", 3500);
            player = UserFactory.getUser(playerId);
            if(player!=null){
                players.add(player);
            }
        }
        return players;
    }
    private static void printList(List<Game> allGames) {
        for(Game game: allGames){
            System.out.println(game.toString());
        }
    }

    public static List<Date> getDates() {
        LinkedList<Date> dates = new LinkedList<>();
        for (int i = 1; i < 30; i++) {
            dates.add(new Date (120, 5, i, 20, 0));
        }
        for (int i = 1; i < 31; i++) {
            dates.add(new Date (120, 6, i, 20, 0));
        }
        for (int i = 1; i <31 ; i++) {
            dates.add(new Date (120, 7, i, 20, 0));
        }
        for (int i = 1; i <30 ; i++) {
            dates.add(new Date (120, 8, i, 20, 0));
        }
        for (int i = 1; i <31 ; i++) {
            dates.add(new Date (120, 9, i, 20, 0));
        }
        for (int i = 1; i <30 ; i++) {
            dates.add(new Date (120, 10, i, 20, 0));
        }
        for (int i = 1; i <31 ; i++) {
            dates.add(new Date (120, 11, i, 20, 0));
        }
        return dates;
    }

}

