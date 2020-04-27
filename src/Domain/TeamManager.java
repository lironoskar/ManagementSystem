package Domain;

import java.util.HashSet;

public class TeamManager extends Manager implements Role,PartOfATeam {


    private String id;
    private HashSet<Team> teams;
    private boolean isActive;
    private double price;


    public TeamManager(String id,double price) {
        this.id = id;
        teams = new HashSet<>();
        this.price = price;
        isActive = true;
    }

    @Override
    public String getID() {
        return id;
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

    @Override
    public void addTeam(Team team) {
        teams.add(team);
    }

    @Override
    public void removeTeam(Team team) {
        teams.remove(team);
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public String myRole() {
        return "TeamManager";
    }

}