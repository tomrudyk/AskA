package com.example.aska;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import java.util.Random;

public class AnswerQ extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private static final String TAG = "AnswerQ";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String UserId = mAuth.getCurrentUser().getUid();
    private String UserLocation = "";
    private String UserControlingCash = "";private String UserControlingLocation = "";

    private int QuestionNumView = -1;
    private int ChildrenCount=0;
    private String TheQOfLocationCard="";private String TheAOfLocationCard="";private String UserIdOfSender="";private String UserIdOfAnswer="";
    private String TheQOfLocationCard2="";private String TheAOfLocationCard2="";private String UserIdOfSender2="";private String UserIdOfAnswer2="";
    private String StringCash;
    private int QNum; private int QNum2;

    String LocationsOfQUser[] = new String[5];
    private String Q1="";private String Q2="";private String Q3="";
    private String Q4="";private String Q5="";
    private String A1="";private String A2="";private String A3="";
    private String A4="";private String A5="";

    private boolean FirstTime=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_q);

        Random rd = new Random();

        Button nextBtn = findViewById(R.id.nextBtn2);
        Button SendA = findViewById(R.id.SendABtn);
        TextView TheQuesion = findViewById(R.id.TheQTxt);
        TextView Location = findViewById(R.id.QLocationTxt);
        TextView cashView = findViewById(R.id.cashView2);
        EditText AnswerQEditTxt = findViewById(R.id.AnswerQ);

        myRef = database.getReference("Users").child(UserId);
        getValueOfUser(myRef);
        getValueOfUserControling(myRef);


        AnswerQEditTxt.setMovementMethod(new ScrollingMovementMethod());
        SendA.setEnabled(false);



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendA.setEnabled(true);
                RandomQ();
            }
        });

        SendA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Ans = AnswerQEditTxt.getText().toString();
                if (!Ans.isEmpty()) {
                    if (EmptyFromForbiddenLanguage(Ans.toLowerCase())) {
                        if (Ans.equals("0")) {
                            Ans = "Zero";
                        }
                        myRef = database.getReference(UserLocation).child(String.valueOf(QNum2));
                        LocationCard QCard = new LocationCard(TheQOfLocationCard2, Ans, UserIdOfSender2,UserId);
                        myRef.setValue(QCard);
                        myRef = database.getReference("Users").child(UserId);
                        getValueOfUserControling(myRef);
                        int c = Integer.parseInt(UserControlingCash);
                        int c2 = c + 2;
                        myRef = database.getReference("Users").child(UserId).child("cash");
                        myRef.setValue(String.valueOf(c2));
                        cashView.setText(String.valueOf(c2));

                        for (int i = 0; i < 5; i++) {
                            myRef = database.getReference("Users").child(String.valueOf(UserIdOfSender2)); ///UserQuestionId2 Is OK but still cant get into his info
                            getValueOfUser(myRef);


                            TheQOfUser TheQUserUpdate = new TheQOfUser(TheQOfLocationCard2, Ans, Location.getText().toString(), String.valueOf(QNum2));
                            if (i == 0) {
                                if (LocationsOfQUser[i].equals(Location.getText().toString()) && Q1.equals(TheQOfLocationCard2)) {
                                    myRef = database.getReference("Users").child(UserIdOfSender2).child("qnum" + String.valueOf(i + 1));
                                    myRef.setValue(TheQUserUpdate);
                                }

                            }
                            if (i == 1) {
                                if (LocationsOfQUser[i].equals(Location.getText().toString()) && Q2.equals(TheQOfLocationCard2)) {
                                    myRef = database.getReference("Users").child(UserIdOfSender2).child("qnum" + String.valueOf(i + 1));
                                    myRef.setValue(TheQUserUpdate);
                                }
                            }
                            if (i == 2) {
                                if (LocationsOfQUser[i].equals(Location.getText().toString()) && Q3.equals(TheQOfLocationCard2)) {
                                    myRef = database.getReference("Users").child(UserIdOfSender2).child("qnum" + String.valueOf(i + 1));
                                    myRef.setValue(TheQUserUpdate);
                                }
                            }
                            if (i == 3) {
                                if (LocationsOfQUser[i].equals(Location.getText().toString()) && Q4.equals(TheQOfLocationCard2)) {
                                    myRef = database.getReference("Users").child(UserIdOfSender2).child("qnum" + String.valueOf(i + 1));
                                    myRef.setValue(TheQUserUpdate);
                                }
                            }
                            if (i == 4) {
                                if (LocationsOfQUser[i].equals(Location.getText().toString()) && Q5.equals(TheQOfLocationCard2)) {
                                    myRef = database.getReference("Users").child(UserIdOfSender2).child("qnum" + String.valueOf(i + 1));
                                    myRef.setValue(TheQUserUpdate);
                                }
                            }
                        }
                    }

                } else {
                    Toast.makeText(AnswerQ.this, "Ans Is Empty", Toast.LENGTH_SHORT).show();
                }
                AnswerQEditTxt.setText("");
                nextBtn.performClick();

            }

        });

        AnswerQEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                myRef = database.getReference("Users").child(String.valueOf(UserIdOfSender2)); ///UserQuestionId2 Is OK but still cant get into his info
                getValueOfUser(myRef);
                myRef = database.getReference("Users").child(UserId);
                getValueOfUserControling(myRef);
            }
        });

    }



    public void getValueOfUser(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                StringCash = userInfo.getCash();
                UserLocation = userInfo.getUserLocation();
                Q1 = userInfo.getQnum1().getUserQ();A1 = userInfo.getQnum1().getUserA();
                Q2 = userInfo.getQnum2().getUserQ();A2 = userInfo.getQnum2().getUserA();
                Q3 = userInfo.getQnum3().getUserQ();A3 = userInfo.getQnum3().getUserA();
                Q4 = userInfo.getQnum4().getUserQ();A4 = userInfo.getQnum4().getUserA();
                Q5 = userInfo.getQnum5().getUserQ();A5 = userInfo.getQnum5().getUserA();
                LocationsOfQUser[0] = userInfo.getQnum1().getqLocation();
                LocationsOfQUser[1] = userInfo.getQnum2().getqLocation();
                LocationsOfQUser[2] = userInfo.getQnum3().getqLocation();
                LocationsOfQUser[3] = userInfo.getQnum4().getqLocation();
                LocationsOfQUser[4] = userInfo.getQnum5().getqLocation();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        DatabaseGetValue.addValueEventListener(postListener);
    }
    public void getValueOfUserControling(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                UserControlingCash = userInfo.getCash();
                UserControlingLocation = userInfo.getUserLocation();
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
                    UserIdOfSender = Card.getUserId();
                    UserIdOfAnswer = Card.getAnsBy();
                    if(UserIdOfSender==null||UserIdOfSender.isEmpty()){
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

    public void getValueOfState(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String StateCounterNum = dataSnapshot.getValue(String.class);
                if (StateCounterNum==null){
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

    public void RandomQ(){
        Button SendA = findViewById(R.id.SendABtn);
        TextView TheQ = findViewById(R.id.TheQTxt);
        TextView Location = findViewById(R.id.QLocationTxt);
        TextView cashView = findViewById(R.id.cashView2);
        myRef = database.getReference("Users").child(UserId);
        getValueOfUser(myRef);
        Random rd = new Random();
        myRef = database.getReference(UserLocation+UserLocation);
        getValueOfState(myRef);
        if(ChildrenCount==0){
            Toast.makeText(AnswerQ.this, "Error", Toast.LENGTH_SHORT).show();
            SendA.setEnabled(false);

        }else {
            QNum2=QNum;
            TheAOfLocationCard2=TheAOfLocationCard;
            TheQOfLocationCard2=TheQOfLocationCard;
            UserIdOfSender2=UserIdOfSender;
            UserIdOfAnswer2=UserIdOfAnswer;
            QNum= rd.nextInt(ChildrenCount)+1;
        }
        myRef = database.getReference(UserLocation).child(String.valueOf(QNum));
        getValueOfCardLocation(myRef);
        if(TheAOfLocationCard2.equals("0")){

            TheQ.setText(TheQOfLocationCard2);
            Location.setText(UserLocation);
            cashView.setText(UserControlingCash);
            myRef = database.getReference(UserLocation).child(String.valueOf(QNum));
            getValueOfCardLocation(myRef);
            TheQ.setText(TheQOfLocationCard2);
            Location.setText(UserLocation);
            cashView.setText(UserControlingCash);
        }
        else{
            TheQ.setText("No Question Found");
            Location.setText("");
            cashView.setText(StringCash);
            SendA.setEnabled(false);
        }

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

}