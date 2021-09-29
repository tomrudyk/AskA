package com.example.aska;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaRouter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aska.AnswerQ;
import com.example.aska.LocationCard;
import com.example.aska.MyInfo;
import com.example.aska.R;
import com.example.aska.TheQOfUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList; // import the ArrayList class

public class SendQuestionPrivateRoom extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private static final String TAG = "MainActivity2";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ;
    private String UserId = mAuth.getCurrentUser().getUid();
    private int ChildrenCount;

    private TheQOfUser TheUserQ1 = new TheQOfUser();
    private TheQOfUser TheUserQ2 = new TheQOfUser();
    private TheQOfUser TheUserQ3 = new TheQOfUser();
    private TheQOfUser TheUserQ4 = new TheQOfUser();
    private TheQOfUser TheUserQ5 = new TheQOfUser();
    private String Q1 = "";
    private String Q2 = "";
    private String Q3 = "";
    private String Q4 = "";
    private String Q5 = "";
    private String StringCash;
    private String UserAllowWrite;



    private ReportsTime ControllerReportsTime;
    private String UsersRoomsJoined;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_question_private_room);

        mAuth = FirebaseAuth.getInstance();
        String UserId = mAuth.getCurrentUser().getUid();

        Button SendQ = findViewById(R.id.SendQPrivateRoom);
        EditText TheQ = findViewById(R.id.TheQPrivateRoom);
        TextView RoomCode = findViewById(R.id.RoomCode);

        QuestionEditor("0", "0", UserId); /// The First Q is Always not working - that way it will not work and user won't notice
        DisAllowToWrite();


        SendQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowToWrite();
                if (EmptyFromForbiddenLanguage(TheQ.getText().toString().toLowerCase())) {
                    QuestionEditor(TheQ.getText().toString(), "0", UserId);
                    ChildrenCount = 0;
                    TheQ.setText("");
                }
                DisAllowToWrite();

            }

        });

        TheQ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(UsersRoomsJoined!=null) {
                    myRef = database.getReference("Rooms").child(UsersRoomsJoined.toString()).child("QuestionCount");
                    getValueOfRoom(myRef);
                    ChildrenCount = 0;
                    RoomCode.setText(UsersRoomsJoined);
                }
                myRef = database.getReference("Users").child(UserId);
                getValueOfUser(myRef);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    public void QuestionEditor(String Q, String A, String UserId) {
        if (UsersRoomsJoined != null) {
            myRef = database.getReference("Rooms").child(UsersRoomsJoined.toString()).child("QuestionCount");
            getValueOfRoom(myRef);
            if (Q.equals("0")) {
                Q = "Zero";
            }
            int SpaceForQFlag = 0;
            for (int i = 1; i <= 5; i++) {
                myRef = database.getReference("Users").child(UserId);
                getValueOfUser(myRef);
                if (Q1 != null && Q2 != null && Q3 != null && Q4 != null && Q5 != null) {
                    if (Q5.equals("0") && i == 5) {
                        SpaceForQFlag = i;
                    }
                    if (Q4.equals("0") && i == 4) {
                        SpaceForQFlag = i;
                    }
                    if (Q3.equals("0") && i == 3) {
                        SpaceForQFlag = i;
                    }
                    if (Q2.equals("0") && i == 2) {
                        SpaceForQFlag = i;
                    }
                    if (Q1.equals("0") && i == 1) {
                        SpaceForQFlag = i;
                    }
                } else {
                    myRef = database.getReference("Users").child(UserId);
                    getValueOfUser(myRef);
                    QuestionEditor(Q, A, UserId);
                }
            }
            if (CheckForCash(StringCash)) {
                if (SpaceForQFlag != 0) {
                    String Ans0 = "0";
                    myRef = database.getReference("Rooms").child(UsersRoomsJoined.toString()).child("QuestionCount");
                    getValueOfRoom(myRef);
                    String qNumString = String.valueOf(ChildrenCount + 1);
                    myRef = database.getReference("Rooms").child(UsersRoomsJoined.toString()).child(qNumString);
                    LocationCard QCard = new LocationCard(Q, Ans0, UserId, "0", "None", "None");
                    myRef.setValue(QCard);
                    myRef = database.getReference("Rooms").child(UsersRoomsJoined.toString()).child("QuestionCount");
                    myRef.setValue(qNumString);
                    QCard = new LocationCard(Q, Ans0, UserId, "0", "None", "None");
                    myRef = database.getReference("Rooms").child(UsersRoomsJoined.toString()).child(qNumString);
                    myRef.setValue(QCard);

                    TheQOfUser TheQOfUser1 = new TheQOfUser(Q, "0", UsersRoomsJoined, String.valueOf(ChildrenCount + 1), "None", "None");
                    myRef = database.getReference("Users").child(UserId).child("qnum" + (SpaceForQFlag));
                    myRef.setValue(TheQOfUser1);
                    Toast.makeText(SendQuestionPrivateRoom.this, "Question Send, " + (SpaceForQFlag - 1) + "-Question Space", Toast.LENGTH_SHORT).show();

                    myRef = database.getReference("Users").child(UserId);
                    getValueOfUser(myRef);
                    if (StringCash != null) {
                        int c = Integer.parseInt(StringCash);
                        int c2 = c - 2;
                        myRef = database.getReference("Users").child(UserId).child("cash");
                        myRef.setValue(String.valueOf(c2));
                    }
                } else {
                    Toast.makeText(SendQuestionPrivateRoom.this, "You Already Sent 5 Q", Toast.LENGTH_SHORT).show();
                }
            }
            ChildrenCount = 0;

        }
    }


    public void getValueOfRoom(DatabaseReference DatabaseGetValue) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String StateCounterNum = dataSnapshot.getValue(String.class);
                if (StateCounterNum == null) {
                    getValueOfRoom(DatabaseGetValue);
                } else {
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


    public void getValueOfUser(DatabaseReference DatabaseGetValue) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                if (userInfo == null) {
                    getValueOfUser(DatabaseGetValue);
                } else {
                    TheUserQ1 = userInfo.getQnum1();
                    TheUserQ2 = userInfo.getQnum2();
                    TheUserQ3 = userInfo.getQnum3();
                    TheUserQ4 = userInfo.getQnum4();
                    TheUserQ5 = userInfo.getQnum5();
                    StringCash = userInfo.getCash();
                    Q1 = TheUserQ1.getUserQ();
                    Q2 = TheUserQ2.getUserQ();
                    Q3 = TheUserQ3.getUserQ();
                    Q4 = TheUserQ4.getUserQ();
                    Q5 = TheUserQ5.getUserQ();

                    ControllerReportsTime = userInfo.getReportsTime();
                    boolean CheckIfNotBan = CheckIfCanLogIn(); //Checks If You get Banned During the use of the app
                    if (!CheckIfNotBan) {
                        changeActivityIfBanned();
                    }
                    UsersRoomsJoined = userInfo.getUsersRooms();
                    UserAllowWrite = userInfo.getAllowWrite();
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

    public boolean EmptyFromForbiddenLanguage(String Ans) {
        String[] ForbiddenLanguage = getResources().getStringArray(R.array.ForbiddenLanguage);
        for (int i = 0; i < ForbiddenLanguage.length; i++) {
            if (Ans.contains(ForbiddenLanguage[i])) {
                Toast.makeText(SendQuestionPrivateRoom.this, "You Are Using Forbidden Language", Toast.LENGTH_SHORT).show();
                return (false);
            }
        }
        return true;
    }

    public boolean CheckForCash(String Cash) {
        if (Cash == null) {
            return false;
        } else {
            if (Integer.parseInt(Cash) < 3) {
                Toast.makeText(SendQuestionPrivateRoom.this, "You Don't have enough cash", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean CheckIfCanLogIn() {
        Date currentTime = Calendar.getInstance().getTime();
        int currentYear = currentTime.getYear();// +1900
        int currentMonth = currentTime.getMonth();// Jan==0
        int currentDay = currentTime.getDate();
        String TimeString = ControllerReportsTime.getBanTill(); /// String = D00,M00,Y000,Min00,H00,E  -----Jan==0, Year = Year+1900, T=Min
        if (TimeString.equals("0")) {
            return true;
        } else {
            if (TimeString.equals("Error") || TimeString.equals("Forever")) {
                Toast.makeText(SendQuestionPrivateRoom.this, "You have been banned", Toast.LENGTH_SHORT).show();
                return false;
            }
            int BanYear = Integer.valueOf(TimeString.substring(TimeString.indexOf("Y") + 1, TimeString.indexOf(",Min")));
            int BanMonth = Integer.valueOf(TimeString.substring(TimeString.indexOf("M") + 1, TimeString.indexOf(",Y")));
            int BanDay = Integer.valueOf(TimeString.substring(TimeString.indexOf("D") + 1, TimeString.indexOf(",M")));
            boolean c = (currentYear >= BanYear) && (currentMonth >= BanMonth) && (currentDay >= BanDay);
            if (!c) {
                BanYear = BanYear + 1900;
                BanMonth = BanMonth + 1;
                Toast.makeText(SendQuestionPrivateRoom.this, "Banned till - " + BanDay + "." + BanMonth + "." + BanYear, Toast.LENGTH_SHORT).show();
            }
            return (c);
        }
    }

    public void changeActivityIfBanned() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
