package com.example.aska;

import java.util.Date;

public class ReportsTime {

    public String lastReportSend;
    public String sumReportSendToday;
    public String banTill;
    public String bansGot;


    public ReportsTime(){

    }

    public ReportsTime(String LastReportSend, String SumReportsSendToday, String BanTill,String BanGot){

        this.lastReportSend=LastReportSend;
        this.sumReportSendToday=SumReportsSendToday;
        this.banTill=BanTill;
        this.bansGot=BanGot;
    }

    public String getLastReportSend() {
        return lastReportSend;
    }

    public void setLastReportSend(String lastReportSend) {
        this.lastReportSend = lastReportSend;
    }

    public String getSumReportSendToday() {
        return sumReportSendToday;
    }

    public void setSumReportSendToday(String sumReportSendToday) {
        this.sumReportSendToday = sumReportSendToday;
    }

    public String getBanTill() {
        return banTill;
    }

    public void setBanTill(String banTill) {
        this.banTill = banTill;
    }

    public String getBansGot() {
        return bansGot;
    }

    public void setBansGot(String bansGot) {
        this.bansGot = bansGot;
    }
}
