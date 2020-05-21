package Data;
import Domain.*;

import java.security.MessageDigest;
import java.util.*;


public class Database //maybe generalize with interface? //for now red layer
{
    private static HashMap<String,String> mailsAndPasswords; //- <mail, encryptedPassword??>
    private static HashMap<String,String> mailsAndUserID; //- <mail, userId>
    private static HashMap<String,User> usersInDatabase; // - <userId,User>
    private static HashMap<String, User> admins;
    private static HashMap<String, PartOfATeam> assetsInDatabase;// - <asset.name, PartOfATeam>
    private static HashMap<String, Game> gamesInDatabase; // - <game.id, Game>
    private static HashMap<String, PersonalPage> pagesInDatabase;//-<userId, PersonalPage>
    private static HashSet<League> leagues;
    private static HashSet<Season> seasons;
    private static HashMap<String, LeagueInSeason> leaguesInSeasons; //-<id, LeagueInSeason>
    private static HashMap<String , Complaint> complaints; //-<complaintId, Complaint>
    private static HashMap<String, Team> teams;
    private static HashMap<User, Referee> referees;

    private static DataAccess dataAccess;

    public Database() {
        mailsAndPasswords = new HashMap<>();
        mailsAndUserID = new HashMap<>();
        usersInDatabase = new HashMap<>();
        assetsInDatabase = new HashMap<>();
        gamesInDatabase = new HashMap<>();
        pagesInDatabase = new HashMap<>();
        leaguesInSeasons = new HashMap<>();
        leagues = new HashSet<>();
        seasons = new HashSet<>();
        complaints = new HashMap<>();
        teams = new HashMap<>();
        admins = new HashMap<>();
        referees = new HashMap<>();

        dataAccess = new DataAccess();
    }


