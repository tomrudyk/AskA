package com.example.aska;

public class UserInfo {

    public TheQOfUser qnum1;public TheQOfUser qnum2;public TheQOfUser qnum3;public TheQOfUser qnum4;public TheQOfUser qnum5;
    public String cash;
    public String userLocation;
    public String reportSent;public String reportGot;

    public UserInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserInfo(TheQOfUser Qnum1,TheQOfUser Qnum2,TheQOfUser Qnum3,TheQOfUser Qnum4,TheQOfUser Qnum5, String cash, String UserLocation,String reportSent,String reportGot) {
        this.cash = cash;
        this.userLocation = UserLocation;
        this.reportGot = reportGot;
        this.reportSent = reportSent;
        this.qnum1 = Qnum1;
        this.qnum2 = Qnum2;
        this.qnum3 = Qnum3;
        this.qnum4 = Qnum4;
        this.qnum5 = Qnum5;
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
}
