package com.example.aska;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;
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
    String LocationsOfQUser[] = new String[5];
    private int QuestionNumView=-1;
    private String Q1Location="";private String Q2Location="";private String Q3Location="";
    private String Q4Location="";private String Q5Location="";
    public int ChildrenCount=0 ;
    private String TheQOfLocationCard="";private String TheAOfLocationCard="";private String UserIdOfSender="";private String UserIdOfAnswer="";

    private boolean MaxReport=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        Button nextBtn = findViewById(R.id.nextBtn);
        Button backBtn = findViewById(R.id.backBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);
        Button reSendBtn = findViewById(R.id.reSendBtn);
        Button ReportAns = findViewById(R.id.ReportAns);
        TextView TheQ = findViewById(R.id.UserQ);
        TextView TheA = findViewById(R.id.UserA);
        TextView cashView = findViewById(R.id.cashView);
        TextView LocationOfQTxt = findViewById(R.id.LocationOfQtxt);
        TextView Qnumber = findViewById(R.id.Qnumber);

        deleteBtn.setEnabled(false);
        reSendBtn.setEnabled(false);
        ReportAns.setEnabled(false);
        Qnumber.setText(String.valueOf(QuestionNumView));
        TheA.setMovementMethod(new ScrollingMovementMethod());
        TheQ.setMovementMethod(new ScrollingMovementMethod());

        TheQ.setText("");TheA.setText("");cashView.setText("");LocationOfQTxt.setText("");
        Qnumber.setText(String.valueOf(QuestionNumView));

        myRef = database.getReference("Users").child(UserId);
        getValueOfUser(myRef);
        getValueOfUserControl(myRef);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashView.setText(StringCashOfController);
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
                        }
                        else {
                            TheA.setText(UserAnswers[i+1]);
                            reSendBtn.setEnabled(true);
                            ReportAns.setEnabled(true);
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
                        }
                        else {
                            TheA.setText(UserAnswers[i-1]);
                            reSendBtn.setEnabled(true);
                            ReportAns.setEnabled(true);
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
                TheQOfUser TheQ2 = new TheQOfUser("0","0","0","0");
                if(QuestionNumView==0){
                    UserInfoEdit(TheQ2,TheUserQ2,TheUserQ3,TheUserQ4,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId);
                    Location = Q1Location;
                    NumInState=NumInState1;
                }
                if(QuestionNumView==1){
                    UserInfoEdit(TheUserQ1,TheQ2,TheUserQ3,TheUserQ4,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId);
                    Location = Q2Location;
                    NumInState=NumInState2;
                }
                if(QuestionNumView==2){
                    UserInfoEdit(TheUserQ1,TheUserQ2,TheQ2,TheUserQ4,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId);
                    Location = Q3Location;
                    NumInState=NumInState3;
                }
                if(QuestionNumView==3){
                    UserInfoEdit(TheUserQ1,TheUserQ2,TheUserQ3,TheQ2,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId);
                    Location = Q4Location;
                    NumInState=NumInState4;
                }
                if(QuestionNumView==4){
                    UserInfoEdit(TheUserQ1,TheUserQ2,TheUserQ3,TheUserQ4,TheQ2,StringCash,UserLocation,ReportSent,ReportGot,UserId);
                    Location = Q5Location;
                    NumInState=NumInState5;
                }
                myRef = database.getReference(Location+Location);
                getValueOfState(myRef);
                myRef = database.getReference(Location).child(String.valueOf(ChildrenCount));
                getValueOfCardLocation(myRef);
                LocationCard LocationQCardLAST= new LocationCard(TheQOfLocationCard,TheAOfLocationCard,UserIdOfSender,UserIdOfAnswer);

                myRef = database.getReference(Location).child(NumInState);
                myRef.setValue(LocationQCardLAST);
                myRef = database.getReference(Location).child(String.valueOf(ChildrenCount));
                LocationCard TheQ2OfLocationCard = new LocationCard("0","0","0","0");
                myRef.setValue(TheQ2OfLocationCard);
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
                    String Location="";
                    String NumInState="";
                    if(QuestionNumView==0){
                        TheQOfUser TheQ2 = new TheQOfUser(Q1,"0",Q1Location,NumInState1);
                        UserInfoEdit(TheQ2,TheUserQ2,TheUserQ3,TheUserQ4,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId);
                        Location = Q1Location;
                        NumInState=String.valueOf(NumInState1);
                        TheQOfLocationCard=String.valueOf(Q1);
                    }
                    if(QuestionNumView==1){
                        TheQOfUser TheQ2 = new TheQOfUser(Q2,"0",Q2Location,NumInState2);
                        UserInfoEdit(TheUserQ1,TheQ2,TheUserQ3,TheUserQ4,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId);
                        Location = Q2Location;
                        NumInState=String.valueOf(NumInState2);
                        TheQOfLocationCard=String.valueOf(Q2);
                    }
                    if(QuestionNumView==2){
                        TheQOfUser TheQ2 = new TheQOfUser(Q3,"0",Q3Location,NumInState3);
                        UserInfoEdit(TheUserQ1,TheUserQ2,TheQ2,TheUserQ4,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId);
                        Location = Q3Location;
                        NumInState=String.valueOf(NumInState3);
                        TheQOfLocationCard=String.valueOf(Q3);
                    }
                    if(QuestionNumView==3){
                        TheQOfUser TheQ2 = new TheQOfUser(Q4,"0",Q4Location,NumInState4);
                        UserInfoEdit(TheUserQ1,TheUserQ2,TheUserQ3,TheQ2,TheUserQ5,StringCash,UserLocation,ReportSent,ReportGot,UserId);
                        Location = Q4Location;
                        NumInState=String.valueOf(NumInState4);
                        TheQOfLocationCard=String.valueOf(Q4);
                    }
                    if(QuestionNumView==4){
                        TheQOfUser TheQ2 = new TheQOfUser(Q5,"0",Q5Location,NumInState5);
                        UserInfoEdit(TheUserQ1,TheUserQ2,TheUserQ3,TheUserQ4,TheQ2,StringCash,UserLocation,ReportSent,ReportGot,UserId);
                        Location = Q5Location;
                        NumInState=String.valueOf(NumInState5);
                        TheQOfLocationCard=String.valueOf(Q5);
                    }
                    LocationCard CardUpdate= new LocationCard(TheQOfLocationCard,"0",UserId,"0");
                    myRef = database.getReference(Location).child(NumInState);
                    myRef.setValue(CardUpdate);

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
            }
        });

        ReportAns.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((UserId != null && UserIdOfAnswer != null)&&(!UserId.isEmpty()&&!UserIdOfAnswer.isEmpty())&&(!UserIdOfAnswer.equals(UserId))) {
                    myRef = database.getReference("Users").child(UserIdOfAnswer);
                    getValueOfUser(myRef);
                    myRef = database.getReference("Users").child(UserId);
                    getValueOfUserControl(myRef);
                    if(Integer.parseInt(ReportSentOfController)>=5){
                        MaxReport=true;
                        Toast.makeText(MyInfo.this, "You Sent Max Reports For Today", Toast.LENGTH_SHORT).show();
                    }
                    if (CheckForEqualUsers()&&!MaxReport) {
                        int a1 = Integer.parseInt(ReportGot) + 1;
                        ReportGot = String.valueOf(a1);
                        UserInfoEdit(TheUserQ1, TheUserQ2, TheUserQ3, TheUserQ4, TheUserQ5, StringCash, UserLocation, ReportSent, ReportGot, UserIdOfAnswer);
                        int a2 = Integer.parseInt(ReportSentOfController) + 1;
                        ReportSentOfController = String.valueOf(a2);
                        UserInfoEdit(TheUserQ1Controller, TheUserQ2Controller, TheUserQ3Controller, TheUserQ4Controller, TheUserQ5Controller,
                                StringCashOfController, UserLocationOfController, ReportSentOfController, ReportGotOfController, UserId);
                    }
                    else{
                        if(!(Integer.parseInt(ReportSentOfController)>=5)) {
                            Toast.makeText(MyInfo.this, "Press Again to Send Report", Toast.LENGTH_SHORT).show();
                            myRef = database.getReference("Users").child(UserIdOfAnswer);
                            getValueOfUser(myRef);
                            myRef = database.getReference("Users").child(UserId);
                            getValueOfUserControl(myRef);
                        }
                    }
                }
                else{
                    if(UserId.equals(UserIdOfAnswer)){
                        Toast.makeText(MyInfo.this, "You Can't Report your own Ans", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void getValueOfUser(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                TheUserQ1 = userInfo.getQnum1();TheUserQ2 = userInfo.getQnum2();
                TheUserQ3 = userInfo.getQnum3();TheUserQ4 = userInfo.getQnum4();
                TheUserQ5 = userInfo.getQnum5();StringCash=userInfo.getCash();
                Q1 = TheUserQ1.getUserQ();A1 = TheUserQ1.getUserA();
                Q2 = TheUserQ2.getUserQ();A2 = TheUserQ2.getUserA();
                Q3 = TheUserQ3.getUserQ();A3 = TheUserQ3.getUserA();
                Q4 = TheUserQ4.getUserQ();A4 = TheUserQ4.getUserA();
                Q5 = TheUserQ5.getUserQ();A5 = TheUserQ5.getUserA();
                UserLocation = userInfo.getUserLocation();
                ReportGot=userInfo.getReportGot();
                ReportSent=userInfo.getReportSent();

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

    public void UserInfoEdit(TheQOfUser TheQ1,TheQOfUser TheQ2,TheQOfUser TheQ3,TheQOfUser TheQ4,TheQOfUser TheQ5,String Cash,String location, String reportSent, String reportGot,String UserIdEdit){
        UserInfo userInfo = new UserInfo(TheQ1,TheQ2,TheQ3,TheQ4,TheQ5,Cash,location,reportSent,reportGot);
        myRef = database.getReference("Users").child(UserIdEdit);
        myRef.setValue(userInfo);
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

}