    public static boolean updateObject(Object object){


        if(object instanceof Coach){
            boolean ans1=true,ans2=true,ans3=true,ans4=true,ans5=true;
            /**
             * [Training] [varchar](50) NOT NULL,
             * [RoleInTeam] [varchar](50) NOT NULL,
             * 	[Teams] [varchar](255) NOT NULL,
             * 	[isActive] [bit] NOT NULL,
             * 	[Price] [real] NOT NULL,
             * 	*/
            ans1 = dataAccess.updateCellValue("Coaches" ,"Training" ,((Coach) object).getID() ,((Coach) object).getTraining() );
            ans2 = dataAccess.updateCellValue("Coaches" ,"RoleInTeam" ,((Coach) object).getID() ,((Coach) object).getRoleInTeam() );
            ans3 = dataAccess.updateCellValue("Coaches" ,"Teams" ,((Coach) object).getID() , listOfTeamsToStringIDs(((Coach) object).getTeams()) );
            ans4 = dataAccess.updateCellValue("Coaches" ,"isActive" ,((Coach) object).getID() ,""+((Coach) object).isActive() );
            ans5 = dataAccess.updateCellValue("Coaches" ,"Price" ,((Coach) object).getID() ,""+((Coach) object).getPrice() );

            return ans1 && ans2 && ans3 && ans4 && ans5;
        }
        else if(object instanceof Complaint){
            boolean ans1=true,ans2=true,ans3=true,ans4=true;
            /**
             * [ComplaintDate] [date] NOT NULL,
             * 	[isActive] [bit] NOT NULL,
             * 	[Description] [varchar] (1000)NOT NULL ,
             * 	[ComplainedFanID] [char](50) NOT NULL,
             * */

            ans1 = dataAccess.updateCellValue("Complaints" ,"ComplaintDate" ,((Complaint) object).getId() ,""+((Complaint) object).getDate() );
            ans2 = dataAccess.updateCellValue("Complaints" ,"isActive" ,((Complaint) object).getId() ,""+((Complaint) object).getIsActive());
            ans3 = dataAccess.updateCellValue("Complaints" ,"Description" ,((Complaint) object).getId() ,((Complaint) object).getDescription() );
            ans4 = dataAccess.updateCellValue("Complaints" ,"ComplainedFanID" ,((Complaint) object).getId() ,((Complaint) object).getFanComplained().getUser().getID() );

            return ans1 && ans2 && ans3 && ans4 ;
        }

        else if(object instanceof Event){
            boolean ans1=true,ans2=true,ans3=true,ans4=true;
            /**
             *
             * [EventType] [char](50) NOT NULL,
             * 	[EventDate] [date] NOT NULL,
             * 	[MinutesInGame] [real] NOT NULL,
             * 	[Description] [varchar](max) NOT NULL,
             * */

            ans1 = dataAccess.updateCellValue("Events","EventType",((Event) object).getId() ,""+((Event) object).getType());
            ans2 = dataAccess.updateCellValue("Events","EventDate", ((Event) object).getId(),""+((Event) object).getDate());
            ans3 = dataAccess.updateCellValue("Events","MinutesInGame",((Event) object).getId(),""+((Event) object).getMinuteInGame());
            ans4 = dataAccess.updateCellValue("Events","Description" ,((Event) object).getId(),((Event) object).getDescription());


            return ans1 && ans2 && ans3 && ans4 ;
        }
        else if(object instanceof EventReport){
            boolean ans1=true,ans2=true,ans3=true,ans4=true;
            /**
             *
             * 	[EventsIDs] [varchar](max) NOT NULL,
             * */

            ans2 = dataAccess.updateCellValue("EventReports","EventsIDs",((EventReport) object).getId() , ((EventReport) object).getEventsId());


            return ans1 && ans2 && ans3 && ans4 ;
        }
        else if(object instanceof Fan){
            boolean ans1=true,ans2=true,ans3=true,ans4=true ,ans5=true;
            /**
             * [Address] [varchar](255) NOT NULL,
             * 	[Phone] [varchar](50) NOT NULL unique,
             * 	[FollowedPagesIDs] [varchar](255) ,
             *[ComplaintsIDs] [varchar](255) ,
             * */

            ans2 = dataAccess.updateCellValue("Fans" ,"Address" , ((Fan)object).getUser().getID(),((Fan) object).getAddress() );
            ans3 = dataAccess.updateCellValue("Fans" ,"Phone" ,((Fan)object).getUser().getID() , ((Fan) object).getPhone());
            ans4 = dataAccess.updateCellValue("Fans" ,"FollowedPagesIDs" , ((Fan)object).getUser().getID(), ((Fan) object).getfollowPagesId());
            ans4 = dataAccess.updateCellValue("Fans" ,"ComplaintsIDs" , ((Fan)object).getUser().getID(), ((Fan) object).getComplaintsId());

            return ans1 && ans2 && ans3 && ans4 && ans5;
        }
        else if(object instanceof Field){
            boolean ans1=true,ans2=true,ans3=true,ans4=true,ans5=true,ans6=true;
            /**
             *
             * [Location] [char](50) NOT NULL,
             * 	[Name] [char](50) NOT NULL,
             * 	[Capacity] [int] NOT NULL,
             * 	[Teams] [varchar](255) NOT NULL,
             * 	[isActive] [bit] NOT NULL,
             * 	[Price] [real] NOT NULL,
             * */

            ans1 = dataAccess.updateCellValue("Fields","Location",((Field) object).getID() ,((Field) object).getLocation());
            ans2 = dataAccess.updateCellValue("Fields","Name",((Field) object).getID() ,((Field) object).getName());
            ans3 = dataAccess.updateCellValue("Fields","Capacity", ((Field) object).getID(),""+((Field) object).getCapacity());
            ans4 = dataAccess.updateCellValue("Fields","Teams", ((Field) object).getID(),listOfTeamsToStringIDs(((Field) object).getTeams()) );
            ans5 = dataAccess.updateCellValue("Fields","isActive" ,((Field) object).getID(),""+((Field) object).isActive());
            ans6 = dataAccess.updateCellValue("Fields","Price" ,((Field) object).getID(),""+((Field) object).getPrice());


            return ans1 && ans2 && ans3 && ans4  && ans5 && ans6;
        }
        else if(object instanceof Game){
            boolean ans1=true,ans2=true,ans3=true,ans4=true,
                    ans5=true,ans6=true,ans7=true,ans8=true,
                    ans9=true,ans10=true ,ans11=true;
            /**
             *
             [GameDate] [date] NOT NULL,
             [HostScore] [int] NOT NULL,
             [GuestScore] [int] NOT NULL,
             [FieldID] [char] (50) NOT NULL,
             [MainRefereeID] [char] (50) NOT NULL,
             [SideRefereesIDs] [char] (50) NOT NULL,
             [HostTeamID] [char] (50) NOT NULL,
             [GuestTeamID] [char] (50) NOT NULL,
             [AlertsFansIDs] [varchar](max) NOT NULL,
             [EventReportID] [char] (50) NOT NULL,
             [LeagueInSeasonID] [char] (50) NOT NULL,
             * */

            ans1 = dataAccess.updateCellValue("Games","GameDate",((Game) object).getId() ,""+((Game) object).getDate());
            ans2 = dataAccess.updateCellValue("Games","HostScore", ((Game) object).getId(),""+((Game) object).hostScore());
            ans3 = dataAccess.updateCellValue("Games","GuestScore", ((Game) object).getId(),""+((Game) object).guestScore());
            ans4 = dataAccess.updateCellValue("Games","FieldID" ,((Game) object).getId(),((Game) object).getField().getID());
            ans5 = dataAccess.updateCellValue("Games","MainRefereeID" ,((Game) object).getId(),((Game) object).getMainReferee().getUser().getID());
            ans6 = dataAccess.updateCellValue("Games","SideRefereesIDs" ,((Game) object).getId(),((Game) object).getSideRefereesId() );
            ans7 = dataAccess.updateCellValue("Games","HostTeamID" ,((Game) object).getId(),((Game) object).getHostTeam().getID());
            ans8 = dataAccess.updateCellValue("Games","GuestTeamID" ,((Game) object).getId(),((Game) object).getGuestTeam().getID());
            ans9 = dataAccess.updateCellValue("Games","AlertsFansIDs" ,((Game) object).getId(),getFansForAlerts(((Game) object).getFansForAlerts()));
            ans10 = dataAccess.updateCellValue("Games","EventReportID" ,((Game) object).getId(),((Game) object).getEventReport().getId());
            ans11 = dataAccess.updateCellValue("Games","LeagueInSeasonID" ,((Game) object).getId(),((Game) object).getLeague().getId());


            return ans1 && ans2 && ans3 && ans4  && ans5 &&
                    ans6 && ans7 && ans8 && ans9  && ans10 && ans11;
        }
        else if(object instanceof League){
            boolean ans1=true,ans2=true,ans3=true,ans4=true;
            /**
             [Name] [varchar](50) NOT NULL,
             [LeagueLevel] [varchar](50) NOT NULL,
             [LeaguesInSeasonsIDs] [varchar](255) NOT NULL,
             * */

            ans1 = dataAccess.updateCellValue("Leagues" ,"Name" , ((League) object).getId() , ((League) object).getName());
            ans2 = dataAccess.updateCellValue("Leagues" ,"LeagueLevel" ,((League) object).getId() ,((League) object).getLevel() );
            ans3 = dataAccess.updateCellValue("Leagues" ,"LeaguesInSeasonsIDs" ,((League) object).getId() ,leagueInSeasonToStringIDs(((League) object).getLeagueInSeasons()) );
            //ans4 = dataAccess.updateCellValue("Leagues" ,"SeasonsIDs" , ((League) object).getId(),getSeasonsFromLeagueInSeasons(((League) object).getLeagueInSeasons()) );

            return ans1 && ans2 && ans3 && ans4 ;
        }
        else if(object instanceof LeagueInSeason){
            boolean ans1=true,ans2=true,ans3=true,ans4=true,ans5=true ,ans6=true,ans7=true;
            /**
             *
             [AssignmentPolicy] [char](255) NOT NULL,
             [ScorePolicy] [char](255) NOT NULL,
             [GamesIDs] [varchar](255) NOT NULL,
             [RefereesIDs] [varchar](255) NOT NULL,
             [TeamsIDs] [varchar](255) NOT NULL,
             [RegistrationFee] [real] NOT NULL,
             [Records] [varchar](1000) NOT NULL,
             * */
            String assignmentPolicy ="null";
            String scorePolicy ="null";
            if(((LeagueInSeason) object).getAssignmentPolicy() instanceof PlayOnceWithEachTeamPolicy){
                assignmentPolicy ="PlayOnceWithEachTeamPolicy";
            }
            else if(((LeagueInSeason) object).getAssignmentPolicy() instanceof PlayTwiceWithEachTeamPolicy){
                assignmentPolicy = "PlayTwiceWithEachTeamPolicy";
            }

            if(((LeagueInSeason) object).getScorePolicy() instanceof StandardScorePolicy){
                assignmentPolicy ="StandardScorePolicy";
            }
            else if(((LeagueInSeason) object).getScorePolicy() instanceof CupScorePolicy){
                assignmentPolicy = "CupScorePolicy";
            }
            ans1 = dataAccess.updateCellValue("LeaguesInSeasons","AssignmentPolicy", ((LeagueInSeason) object).getId() ,assignmentPolicy);
            ans2 = dataAccess.updateCellValue("LeaguesInSeasons","ScorePolicy", ((LeagueInSeason) object).getId(),scorePolicy);
            ans3 = dataAccess.updateCellValue("LeaguesInSeasons","GamesIDs", ((LeagueInSeason) object).getId(), ((LeagueInSeason) object).getGamesId());
            ans4 = dataAccess.updateCellValue("LeaguesInSeasons","RefereesIDs" ,((LeagueInSeason) object).getId(),((LeagueInSeason) object).getRefereesId());

            ans5 = dataAccess.updateCellValue("LeaguesInSeasons","TeamsIDs" ,((LeagueInSeason) object).getId(),listOfTeamsToStringIDs(((LeagueInSeason) object).getTeams()));

        //    ans5 = dataAccess.updateCellValue("LeaguesInSeasons","TeamsIDs" ,((LeagueInSeason) object).getId(),((LeagueInSeason) object).getTeamsId());

            ans6 = dataAccess.updateCellValue("LeaguesInSeasons","RegistrationFee" ,((LeagueInSeason) object).getId(),""+((LeagueInSeason) object).getRegistrationFee());
            ans7 = dataAccess.updateCellValue("LeaguesInSeasons","Records" ,((LeagueInSeason) object).getId(), createScoreTable(((LeagueInSeason) object).getScoreTable()));


            return ans1 && ans2 && ans3 && ans4  && ans5 && ans6  && ans7;
        }
        else if(object instanceof PersonalPage){
            boolean ans1=true,ans2=true,ans3=true,ans4=true;
            /**
             [OwnerID] [char](30) NOT NULL unique,
             [PageData] [varchar](max) ,
             [Followers] [varchar](max) ,
             * */

            ans1 = dataAccess.updateCellValue("PersonalPages" ,"OwnerID" , ((PersonalPage) object).getId() ,((PersonalPage) object).getUser().getID() );
            ans2 = dataAccess.updateCellValue("PersonalPages" ,"PageData" ,((PersonalPage) object).getId() , ((PersonalPage) object).getData() );
            ans3 = dataAccess.updateCellValue("PersonalPages" ,"Followers" , ((PersonalPage) object).getId(), ((PersonalPage) object).getFollowersIds());

            return ans1 && ans2 && ans3 && ans4 ;
        }
        else  if(object instanceof Player){
            boolean ans1=true,ans2=true,ans3=true,ans4=true ,ans5=true;
            /**
             [Birthdate] [date] NOT NULL,
             [Teams] [varchar](255) NOT NULL,
             [RoleInTeam] [varchar](255) NOT NULL,
             [isActive] [bit] NOT NULL,
             [Price] [real] NOT NULL,
             * */

            ans1 = dataAccess.updateCellValue("Players" ,"Birthdate" ,((Player) object).getID() , ""+((Player) object).getBirthDate());
            ans2 = dataAccess.updateCellValue("Players" ,"Teams" , ((Player) object).getID(), listOfTeamsToStringIDs(((Player) object).getTeams()));
            ans3 = dataAccess.updateCellValue("Players" ,"RoleInTeam" , ((Player) object).getID(), ((Player) object).getRole());
            ans4 = dataAccess.updateCellValue("Players" ,"isActive" , ((Player) object).getID(), ""+((Player) object).isActive());
            ans5 = dataAccess.updateCellValue("Players" ,"Price" , ((Player) object).getID(), ""+((Player) object).getPrice());

            return ans1 && ans2 && ans3 && ans4 && ans5 ;
        }
        else if(object instanceof Referee){
            boolean ans1=true,ans2=true,ans3=true,ans4=true;
            /**
             [Training] [varchar](50) NOT NULL,
             [Games] [varchar](255) NOT NULL,
             * */

            ans1 = dataAccess.updateCellValue("Referees" ,"Training" , ((Referee) object).getUser().getID() ,((Referee) object).getTraining() );
            ans2 = dataAccess.updateCellValue("Referees" ,"Games" , ((Referee) object).getUser().getID(), ((Referee) object).getGamesId() );

            return ans1 && ans2 && ans3 && ans4 ;
        }
        else if(object instanceof Season){
            boolean ans1=true,ans2=true,ans3=true,ans4=true;
            /**
             [SeasonYear] [int] NOT NULL,
             [StartDate] [date] NOT NULL,
             [LeaguesInSeasonsIDs] [varchar](255) NOT NULL,
             * */

            ans1 = dataAccess.updateCellValue("Seasons" ,"SeasonYear" , ((Season) object).getId() ,""+((Season) object).getYear() );
            ans2 = dataAccess.updateCellValue("Seasons" ,"StartDate" , ((Season) object).getId(), ""+((Season) object).getStartDate() );
            ans3 = dataAccess.updateCellValue("Seasons" ,"LeaguesInSeasonsIDs" , ((Season) object).getId(), leagueInSeasonToStringIDs(((Season) object).getLeagueInSeasons()) );
            //ans3 = dataAccess.updateCellValue("Seasons" ,"LeaguesIDs" , ((Season) object).getId(), ((Season) object).getLeaguesId() );

            return ans1 && ans2 && ans3 && ans4 ;
        }
        else if(object instanceof Team){
            boolean ans1=true,ans2=true,ans3=true,ans4=true,
                    ans5=true,ans6=true,ans7=true,ans8=true,
                    ans9=true,ans10=true ,ans11=true, ans12=true,
                    ans13=true,ans14=true ,ans15=true;
            /**
             *
             [Name] [varchar](50) NOT NULL,
             [Wins] [int] NOT NULL,
             [Losses] [int] NOT NULL,
             [Draws] [int] NOT NULL,
             [PersonalPageID] [char] (30) NOT NULL,
             [TeamOwners] [varchar](255) NOT NULL ,
             [TeamManagers] [varchar](255) NOT NULL,
             [Players] [varchar](255) NOT NULL,
             [Coaches] [varchar](255) NOT NULL,
             [Budget] [real] NOT NULL,
             [GamesIDs] [varchar] (255) NOT NULL,
             [Fields] [varchar] (255) NOT NULL,
             [LeaguesInSeasons] [varchar] (255) NOT NULL,
             [isActive] [bit] NOT NULL,
             [isPermanentlyClosed] [bit] NOT NULL,
             * */

            ans1 = dataAccess.updateCellValue("Teams","Name", ((Team) object).getID(), ((Team) object).getName());
            ans2 = dataAccess.updateCellValue("Teams","Wins", ((Team) object).getID(), ""+((Team) object).getWins());
            ans3 = dataAccess.updateCellValue("Teams","Losses", ((Team) object).getID(), ""+((Team) object).getLosses());
            ans4 = dataAccess.updateCellValue("Teams","Draws", ((Team) object).getID(), ""+((Team) object).getDraws());
            ans5 = dataAccess.updateCellValue("Teams","PersonalPageID", ((Team) object).getID(),((Team) object).getPage().getId() );
            ans6 = dataAccess.updateCellValue("Teams","TeamOwners", ((Team) object).getID(), getUserId(((Team) object).getTeamOwners()));
            ans7 = dataAccess.updateCellValue("Teams","TeamManagers", ((Team) object).getID(), getUserId(((Team) object).getTeamManagers()));
            ans8 = dataAccess.updateCellValue("Teams","Players", ((Team) object).getID(), getUserId(((Team) object).getPlayers())  );
            ans9 = dataAccess.updateCellValue("Teams","Coaches",((Team) object).getID() , getUserId(((Team) object).getCoaches()));
            ans10 = dataAccess.updateCellValue("Teams","Budget" , ((Team) object).getID(), ""+((Team) object).getBudget().getBalance());
            ans11 = dataAccess.updateCellValue("Teams","GamesIDs" , ((Team) object).getID(), ((Team) object).getGamesId() );
            ans12 = dataAccess.updateCellValue("Teams","Fields" , ((Team) object).getID(), getFieldsIds(((Team) object).getFields()) );
            ans13 = dataAccess.updateCellValue("Teams","LeaguesInSeasons" , ((Team) object).getID(), getLeagueInSeasonIds(((Team) object).getLeaguesInSeason()));
            ans14 = dataAccess.updateCellValue("Teams","isActive" ,((Team) object).getID() , ""+((Team) object).isActive());
            ans15 = dataAccess.updateCellValue("Teams","isPermanentlyClosed" , ((Team) object).getID(), ""+((Team) object).isPermanentlyClosed());


            return ans1 && ans2 && ans3 && ans4  && ans5 &&
                    ans6 && ans7 && ans8 && ans9  && ans10 && ans11
                    && ans12 && ans13  && ans14 && ans15;
        }
        else if(object instanceof TeamManager){
            boolean ans1=true,ans2=true,ans3=true,ans4=true,ans5=true;
            /**
             [Teams] [varchar](255) NOT NULL,
             [isActive] [bit] NOT NULL,
             [Price] [real] NOT NULL,
             [ManageAssets] [bit] NOT NULL,
             [Finance] [bit] ,
             * */


            ans1 = dataAccess.updateCellValue("TeamManagers" ,"Teams" , ((TeamManager) object).getID() , listOfTeamsToStringIDs(((TeamManager) object).getTeams()));
            ans2 = dataAccess.updateCellValue("TeamManagers" ,"isActive" ,((TeamManager) object).getID() , ""+((TeamManager) object).isActive());
            ans3 = dataAccess.updateCellValue("TeamManagers" ,"Price" , ((TeamManager) object).getID(), ""+((TeamManager) object).getPrice());

            //ManageAssets and Finance are permissionManageAssets and permissionFinance
            ans4 = dataAccess.updateCellValue("TeamManagers" ,"ManageAssets" , ((TeamManager) object).getID(), ""+((TeamManager) object).isPermissionManageAssets());
            ans5 = dataAccess.updateCellValue("TeamManagers" ,"Finance" , ((TeamManager) object).getID(), ""+((TeamManager) object).isPermissionFinance());

            return ans1 && ans2 && ans3 && ans4 && ans5 ;
        }
        else if(object instanceof TeamOwner){
            boolean ans1=true,ans2=true,ans3=true,ans4=true;
            /**
             [Teams] [varchar](255) NOT NULL,
             [ClosedTeams] [varchar](255) ,
             [AppointedTeamOwners] [varchar] ,
             [AppointedTeamManagers] [varchar](255) ,
             * */

            ans1 = dataAccess.updateCellValue("TeamOwners" ,"Teams" , ((TeamOwner) object).getUser().getID(), listOfTeamsToStringIDs(((TeamManager) object).getTeams()));
            ans2 = dataAccess.updateCellValue("TeamOwners" ,"ClosedTeams" , ((TeamOwner) object).getUser().getID(), listOfTeamsToStringIDs(((TeamOwner) object).getClosedTeams()) );

            //HashMap for user and team, need to save them together
            ans3 = dataAccess.updateCellValue("TeamOwners" ,"AppointedTeamOwners" , ((TeamOwner) object).getUser().getID(), appointmentUsersIds(((TeamOwner) object).getAppointedTeamOwners()));
            ans4 = dataAccess.updateCellValue("TeamOwners" ,"AppointedTeamManagers" , ((TeamOwner) object).getUser().getID(), appointmentUsersIds(((TeamOwner) object).getAppointedTeamManagers()));

            return ans1 && ans2 && ans3 && ans4 ;
        }
        else if(object instanceof User){
            boolean ans1=true,ans2=true,ans3=true,ans4=true,ans5=true ,ans6=true;
            /**
             *
             [FirstName] [varchar](255) NOT NULL,
             [LastName] [varchar](255) NOT NULL,
             [Mail] [varchar](255) NOT NULL unique,
             [isActive] [bit] NOT NULL,
             [Roles] [varchar](255) ,
             [searchHistories] [varchar](1000) ,
             * */

            ans1 = dataAccess.updateCellValue("Users","FirstName",((User) object).getID() , ((User) object).getFirstName());
            ans2 = dataAccess.updateCellValue("Users","LastName", ((User) object).getID(),((User) object).getLastName());
            ans3 = dataAccess.updateCellValue("Users","Mail", ((User) object).getID(), ((User) object).getMail());
            ans4 = dataAccess.updateCellValue("Users","isActive" ,((User) object).getID(), ""+((User) object).isActive());
            ans5 = dataAccess.updateCellValue("Users","Roles" ,((User) object).getID(), listToString(((User) object).getStringRoles()));
            ans6 = dataAccess.updateCellValue("Users","searchHistories" ,((User) object).getID(), listToString(((User) object).getSearchHistory()));


            return ans1 && ans2 && ans3 && ans4  && ans5 && ans6  ;
        }
        return false;

    }


