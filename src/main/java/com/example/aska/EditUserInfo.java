package com.example.aska;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditUserInfo extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private static final String TAG = "MyInfo";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String UserId = mAuth.getCurrentUser().getUid();


    private String UserHobby;private String UserProfession;private String UserHobby2;private String UserHobby3;
    private String ReportGot;private String ReportSent;private String UserLocation;
    private Spinner DialogHobbyToAsk;private Spinner DialogProfessionToAsk;private Spinner DialogHobbyToAsk2;private Spinner DialogHobbyToAsk3;
    private String HobbyChange;private String ProfessionChange;private String HobbyChange2;private String HobbyChange3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        TextView CurHobby = findViewById(R.id.CurHobby);
        TextView CurProfession = findViewById(R.id.CurProffesion);
        TextView CurLocation = findViewById(R.id.CurLocation);
        Button Save = findViewById(R.id.SaveInfoEdit);
        Button Reload = findViewById(R.id.ReloadBtn);

        myRef = database.getReference("Users").child(UserId);
        getValueOfUser(myRef);

        DialogHobbyToAsk = findViewById(R.id.HobbyInfoEdit);
        String[] HobbyList = getResources().getStringArray(R.array.Hobbies);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, HobbyList);
        DialogHobbyToAsk.setAdapter(adapter2);
        DialogHobbyToAsk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                HobbyChange= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        DialogHobbyToAsk2 = findViewById(R.id.HobbyInfoEdit2);
        String[] HobbyList2 = getResources().getStringArray(R.array.Hobbies);
        ArrayAdapter<String> adapterH2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, HobbyList2);
        DialogHobbyToAsk2.setAdapter(adapterH2);
        DialogHobbyToAsk2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                HobbyChange2= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        DialogHobbyToAsk3 = findViewById(R.id.HobbyInfoEdit3);
        String[] HobbyList3 = getResources().getStringArray(R.array.Hobbies);
        ArrayAdapter<String> adapterH3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, HobbyList3);
        DialogHobbyToAsk3.setAdapter(adapterH3);
        DialogHobbyToAsk3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                HobbyChange3= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        DialogProfessionToAsk = findViewById(R.id.ProfessionInfoEdit);
        String[] ProfessionList = getResources().getStringArray(R.array.Professions);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ProfessionList);
        DialogProfessionToAsk.setAdapter(adapter3);
        DialogProfessionToAsk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ProfessionChange= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!HobbyChange.equals("None")&&!HobbyChange2.equals("None")&&!HobbyChange3.equals("None")&&!ProfessionChange.equals("None")){
                    myRef = database.getReference("Users").child(UserId).child("hobby");
                    myRef.setValue(HobbyChange);
                    myRef = database.getReference("Users").child(UserId).child("hobby2");
                    myRef.setValue(HobbyChange2);
                    myRef = database.getReference("Users").child(UserId).child("hobby3");
                    myRef.setValue(HobbyChange3);
                    myRef = database.getReference("Users").child(UserId).child("profession");
                    myRef.setValue(ProfessionChange);
                    myRef = database.getReference("Users").child(UserId);
                    getValueOfUser(myRef);
                    CurHobby.setText("Current Hobbies:" + "\n" + " 1- " + UserHobby.toString() + "\n" + "2- " + UserHobby2.toString() + "\n" + "3- " + UserHobby3.toString());
                    CurProfession.setText("Current Profession: " + UserProfession.toString());
                    CurLocation.setText("Your Location: " + UserLocation.toString());
                }
                else{
                    Toast.makeText(EditUserInfo.this, "Please Fill All From Above", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = database.getReference("Users").child(UserId);
                getValueOfUser(myRef);
                CurHobby.setText("Current Hobbies: 1- "+ UserHobby.toString()+"\n"+"2- "+ UserHobby2.toString()+"\n"+"3- "+ UserHobby3.toString());
                CurProfession.setText("Current Profession: "+ UserProfession.toString());
                CurLocation.setText("Your Location: "+ UserLocation.toString());
            }
        });

    }


    public void getValueOfUser(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                UserLocation = userInfo.getUserLocation();
                ReportGot=userInfo.getReportGot();
                ReportSent=userInfo.getReportSent();
                UserHobby=userInfo.getHobby();
                UserHobby2=userInfo.getHobby2();
                UserHobby3=userInfo.getHobby3();
                UserProfession=userInfo.getProfession();

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