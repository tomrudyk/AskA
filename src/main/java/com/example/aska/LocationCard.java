package com.example.aska;

public class LocationCard {

    public String theQ;
    public String theA;
    public String userId;
    public String ansBy;
    public String theHobby;
    public String theProfession;


    public LocationCard() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public LocationCard(String TheQ,String TheA,String UserId,String AnsBy,String Hobby,String Profession) {
        this.theQ = TheQ;
        this.theA = TheA;
        this.userId = UserId;
        this.ansBy = AnsBy;
        this.theHobby=Hobby;
        this.theProfession=Profession;
    }

    public String getTheQ() {
        return theQ;
    }

    public void setTheQ(String theQ) {
        this.theQ = theQ;
    }

    public String getTheA() {
        return theA;
    }

    public void setTheA(String theA) {
        this.theA = theA;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnsBy() {
        return ansBy;
    }

    public void setAnsBy(String ansBy) {
        this.ansBy = ansBy;
    }

    public String getTheHobby() {
        return theHobby;
    }

    public void setTheHobby(String theHobby) {
        this.theHobby = theHobby;
    }

    public String getTheProfession() {
        return theProfession;
    }

    public void setTheProfession(String theProfession) {
        this.theProfession = theProfession;
    }
}