    private static String getFansForAlerts(HashMap<Fan, Boolean> fansForAlerts) {
        String listOfStrings="";

        for (HashMap.Entry<Fan, Boolean> entry : fansForAlerts.entrySet()) {

            Fan fan = entry.getKey();
            Boolean bool = entry.getValue();
            if(listOfStrings.equals("")){
                listOfStrings= listOfStrings + fan.getUser().getID() +":"+ bool;
            }else {
                listOfStrings = listOfStrings + "," + fan.getUser().getID() +":"+ bool;
            }
        }
        return listOfStrings;
    }

    private static String appointmentUsersIds(HashMap<User, Team> appointedTeamOwners) {
        String listOfStrings="";

        for (HashMap.Entry<User, Team> entry : appointedTeamOwners.entrySet()) {

            User user = entry.getKey();
            Team team = entry.getValue();
            if(listOfStrings.equals("")){
                listOfStrings= listOfStrings + user.getID() +":"+ team.getID();
            }else {
                listOfStrings = listOfStrings + "," + user.getID() +":"+ team.getID();
            }
        }
        return listOfStrings;
    }

    private static String getLeagueInSeasonIds(List<LeagueInSeason> leaguesInSeason) {
        String listOfStringsID="";
        for (LeagueInSeason league:leaguesInSeason) {
            if(listOfStringsID.equals("")){
                listOfStringsID= listOfStringsID +league.getId();
            }else {
                listOfStringsID = listOfStringsID + "," + league.getId();
            }
        }
        return listOfStringsID;
    }

