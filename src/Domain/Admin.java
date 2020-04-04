package Domain;

import Service.Notifications;

import java.util.List;

public class Admin extends User {

    private List<Complaint> allComplaints; // needs to be synchronized with all users' complaints
    private Notifications notifications;

    public Admin()
    {
        notifications = new Notifications();
    }
    // ++++++++++++++++++++++++++++ Functions ++++++++++++++++++++++++++++


    /**
     *
     * @param team
     */
    public void deleteTeam(Team team)
    {
        if(!team.isPermanentlyClosed()){
            team.setActive(false);
            team.setPermanentlyClosed(true);
            notifications.openORcloseTeam("permanently Closed", team , true);
            System.out.println("Done successfully");
        }
    }

    /**
     *
     * @param user
     */
    public void deleteUser(User user)
    {

        // need to check that the Admin doesn't delete himself

    }

    /**
     *
     */
    public void responseToComplaint()
    {

    }

    /**
     *
     */
    public void viewLog()
    {

    }

    /**
     *
     */
    public void trainModel()
    {

    }

    // ++++++++++++++++++++++++++++ getter&setter ++++++++++++++++++++++++++++
    public List<Complaint> getAllComplaints() {
        return allComplaints;
    }
}
