package com.example.aska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PrivateRoom extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private static final String TAG = "PrivateRoom";
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();;
    private String UserId = mAuth.getCurrentUser().getUid();
    private ReportsTime ControllerReportsTime;

    private int RoomsChildrenCount;
    private String UserRoomCode;
    private String UsersRoomsJoined;
    private String RoomsString;
    private String RoomToAsk;
    private String UserCash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_room);

        Button CreateRoomBtn = findViewById(R.id.CreateRoomBtn);
        Button JoinRoomBtn = findViewById(R.id.JoinRoomBtn);
        Button GoToAnswerYourRoom = findViewById(R.id.AnswerYourRoom);
        EditText JoinRoomCode = findViewById(R.id.JoinRoomCode);
        TextView RoomCode = findViewById(R.id.UserRoomCodeTxt);

        myRef = database.getReference("Rooms").child("RoomsString");
        getValueOfRoomsCodeString(myRef);
        myRef = database.getReference("Users").child(UserId);
        getValueOfUser(myRef);

        CreateRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = database.getReference("Users").child(UserId);
                getValueOfUser(myRef);
                if(CheckForCashPrivateRoom(UserCash)) {
                    String newCode = RandomRoomCode();
                    if (!(UserRoomCode == null)) {
                        myRef = database.getReference("Rooms").child(String.valueOf(UserRoomCode));
                        myRef.setValue(null);
                        myRef = database.getReference("Rooms").child(String.valueOf(newCode)).child("QuestionCount");
                        myRef.setValue("0");
                        myRef = database.getReference("Users").child(UserId).child("roomCode");
                        myRef.setValue(newCode);
                        Toast.makeText(PrivateRoom.this, "Room Created Successfully", Toast.LENGTH_SHORT).show();
                        int c = Integer.parseInt(UserCash);
                        int c2 = c - 5;
                        myRef = database.getReference("Users").child(UserId).child("cash");
                        myRef.setValue(String.valueOf(c2));
                    }
                }
            }
        });

        JoinRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String RoomCodeToJoin = JoinRoomCode.getText().toString();
                myRef = database.getReference("Users").child(UserId);
                getValueOfUser(myRef);
                if (!RoomCodeToJoin.isEmpty()){
                    if(RoomsString.contains(RoomCodeToJoin)){
                        String a = RoomCodeToJoin;
                        myRef = database.getReference("Users").child(UserId).child("usersRooms");
                        myRef.setValue(a);
                        changeActivitySendQToPrivateRoom();
                    } else{
                        Toast.makeText(PrivateRoom.this, "No Room Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(PrivateRoom.this, "Code is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        GoToAnswerYourRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserRoomCode!=null&&!UserRoomCode.equals("0")) {
                    changeActivityAnswerQToPrivateRoom();
                }
                else{
                    Toast.makeText(PrivateRoom.this, "You don't own a private room", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void getValueOfPrivateRooms(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String RoomsCounterNum = dataSnapshot.getValue(String.class);
                if (RoomsCounterNum==null){
                    getValueOfPrivateRooms(DatabaseGetValue);
                }
                else{
                    RoomsChildrenCount = Integer.parseInt(RoomsCounterNum);
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
                    ControllerReportsTime = userInfo.getReportsTime();
                    boolean CheckIfNotBan = CheckIfCanLogIn(); //Checks If You get Banned During the use of the app
                    if(!CheckIfNotBan){
                        changeActivityIfBanned();
                    }
                    UserRoomCode = userInfo.getRoomCode();
                    TextView RoomCode = findViewById(R.id.UserRoomCodeTxt);
                    if((UserRoomCode!=null)&&!UserRoomCode.equals("0")) {
                        RoomCode.setText("Your Private Room Code Is: " + String.valueOf(UserRoomCode));
                    }
                    else{
                        RoomCode.setText("You Do Not Own A Private Room"+"\n"+ "New Room = 5 Coins");
                    }
                    UsersRoomsJoined = userInfo.getUsersRooms();
                    UserCash=userInfo.getCash();

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
                Toast.makeText(PrivateRoom.this, "You have been banned", Toast.LENGTH_SHORT).show();
                return false;
            }
            int BanYear = Integer.valueOf(TimeString.substring(TimeString.indexOf("Y") + 1, TimeString.indexOf(",Min")));
            int BanMonth = Integer.valueOf(TimeString.substring(TimeString.indexOf("M") + 1, TimeString.indexOf(",Y")));
            int BanDay = Integer.valueOf(TimeString.substring(TimeString.indexOf("D") + 1, TimeString.indexOf(",M")));
            boolean c = (currentYear >= BanYear) && (currentMonth >= BanMonth) && (currentDay >= BanDay);
            if (!c) {
                BanYear = BanYear + 1900;
                BanMonth = BanMonth + 1;
                Toast.makeText(PrivateRoom.this, "Banned till - " + BanDay + "." + BanMonth + "." + BanYear, Toast.LENGTH_SHORT).show();
            }
            return (c);
        }
    }
    public void changeActivityIfBanned() {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
    public void changeActivitySendQToPrivateRoom() {
        Intent intent = new Intent(this, SendQuestionPrivateRoom.class);
        startActivity(intent);
    }
    public void changeActivityAnswerQToPrivateRoom() {
        Intent intent = new Intent(this, AnswerQPrivateRoom.class);
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

    public String RandomRoomCode(){
        myRef = database.getReference("Rooms").child("RoomsString");
        getValueOfRoomsCodeString(myRef);
        String A = "12346789ABCDEFGHJKLMNPQRSTUVWXYZ";
        Random rd = new Random();
        String Code="";
        for(int i =0;i<5;i++){
            int B = rd.nextInt(A.length());
            Code = Code + A.charAt(B);
        }
        if (RoomsString!=null) {
            if (RoomsString.contains(Code)) {
                return RandomRoomCode();
            }
            else{
                if(UserRoomCode!=null) {
                    myRef = database.getReference("Rooms").child("RoomsString");
                    myRef.setValue(RoomsString.replace(UserRoomCode.toString(), "") + Code);
                    return Code;
                }
                else{
                    myRef = database.getReference("Users").child(UserId);
                    getValueOfUser(myRef);
                    return RandomRoomCode();
                }
            }
        }else {
            myRef = database.getReference("Rooms").child("RoomsString");
            myRef.setValue(Code);
            return Code;
        }

    }

    public boolean CheckForCashPrivateRoom(String Cash){
        if(Cash==null){
            return false;
        }
        else{
            if(Integer.parseInt(Cash)<5){
                Toast.makeText(PrivateRoom.this, "You Don't have enough cash", Toast.LENGTH_SHORT).show();
                return false;
            }
            else{
                return true;
            }
        }
    }


}