    private static String getFieldsIds(List<Field> fields) {
        String listOfStringsID="";
        for (Field field:fields) {
            if(listOfStringsID.equals("")){
                listOfStringsID= listOfStringsID +field.getID();
            }else {
                listOfStringsID = listOfStringsID + "," + field.getID();
            }
        }
        return listOfStringsID;
    }

    private static String getUserId(List<User> users) {
        String listOfStringsID="";
        for (User user:users) {
            if(listOfStringsID.equals("")){
                listOfStringsID= listOfStringsID +user.getID();
            }else {
                listOfStringsID = listOfStringsID + "," + user.getID();
            }
        }
        return listOfStringsID;
    }

    private static String createScoreTable(Queue<ScoreTableRecord> scoreTable) {
        String listOfStrings="";
        for (ScoreTableRecord scoreTableRecord : scoreTable) {
            if(listOfStrings.equals("")){
                listOfStrings= listOfStrings +scoreTableRecord.getTeam().getID() +":"+scoreTableRecord.getTotalScore();
            }else {
                listOfStrings = listOfStrings + "," +scoreTableRecord.getTeam().getID() +":"+scoreTableRecord.getTotalScore();
            }
        }
        return listOfStrings;

    }

        private static Queue<ScoreTableRecord> getScoreTableQueue(String scoreTable) {

            Queue<ScoreTableRecord> scoreTableQueue = new LinkedList<>();
            List<String> temp;
            //scoreTable= teamID1:score,teamID2:score,..

            /**
            * after split =>
            * scoreTableList.get(0) = teamID1:score
            * scoreTableList.get(1) = teamID2:score
            * ...
            */
            List<String> scoreTableList = split(scoreTable);

            for(String s : scoreTableList){
                temp=splitHashMap(s);
                Team team = getTeam(temp.get(0));
                int score = Integer.parseInt(temp.get(1));
                if(team != null) {
                    ScoreTableRecord scoreTableRecord = new ScoreTableRecord(team, score);
                    ((LinkedList<ScoreTableRecord>) scoreTableQueue).add(scoreTableRecord);
                }
            }
            return scoreTableQueue;
    }

    private static String getSeasonsFromLeagueInSeasons(List<LeagueInSeason> leagueInSeasons) {
        String listOfStrings="";
        for (LeagueInSeason leagueInSeason : leagueInSeasons) {
            if(listOfStrings.equals("")){
                listOfStrings= listOfStrings +leagueInSeason.getSeason().getId();
            }else {
                listOfStrings = listOfStrings + "," + leagueInSeason.getSeason().getId();
            }
        }
        return listOfStrings;
    }


    private static String listToString(Collection objects){
        String listOfStrings="";
        for (Object object:objects) {
            if(listOfStrings.equals("")){
                listOfStrings= listOfStrings +object;
            }else {
                listOfStrings = listOfStrings + "," + object;
            }
        }
        return listOfStrings;

    }

    private static String listOfTeamsToStringIDs(Collection<Team> teams){
        String listOfStringsID="";
        for (Team team:teams) {
            if(listOfStringsID.equals("")){
                listOfStringsID= listOfStringsID +team.getID();
            }else {
                listOfStringsID = listOfStringsID + "," + team.getID();
            }
        }
        return listOfStringsID;

    }

    private static String leagueInSeasonToStringIDs(List<LeagueInSeason> leagueInSeasons){
        String listOfStringsID="";
        for (LeagueInSeason leagueInSeason:leagueInSeasons) {
            if(listOfStringsID.equals("")){
                listOfStringsID= listOfStringsID +leagueInSeason.getId();
            }else {
                listOfStringsID = listOfStringsID + "," + leagueInSeason.getId();
            }
        }
        return listOfStringsID;

    }
   /* private static String listToStringForEventsID(List<Event> events){
        String listOfStrings="";
        for (Event event:events) {
            if(listOfStrings.equals("")){
                listOfStrings= listOfStrings +event.getId();
            }else {
                listOfStrings = listOfStrings + "," + event.getId();
            }
        }
        return listOfStrings;

    }*/

    public static boolean addReferee(User user, Referee referee){
        if(referees.containsKey(user))
            return false;
        referees.put(user, referee);
        //dataAccess.addCell("Referees" ,);
        //dataAccess.addCell("Referees")
        return true;
    }

    public static List<Referee> getReferees() {
        return new LinkedList<>(referees.values());
    }

    public static boolean addLeague(League league) {
        /**
         *
         * [ID] [char](30)  Primary key,
         * 	[Name] [varchar](50) NOT NULL,
         * 	[LeagueLevel] [varchar](50) NOT NULL,
         * 	[SeasonsIDs] [varchar](255) NOT NULL,
         *
         * 	*/

        //return dataAccess.addCell("Leagues" ,league.getId() , league.getName() , league.getLevel(),listToString(league.getLeagueInSeasons()));

        if(!leagues.contains(league)){
            leagues.add(league);
            return true;
        }
        return false;
    }

    public static boolean addSeason(Season season) {

        /**
         * [ID] [char](30)  Primary key,
         * 	[SeasonYear] [int] NOT NULL,
         * 	[StartDate] [date] NOT NULL,
         * 	[LeaguesIDs] [varchar](255) NOT NULL,
         * 	*/

       // return dataAccess.addCell("Seasons" ,season.getId(),""+season.getYear(),""+season.getStartDate() ,season.getLeaguesId() );
        if(!seasons.contains(season)){
            seasons.add(season);
            return true;
        }
        return false;
    }

    public static boolean addTeam(Team team){

        /**
         * [ID] [char](30)  Primary key,
         * 	[Name] [varchar](50) NOT NULL,
         * 	[Wins] [int] NOT NULL,
         * 	[Losses] [int] NOT NULL,
         * 	[Draws] [int] NOT NULL,
         * 	[PersonalPageID] [char] (30) NOT NULL,
         * 	[TeamOwners] [varchar](255) NOT NULL ,
         * 	[TeamManagers] [varchar](255) NOT NULL,
         * 	[Players] [varchar](255) NOT NULL,
         * 	[Coaches] [varchar](255) NOT NULL,
         * 	[Budget] [real] NOT NULL,
         * 	[GamesIDs] [varchar] (255) NOT NULL,
         * 	[Fields] [varchar] (255) NOT NULL,
         * 	[LeaguesInSeasons] [varchar] (255) NOT NULL,
         * 	[isActive] [bit] NOT NULL,
         * 	[isPermanentlyClosed] [bit] NOT NULL,
         * 	*/

        //Budget?
        //LeaguesInSeasons?
      /*  return dataAccess.addCell(team.getID(),team.getName(),""+team.getWins(),""+team.getLosses(),
                ""+team.getDraws(),team.getPage().getId() ,listToString(team.getTeamOwners()),
                listToString(team.getTeamManagers()) , listToString(team.getPlayers()) , listToString(team.getCoaches()) ,
                        ""+team.getBudget().getBalance() ,team.getGamesId() , listToString(team.getFields()) , ""+team.isActive() , ""+team.isPermanentlyClosed());
        */
        if(!teams.containsKey(team.getID())){
            teams.put(team.getID(), team);
            return true;
        }

        return false;
    }

