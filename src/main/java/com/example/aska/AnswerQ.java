package com.example.aska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AnswerQ extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private static final String TAG = "AnswerQ";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String UserId = mAuth.getCurrentUser().getUid();

    private String UserLocation = "";private String UserCash = "";
    private String UserHobby = "";private String UserProfession = "";private String UserHobby2 = "";private String UserHobby3 = "";
    private int ChildrenCount = 0;private int ChildrenCountNullState = 0;private int ChildrenBoth = 0;
    private boolean IsStateNull;
    private String TheQOfLocationCard = "";private String TheAOfLocationCard = "";
    private String TheQOfLocationCardHobby = "";private String TheQOfLocationCardProfession = "";
    private String UserIdOfSenderLocationCard = "";private String UserIdOfAnswerLocationCard = "";
    private String QLocation = "";private String NumOfQInState;

    String LocationsOfQOtherUser[] = new String[5];
    private String Q1="";private String Q2="";private String Q3="";
    private String Q4="";private String Q5="";
    private String A1="";private String A2="";private String A3="";
    private String A4="";private String A5="";
    private String OtherUserCash = "";private String OtherUserLocation;
    private ReportsTime ControllerReportsTime;
    private String UserAllowWrite;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_q);


        Button nextBtn = findViewById(R.id.nextQBtnPrivateRoom);
        Button SendA = findViewById(R.id.SendABtnPrivateRoom);
        Button Reload = findViewById(R.id.ReloadAnswerQPrivateRoom);
        Reload.performClick();
        TextView TheQuestion = findViewById(R.id.TheQTxtPrivateRoom);
        TextView LocationView = findViewById(R.id.QLocationTxtPrivateRoom);
        TextView cashView = findViewById(R.id.cashViewPrivateRoom);
        EditText TheAnswerOfQ = findViewById(R.id.AnswerQPrivateRoom);
        SendA.setEnabled(false);
        TheQuestion.setText("Press Next Q");
        cashView.setText(UserCash);

        myRef = database.getReference("Users").child(UserId);
        getValueOfUser(myRef);

        DisAllowToWrite();


        Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TheQuestion.setText(TheQOfLocationCard);
                cashView.setText(UserCash);
                LocationView.setText("Location- "+ QLocation+"\n"+"Hobby- " + TheQOfLocationCardHobby+"\n"+"Profession- " + TheQOfLocationCardProfession);
                SendA.setEnabled(false);
                boolean CheckHobby= (TheQOfLocationCardHobby.equals(UserHobby))||(TheQOfLocationCardHobby.equals("None"))
                        ||(TheQOfLocationCardHobby.equals(UserHobby2)||(TheQOfLocationCardHobby.equals(UserHobby3)));
                boolean CheckProfession= (TheQOfLocationCardProfession.equals(UserProfession))||(TheQOfLocationCardProfession.equals("None"));
                if((TheAOfLocationCard.equals("0")&&(CheckHobby)&&(CheckProfession))){
                    TheQuestion.setText(TheQOfLocationCard);
                    LocationView.setText("Location- "+ QLocation+"\n"+"Hobby- " + TheQOfLocationCardHobby+"\n"+"Profession- " + TheQOfLocationCardProfession);
                    cashView.setText(UserCash);
                    SendA.setEnabled(true);
                    myRef = database.getReference("Users").child(UserIdOfSenderLocationCard);
                    getValueOfOtherUser(myRef);
                    // Add Profession and Hobby asked for the Q
                }
                else{
                    TheQuestion.setText("No Q Found -");
                    LocationView.setText("No Q Found -");
                }
                TheAnswerOfQ.clearFocus();
                TheAnswerOfQ.setText("");
                AllowSend();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = database.getReference("Users").child(UserId);
                getValueOfUser(myRef);
                myRef = database.getReference(UserLocation+UserLocation);
                getValueOfState(myRef);
                myRef = database.getReference("nullnull");
                getValueOfStateNull(myRef);
                SendA.setEnabled(true);
                RandomQ();
                TheQuestion.setText(TheQOfLocationCard);
                cashView.setText(UserCash);
                LocationView.setText("Location- "+ QLocation+"\n"+"Hobby- " + TheQOfLocationCardHobby+"\n"+"Profession- " + TheQOfLocationCardProfession);
                TheAnswerOfQ.clearFocus();
                TheAnswerOfQ.setText("");
                ReloadQInfo();
                DisAllowSend();

            }
        });

        SendA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowToWrite();
                String UserAns = TheAnswerOfQ.getText().toString();
                if(UserAns=="null"||!EmptyFromForbiddenLanguage(UserAns)||UserAns.isEmpty()){

                    Toast.makeText(AnswerQ.this, "Answer Is Empty", Toast.LENGTH_SHORT).show();

                }
                else{
                    if (UserAns.equals("0")) {
                        UserAns = "Zero";
                    }

                    myRef = database.getReference(QLocation).child(NumOfQInState);
                    LocationCard TheLocationCard = new LocationCard(TheQOfLocationCard, UserAns, UserIdOfSenderLocationCard , UserId, TheQOfLocationCardHobby, TheQOfLocationCardProfession);
                    myRef.setValue(TheLocationCard);

                    myRef = database.getReference("Users").child(UserIdOfSenderLocationCard);
                    getValueOfOtherUser(myRef);

                    int c = Integer.parseInt(UserCash);
                    int c2 = c + 1;
                    myRef = database.getReference("Users").child(UserId).child("cash");
                    myRef.setValue(String.valueOf(c2));
                    cashView.setText(String.valueOf(c2));

                    for(int i=5;i>=1;i--){

                        TheQOfUser TheQUserUpdate = new TheQOfUser(TheQOfLocationCard, UserAns, QLocation, NumOfQInState,TheQOfLocationCardHobby,TheQOfLocationCardProfession);

                        if (i == 5) {
                            if (LocationsOfQOtherUser[i-1].equals(QLocation) && Q5.equals(TheQOfLocationCard)) {
                                myRef = database.getReference("Users").child(UserIdOfSenderLocationCard).child("qnum" + String.valueOf(i));
                                myRef.setValue(TheQUserUpdate);
                            }
                        }
                        if (i == 4) {
                            if (LocationsOfQOtherUser[i-1].equals(QLocation) && Q4.equals(TheQOfLocationCard)) {
                                myRef = database.getReference("Users").child(UserIdOfSenderLocationCard).child("qnum" + String.valueOf(i));
                                myRef.setValue(TheQUserUpdate);
                            }
                        }
                        if (i == 3) {
                            if (LocationsOfQOtherUser[i-1].equals(QLocation) && Q3.equals(TheQOfLocationCard)) {
                                myRef = database.getReference("Users").child(UserIdOfSenderLocationCard).child("qnum" + String.valueOf(i));
                                myRef.setValue(TheQUserUpdate);
                            }
                        }
                        if (i == 2) {
                            if (LocationsOfQOtherUser[i-1].equals(QLocation) && Q2.equals(TheQOfLocationCard)) {
                                myRef = database.getReference("Users").child(UserIdOfSenderLocationCard).child("qnum" + String.valueOf(i));
                                myRef.setValue(TheQUserUpdate);
                            }
                        }
                        if (i == 1) {
                            if (LocationsOfQOtherUser[i-1].equals(QLocation) && Q1.equals(TheQOfLocationCard)) {
                                myRef = database.getReference("Users").child(UserIdOfSenderLocationCard).child("qnum" + String.valueOf(i));
                                myRef.setValue(TheQUserUpdate);
                            }
                        }

                    }

                }
                TheAnswerOfQ.clearFocus();
                TheAnswerOfQ.setText("");
                SendA.setEnabled(false);
                ReloadQInfo();
                DisAllowSend();
                DisAllowToWrite();
            }
        });

        TheAnswerOfQ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ReloadQInfo();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }




    public boolean EmptyFromForbiddenLanguage(String Ans){
        String[] ForbiddenLanguage = getResources().getStringArray(R.array.ForbiddenLanguage);
        for (int i=0;i<ForbiddenLanguage.length;i++){
            if(Ans.contains(ForbiddenLanguage[i])){
                Toast.makeText(AnswerQ.this, "You Are Using Forbidden Language", Toast.LENGTH_SHORT).show();
                return (false);
            }
        }
        return true;
    }

    public void getValueOfUser(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                UserCash = userInfo.getCash();
                UserLocation= userInfo.getUserLocation();
                UserHobby = userInfo.getHobby();
                UserHobby2=userInfo.getHobby2();
                UserHobby3=userInfo.getHobby3();
                UserProfession = userInfo.getProfession();
                ControllerReportsTime = userInfo.getReportsTime();
                boolean CheckIfNotBan = CheckIfCanLogIn(); //Checks If You get Banned During the use of the app
                if(!CheckIfNotBan){
                    changeActivityIfBanned();
                }
                UserAllowWrite=userInfo.getAllowWrite();

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
                if (StateCounterNum==null){
                   // getValueOfState(DatabaseGetValue);     Making the App lag hard
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

    public void getValueOfStateNull(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String StateCounterNum = dataSnapshot.getValue(String.class);
                if (StateCounterNum==null){
                  //  getValueOfStateNull(DatabaseGetValue);     Making the App lag hard
                }
                else{
                    ChildrenCountNullState = Integer.parseInt(StateCounterNum);
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

    public void getValueOfCardLocation(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LocationCard Card = dataSnapshot.getValue(LocationCard.class);
                if(Card!=null) {
                    TheQOfLocationCard = Card.getTheQ();
                    TheAOfLocationCard = Card.getTheA();
                    UserIdOfSenderLocationCard = Card.getUserId();
                    UserIdOfAnswerLocationCard = Card.getAnsBy();
                    TheQOfLocationCardHobby=Card.getTheHobby();
                    TheQOfLocationCardProfession=Card.getTheProfession();
                    if(UserIdOfSenderLocationCard==null||UserIdOfSenderLocationCard.isEmpty()){
                        getValueOfCardLocation(DatabaseGetValue);
                    }
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

    public void getValueOfOtherUser(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                OtherUserCash = userInfo.getCash();
                OtherUserLocation = userInfo.getUserLocation();
                Q1 = userInfo.getQnum1().getUserQ();A1 = userInfo.getQnum1().getUserA();
                Q2 = userInfo.getQnum2().getUserQ();A2 = userInfo.getQnum2().getUserA();
                Q3 = userInfo.getQnum3().getUserQ();A3 = userInfo.getQnum3().getUserA();
                Q4 = userInfo.getQnum4().getUserQ();A4 = userInfo.getQnum4().getUserA();
                Q5 = userInfo.getQnum5().getUserQ();A5 = userInfo.getQnum5().getUserA();
                LocationsOfQOtherUser[0] = userInfo.getQnum1().getqLocation();
                LocationsOfQOtherUser[1] = userInfo.getQnum2().getqLocation();
                LocationsOfQOtherUser[2] = userInfo.getQnum3().getqLocation();
                LocationsOfQOtherUser[3] = userInfo.getQnum4().getqLocation();
                LocationsOfQOtherUser[4] = userInfo.getQnum5().getqLocation();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        DatabaseGetValue.addValueEventListener(postListener);
    }

    public void RandomQ(){
        myRef = database.getReference("Users").child(UserId);
        getValueOfUser(myRef);
        myRef = database.getReference(UserLocation+UserLocation);
        getValueOfState(myRef);
        myRef = database.getReference("nullnull");
        getValueOfStateNull(myRef);
        TextView TheQuestion = findViewById(R.id.TheQTxtPrivateRoom);
        TextView LocationView = findViewById(R.id.QLocationTxtPrivateRoom);
        TextView cashView = findViewById(R.id.cashViewPrivateRoom);
        EditText TheAnswerOfQ = findViewById(R.id.AnswerQPrivateRoom);
        Button SendA = findViewById(R.id.SendABtnPrivateRoom);

        SendA.setEnabled(false);
        cashView.setText(UserCash);

        ChildrenBoth=ChildrenCount+ChildrenCountNullState;
        if (ChildrenBoth==0){
            ChildrenBoth=1;
        }
        Random rd = new Random();
        int RandomInt = rd.nextInt(ChildrenBoth)+1;
        if((RandomInt<=ChildrenCount)){
            IsStateNull=false;
            myRef = database.getReference(UserLocation).child(String.valueOf(RandomInt));
            getValueOfCardLocation(myRef);
            QLocation=UserLocation;
            NumOfQInState=String.valueOf(RandomInt);
        }
        else{
            IsStateNull=true;
            if(ChildrenCountNullState==1){
                myRef = database.getReference("null").child(String.valueOf(1));
                getValueOfCardLocation(myRef);
                QLocation = "null";
                NumOfQInState = String.valueOf(1);
            }else {
                myRef = database.getReference("null").child(String.valueOf(ChildrenBoth - RandomInt));
                getValueOfCardLocation(myRef);
                QLocation = "null";
                NumOfQInState = String.valueOf(ChildrenBoth - RandomInt);
            }
        }

        boolean CheckHobby= (TheQOfLocationCardHobby.equals(UserHobby))||(TheQOfLocationCardHobby.equals("None"))
                ||(TheQOfLocationCardHobby.equals(UserHobby2)||(TheQOfLocationCardHobby.equals(UserHobby3)));
        boolean CheckProfession= (TheQOfLocationCardProfession.equals(UserProfession))||(TheQOfLocationCardProfession.equals("None"));
        if((TheAOfLocationCard.equals("0")&&(CheckHobby)&&(CheckProfession))){
            TheQuestion.setText(TheQOfLocationCard);
            LocationView.setText("Location- "+ QLocation+"\n"+"Hobby- " + TheQOfLocationCardHobby+"\n"+"Profession- " + TheQOfLocationCardProfession);
            cashView.setText(UserCash);
            SendA.setEnabled(true);
            myRef = database.getReference("Users").child(UserIdOfSenderLocationCard);
            getValueOfOtherUser(myRef);
        }
        else{
            TheQuestion.setText("No Q Found -");
            LocationView.setText("No Q Found -");
        }
        Button Reload = findViewById(R.id.ReloadAnswerQPrivateRoom);
        ReloadQInfo();
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
                Toast.makeText(AnswerQ.this, "You have been banned", Toast.LENGTH_SHORT).show();
                return false;
            }
            int BanYear = Integer.valueOf(TimeString.substring(TimeString.indexOf("Y") + 1, TimeString.indexOf(",Min")));
            int BanMonth = Integer.valueOf(TimeString.substring(TimeString.indexOf("M") + 1, TimeString.indexOf(",Y")));
            int BanDay = Integer.valueOf(TimeString.substring(TimeString.indexOf("D") + 1, TimeString.indexOf(",M")));
            boolean c = (currentYear >= BanYear) && (currentMonth >= BanMonth) && (currentDay >= BanDay);
            if (!c){
                BanYear=BanYear+1900;
                BanMonth=BanMonth+1;
                Toast.makeText(AnswerQ.this, "Banned till - "+BanDay+"."+BanMonth+"." +BanYear , Toast.LENGTH_SHORT).show();
            }
            return (c);
        }
    }

    public void changeActivityIfBanned() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ReloadQInfo(){
        Button SendA = findViewById(R.id.SendABtnPrivateRoom);
        Button Reload = findViewById(R.id.ReloadAnswerQPrivateRoom);
        TextView TheQuestion = findViewById(R.id.TheQTxtPrivateRoom);
        TextView LocationView = findViewById(R.id.QLocationTxtPrivateRoom);
        TextView cashView = findViewById(R.id.cashViewPrivateRoom);
        TheQuestion.setText(TheQOfLocationCard);
        cashView.setText(UserCash);
        LocationView.setText("Location- "+ QLocation+"\n"+"Hobby- " + TheQOfLocationCardHobby+"\n"+"Profession- " + TheQOfLocationCardProfession);
        SendA.setEnabled(false);
        boolean CheckHobby= (TheQOfLocationCardHobby.equals(UserHobby))||(TheQOfLocationCardHobby.equals("None"))
                ||(TheQOfLocationCardHobby.equals(UserHobby2)||(TheQOfLocationCardHobby.equals(UserHobby3)));
        boolean CheckProfession= (TheQOfLocationCardProfession.equals(UserProfession))||(TheQOfLocationCardProfession.equals("None"));
        if((TheAOfLocationCard.equals("0")&&(CheckHobby)&&(CheckProfession))){
            TheQuestion.setText(TheQOfLocationCard);
            LocationView.setText("Location- "+ QLocation+"\n"+"Hobby- " + TheQOfLocationCardHobby+"\n"+"Profession- " + TheQOfLocationCardProfession);
            cashView.setText(UserCash);
            SendA.setEnabled(true);
            myRef = database.getReference("Users").child(UserIdOfSenderLocationCard);
            getValueOfOtherUser(myRef);
            // Add Profession and Hobby asked for the Q
        }
        else{
            TheQuestion.setText("No Q Found -");
            LocationView.setText("No Q Found -");
        }
        Reload.performClick();
    }

    public void AllowSend(){
        Button nextBtn = findViewById(R.id.nextQBtnPrivateRoom);
        Button SendA = findViewById(R.id.SendABtnPrivateRoom);
        Button Reload = findViewById(R.id.ReloadAnswerQPrivateRoom);
        nextBtn.setEnabled(true);
        SendA.setEnabled(true);
        Reload.setEnabled(false);
    }
    public void DisAllowSend(){
        Button nextBtn = findViewById(R.id.nextQBtnPrivateRoom);
        Button SendA = findViewById(R.id.SendABtnPrivateRoom);
        Button Reload = findViewById(R.id.ReloadAnswerQPrivateRoom);
        nextBtn.setEnabled(false);
        SendA.setEnabled(false);
        Reload.setEnabled(true);
    }

    public void AllowToWrite(){
        UserAllowWrite = "true";
        myRef = database.getReference("Users").child(UserId).child("allowWrite");
        myRef.setValue(UserAllowWrite);
    }
    public void DisAllowToWrite(){
        UserAllowWrite = "false";
        myRef = database.getReference("Users").child(UserId).child("allowWrite");
        myRef.setValue(UserAllowWrite);
    }

}



