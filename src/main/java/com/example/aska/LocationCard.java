package com.example.aska;

public class LocationCard {

    public String theQ;
    public String theA;
    public String userId;
    public String ansBy;


    public LocationCard() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public LocationCard(String TheQ,String TheA,String UserId,String AnsBy) {
        this.theQ = TheQ;
        this.theA = TheA;
        this.userId = UserId;
        this.ansBy = AnsBy;
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
}