    public static boolean addLeagueInSeason(LeagueInSeason leagueInSeason){

        /**
         * [ID] [char](30)  Primary key,
         * 	[AssignmentPolicy] [char](255) NOT NULL,
         * 	[ScorePolicy] [char](255) NOT NULL,
         * 	[GamesIDs] [varchar](255) NOT NULL,
         * 	[RefereesIDs] [varchar](255) NOT NULL,
         * 	[TeamsIDs] [varchar](255) NOT NULL,
         * 	[RegistrationFee] [real] NOT NULL,
         * 	[Records] [varchar](1000) NOT NULL,
         * */
        //dataAccess.addCell("LeaguesInSeasons" , leagueInSeason.getId(),);
        String id = leagueInSeason.getId();
        if(!leaguesInSeasons.containsKey(id)){
            leaguesInSeasons.put(id, leagueInSeason);
            return true;
        }
        return false;
    }

    public static LeagueInSeason getLeagueInSeason(String leagueInSeasonId){
        return leaguesInSeasons.get(leagueInSeasonId);
    }



    public static List<League> getLeagues(){ return new LinkedList<>(leagues);}

    public static List<Season> getSeasons() {
        return new LinkedList<>(seasons);
    }

    /*
            this function gets a name of an asset and returns a pointer to the object of this asset
            for example input: "Blumfield stadium" - the output will be a pointer to Blumfield stadium object or Null if it doesn't exists
            return null if cant find asset
             */
    public static PartOfATeam getAsset(String name){
        return (PartOfATeam)search("PartOfATeam", name);
    }
    /*
    this function gets a user id and returns a pointer to the object of this user
    for example input: "Leonardo Messi" - the output will be a pointer to messi's user or Null if it doesn't exists
    return null if cant find user
     */
    public static User getUser(String userId){
        return (User)search("User", userId);
    }

    public static User getUserByMail(String mail , String password){
        if(authenticationCheck(mail, password)) {
            return (User) search("Mail", mail);
        }
        return null;
    }
    /*
    this function gets a gameId - Game.toString (its address in memory) and returns a pointer to the object of this game
    return null if cant find game
    */
    public static Game getGame(String gameId){
        return (Game)search("Game", gameId);
    }
    /*
    this function gets a userId and return its personalPage if exists
    if page not exists the function returns null
     */
    public static PersonalPage getPage(String pageId){
        return (PersonalPage)search("Page", pageId);
    }
    /*
    this function returns all games in database
     */
   /* public static LinkedList<Game> getAllGames(){
        return new LinkedList<>(gamesInDatabase.values());
    }*/


    public static HashMap<String, PartOfATeam> getAssetsInDatabase() {
        return assetsInDatabase;
    }

    /*
        adds an asset to the database
        returns false if the asset already exists
         */
    public static boolean addAsset(PartOfATeam asset){
        String assetID = asset.getID();
        if(assetsInDatabase.containsKey(assetID)){
            return false;
        }
        assetsInDatabase.put(assetID, asset);

        return true;
    }

    public static PartOfATeam getAssetById(String assetId){
        return assetsInDatabase.get(assetId);
    }
    /*
    adds a user to the database
    returns false if the user already exists
     */
    public static boolean addUser(String password, User user){
        if(!mailsAndUserID.containsKey(user.getMail())&& user.isActive()){
            String encryptedPassword = encrypt(password);
            mailsAndPasswords.put(user.getMail(), encryptedPassword);
            mailsAndUserID.put(user.getMail(), user.getID());
            usersInDatabase.put(user.getID(), user);
            return true;
        }
        return false;
    }

    private static String encrypt(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        messageDigest.update(password.getBytes());
        return new String(messageDigest.digest());
    }

    /*
    this function adds a game to the database according to its toString() value
    returns false if the game already exists
    */
    public static boolean addGame(Game game){
        if(gamesInDatabase.containsKey(game.getId()))
            return false;
        gamesInDatabase.put(game.getId(), game);

        addTeam(game.getGuestTeam());
        addTeam(game.getHostTeam());
        return true;
    }
    /*
    this function adds a new personal page to the database according to the user id
     */
    public static boolean addPage(PersonalPage page){
        String userId = page.getId();
        if(pagesInDatabase.containsKey(userId))
            return false;
        pagesInDatabase.put(userId, page);
        return true;
    }
    /*
    this function perform a authentication check for username an password
    returns true if this is the correct credentials and false otherwise
     */
    private static boolean authenticationCheck(String mail, String password){
        if(mailsAndPasswords.containsKey(mail) && usersInDatabase.get(mailsAndUserID.get(mail)).isActive()){
            String encryptedPassword = encrypt(password);
            String passwordInSystem = mailsAndPasswords.get(mail);
            return passwordInSystem.equals(encryptedPassword);
        }
        return false;
    }
    public static boolean changePassword(String mail,String oldPassword , String newPassword){
        if(authenticationCheck(mail , oldPassword)) {
            if (mailsAndPasswords.containsKey(mail)) {
                String encryptedPassword = encrypt(newPassword);
                mailsAndPasswords.replace(mail, encryptedPassword);
                return true;
            }
        }
        return false;

    }
    /*
    this function returns a list of users of a specific type. for example all admins, all players ext.
    the input is a string of the type "Admin", "Player"
    if there aren't any users of this type - the list will be empty
    if the string type is wrong the function will return null
     */
    public static List<PartOfATeam> getListOfAllSpecificAssets(String userType){
        LinkedList<PartOfATeam> listOfAssets = new LinkedList<>();
        switch(userType){
            case("Coach"):{
                for(PartOfATeam asset : assetsInDatabase.values()){
                    if(asset instanceof Coach &&asset.isActive())
                        listOfAssets.add(asset);
                }
                return listOfAssets;

            }
            case("Fan"):{
                //for(User user : usersInDatabase.values()){
                //    if(user instanceof Fan &&user.isActive())
                //        listOfUsers.add(user);
                //}
                //return listOfUsers;
                break;

            }
            case("Player"):{
                for(PartOfATeam user : assetsInDatabase.values()){
                    if(user instanceof Player &&user.isActive())
                        listOfAssets.add(user);
                }
                return listOfAssets;
            }
            case("Referee"):{
                //for(User user : usersInDatabase.values()){
                //    if(user instanceof Referee &&user.isActive())
                //        listOfUsers.add(user);
                //}
                //return listOfUsers;
                break;
            }
            case("TeamManager"):{
                for(PartOfATeam user : assetsInDatabase.values()){
                    if(user instanceof TeamManager &&user.isActive())
                        listOfAssets.add(user);
                }
                return listOfAssets;

            }
            case("TeamOwner"):{
                //for(User user : usersInDatabase.values()){
                //    if(user instanceof TeamOwner &&user.isActive())
                //        listOfUsers.add(user);
                //}
                //return listOfUsers;
                break;
            }
            case ("Field"):{
                for(PartOfATeam asset : assetsInDatabase.values()){
                    if(asset instanceof Field && asset.isActive())
                        listOfAssets.add(asset);
                }
                return listOfAssets;
            }
        }
        return null;
    }
    public static List<Role> getListOfAllSpecificRoles(String userType) {
        LinkedList<Role> listOfUsers = new LinkedList<>();
        switch(userType) {
            case ("UnionRepresentative"): {
                for (User user : usersInDatabase.values()) {
                    if (user.isActive()) {
                        UnionRepresentative union = (UnionRepresentative) user.checkUserRole("UnionRepresentative");
                        if(union instanceof UnionRepresentative)
                            listOfUsers.add(union);
                    }
                }
                return listOfUsers;
            }
        }
        return null;
    }

    public static List<User> getSystemAdmins(){
        LinkedList<User> ListOfUsers = new LinkedList(admins.values());

        return ListOfUsers;
    }

    public static boolean addAdmin(String password, User admin){
        admins.put(admin.getID(), admin);
        return addUser(password, admin);
    }

