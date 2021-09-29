package com.example.aska;

public class UserInfo {

    public TheQOfUser qnum1;public TheQOfUser qnum2;public TheQOfUser qnum3;public TheQOfUser qnum4;public TheQOfUser qnum5;
    public String cash;
    public String userLocation;
    public String reportSent;public String reportGot;
    public String hobby;public String profession;public String hobby2;public String hobby3;
    public ReportsTime reportsTime;
    public String roomCode;
    public String usersRooms;
    public UserLikes userLikes;
    public String allowWrite;

    public UserInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserInfo(TheQOfUser Qnum1,TheQOfUser Qnum2,TheQOfUser Qnum3,TheQOfUser Qnum4,TheQOfUser Qnum5, String cash,
                    String UserLocation,String reportSent,String reportGot,String Hobby,String Hobby2,String Hobby3,String Profession, ReportsTime reportsTime,
                    String RoomCode,String UsersRooms,UserLikes userLikes, String AllowWrite) {
        this.cash = cash;
        this.userLocation = UserLocation;
        this.reportGot = reportGot;
        this.reportSent = reportSent;
        this.qnum1 = Qnum1;
        this.qnum2 = Qnum2;
        this.qnum3 = Qnum3;
        this.qnum4 = Qnum4;
        this.qnum5 = Qnum5;
        this.hobby=Hobby;
        this.profession=Profession;
        this.hobby2=Hobby2;
        this.hobby3=Hobby3;
        this.reportsTime=reportsTime;
        this.roomCode=RoomCode;
        this.usersRooms=UsersRooms;
        this.userLikes=userLikes;
        this.allowWrite=AllowWrite;
    }

    public TheQOfUser getQnum1() {
        return qnum1;
    }

    public void setQnum1(TheQOfUser qnum1) {
        this.qnum1 = qnum1;
    }

    public TheQOfUser getQnum2() {
        return qnum2;
    }

    public void setQnum2(TheQOfUser qnum2) {
        this.qnum2 = qnum2;
    }

    public TheQOfUser getQnum3() {
        return qnum3;
    }

    public void setQnum3(TheQOfUser qnum3) {
        this.qnum3 = qnum3;
    }

    public TheQOfUser getQnum4() {
        return qnum4;
    }

    public void setQnum4(TheQOfUser qnum4) {
        this.qnum4 = qnum4;
    }

    public TheQOfUser getQnum5() {
        return qnum5;
    }

    public void setQnum5(TheQOfUser qnum5) {
        this.qnum5 = qnum5;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getReportSent() {
        return reportSent;
    }

    public void setReportSent(String reportSent) {
        this.reportSent = reportSent;
    }

    public String getReportGot() {
        return reportGot;
    }

    public void setReportGot(String reportGot) {
        this.reportGot = reportGot;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getHobby2() {
        return hobby2;
    }

    public void setHobby2(String hobby2) {
        this.hobby2 = hobby2;
    }

    public String getHobby3() {
        return hobby3;
    }

    public void setHobby3(String hobby3) {
        this.hobby3 = hobby3;
    }

    public ReportsTime getReportsTime() {
        return reportsTime;
    }

    public void setReportsTime(ReportsTime reportsTime) {
        this.reportsTime = reportsTime;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getUsersRooms() {
        return usersRooms;
    }

    public void setUsersRooms(String usersRooms) {
        this.usersRooms = usersRooms;
    }

    public UserLikes getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(UserLikes userLikes) {
        this.userLikes = userLikes;
    }

    public String getAllowWrite() {
        return allowWrite;
    }

    public void setAllowWrite(String allowWrite) {
        this.allowWrite = allowWrite;
    }
}
