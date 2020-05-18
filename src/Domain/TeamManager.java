package Domain;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

public class TeamManager extends Manager implements PartOfATeam, Observer {

    private HashSet<Team> teams;
    private boolean isActive;
    private double price;

    private boolean permissionManageAssets;
    private boolean permissionFinance;



    public TeamManager(User user, double price, boolean manageAssets , boolean finance ) {
        teams = new HashSet<>();
        this.price = price;
        isActive = true;
        permissionManageAssets = manageAssets;
        permissionFinance = finance;
        myRole = "TeamManager";
        this.user = user;
    }

    @Override
    public String getID() {
        return user.getID();
    }

    @Override
    public void deactivate() {
        isActive=false;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double update) {
        price = update;
    }

    @Override
    public HashSet<Team> getTeams() {
        return teams;
    }

    public boolean isPermissionManageAssets() {
        return permissionManageAssets;
    }

    public boolean isPermissionFinance() {
        return permissionFinance;
    }

    @Override
    public void addTeam(Team team) {
        teamsToManage.add(team);
        teams.add(team);
    }

    @Override
    public void removeTeam(Team team) {
        teamsToManage.remove(team);
        teams.remove(team);
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void reactivate() {
        isActive = true;
    }

    @Override
    public String myRole() {
        return "TeamManager";
    }

    @Override
    public String toString() {
        return "TeamManager" +
                ", id="+ user.getID()+
                ": name="+ user.getName()+
                ", price=" + price +
                ", permission manage assets=" + permissionManageAssets +
                ", permission finance=" + permissionFinance+
                ", teams= "+ teamsString(teams);
    }

    @Override
    public void update(Observable o, Object arg) {
        String news = (String)arg;
        user.addMessage(news);
    }
}