    private static Object search(String whatType, String searchWord){
            switch(whatType){
                case("PartOfATeam"):{
                    for(String nameOfAsset : assetsInDatabase.keySet()) {
                        if (searchWord.contains(nameOfAsset))
                            return assetsInDatabase.get(searchWord);
                    }
                    break;
                }
                case("User"): {
                    for (String userId : usersInDatabase.keySet()) {
                        if (searchWord.equals(userId)) {
                            if (usersInDatabase.get(searchWord).isActive())
                                return usersInDatabase.get(searchWord);
                        }
                    }
                    break;
                }
            case ("Mail"):{
                if(mailsAndUserID.containsKey(searchWord)){
                    return usersInDatabase.get(mailsAndUserID.get(searchWord));
                }
                break;
            }
            case("Game"):{
                for(String gameId:gamesInDatabase.keySet()){
                    if(searchWord.equals(gameId))
                        return gamesInDatabase.get(gameId);
                }
                break;
            }
            case("Page"):{
                for(String userId:pagesInDatabase.keySet()){
                    if(searchWord.equals(userId))
                        return pagesInDatabase.get(userId);
                }
                break;
            }
            case("League"):{
                for(League league:leagues){
                    if(searchWord.equals(league.getName()))
                        return league;
                }
            }
            case("Season"):{
                String year="";
                for(Season season:seasons){
                    year =""+season.getYear();
                    if(searchWord.equals(year))
                        return season;
                }
            }
            case("Team"):{
                for(Team team:teams.values()){
                    if(searchWord.equals(team.getID())&&team.isActive())
                        return team;
                }
            }
            case("Password"):{
                //think about it
                break;
            }
        }
        return null;

    }

    public static String removeUser(String userId) {
        User user = usersInDatabase.get(userId);
        String userMail="";
        if(user!=null){
            user.deactivate();
            userMail= user.getMail();
        }
        return userMail;
    }

    /*
     *
     * */
    public static void removeAsset(String assetId) {
        PartOfATeam asset = assetsInDatabase.get(assetId);
        if(asset!=null){
            asset.deactivate();
        }

    }

    public static League getLeague(String nameOfLeague) {
        return (League)search("League", nameOfLeague);
    }

    public static Season getSeason(String yearOfSeason) {
        return (Season)search("Season", yearOfSeason);
    }





    public static boolean addComplaint(Complaint complaint){
        if(!complaints.containsKey(complaint.getId())){
            complaints.put(complaint.getId(), complaint);
            return true;
        }
        return false;
    }


    public static void loadDatabaseFromDisk(String path){
        //*
    }

    public static List<Object> searchObject(String searchWord){
        List<Object> result = new LinkedList<>();
        for(User user : usersInDatabase.values()){
            if(searchWord.contains(user.getName())||searchWord.contains(user.getMail()))
                result.add(user);
        }
        for(Team team : teams.values()){
            if(searchWord.contains(team.getName()))
                result.add(team);
        }
        for(PartOfATeam asset : assetsInDatabase.values()){
            if(asset instanceof Field && ((Field)asset).getLocation().contains(searchWord)){
                result.add(asset);
            }
        }
        return result;

    }

    public static List<Team> getOpenTeams() {
        List<Team> openTeams = new LinkedList<>();
        for(Team team : teams.values()){
            if(team.isActive() && !team.isPermanentlyClosed())
                openTeams.add(team);
        }
        return openTeams;
    }

    public static List<Team> getCloseTeams() {
        List<Team> closeTeams = new LinkedList<>();
        for(Team team : teams.values()){
            if(team.isActive() && !team.isPermanentlyClosed())
                closeTeams.add(team);
        }
        return closeTeams;
    }




    public static Object createObject(String type,List<String> object){
        User user;
        switch (type){
            case "Admin":
                user = createUser(object.get(0));
                Admin admin = new Admin(user);
                return admin;
            case "Coach":
                user = createUser(object.get(0));
                Coach coach = new Coach(user, getEnumTraining(object.get(1)) ,getEnumRole(object.get(2)), Integer.parseInt(object.get(3)));
                return coach;
            case "Complaint":
                Complaint complaint = new Complaint(object.get(0),
                        dataAccess.stringToDate(object.get(1)) ,Boolean.parseBoolean(object.get(2)),
                        object.get(3),getFan(object.get(4)));
                return complaint;
            case "Event":
                Event event = new Event(object.get(0) ,object.get(1) , dataAccess.stringToDate(object.get(2)),
                        Double.parseDouble(object.get(3)) ,object.get(4));
                return event;
            case "EventReport":
                EventReport eventReport = new EventReport(object.get(0),listOfEvents(object.get(1)));
                return eventReport;
            case "Fan":
                user= createUser(object.get(0));
                Fan fan = new Fan(user , object.get(1),object.get(2) ,listOfPersonalPage(object.get(3)), listOfComplaints(object.get(4)));
                return fan;
            case "Field":
                Field field = new Field(object.get(0) , object.get(1) , object.get(2),
                        Integer.parseInt(object.get(3)) ,teamHashSet(object.get(4)) ,Boolean.parseBoolean(object.get(5)) ,
                        Double.parseDouble(object.get(6)));
                return field;
            case "Game":
                Game game = new Game(object.get(0),dataAccess.stringToDate(object.get(1)),
                        Integer.parseInt(object.get(2)) , Integer.parseInt(object.get(3)),
                        getField(object.get(4)) ,getReferee(object.get(5))
                        ,listOfReferees(object.get(6)),getTeam(object.get(7)), getTeam(object.get(8)),
                        getFansForAlertsHashMap(object.get(9)) ,getEventReport(object.get(10)) ,
                        getLeagueInSeason(object.get(11)));
                return game;
            case "League":
                League league = new League(object.get(0) , object.get(1) ,getEnumLevelLeague(object.get(2)),
                        listOfLeagueInSeason(object.get(3)));
                return league;
            case "LeagueInSeason":
                LeagueInSeason leagueInSeason = new LeagueInSeason(object.get(0) ,getGameAssignmentPolicy(object.get(1)),
                        getScorePolicy(object.get(2)) ,listOfGames(object.get(3)) ,listOfReferees(object.get(4)),
                        listOfTeams(object.get(5)) , Double.parseDouble(object.get(6)) ,getScoreTableQueue(object.get(7)));
                break;
            case "PersonalPage":
                break;
            case "Player":
                break;
            case "Referee":
                break;
            case "Season":
                break;
            case "Team":
                break;
            case "TeamManager":
                break;
            case "TeamOwner":
                break;
            case "UnionRepresentative":
                break;
            case "User":
                user = new User(object.get(0) ,object.get(1),object.get(2),object.get(3),
                        stringToBoolean(object.get(4)) ,createListOfRoles(object.get(5) , object.get(0)),
                        split(object.get(6)));
                return user;

        }
        return null;

    }





    private static boolean stringToBoolean(String s) {
        if(s.equals("1")){
            return true;
        }else{
            return false;
        }

    }



    private static List<Role> createListOfRoles(String roles ,String userId) {
        List<String> listOfRoles = split(roles);
        List<Role> allRoles = new LinkedList<>();

        for(String s : listOfRoles){
            switch (s){
                case "Admin":
                    allRoles.add(getAdmin(userId));
                    break;
                case "Coach":
                    allRoles.add(getCoach(userId));
                    break;
                case "Fan":
                    allRoles.add(getFan(userId));
                    break;
                case "Player":
                    allRoles.add(getPlayer(userId));
                    break;
                case "Referee":
                    allRoles.add(getReferee(userId));
                    break;
                case "TeamManager":
                    allRoles.add(getTeamManager(userId));
                    break;
                case "TeamOwner":
                    allRoles.add(getTeamOwner(userId));
                    break;
                case "UnionRepresentative":
                    allRoles.add(getUnionRepresentative(userId));
                    break;
            }
        }
        return allRoles;

    }

    private static List<LeagueInSeason> listOfLeagueInSeason(String leagueInSeasonIds) {
        List<String> leagueInSeason = split(leagueInSeasonIds);
        List<LeagueInSeason> allLeagueInSeason = new LinkedList<>();

        for (String leagueId : leagueInSeason){
            allLeagueInSeason.add(getLeagueInSeason(leagueId));
        }
        return allLeagueInSeason;
    }

    /*private static  List<Referee> createSideReferees(String referees) {
        List<String> listOfStrings = split(referees);
        List<Referee> sideReferees = new LinkedList<>();
        Referee sideR1 = getReferee(listOfStrings.get(0));
        Referee sideR2 = getReferee(listOfStrings.get(1));
        sideReferees.add(sideR1);
        sideReferees.add(sideR2);

        return sideReferees;
    }*/


    private static List<Referee> listOfReferees(String referees){
        List<String> listOfReferees = split(referees);
        List<Referee> allReferees = new LinkedList<>();

        for (String refereeId : listOfReferees){
            Referee referee = getReferee(refereeId);
            if(referee != null) {
                allReferees.add(referee);
            }
        }

        return allReferees;
    }

    private static List<Game> listOfGames(String games){
        List<String> listOfGames = split(games);
        List<Game> allGames = new LinkedList<>();

        for (String gameId : listOfGames){
            Game game = getGame(gameId);
            if(game != null) {
                allGames.add(game);
            }
           // allGames.add(getGame(gameId));
        }

        return allGames;
    }

