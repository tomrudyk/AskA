package com.example.aska;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class MyInfo extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private static final String TAG = "MyInfo";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String UserId = mAuth.getCurrentUser().getUid();

    private TheQOfUser TheUserQ1 = new TheQOfUser();  private TheQOfUser TheUserQ2 = new TheQOfUser();
    private TheQOfUser TheUserQ3 = new TheQOfUser(); private TheQOfUser TheUserQ4 = new TheQOfUser();
    private TheQOfUser TheUserQ5 = new TheQOfUser();
    private String Q1="";private String Q2="";private String Q3="";
    private String Q4="";private String Q5="";
    private String Location1="";private String Location2="";private String Location3="";
    private String Location4="";private String Location5="";
    private String A1="";private String A2="";private String A3="";
    private String A4="";private String A5="";
    private String StringCash;private String ReportGot;private String ReportSent;private String UserLocation;
    private TheQOfUser TheUserQ1Controller = new TheQOfUser();  private TheQOfUser TheUserQ2Controller = new TheQOfUser();
    private TheQOfUser TheUserQ3Controller = new TheQOfUser(); private TheQOfUser TheUserQ4Controller = new TheQOfUser();
    private TheQOfUser TheUserQ5Controller = new TheQOfUser();
    private String StringCashOfController;private String ReportGotOfController;private String ReportSentOfController;private String UserLocationOfController;
    private String NumInState1="";private String NumInState2="";private String NumInState3="";
    private String NumInState4="";private String NumInState5="";
    String UserQuestions[] = new String[5];
    String UserAnswers[] = new String[5];
    String NumInState[] = new String[5];
    String LocationsOfQUser[] = new String[5];
    private int QuestionNumView=-1;
    private String Q1Location="";private String Q2Location="";private String Q3Location="";
    private String Q4Location="";private String Q5Location="";
    public int ChildrenCount=0 ;
    private String TheQOfLocationCard="";private String TheAOfLocationCard="";private String UserIdOfSender="";private String UserIdOfAnswer="";

    private String UserHobby;private String UserHobby2;private String UserHobby3;private String UserProfession;private String UserControllerHobby;
    private String UserControllerHobby2;private String UserControllerProfession;private String UserControllerHobby3;
    private String QHobby1;private String QProfession1;private String QHobby2;private String QProfession2;
    private String QHobby3;private String QProfession3;private String QHobby4;private String QProfession4;
    private String QHobby5;private String QProfession5;
    private String TheQOfLocationCardHobby="";private String TheQOfLocationCardProfession="";
    private ReportsTime UserReportsTime;private ReportsTime ControllerReportsTime;
    int NotMovedOnPressAgain1=-5;int NotMovedOnPressAgain2=-5;
    private String UserControllingRoomCode;private String OtherUserRoomCode;
    private String UserControllingRoomsJoined;private String OtherUserRoomsJoined;

    private UserLikes OtherUserLikes;private String OtherUserLikedByWhom; String OtherUserLikeCount;
    private UserLikes ControllingUserLikes;private String ControllingUserLikedByWhom; String ControllingUserLikeCount;
    private String RoomsString="";

    private boolean FromBack=false; private boolean FromNext=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        Button GoEditInfo = findViewById(R.id.EditInfo);
        Button nextBtn = findViewById(R.id.nextBtn);
        Button backBtn = findViewById(R.id.backBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);
        Button reSendBtn = findViewById(R.id.reSendBtn);
        Button ReportAns = findViewById(R.id.ReportAns);
        Button UserLikeBtn = findViewById(R.id.LikeBtn);
        UserLikeBtn.setVisibility(View.GONE); // Hides Like Btn
        TextView TheQ = findViewById(R.id.UserQ);
        TextView TheA = findViewById(R.id.UserA);
        TextView cashView = findViewById(R.id.cashView);
        TextView LocationOfQTxt = findViewById(R.id.LocationOfQtxt);
        TextView Qnumber = findViewById(R.id.Qnumber);

        deleteBtn.setEnabled(false);
        reSendBtn.setEnabled(false);
        ReportAns.setEnabled(false);
        UserLikeBtn.setEnabled(false);
        Qnumber.setText(String.valueOf(QuestionNumView));
        TheA.setMovementMethod(new ScrollingMovementMethod());
        TheQ.setMovementMethod(new ScrollingMovementMethod());

        TheQ.setText("");TheA.setText("");cashView.setText("");LocationOfQTxt.setText("");
        Qnumber.setText(String.valueOf(QuestionNumView));

        myRef = database.getReference("Users").child(UserId);
        getValueOfUser(myRef);
        getValueOfUserControl(myRef);
        myRef = database.getReference("Rooms").child("RoomsString");
        getValueOfRoomsCodeString(myRef);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashView.setText(StringCashOfController);
                FromNext=true;
                FromBack=false;
                myRef = database.getReference("Rooms").child("RoomsString");
                getValueOfRoomsCodeString(myRef);
                for (int i=-1;i<4;i++){
                    if (i==QuestionNumView){
                        TheQ.setText(UserQuestions[i+1]);
                        LocationOfQTxt.setText(LocationsOfQUser[i+1]);
                        myRef = database.getReference(LocationsOfQUser[i+1]+LocationsOfQUser[i+1]);
                        getValueOfState(myRef);
                        myRef = database.getReference(LocationsOfQUser[i+1]).child(String.valueOf(ChildrenCount));
                        getValueOfCardLocation(myRef);

                        if(UserAnswers[i+1].equals("0")){
                            TheA.setText("-Not Ans Yet-");
                            reSendBtn.setEnabled(false);
                            ReportAns.setEnabled(false);
                            UserLikeBtn.setEnabled(false);
                        }
                        else {
                            TheA.setText(UserAnswers[i+1]);
                            reSendBtn.setEnabled(true);
                            if(RoomsString.contains(LocationsOfQUser[i+1])) {
                                ReportAns.setEnabled(false);
                            }else{
                                ReportAns.setEnabled(true);
                            }
                            myRef = database.getReference("Users").child(String.valueOf(UserIdOfAnswer));
                            /*if(!String.valueOf(UserIdOfAnswer).equals("null")&&UserIdOfAnswer!=null){
                                Toast.makeText(MyInfo.this, String.valueOf(UserIdOfAnswer), Toast.LENGTH_SHORT).show();
                                //getValueOfUser(myRef);
                            }
                            UserLikeBtn.setEnabled(true); */ //UserLike Thing -- Doesn't Work
                        }
                        if(UserQuestions[i+1].equals("0")){
                            TheQ.setText("-Not Sent Yet-");
                            deleteBtn.setEnabled(false);
                        }
                        else {
                            TheQ.setText(UserQuestions[i+1]);
                            deleteBtn.setEnabled(true);
                        }
                        QuestionNumView++;
                        Qnumber.setText(String.valueOf(QuestionNumView+1));
                        i=5;
                    }
                }
            }

        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashView.setText(StringCashOfController);
                FromNext=false;
                FromBack=true;
                myRef = database.getReference("Rooms").child("RoomsString");
                getValueOfRoomsCodeString(myRef);
                for (int i=1;i<6;i++){
                    if (i==QuestionNumView){
                        TheQ.setText(UserQuestions[i-1]);
                        LocationOfQTxt.setText(LocationsOfQUser[i-1]);
                        myRef = database.getReference(LocationsOfQUser[i-1]+LocationsOfQUser[i-1]);
                        getValueOfState(myRef);
                        myRef = database.getReference(LocationsOfQUser[i-1]).child(String.valueOf(ChildrenCount));
                        getValueOfCardLocation(myRef);

                        if(UserAnswers[i-1].equals("0")){
                            TheA.setText("-Not Ans Yet-");
                            reSendBtn.setEnabled(false);
                            ReportAns.setEnabled(false);
                            UserLikeBtn.setEnabled(false);
                        }
                        else {
                            TheA.setText(UserAnswers[i-1]);
                            reSendBtn.setEnabled(true);
                            if(RoomsString.contains(LocationsOfQUser[i-1])) {
                                ReportAns.setEnabled(false);
                            }else{
                                ReportAns.setEnabled(true);
                            }
                            myRef = database.getReference("Users").child(String.valueOf(UserIdOfAnswer));
                            /*if(!String.valueOf(UserIdOfAnswer).equals("null")&&UserIdOfAnswer!=null){
                                Toast.makeText(MyInfo.this, String.valueOf(UserIdOfAnswer), Toast.LENGTH_SHORT).show();
                                //getValueOfUser(myRef);
                            }
                            UserLikeBtn.setEnabled(true); */ //UserLike Thing -- Doesn't Work
                        }
                        if(UserQuestions[i-1].equals("0")){
                            TheQ.setText("-Not Sent Yet-");
                            deleteBtn.setEnabled(false);
                        }
                        else {
                            TheQ.setText(UserQuestions[i-1]);
                            deleteBtn.setEnabled(true);
                        }
                        QuestionNumView--;
                        Qnumber.setText(String.valueOf(QuestionNumView+1));
                        i=5;
                    }
                }
            }
        });



        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = database.getReference("Users").child(UserId);
                getValueOfUserControl(myRef);
                String Location="";
                String NumInState="";
                TheQOfUser TheQ2 = new TheQOfUser("0","0","0","0","0","0");
                if(QuestionNumView==0){
                    UserInfoEdit(TheQ2,TheUserQ2,TheUserQ3,TheUserQ4,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId,UserHobby,UserHobby2,UserHobby3,UserProfession
                            ,UserReportsTime, OtherUserRoomCode, OtherUserRoomsJoined, OtherUserLikes);
                    Location = Q1Location;
                    NumInState=NumInState1;
                }
                if(QuestionNumView==1){
                    UserInfoEdit(TheUserQ1,TheQ2,TheUserQ3,TheUserQ4,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId,UserHobby,UserHobby2,UserHobby3,UserProfession
                            ,UserReportsTime, OtherUserRoomCode, OtherUserRoomsJoined, OtherUserLikes);
                    Location = Q2Location;
                    NumInState=NumInState2;
                }
                if(QuestionNumView==2){
                    UserInfoEdit(TheUserQ1,TheUserQ2,TheQ2,TheUserQ4,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId,UserHobby,UserHobby2,UserHobby3,UserProfession
                            ,UserReportsTime, OtherUserRoomCode, OtherUserRoomsJoined, OtherUserLikes);
                    Location = Q3Location;
                    NumInState=NumInState3;
                }
                if(QuestionNumView==3){
                    UserInfoEdit(TheUserQ1,TheUserQ2,TheUserQ3,TheQ2,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId,UserHobby,UserHobby2,UserHobby3,UserProfession
                            ,UserReportsTime, OtherUserRoomCode, OtherUserRoomsJoined, OtherUserLikes);
                    Location = Q4Location;
                    NumInState=NumInState4;
                }
                if(QuestionNumView==4){
                    UserInfoEdit(TheUserQ1,TheUserQ2,TheUserQ3,TheUserQ4,TheQ2,StringCash,UserLocation,ReportSent,ReportGot,UserId,UserHobby,UserHobby2,UserHobby3,UserProfession
                            ,UserReportsTime, OtherUserRoomCode, OtherUserRoomsJoined, OtherUserLikes);
                    Location = Q5Location;
                    NumInState=NumInState5;
                }
                myRef = database.getReference(Location+Location);
                getValueOfState(myRef);
                myRef = database.getReference(Location).child(String.valueOf(ChildrenCount));
                getValueOfCardLocation(myRef);
                LocationCard LocationQCardLAST= new LocationCard(TheQOfLocationCard,TheAOfLocationCard,UserIdOfSender,UserIdOfAnswer,TheQOfLocationCardHobby,TheQOfLocationCardProfession);

                myRef = database.getReference(Location).child(NumInState);
                myRef.setValue(LocationQCardLAST);
                myRef = database.getReference(Location).child(String.valueOf(ChildrenCount));
                LocationCard TheQ2OfLocationCard = new LocationCard("0","0","0","0","0","0");
                myRef.setValue(TheQ2OfLocationCard);

                myRef = database.getReference("Users").child(UserIdOfSender);
                getValueOfUser(myRef);
                for(int i =1;i<=5;i++){
                    if(i==1&&TheQOfLocationCard.equals(Q1)&&Location.equals(Location1)){
                        myRef = database.getReference("Users").child(UserIdOfSender).child("qnum1").child("numInState");
                        myRef.setValue(NumInState);
                    }
                    if(i==2&&TheQOfLocationCard.equals(Q2)&&Location.equals(Location2)){
                        myRef = database.getReference("Users").child(UserIdOfSender).child("qnum2").child("numInState");
                        myRef.setValue(NumInState);
                    }
                    if(i==3&&TheQOfLocationCard.equals(Q3)&&Location.equals(Location3)){
                        myRef = database.getReference("Users").child(UserIdOfSender).child("qnum3").child("numInState");
                        myRef.setValue(NumInState);
                    }
                    if(i==4&&TheQOfLocationCard.equals(Q4)&&Location.equals(Location4)){
                        myRef = database.getReference("Users").child(UserIdOfSender).child("qnum4").child("numInState");
                        myRef.setValue(NumInState);
                    }
                    if(i==5&&TheQOfLocationCard.equals(Q5)&&Location.equals(Location5)){
                        myRef = database.getReference("Users").child(UserIdOfSender).child("qnum5").child("numInState");
                        myRef.setValue(NumInState);
                    }
                }

                myRef = database.getReference(Location+Location);
                myRef.setValue(String.valueOf(ChildrenCount-1));

                TheQ.setText("");
                LocationOfQTxt.setText("");
                deleteBtn.setEnabled(false);

            }
        });

        reSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    myRef = database.getReference("Users").child(UserId);
                    getValueOfUserControl(myRef);
                    myRef = database.getReference("Rooms").child("RoomsString");
                    getValueOfRoomsCodeString(myRef);
                    String Location="";
                    String NumInState="";
                    if(QuestionNumView==0){
                        TheQOfUser TheQ2 = new TheQOfUser(Q1,"0",Q1Location,NumInState1,QHobby1,QProfession1);
                        UserInfoEdit(TheQ2,TheUserQ2,TheUserQ3,TheUserQ4,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId,UserHobby,UserHobby2,UserHobby3,UserProfession
                                ,UserReportsTime, OtherUserRoomCode, OtherUserRoomsJoined, OtherUserLikes);
                        Location = Q1Location;
                        NumInState=String.valueOf(NumInState1);
                        TheQOfLocationCard=String.valueOf(Q1);
                        TheQOfLocationCardHobby=String.valueOf(QHobby1);
                        TheQOfLocationCardProfession=String.valueOf(QProfession1);
                    }
                    if(QuestionNumView==1){
                        TheQOfUser TheQ2 = new TheQOfUser(Q2,"0",Q2Location,NumInState2,QHobby2,QProfession2);
                        UserInfoEdit(TheUserQ1,TheQ2,TheUserQ3,TheUserQ4,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId,UserHobby,UserHobby2,UserHobby3,UserProfession
                                ,UserReportsTime, OtherUserRoomCode, OtherUserRoomsJoined, OtherUserLikes);
                        Location = Q2Location;
                        NumInState=String.valueOf(NumInState2);
                        TheQOfLocationCard=String.valueOf(Q2);
                        TheQOfLocationCardHobby=String.valueOf(QHobby2);
                        TheQOfLocationCardProfession=String.valueOf(QProfession2);
                    }
                    if(QuestionNumView==2){
                        TheQOfUser TheQ2 = new TheQOfUser(Q3,"0",Q3Location,NumInState3,QHobby3,QProfession3);
                        UserInfoEdit(TheUserQ1,TheUserQ2,TheQ2,TheUserQ4,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId,UserHobby,UserHobby2,UserHobby3,UserProfession
                                ,UserReportsTime, OtherUserRoomCode, OtherUserRoomsJoined, OtherUserLikes);
                        Location = Q3Location;
                        NumInState=String.valueOf(NumInState3);
                        TheQOfLocationCard=String.valueOf(Q3);
                        TheQOfLocationCardHobby=String.valueOf(QHobby3);
                        TheQOfLocationCardProfession=String.valueOf(QProfession3);
                    }
                    if(QuestionNumView==3){
                        TheQOfUser TheQ2 = new TheQOfUser(Q4,"0",Q4Location,NumInState4,QHobby4,QProfession4);
                        UserInfoEdit(TheUserQ1,TheUserQ2,TheUserQ3,TheQ2,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId,UserHobby,UserHobby2,UserHobby3,UserProfession
                                ,UserReportsTime, OtherUserRoomCode, OtherUserRoomsJoined, OtherUserLikes);
                        Location = Q4Location;
                        NumInState=String.valueOf(NumInState4);
                        TheQOfLocationCard=String.valueOf(Q4);
                        TheQOfLocationCardHobby=String.valueOf(QHobby4);
                        TheQOfLocationCardProfession=String.valueOf(QProfession4);
                    }
                    if(QuestionNumView==4){
                        TheQOfUser TheQ2 = new TheQOfUser(Q5,"0",Q5Location,NumInState5,QHobby5,QProfession5);
                        UserInfoEdit(TheUserQ1,TheUserQ2,TheUserQ3,TheUserQ4,TheQ2,StringCash,UserLocation,ReportSent,ReportGot,UserId,UserHobby,UserHobby2,UserHobby3,UserProfession
                                ,UserReportsTime, OtherUserRoomCode, OtherUserRoomsJoined, OtherUserLikes);
                        Location = Q5Location;
                        NumInState=String.valueOf(NumInState5);
                        TheQOfLocationCard=String.valueOf(Q5);
                        TheQOfLocationCardHobby=String.valueOf(QHobby5);
                        TheQOfLocationCardProfession=String.valueOf(QProfession5);
                    }
                    LocationCard CardUpdate= new LocationCard(TheQOfLocationCard,"0",UserId,"0",TheQOfLocationCardHobby,TheQOfLocationCardProfession);
                    if(!RoomsString.contains(Location)) {
                        myRef = database.getReference(Location).child(NumInState);
                        myRef.setValue(CardUpdate);
                    }else{
                        myRef = database.getReference("Rooms").child(Location).child(NumInState);
                        myRef.setValue(CardUpdate);
                    }

                    myRef = database.getReference("Users").child(UserId);
                    getValueOfUserControl(myRef);
                    if(StringCash!=null) {
                        int c = Integer.parseInt(StringCash);
                        int c2 = c - 1;
                        myRef = database.getReference("Users").child(UserId).child("cash");
                        myRef.setValue(String.valueOf(c2));
                        cashView.setText(String.valueOf(c2));
                    }
                    TheA.setText("-Not Ans Yet-");
                    reSendBtn.setEnabled(false);
                    ReportAns.setEnabled(false);
            }
        });

        ReportAns.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((UserId != null && UserIdOfAnswer != null) && (!UserId.isEmpty() && !UserIdOfAnswer.isEmpty()) && (!UserIdOfAnswer.equals(UserId))) {
                    myRef = database.getReference("Users").child(UserIdOfAnswer);
                    getValueOfUser(myRef);
                    myRef = database.getReference("Users").child(UserId);
                    getValueOfUserControl(myRef);
                    NotMovedOnPressAgain2=QuestionNumView;
                    if (CheckIfCanSendReport()&&((NotMovedOnPressAgain1==NotMovedOnPressAgain2)||(NotMovedOnPressAgain1==-5))) {
                        ///NotMovedOnPressAgain1 - Takes Card Num , NotMovedOnPressAgain2 - Take Card Num at First Report Click == Makes Sure User Wont Move While Press Again Is on
                        if (CheckForEqualUsers()) {
                            int a1 = Integer.parseInt(ReportGot) + 1;
                            ReportGot = String.valueOf(a1);
                            if (ReportGot.equals("7")) { /// If get 7 Reports - Gets Ban --
                                ReportGot = "0";
                                String numOfBans = UserReportsTime.getBansGot();
                                Date banTillDate = Calendar.getInstance().getTime();
                                String banTillDate2 = MakeBanDate(banTillDate, numOfBans); /// By numOfBans Know for how long the next Ban
                                UserReportsTime.setBanTill(banTillDate2);
                                int sumOfBans = Integer.valueOf(numOfBans);
                                sumOfBans++;
                                UserReportsTime.setBansGot(String.valueOf(sumOfBans));

                            }
                            UserInfoEdit(TheUserQ1, TheUserQ2, TheUserQ3, TheUserQ4, TheUserQ5, StringCash, UserLocation, ReportSent, ReportGot, UserIdOfAnswer, UserHobby, UserHobby2,UserHobby3, UserProfession
                                    , UserReportsTime, OtherUserRoomCode, OtherUserRoomsJoined, OtherUserLikes);
                            int a2 = Integer.parseInt(ReportSentOfController) + 1;
                            ReportSentOfController = String.valueOf(a2);
                            Date newLastReportTime = Calendar.getInstance().getTime();
                            String newLastReportTime2 = MakeDateString(newLastReportTime);
                            ControllerReportsTime.setLastReportSend(newLastReportTime2);
                            int sumOfReportsSendToday = Integer.valueOf(ControllerReportsTime.getSumReportSendToday());
                            sumOfReportsSendToday++;
                            ControllerReportsTime.setSumReportSendToday(String.valueOf(sumOfReportsSendToday));
                            UserInfoEdit(TheUserQ1Controller, TheUserQ2Controller, TheUserQ3Controller, TheUserQ4Controller, TheUserQ5Controller,
                                    StringCashOfController, UserLocationOfController, ReportSentOfController, ReportGotOfController,
                                    UserId, UserControllerHobby, UserControllerHobby2,UserControllerHobby3, UserControllerProfession, ControllerReportsTime, UserControllingRoomCode, UserControllingRoomsJoined,ControllingUserLikes);
                        } else {
                            if (UserId.equals(UserIdOfAnswer)) {
                                Toast.makeText(MyInfo.this, "You Can't Report your own Ans", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MyInfo.this, "Press Again to Send Report", Toast.LENGTH_SHORT).show();
                                NotMovedOnPressAgain1=QuestionNumView;
                                myRef = database.getReference("Users").child(UserIdOfAnswer);
                                getValueOfUser(myRef);
                                myRef = database.getReference("Users").child(UserId);
                                getValueOfUserControl(myRef);
                            }
                        }
                    }

                }
            }
        });

        GoEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivityToEditInfo();
            }
        });

        UserLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!OtherUserLikedByWhom.contains(UserId)) {
                    OtherUserLikeCount = String.valueOf(Integer.valueOf(OtherUserLikeCount) + 1);
                    OtherUserLikedByWhom = OtherUserLikedByWhom + UserId;
                    UserLikes UserLikes = new UserLikes(OtherUserLikeCount,OtherUserLikedByWhom);
                    myRef = database.getReference("Users").child(UserIdOfAnswer).child("userLikes");
                    myRef.setValue(UserLikes);
                    Toast.makeText(MyInfo.this, "User Have Been Liked", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MyInfo.this, "You Already Liked That Account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getValueOfUser(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                if(userInfo!=null) {
                    TheUserQ1 = userInfo.getQnum1();
                    TheUserQ2 = userInfo.getQnum2();
                    TheUserQ3 = userInfo.getQnum3();
                    TheUserQ4 = userInfo.getQnum4();
                    TheUserQ5 = userInfo.getQnum5();
                    StringCash = userInfo.getCash();
                    Q1 = String.valueOf(TheUserQ1.getUserQ());
                    A1 = TheUserQ1.getUserA();
                    Q2 = TheUserQ2.getUserQ();
                    A2 = TheUserQ2.getUserA();
                    Q3 = TheUserQ3.getUserQ();
                    A3 = TheUserQ3.getUserA();
                    Q4 = TheUserQ4.getUserQ();
                    A4 = TheUserQ4.getUserA();
                    Q5 = TheUserQ5.getUserQ();
                    A5 = TheUserQ5.getUserA();
                    UserLocation = userInfo.getUserLocation();
                    ReportGot = userInfo.getReportGot();
                    ReportSent = userInfo.getReportSent();
                    UserHobby = userInfo.getHobby();
                    UserHobby2 = userInfo.getHobby2();
                    UserHobby3 = userInfo.getHobby3();
                    UserProfession = userInfo.getProfession();
                    UserReportsTime = userInfo.getReportsTime();
                    OtherUserRoomCode = userInfo.getRoomCode();
                    OtherUserRoomsJoined = userInfo.getUsersRooms();
                    Location1 = TheUserQ1.getqLocation();
                    Location2 = TheUserQ2.getqLocation();
                    Location3 = TheUserQ3.getqLocation();
                    Location4 = TheUserQ4.getqLocation();
                    Location5 = TheUserQ5.getqLocation();
                    OtherUserLikes = userInfo.getUserLikes();
                    OtherUserLikedByWhom = OtherUserLikes.getLikedByWhom();
                    OtherUserLikeCount = OtherUserLikes.getLikesGot();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        DatabaseGetValue.addValueEventListener(postListener);
    }
    public void getValueOfUserControl(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                TheUserQ1Controller = userInfo.getQnum1();TheUserQ2Controller = userInfo.getQnum2();
                TheUserQ3Controller = userInfo.getQnum3();TheUserQ4Controller = userInfo.getQnum4();
                TheUserQ5Controller = userInfo.getQnum5();
                StringCashOfController=userInfo.getCash();
                UserLocationOfController = userInfo.getUserLocation();
                ReportGotOfController=userInfo.getReportGot();
                ReportSentOfController=userInfo.getReportSent();
                NumInState1=TheUserQ1Controller.getNumInState();
                NumInState2=TheUserQ2Controller.getNumInState();
                NumInState3=TheUserQ3Controller.getNumInState();
                NumInState4=TheUserQ4Controller.getNumInState();
                NumInState5=TheUserQ5Controller.getNumInState();
                NumInState[0] = NumInState1;
                NumInState[1] = NumInState2;
                NumInState[2] = NumInState3;
                NumInState[3] = NumInState4;
                NumInState[4] = NumInState5;
                Q1Location=TheUserQ1.getqLocation();
                Q2Location=TheUserQ2.getqLocation();
                Q3Location=TheUserQ3.getqLocation();
                Q4Location=TheUserQ4.getqLocation();
                Q5Location=TheUserQ5.getqLocation();
                LocationsOfQUser[0] = Q1Location;
                LocationsOfQUser[1] = Q2Location;
                LocationsOfQUser[2] = Q3Location;
                LocationsOfQUser[3] = Q4Location;
                LocationsOfQUser[4] = Q5Location;
                UserQuestions[0] = TheUserQ1Controller.getUserQ();UserAnswers[0] = TheUserQ1Controller.getUserA();
                UserQuestions[1] = TheUserQ2Controller.getUserQ();UserAnswers[1] = TheUserQ2Controller.getUserA();;
                UserQuestions[2] = TheUserQ3Controller.getUserQ();UserAnswers[2] = TheUserQ3Controller.getUserA();;
                UserQuestions[3] = TheUserQ4Controller.getUserQ();UserAnswers[3] = TheUserQ4Controller.getUserA();;
                UserQuestions[4] = TheUserQ5Controller.getUserQ();UserAnswers[4] = TheUserQ5Controller.getUserA();;
                UserControllerHobby=userInfo.getHobby();
                UserControllerHobby2=userInfo.getHobby2();
                UserControllerHobby3=userInfo.getHobby3();
                UserControllerProfession=userInfo.getProfession();
                QHobby1=TheUserQ1.getqHobby();QProfession1=TheUserQ1.getqProfession();
                QHobby2=TheUserQ2.getqHobby();QProfession2=TheUserQ2.getqProfession();
                QHobby3=TheUserQ3.getqHobby();QProfession3=TheUserQ3.getqProfession();
                QHobby4=TheUserQ4.getqHobby();QProfession4=TheUserQ4.getqProfession();
                QHobby5=TheUserQ5.getqHobby();QProfession5=TheUserQ5.getqProfession();
                ControllerReportsTime=userInfo.getReportsTime();
                boolean CheckIfNotBan = CheckIfCanLogIn(); //Checks If You get Banned During the use of the app
                if(!CheckIfNotBan){
                    changeActivityIfBanned();
                }
                UserControllingRoomCode=userInfo.getRoomCode();
                UserControllingRoomsJoined=userInfo.getUsersRooms();
                ControllingUserLikes = userInfo.getUserLikes();
                ControllingUserLikedByWhom = ControllingUserLikes.getLikedByWhom();
                ControllingUserLikeCount = ControllingUserLikes.getLikesGot();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        DatabaseGetValue.addValueEventListener(postListener);
    }


    public void getValueOfCardLocation(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LocationCard Card = dataSnapshot.getValue(LocationCard.class);
                if(Card==null) {
                    getValueOfCardLocation(DatabaseGetValue);

                }
                else{
                    TheQOfLocationCard = Card.getTheQ();
                    TheAOfLocationCard = Card.getTheA();
                    UserIdOfSender = Card.getUserId();
                    UserIdOfAnswer = Card.getAnsBy();
                    TheQOfLocationCardHobby=Card.getTheHobby();
                    TheQOfLocationCardProfession=Card.getTheProfession();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        DatabaseGetValue.addValueEventListener(postListener);
    }

    public void getValueOfState(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String StateCounterNum = dataSnapshot.getValue(String.class);
                if (StateCounterNum==null||StateCounterNum.equals("0")){
                    getValueOfState(DatabaseGetValue);
                }
                else{
                    ChildrenCount = Integer.parseInt(StateCounterNum);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        DatabaseGetValue.addValueEventListener(postListener);
    }

    public void UserInfoEdit(TheQOfUser TheQ1,TheQOfUser TheQ2,TheQOfUser TheQ3,TheQOfUser TheQ4,TheQOfUser TheQ5,String Cash,String location, String reportSent,
                             String reportGot,String UserIdEdit, String Hobby,String Hobby2,String Hobby3,String Profession,ReportsTime reportsTime, String roomCode, String usersRooms,UserLikes userLikes){
        UserInfo userInfo = new UserInfo(TheQ1,TheQ2,TheQ3,TheQ4,TheQ5,Cash,location,reportSent,reportGot,Hobby,Hobby2,Hobby3,Profession,reportsTime, roomCode, usersRooms,userLikes);
        myRef = database.getReference("Users").child(UserIdEdit);
        myRef.setValue(userInfo);
    }

    public void changeActivityToEditInfo() {
        Intent intent = new Intent(this, EditUserInfo.class);
        startActivity(intent);
    }

    public boolean CheckForEqualUsers(){
        myRef = database.getReference("Users").child(UserIdOfAnswer);
        getValueOfUser(myRef);
        myRef = database.getReference("Users").child(UserId);
        getValueOfUserControl(myRef);
        boolean a = TheUserQ1.getUserQ().equals(TheUserQ1Controller.getUserQ())&&TheUserQ2.getUserQ().equals(TheUserQ2Controller.getUserQ())&&TheUserQ3.getUserQ().equals(TheUserQ3Controller.getUserQ())
                &&TheUserQ4.getUserQ().equals(TheUserQ4Controller.getUserQ())&&TheUserQ5.getUserQ().equals(TheUserQ5Controller.getUserQ());
        return !a;
    }

    public boolean CheckIfCanSendReport(){
        Date currentTime = Calendar.getInstance().getTime();
        int currentYear = currentTime.getYear();// +1900
        int currentMonth = currentTime.getMonth();// Jan==0
        int currentDay = currentTime.getDate();
        int currentMin = currentTime.getMinutes();
        int currentHours = currentTime.getHours();
        String TimeString = ControllerReportsTime.getLastReportSend(); /// String = D00,M00,Y000,Min00,H00,E  -----Jan==0, Year = Year+1900, T=Min
        if(!TimeString.equals("0")) {
            int lastReportYear = Integer.valueOf(TimeString.substring(TimeString.indexOf("Y") + 1, TimeString.indexOf(",Min")));
            int lastReportMonth = Integer.valueOf(TimeString.substring(TimeString.indexOf("M") + 1, TimeString.indexOf(",Y")));
            int lastReportDay = Integer.valueOf(TimeString.substring(TimeString.indexOf("D") + 1, TimeString.indexOf(",M")));
            int lastReportMin = Integer.valueOf(TimeString.substring(TimeString.indexOf("n") + 1, TimeString.indexOf(",H")));
            int lastReportHours = Integer.valueOf(TimeString.substring(TimeString.indexOf("H") + 1, TimeString.indexOf(",E")));
            int sumOfReportsToday = Integer.valueOf(ControllerReportsTime.getSumReportSendToday());
            if ((currentYear > lastReportYear) || (currentMonth > lastReportMonth) || (currentDay > lastReportDay)) { /// Checks if it has been a day since last Report
                sumOfReportsToday = 0;
                ControllerReportsTime.setSumReportSendToday("0");
            }
            boolean c = ((currentYear > lastReportYear) || (currentMonth > lastReportMonth) || (currentDay > lastReportDay) || (currentHours > lastReportHours) || (currentMin >= (lastReportMin + 3))) &&
                    (sumOfReportsToday < 5);// Check Also if sum of reports today is <5

            if (!(sumOfReportsToday<5)){
                Toast.makeText(MyInfo.this, "You Send Max Reports For Today" , Toast.LENGTH_SHORT).show();
            }
            else {
                if (!c && (currentMin < (lastReportMin + 3))) {
                    Toast.makeText(MyInfo.this, "Not enough time has passed since last Report", Toast.LENGTH_SHORT).show();
                }
            }

            return (c);
        }
        else{
            return true;
        }
    }

    public String MakeDateString(Date curDate){
        String Day =String.valueOf(curDate.getDate());
        String Hour =String.valueOf(curDate.getHours());
        String Month =String.valueOf(curDate.getMonth());
        String Year =String.valueOf(curDate.getYear());
        String Min = String.valueOf(curDate.getMinutes());
        String time = "D"+Day+",M"+Month+",Y"+Year+",Min"+Min+",H"+Hour+",E";
        return time;

    }
    public String MakeBanDate(Date curDate,String num){
        String time="Error";
        if(num.equals("0")){
            String Day =String.valueOf(curDate.getDate()+1); // First Ban for 1 Day
            String Hour =String.valueOf(curDate.getHours());
            String Month =String.valueOf(curDate.getMonth());
            String Year =String.valueOf(curDate.getYear());
            String Min = String.valueOf(curDate.getMinutes());
            time = "D"+Day+",M"+Month+",Y"+Year+",Min"+Min+",H"+Hour+",E";
        }
        if(num.equals("1")){
            String Day =String.valueOf(curDate.getDate()+7); // Second Ban for 1 Week
            String Hour =String.valueOf(curDate.getHours());
            String Month =String.valueOf(curDate.getMonth());
            String Year =String.valueOf(curDate.getYear());
            String Min = String.valueOf(curDate.getMinutes());
            time = "D"+Day+",M"+Month+",Y"+Year+",Min"+Min+",H"+Hour+",E";
        }if(num.equals("2")){
            String Day =String.valueOf(curDate.getDate());
            String Hour =String.valueOf(curDate.getHours());
            String Month =String.valueOf(curDate.getMonth()+1); // Third Ban for 1 month
            String Year =String.valueOf(curDate.getYear());
            String Min = String.valueOf(curDate.getMinutes());
            time = "D"+Day+",M"+Month+",Y"+Year+",Min"+Min+",H"+Hour+",E";
        }
        if(num.equals("3")){
            time = "Forever"; // Fourth Ban ForEver

        }

        return time;

    }

    public boolean CheckIfCanLogIn(){
        Date currentTime = Calendar.getInstance().getTime();
        int currentYear = currentTime.getYear();// +1900
        int currentMonth = currentTime.getMonth();// Jan==0
        int currentDay = currentTime.getDate();
        String TimeString = ControllerReportsTime.getBanTill(); /// String = D00,M00,Y000,Min00,H00,E  -----Jan==0, Year = Year+1900, T=Min
        if(TimeString.equals("0")){
            return true;
        }
        else {
            if(TimeString.equals("Error")||TimeString.equals("Forever")){
                Toast.makeText(MyInfo.this, "You have been banned", Toast.LENGTH_SHORT).show();
                return false;
            }
            int BanYear = Integer.valueOf(TimeString.substring(TimeString.indexOf("Y") + 1, TimeString.indexOf(",Min")));
            int BanMonth = Integer.valueOf(TimeString.substring(TimeString.indexOf("M") + 1, TimeString.indexOf(",Y")));
            int BanDay = Integer.valueOf(TimeString.substring(TimeString.indexOf("D") + 1, TimeString.indexOf(",M")));
            boolean c = (currentYear >= BanYear) && (currentMonth >= BanMonth) && (currentDay >= BanDay);
            if (!c){
                BanYear=BanYear+1900;
                BanMonth=BanMonth+1;
                Toast.makeText(MyInfo.this, "Banned till - "+BanDay+"."+BanMonth+"." +BanYear , Toast.LENGTH_SHORT).show();
            }
            return (c);
        }
    }

    public void changeActivityIfBanned() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getValueOfRoomsCodeString(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String RoomsLongString = dataSnapshot.getValue(String.class);
                if (RoomsLongString==null){
                    getValueOfRoomsCodeString(DatabaseGetValue);
                }
                else{
                    RoomsString = (RoomsLongString);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        DatabaseGetValue.addValueEventListener(postListener);
    }



}