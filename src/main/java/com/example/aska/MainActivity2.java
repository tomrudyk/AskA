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



public class MainActivity2 extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private static final String TAG = "MainActivity2";
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();;
    private String UserId = mAuth.getCurrentUser().getUid();
    private int ChildrenCount ;

    private TheQOfUser TheUserQ1 = new TheQOfUser();  private TheQOfUser TheUserQ2 = new TheQOfUser();
    private TheQOfUser TheUserQ3 = new TheQOfUser(); private TheQOfUser TheUserQ4 = new TheQOfUser();
    private TheQOfUser TheUserQ5 = new TheQOfUser();
    private String Q1="";private String Q2="";private String Q3="";
    private String Q4="";private String Q5="";private String StringCash;

    private Spinner DialogLocationToAsk;private String LocationToAsk;
    private Spinner DialogHobbyToAsk;private Spinner DialogProfessionToAsk;
    private String HobbyToAsk;private String ProfessionToAsk;
    private ReportsTime ControllerReportsTime;
    private String UsersRoomsJoined;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();
        String UserId = mAuth.getCurrentUser().getUid();

        Button GotoAnswerBtn = findViewById(R.id.GoToAnswer);
        Button PersonalInfoBtn = findViewById(R.id.PersonalInfo);
        Button SendQ = findViewById(R.id.SendQ);
        Button PrivateRooms = findViewById(R.id.PrivateRooms);
        Button GoToInfoOfApp = findViewById(R.id.GetInfoBtn);
        EditText TheQ = findViewById(R.id.TheQ);

        QuestionEditor("0", "0", UserId); /// The First Q is Always not working - that way it will not work and user won't notice

        DialogLocationToAsk = findViewById(R.id.LocationToAskList);
        String[] StateList = getResources().getStringArray(R.array.States);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, StateList);
        DialogLocationToAsk.setAdapter(adapter1);
        DialogLocationToAsk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                LocationToAsk= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        DialogHobbyToAsk = findViewById(R.id.Hobbytoask);
        String[] HobbyList = getResources().getStringArray(R.array.Hobbies);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, HobbyList);
        DialogHobbyToAsk.setAdapter(adapter2);
        DialogHobbyToAsk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                HobbyToAsk= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        DialogProfessionToAsk = findViewById(R.id.Professiontoask);
        String[] ProfessionList = getResources().getStringArray(R.array.Professions);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ProfessionList);
        DialogProfessionToAsk.setAdapter(adapter3);
        DialogProfessionToAsk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ProfessionToAsk= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

         myRef = database.getReference(LocationToAsk+LocationToAsk);
         getValueOfState(myRef);
         ChildrenCount=0;
         myRef = database.getReference("Users").child(UserId);
         getValueOfUser(myRef);


        GotoAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity2();
            }
        });

        PersonalInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });


        SendQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EmptyFromForbiddenLanguage(TheQ.getText().toString().toLowerCase())) {
                    QuestionEditor(TheQ.getText().toString(), "0", UserId);
                    ChildrenCount = 0;
                    TheQ.setText("");
                }

            }

        });

        TheQ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // myRef = database.getReference(LocationToAsk+LocationToAsk);
               // getValueOfState(myRef);
               // ChildrenCount=0;
              //  myRef = database.getReference("Users").child(UserId);
               // getValueOfUser(myRef);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        PrivateRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity3();
            }
        });

        GoToInfoOfApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivityToGetInfoOfApp();
            }
        });

    }


    public void QuestionEditor(String Q, String A, String UserId) {
        myRef = database.getReference(LocationToAsk+LocationToAsk);
        getValueOfState(myRef);
        if (Q.equals("0")){
            Q="Zero";
        }
        int SpaceForQFlag = 0;
        for (int i=1;i<=5;i++) {
            myRef = database.getReference("Users").child(UserId);
            getValueOfUser(myRef);
            if (Q1!=null&&Q2!=null&&Q3!=null&&Q4!=null&&Q5!=null) {
                if (Q5.equals("0")&&i==5) {
                    SpaceForQFlag = i;
                }
                if (Q4.equals("0")&&i==4) {
                    SpaceForQFlag = i;
                }
                if (Q3.equals("0")&&i==3) {
                    SpaceForQFlag = i;
                }
                if (Q2.equals("0")&&i==2) {
                    SpaceForQFlag = i;
                }
                if (Q1.equals("0")&&i==1) {
                    SpaceForQFlag = i;
                }
            }
            else{
                myRef = database.getReference("Users").child(UserId);
                getValueOfUser(myRef);
                QuestionEditor(Q, A, UserId);
            }
        }
        if(CheckForCash(StringCash)) {
            if (SpaceForQFlag != 0) {
                String Ans0 = "0";
                myRef = database.getReference((LocationToAsk + LocationToAsk).toString());
                getValueOfState(myRef);
                String qNumString = String.valueOf(ChildrenCount + 1);
                myRef = database.getReference(LocationToAsk).child(qNumString);
                LocationCard QCard = new LocationCard(Q, Ans0, UserId, "0",HobbyToAsk,ProfessionToAsk);
                myRef.setValue(QCard);
                myRef = database.getReference((LocationToAsk + LocationToAsk).toString());
                myRef.setValue(qNumString);
                QCard = new LocationCard(Q, Ans0, UserId, "0",HobbyToAsk,ProfessionToAsk);
                myRef = database.getReference(LocationToAsk).child(qNumString);
                myRef.setValue(QCard);

                TheQOfUser TheQOfUser1 = new TheQOfUser(Q, "0", LocationToAsk, String.valueOf(ChildrenCount + 1),HobbyToAsk,ProfessionToAsk);
                myRef = database.getReference("Users").child(UserId).child("qnum" + (SpaceForQFlag));
                myRef.setValue(TheQOfUser1);
                Toast.makeText(MainActivity2.this, "Question Send, "+(SpaceForQFlag - 1)+ "-Question Space", Toast.LENGTH_SHORT).show();

                myRef = database.getReference("Users").child(UserId);
                getValueOfUser(myRef);
                if (StringCash != null) {
                    int c = Integer.parseInt(StringCash);
                    int c2 = c - 2;
                    myRef = database.getReference("Users").child(UserId).child("cash");
                    myRef.setValue(String.valueOf(c2));
                }
            } else {
                Toast.makeText(MainActivity2.this, "You Already Sent 5 Q", Toast.LENGTH_SHORT).show();
            }
        }
        ChildrenCount=0;


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


    public void getValueOfUser(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                if(userInfo==null) {
                    getValueOfUser(DatabaseGetValue);
                }
                else{
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
                    if(!CheckIfNotBan){
                        changeActivityIfBanned();
                    }
                    UsersRoomsJoined=userInfo.getUsersRooms();
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



    public void changeActivity() {
        Intent intent = new Intent(this, MyInfo.class);
        startActivity(intent);
    }
    public void changeActivity2() {
        Intent intent = new Intent(this, AnswerQ.class);
        startActivity(intent);
    }
    public void changeActivity3() {
        Intent intent = new Intent(this, PrivateRoom.class);
        startActivity(intent);
    }
    public boolean EmptyFromForbiddenLanguage(String Ans){
        String[] ForbiddenLanguage = getResources().getStringArray(R.array.ForbiddenLanguage);
        for (int i=0;i<ForbiddenLanguage.length;i++){
            if(Ans.contains(ForbiddenLanguage[i])){
                Toast.makeText(MainActivity2.this, "You Are Using Forbidden Language", Toast.LENGTH_SHORT).show();
                return (false);            }
        }
        return true;
    }

    public boolean CheckForCash(String Cash){
        if(Cash==null){
            return false;
        }
        else{
            if(Integer.parseInt(Cash)<3){
                Toast.makeText(MainActivity2.this, "You Don't have enough cash", Toast.LENGTH_SHORT).show();
                return false;
            }
            else{
                return true;
            }
        }
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
                Toast.makeText(MainActivity2.this, "You have been banned", Toast.LENGTH_SHORT).show();
                return false;
            }
            int BanYear = Integer.valueOf(TimeString.substring(TimeString.indexOf("Y") + 1, TimeString.indexOf(",Min")));
            int BanMonth = Integer.valueOf(TimeString.substring(TimeString.indexOf("M") + 1, TimeString.indexOf(",Y")));
            int BanDay = Integer.valueOf(TimeString.substring(TimeString.indexOf("D") + 1, TimeString.indexOf(",M")));
            boolean c = (currentYear >= BanYear) && (currentMonth >= BanMonth) && (currentDay >= BanDay);
            if (!c){
                BanYear=BanYear+1900;
                BanMonth=BanMonth+1;
                Toast.makeText(MainActivity2.this, "Banned till - "+BanDay+"."+BanMonth+"." +BanYear , Toast.LENGTH_SHORT).show();
            }
            return (c);
        }
    }

    public void changeActivityIfBanned() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void changeActivityToGetInfoOfApp(){
        Intent intent = new Intent(this, WelcomePageMessageOnCreate.class);
        startActivity(intent);
    }



}