    private static HashSet<Team> teamHashSet(String teams){
        List<String> listOfTeams = split(teams);
        HashSet<Team> allTeams = new HashSet<>();

        for(String teamId : listOfTeams){
            Team team = getTeam(teamId);
            if(team != null){
                allTeams.add(team);
            }
            //allTeams.add(getTeam(teamId));
        }
        return allTeams;
    }

    private static List<Team> listOfTeams(String teams){
        List<String> listOfTeams = split(teams);
        List<Team> allTeams = new LinkedList<>();

        for(String teamId : listOfTeams){
            Team team = getTeam(teamId);
            if(team != null){
                allTeams.add(team);
            }
            //allTeams.add(getTeam(teamId));
        }
        return allTeams;
    }

    private static List<Event> listOfEvents(String events){
        List<String> listOfEvents = split(events);
        List<Event> allEvents = new LinkedList<>();

        for (String eventId : listOfEvents){
            Event event = getEvent(eventId);
            if(event != null){
                allEvents.add(event);
            }
            //allEvents.add(getEvent(eventId));
        }

        return allEvents;

    }

    private static List<PersonalPage> listOfPersonalPage(String personalPage){
        List<String> listOfPersonalPage = split(personalPage);
        List<PersonalPage> allPersonalPage = new LinkedList<>();

        for (String pageId : listOfPersonalPage){
            PersonalPage page = getPersonalPage(pageId);
            if (page != null){
                allPersonalPage.add(page);
            }
           // allPersonalPage.add(getPersonalPage(pageId));
        }

        return allPersonalPage;

    }

    private static List<Complaint> listOfComplaints(String complaintId){
        List<String> listOfComplaint = split(complaintId);
        List<Complaint> allComplaints = new LinkedList<>();

        for (String s : listOfComplaint){
            Complaint complaint = getComplaints(complaintId);
            if (complaint != null){
                allComplaints.add(complaint);
            }
           // allComplaints.add(getComplaints(s));
        }

        return allComplaints;
    }

    private static List<String> split(String roles){
        List<String> listOfRoles = new LinkedList<>();
        String[] split = roles.split(",");

        for (String s: split){
            listOfRoles.add(s);
        }
        return listOfRoles;
    }

    private static List<String> splitHashMap(String fansForAlerts){
        List<String> listOfFansForAlerts = new LinkedList<>();
        String[] split = fansForAlerts.split(":");

        for (String s: split){
            listOfFansForAlerts.add(s);
        }
        return listOfFansForAlerts;
    }

    private static HashMap<Fan ,Boolean> getFansForAlertsHashMap(String fansForAlerts) {

        HashMap<Fan ,Boolean> hashMapFansForAlerts = new HashMap<>();
        List<String> temp;
        //fansForAlerts= fanId1:boolean,fanId2:boolean,..

        /**
         * after split =>
         * fansAndAlerts.get(0) = fanId1:boolean
         * fansAndAlerts.get(0) = fanId2:boolean
         * ...
         */
        List<String> fansAndAlerts = split(fansForAlerts);

        for(String s : fansAndAlerts){
            temp=splitHashMap(s);
            Fan fan = getFan(temp.get(0));
            Boolean bool = Boolean.parseBoolean(temp.get(1));
            hashMapFansForAlerts.put(fan,bool);
        }
        return hashMapFansForAlerts;
    }


/*    private static HashMap<Fan ,Boolean> getScoreTableHashMap(String fansForAlerts) {

        HashMap<Fan ,Boolean> hashMapFansForAlerts = new HashMap<>();
        List<String> temp;
        //fansForAlerts= fanId1:boolean,fanId2:boolean,..

        *//**
         * after split =>
         * fansAndAlerts.get(0) = fanId1:boolean
         * fansAndAlerts.get(0) = fanId2:boolean
         * ...
         *//*
        List<String> fansAndAlerts = split(fansForAlerts);

        for(String s : fansAndAlerts){
            temp=splitHashMap(s);
            Fan fan = getFan(temp.get(0));
            Boolean bool = Boolean.parseBoolean(temp.get(1));
            hashMapFansForAlerts.put(fan,bool);
        }
        return hashMapFansForAlerts;
    }*/


    /*private List<String> stringToList(String ans) {
        List<String> res = new LinkedList<>();

        String[] split = ans.split("~");

        for (String s : split)
            res.add(s);

        return res;
    }

    private String ListToString(List<String> list) {

        String res = "";

        if (list.size() == 0)
            return res;

        for (String s : list)
            res = res + s + "~";

        res = res.substring(0, res.length() - 1);
        res = res + "\n";

        return res;
    }*/




    private static Coach.TrainingCoach getEnumTraining(String enumTraining) {
        switch (enumTraining){
            case "IFA_C":
                return Coach.TrainingCoach.IFA_C;
            case "UEFA_A":
                return Coach.TrainingCoach.UEFA_A;
            case "UEFA_B":
                return Coach.TrainingCoach.UEFA_B;
            case "UEFA_PRO":
                return Coach.TrainingCoach.UEFA_PRO;
        }
        return Coach.TrainingCoach.IFA_C;
    }

    private static Coach.RoleCoach getEnumRole(String enumRoleCoach) {
        switch (enumRoleCoach){
            case "main":
                return Coach.RoleCoach.main;
            case "assistantCoach":
                return Coach.RoleCoach.assistantCoach;
            case "fitness":
                return Coach.RoleCoach.fitness;
            case "goalkeeperCoach":
                return Coach.RoleCoach.goalkeeperCoach;
        }
        return Coach.RoleCoach.assistantCoach;
    }

    private static League.LevelLeague getEnumLevelLeague(String enumLevelLeague) {
        switch (enumLevelLeague){
            case "level1":
                return League.LevelLeague.level1;
            case "level2":
                return League.LevelLeague.level2;
            case "level3":
                return League.LevelLeague.level3;
            case "level4":
                return League.LevelLeague.level4;
            case "level5":
                return League.LevelLeague.level5;
        }
        return League.LevelLeague.level1;
    }

   /* public static Team getTeam(String teamId){
       List<String> team;
        team = dataAccess.getAllCellValues("Teams" ,teamId);
        return (Team) createObject("Team" , team);
        //return teams.get(teamId);
    }*/
    public static Team getTeam(String teamId){

        return teams.get(teamId);
    }

    public static List<Team> getTeams(){

        return new LinkedList<>(teams.values());
    }

    public static ScorePolicy getScorePolicy(String scorePolicyName) {

        if(scorePolicyName.equals("StandardScorePolicy")){
            StandardScorePolicy standardScorePolicy = new StandardScorePolicy();
            return standardScorePolicy;
        }else{
            CupScorePolicy cupScorePolicy = new CupScorePolicy();
            return cupScorePolicy;
        }
    }

    public static GameAssignmentPolicy getGameAssignmentPolicy(String gameAssignmentPolicy) {

        if(gameAssignmentPolicy.equals("PlayOnceWithEachTeamPolicy")){
            PlayOnceWithEachTeamPolicy playOnceWithEachTeamPolicy = new PlayOnceWithEachTeamPolicy();
            return playOnceWithEachTeamPolicy;
        }else{
            PlayTwiceWithEachTeamPolicy playTwiceWithEachTeamPolicy = new PlayTwiceWithEachTeamPolicy();
            return playTwiceWithEachTeamPolicy;
        }
    }

    public static Field getField(String fieldId) {
        if(dataAccess.isIDExists("Fields" ,fieldId)) {
            List<String> field;
            field = dataAccess.getAllCellValues("Fields", fieldId);
            return (Field) createObject("Field", field);
        }
        return null;

    }
    public static Complaint getComplaints(String complaintId) {
        if(dataAccess.isIDExists("Complaints" ,complaintId)) {
            List<String> complaint;
            complaint = dataAccess.getAllCellValues("Complaints" ,complaintId );
            return (Complaint) createObject("Complaint" , complaint);
        }
        return null;
    }

    public static PersonalPage getPersonalPage(String pageId){
        if(dataAccess.isIDExists("PersonalPages" ,pageId)) {
            List<String> personalPage;
            personalPage = dataAccess.getAllCellValues("PersonalPages" ,pageId );
            return (PersonalPage) createObject("PersonalPage" , personalPage);
        }
        return null;
    }

    public static Event getEvent(String eventId){
        if(dataAccess.isIDExists("Events" ,eventId)) {
            List<String> event;
            event = dataAccess.getAllCellValues("Events" ,eventId);
            return (Event) createObject("Event" , event);
        }
        return null;
    }

