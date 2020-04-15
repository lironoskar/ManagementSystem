package Presentation;

import Domain.Complaint;
import Domain.PersonalPage;
import java.util.LinkedList;
import java.util.List;

public class Fan extends User {

    private String address;
    private String phone;
    private List<Complaint> complaints;
    private List<PersonalPage> followPages;
    private List<String> searchHistory;

    public Fan(String mail, String firstName, String lastName,
               String phone, String address) {
        super(firstName, lastName,"F", mail);
        this.address = address;
        this.phone = phone;
        complaints = new LinkedList<>();
        followPages = new LinkedList<>();
        searchHistory = new LinkedList<>();
    }

    // ++++++++++++++++++++++++++++ Functions ++++++++++++++++++++++++++++
    /**
     * registration for game alerts- In menu of fan
     */
    public void registrationForGameAlerts(){
    }
    /**
     *
     * @param page
     */
    public void followPage(PersonalPage page)
    {

    }

    /**
     *
     */
    public void submitAComplaint()
    {

    }

    /**
     *
     */
    public void viewSearchHistory()
    {

    }
    public void editDetails(String firstName, String lastName, String address, String phone) {
        super.editDetails(firstName, lastName);
        this.address = address;
        this.phone = phone;

    }
    public boolean addPageToFollow(PersonalPage page){
        if(!followPages.contains(page)){
            followPages.add(page);
            return true;
        }
        return false;
    }


    // ++++++++++++++++++++++++++++ getter&setter ++++++++++++++++++++++++++++
    public String getAddress() {
        return address;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public List<PersonalPage> getFollowPages() {
        return followPages;
    }

    public boolean addToSearchHistory(String word){ return searchHistory.add(word);}

    public List<String> getSearchHistory() {
        return searchHistory;
    }

}
