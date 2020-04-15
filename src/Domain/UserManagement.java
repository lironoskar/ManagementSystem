package Domain;

import Data.Database;
import Presentation.Fan;
import Presentation.User;

public class UserManagement//for admins
{
    private Database database;

    public UserManagement(Database database) {
        this.database = database;
    }

    /*
       this function adds a new user to the system
    */
    public void addUser(String userId, String password, User user) {
        database.addUser(userId, password, user);
    }
    /*
    Remove user
     */
    public void removeUser(String userId){
        database.removeUser(userId);
    }
    /*
     * User login to system
     */
    public User logInUserToSystem(String mail, String password) {
        if(database.authenticationCheck(mail, password))
            return database.getUser(mail);
        return null;
    }
    /*
    Guest registration for the system
     */
    public boolean registrationToSystem(String mail, String password, String firstName, String lastName, String phone,
                                        String address) {
        if(!database.authenticationCheck(mail, password)){
            Fan fan = new Fan(mail, firstName, lastName, phone,address);
            database.addUser(fan.getID(), password, fan);
            return true;
        }
        return false;
    }

    public boolean registrationToFollowUp(Fan fan, PersonalPage page){
        return fan.addPageToFollow(page);
    }
}