    public static EventReport getEventReport(String eventReportId){
        if(dataAccess.isIDExists("EventReports" ,eventReportId)) {
            List<String> eventReport;
            eventReport = dataAccess.getAllCellValues("EventReports" ,eventReportId);
            return (EventReport) createObject("EventReport" , eventReport);
        }
        return null;
    }

    public static Admin getAdmin(String userId){
        if(dataAccess.isIDExists("Admins" ,userId)) {
            List<String> admin;
            admin = dataAccess.getAllCellValues("Admins", userId);
            return (Admin) createObject("Admin", admin);
        }
        return null;

    }

    public static Coach getCoach(String userId){
        if(dataAccess.isIDExists("Coaches" ,userId)) {
            List<String> coach;
            coach = dataAccess.getAllCellValues("Coaches", userId);
            return (Coach) createObject("Coach", coach);
        }
        return null;
    }

    public static Fan getFan(String userId){
        if(dataAccess.isIDExists("Fans" ,userId)) {
            List<String> fan;
            fan = dataAccess.getAllCellValues("Fans", userId);
            return (Fan) createObject("Fan", fan);
        }
        return null;
    }

    public static Player getPlayer(String userId){
        if(dataAccess.isIDExists("Players" ,userId)) {
            List<String> player;
            player = dataAccess.getAllCellValues("Players", userId);
            return (Player) createObject("Player", player);
        }
        return null;
    }

    public static Referee getReferee(String userId){
        if(dataAccess.isIDExists("Referees" ,userId)) {
            List<String> referee;
            referee = dataAccess.getAllCellValues("Referees", userId);
            return (Referee) createObject("Referee", referee);
        }
        return null;
    }

    public static TeamManager getTeamManager(String userId){
        if(dataAccess.isIDExists("TeamManagers" ,userId)) {
            List<String> teamManager;
            teamManager = dataAccess.getAllCellValues("TeamManagers", userId);
            return (TeamManager) createObject("TeamManager", teamManager);
        }
        return null;
    }

    public static TeamOwner getTeamOwner(String userId){
        if(dataAccess.isIDExists("TeamOwners" ,userId)) {
            List<String> teamOwner;
            teamOwner = dataAccess.getAllCellValues("TeamOwners", userId);
            return (TeamOwner) createObject("TeamOwner", teamOwner);
        }
        return null;
    }

    public static UnionRepresentative getUnionRepresentative(String userId){
        if(dataAccess.isIDExists("UnionRepresentatives" ,userId)) {
            List<String> unionRepresentative;
            unionRepresentative = dataAccess.getAllCellValues("UnionRepresentatives", userId);
            return (UnionRepresentative) createObject("UnionRepresentative", unionRepresentative);
        }
        return null;
    }


    public static User createUser(String userId) {
        if(dataAccess.isIDExists("UnionRepresentatives" ,userId)) {
            List<String> user;
            user = dataAccess.getAllCellValues("Users", userId);
            return (User) createObject("User", user);
        }
        return null;
    }


    public static List<User> getAllUsers(){

        List<String> users;
        List<User> allUsers = new LinkedList<>();
        users = dataAccess.getAllTableValues("Users");

        for(String userString : users){
            List<String> tempUser = split(userString);
            allUsers.add((User) createObject("User" , tempUser));
        }

        return allUsers;
    }

    public static List<Team> getAllTeams() {
        List<String> teams;
        List<Team> allTeams = new LinkedList<>();

        teams = dataAccess.getAllTableValues("Teams");

        for(String userString : teams){
            List<String> tempUser = split(userString);
            allTeams.add((Team) createObject("Team" , tempUser));
        }

        return allTeams;

        //return new LinkedList<>(teams.values());
    }


    public static List<Game> getAllPastGames() {
        List<Game> games = getAllGames();
        Date currentDate = new Date();
        List<Game> pastGames = new LinkedList<>();

        for(Game game : games){
            if(currentDate.after(game.getDate())){
                pastGames.add(game);
            }
        }
        return pastGames;
    }

    public static List<Game> getAllFutureGames() {
        List<Game> games = getAllGames();
        Date currentDate = new Date();
        List<Game> futureGames = new LinkedList<>();

        for(Game game : games){
            if(currentDate.after(game.getDate())){
                futureGames.add(game);
            }
        }
        return futureGames;
    }

    public static List<Complaint> getAllActiveComplaints() {
        List<Complaint> complaints = getAllComplaints();
        List<Complaint> activeComplaints = new LinkedList<>();

        for(Complaint complaint : complaints){
            if(complaint.getIsActive()){
                activeComplaints.add(complaint);
            }
        }
        return activeComplaints;
    }

    public static List<Complaint> getAllComplaints() {
        List<String> complaints;
        List<Complaint> allComplaints = new LinkedList<>();

        complaints = dataAccess.getAllTableValues("Complaints");

        for(String userString : complaints){
            List<String> tempUser = split(userString);
            allComplaints.add((Complaint) createObject("Complaint" , tempUser));
        }

        return allComplaints;
    }

    public static List<Game> getAllGames() {
        List<String> games;
        List<Game> allGames = new LinkedList<>();

        games = dataAccess.getAllTableValues("Games");

        for(String userString : games){
            List<String> tempUser = split(userString);
            allGames.add((Game) createObject("Game" , tempUser));
        }

        return allGames;
    }

    public static List<PersonalPage> getAllPages() {
        List<String> personalPages;
        List<PersonalPage> allPersonalPages = new LinkedList<>();

        personalPages = dataAccess.getAllTableValues("PersonalPages");

        for(String userString : personalPages){
            List<String> tempUser = split(userString);
            allPersonalPages.add((PersonalPage) createObject("PersonalPage" , tempUser));
        }

        return allPersonalPages;
    }

    public static List<Player> getAllPlayers() {
        List<String> players;
        List<Player> allPlayers = new LinkedList<>();

        players = dataAccess.getAllTableValues("Players");

        for(String userString : players){
            List<String> tempUser = split(userString);
            allPlayers.add((Player) createObject("Player" , tempUser));
        }

        return allPlayers;
    }

    public static List<Coach> getAllCoaches() {
        List<String> coaches;
        List<Coach> allCoaches = new LinkedList<>();

        coaches = dataAccess.getAllTableValues("Coaches");

        for(String userString : coaches){
            List<String> tempUser = split(userString);
            allCoaches.add((Coach) createObject("Coach" , tempUser));
        }

        return allCoaches;
    }

    public static List<Field> getAllFields() {
        List<String> fields;
        List<Field> allFields = new LinkedList<>();

        fields = dataAccess.getAllTableValues("Fields");

        for(String userString : fields){
            List<String> tempUser = split(userString);
            allFields.add((Field) createObject("Field" , tempUser));
        }

        return allFields;
    }

    public static List<League> getAllLeagues() {
        List<String> leagues;
        List<League> allLeagues = new LinkedList<>();

        leagues = dataAccess.getAllTableValues("Leagues");

        for(String userString : leagues){
            List<String> tempUser = split(userString);
            allLeagues.add((League) createObject("League" , tempUser));
        }

        return allLeagues;
    }

    public static List<Season> getAllSeasons() {
        List<String> seasons;
        List<Season> allSeasons = new LinkedList<>();

        seasons = dataAccess.getAllTableValues("Seasons");

        for(String userString : seasons){
            List<String> tempUser = split(userString);
            allSeasons.add((Season) createObject("Season" , tempUser));
        }

        return allSeasons;
    }

    public static List<Referee> getAllReferees() {
        List<String> referees;
        List<Referee> allReferees = new LinkedList<>();

        referees = dataAccess.getAllTableValues("Referees");

        for(String userString : referees){
            List<String> tempUser = split(userString);
            allReferees.add((Referee) createObject("Referee" , tempUser));
        }

        return allReferees;
    }


/*
    public static void addMessageToUser(String userId , String message){
        String oldMessages = "";
        //if userId exsist
        if(dataAccess.isIDExists("OfflineUsersNotifications",userId)){
            oldMessages = getMessages(userId);
            dataAccess.updateCellValue("OfflineUsersNotifications" ,"Notifications" ,
                    userId ,oldMessages +"," +message);
        }

    }

    private static String getMessages(String userId){
        if(dataAccess.isIDExists("OfflineUsersNotifications",userId)){
           return dataAccess.getCellValue("OfflineUsersNotifications" ,"Notifications" ,userId);
        }
        return "";
    }

    public static List<String> getAllMessages(String userId){
        if(dataAccess.isIDExists("OfflineUsersNotifications",userId)) {
            List<String> allMessages = split(getMessages(userId));
            return allMessages;
        }
        return null;

    }
*/



}



