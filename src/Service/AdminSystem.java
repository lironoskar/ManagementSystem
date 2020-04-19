package Service;

import Domain.*;
import Domain.Admin;
import Domain.User;
import Presentation.Checker;

import java.awt.*;

public class AdminSystem {

    private NotificationSystem notificationSystem;
    private ComplaintManager complaintManager;

    public AdminSystem(NotificationSystem notificationSystem, ComplaintManager complaintManager) {
        this.notificationSystem = notificationSystem;
        this.complaintManager = complaintManager;
    }

    /*
        Remove user by an administrator
        */
    public void removeUser(Admin admin, String userId){
        String userMail = admin.removeUser(userId);
        notificationSystem.UserRemovalNotification(userMail);
    }

    /*
    this function adds a new user to the system
    */
    public boolean addUser(Admin admin, String password, User user) {
        if(Checker.isValidPassword(password)){
            admin.addUser(password, user);
            return true;
        }
        return false;
    }

    /*
Permanently close a group only by an administrator
 */
    public boolean permanentlyCloseTeam(Admin admin, Team team){
        if(admin.permanentlyCloseTeam(team)){
            notificationSystem.openORCloseTeam("closed", team, true);
            return true;
        }
        return false;
    }

    /**
     *
     */
    public void responseToComplaint(Admin admin, Complaint complaint)
    {
        admin.responseToComplaint(complaint);
        complaintManager.responseToComplaint(admin,complaint);
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
}
