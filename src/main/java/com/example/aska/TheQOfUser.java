package com.example.aska;

public class TheQOfUser {

    public String userQ;
    public String userA;
    public String qLocation;
    public String numInState;
    public String qHobby;
    public String qProfession;

    public TheQOfUser() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public TheQOfUser(String UserQ,String UserA,String QLocation,String NumInState,String Hobby,String Profession) {
        this.userQ = UserQ;
        this.userA = UserA;
        this.qLocation = QLocation;
        this.numInState = NumInState;
        this.qHobby=Hobby;
        this.qProfession=Profession;
    }


    public String getUserQ() {
        return userQ;
    }

    public void setUserQ(String userQ) {
        this.userQ = userQ;
    }

    public String getUserA() {
        return userA;
    }

    public void setUserA(String userA) {
        this.userA = userA;
    }

    public String getqLocation() {
        return qLocation;
    }

    public void setqLocation(String qLocation) {
        this.qLocation = qLocation;
    }

    public String getNumInState() {
        return numInState;
    }

    public void setNumInState(String numInState) {
        this.numInState = numInState;
    }

    public String getqHobby() {
        return qHobby;
    }

    public void setqHobby(String qHobby) {
        this.qHobby = qHobby;
    }

    public String getqProfession() {
        return qProfession;
    }

    public void setqProfession(String qProfession) {
        this.qProfession = qProfession;
    }
}


