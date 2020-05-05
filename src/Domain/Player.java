package Domain;

import java.util.Date;
import java.util.HashSet;

public class Player implements PartOfATeam, Role {

    private Date birthDate;
    private String role;
    private User user;
    private String id;
    private String name;
    private HashSet<Team> teams;
    private boolean isActive;
    private double price;


    public Player(String id,String name, Date birthDate, String role, double price, User user) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.role = role;
        this.user = user;
        this.teams = new HashSet<>();
        this.isActive = true;
        this.price = price;

    }

    // ++++++++++++++++++++++++++++ Functions ++++++++++++++++++++++++++++

    public Date getBirthDate() {
        return birthDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void deactivate() {
        isActive = false;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double update) {
        this.price = update;
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
    public void reactivate() {
        isActive = true;
    }

    @Override
    public String myRole() {
        return "Player";
    }

    @Override
    public String toString() {
        return "Player{" +
                "birthDate=" + birthDate +
                ", role='" + role + '\'' +
                '}';
    }
}
