package com.example.aska;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


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

    private String ChosenLocation;
    StateDialog stateDialog = new StateDialog();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();
        String UserId = mAuth.getCurrentUser().getUid();

        Button GotoAnswerBtn = findViewById(R.id.GoToAnswer);
        Button PersonalInfoBtn = findViewById(R.id.PersonalInfo);
        Button SendQ = findViewById(R.id.SendQ);
        TextView LocationToAsk = findViewById(R.id.LocationToAsk);
        EditText TheQ = findViewById(R.id.TheQ);
        Button LocationChoose = findViewById(R.id.LocationChooseBtn);

        QuestionEditor("0", "0", UserId); /// The First Q is Always not working - that way it will not work and user won't notice


        LocationChoose.setOnClickListener(new View.OnClickListener() { /// Lo Oved Maspik Tov!!!!!!!!!!!!!!!!!!!!!!!!***************************
            @Override
            public void onClick(View v) {
                openStateDialog();
                ChosenLocation=stateDialog.getChosenState();
                LocationToAsk.setText(String.valueOf(ChosenLocation));


            }
        });

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
                if(!LocationToAsk.getText().toString().equals("null")) {
                    if(EmptyFromForbiddenLanguage(TheQ.getText().toString().toLowerCase())) {
                        QuestionEditor(TheQ.getText().toString(), "0", UserId);
                        ChildrenCount = 0;
                    }
                }else{
                    Toast.makeText(MainActivity2.this, "Location is null", Toast.LENGTH_SHORT).show();
                }
            }

        });

        LocationToAsk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String LocationOfQ = LocationToAsk.getText().toString();
                myRef = database.getReference(LocationOfQ+LocationOfQ);
                getValueOfState(myRef);
                ChildrenCount=0;
            }
        });

    }



    public void QuestionEditor(String Q, String A, String UserId) {
        TextView LocationToAsk = findViewById(R.id.LocationToAsk);
        String LocationOfQ = LocationToAsk.getText().toString();
        myRef = database.getReference(LocationOfQ+LocationOfQ);
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
                LocationToAsk = findViewById(R.id.LocationToAsk);
                LocationOfQ = LocationToAsk.getText().toString();
                String Ans0 = "0";
                myRef = database.getReference(LocationOfQ + LocationOfQ);
                getValueOfState(myRef);
                String qNumString = String.valueOf(ChildrenCount + 1);
                myRef = database.getReference(LocationOfQ).child(qNumString);
                LocationCard QCard = new LocationCard(Q, Ans0, UserId, "0");
                myRef.setValue(QCard);
                myRef = database.getReference(LocationOfQ + LocationOfQ);
                myRef.setValue(qNumString);
                QCard = new LocationCard(Q, Ans0, UserId, "0");
                myRef = database.getReference(LocationOfQ).child(qNumString);
                myRef.setValue(QCard);

                TheQOfUser TheQOfUser1 = new TheQOfUser(Q, "0", LocationOfQ, String.valueOf(ChildrenCount + 1));
                myRef = database.getReference("Users").child(UserId).child("qnum" + (SpaceForQFlag));
                myRef.setValue(TheQOfUser1);
                Toast.makeText(MainActivity2.this, "Question Send, "+(SpaceForQFlag - 1)+ "-Question Space", Toast.LENGTH_SHORT).show();

                myRef = database.getReference("Users").child(UserId);
                getValueOfUser(myRef);
                if (StringCash != null) {
                    int c = Integer.parseInt(StringCash);
                    int c2 = c - 3;
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
                    String UserLocation = userInfo.getUserLocation();
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
    public void openStateDialog(){
        stateDialog.show(getSupportFragmentManager(), "StateDialog");
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


}
