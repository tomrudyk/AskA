package com.example.aska;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.UUID;

public class CreateUserPage extends AppCompatActivity {

    private static final String TAG = "CreateUserPage";
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private String UserId;
    private Spinner DialogHobbies1;
    private Spinner DialogProfession;
    private Spinner DialogCountry;
    private Spinner DialogHobbies2;
    private Spinner DialogHobbies3;
    private String UserHobby;
    private String UserProfession;
    private String UserCountry;
    private String UserHobby2;
    private String UserHobby3;
    private String RoomsString;
    private String PeopleCounter;
    private boolean IsEmailSent = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_page);

        mAuth = FirebaseAuth.getInstance();

        myRef = database.getReference("Rooms").child("RoomsString");
        getValueOfRoomsCodeString(myRef);

        EditText EmailInp = findViewById(R.id.EmailCreate);
        EditText PasswordInp = findViewById(R.id.PasswordCreate);

        DialogHobbies1 = findViewById(R.id.Hobbies1);
        String[] HobbiesList = getResources().getStringArray(R.array.Hobbies);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, HobbiesList);
        DialogHobbies1.setAdapter(adapter);
        DialogHobbies1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                UserHobby= (String) parent.getItemAtPosition(position);
                myRef = database.getReference("Rooms").child("RoomsString");
                getValueOfRoomsCodeString(myRef);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        DialogHobbies2 = findViewById(R.id.Hobbies2);
        String[] HobbiesList2 = getResources().getStringArray(R.array.Hobbies);
        ArrayAdapter<String> adapterH2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, HobbiesList2);
        DialogHobbies2.setAdapter(adapterH2);
        DialogHobbies2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                UserHobby2= (String) parent.getItemAtPosition(position);
                myRef = database.getReference("Rooms").child("RoomsString");
                getValueOfRoomsCodeString(myRef);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        DialogHobbies3 = findViewById(R.id.Hobbies3);
        String[] HobbiesList3 = getResources().getStringArray(R.array.Hobbies);
        ArrayAdapter<String> adapterH3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, HobbiesList3);
        DialogHobbies3.setAdapter(adapterH3);
        DialogHobbies3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                UserHobby3= (String) parent.getItemAtPosition(position);
                myRef = database.getReference("Rooms").child("RoomsString");
                getValueOfRoomsCodeString(myRef);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        DialogProfession = findViewById(R.id.Profession);
        String[] ProfessionList = getResources().getStringArray(R.array.Professions);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ProfessionList);
        DialogProfession.setAdapter(adapter2);
        DialogProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                UserProfession= (String) parent.getItemAtPosition(position);
                myRef = database.getReference("Rooms").child("RoomsString");
                getValueOfRoomsCodeString(myRef);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        DialogCountry = findViewById(R.id.Country);
        String[] LocationList = getResources().getStringArray(R.array.States);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, LocationList);
        DialogCountry.setAdapter(adapter3);
        DialogCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                UserCountry= (String) parent.getItemAtPosition(position);
                myRef = database.getReference("Rooms").child("RoomsString");
                getValueOfRoomsCodeString(myRef);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });




        Button Finish = findViewById(R.id.CreateBtn);

        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!UserCountry.equals("null")&&!UserHobby.equals("None")&&!UserHobby2.equals("None")&&!UserHobby3.equals("None")&&!UserProfession.equals("None")) {
                   createAccount(EmailInp.getText().toString(), PasswordInp.getText().toString());
               }
               else{
                   Toast.makeText(CreateUserPage.this, " One Of The Above Is Empty ", Toast.LENGTH_SHORT).show();
               }
            }

        });


    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            sendEmailVerification();
                            AccountCreated();
                            Toast.makeText(CreateUserPage.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                            Toast.makeText(CreateUserPage.this, "Verified Email Has Been Sent", Toast.LENGTH_LONG).show();
                            changeActivityBackToSignIn();

                        } else {
                            EditText PasswordInp = findViewById(R.id.PasswordCreate);
                            PasswordInp.setText("");
                            PasswordInp.clearFocus();
                            Toast.makeText(CreateUserPage.this, "Email Taken / Password Too Short", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void AccountCreated(){
        String nickname = mAuth.getUid();
        myRef = database.getReference("Rooms").child("RoomsString");
        getValueOfRoomsCodeString(myRef);
        TheQOfUser TheQ = new TheQOfUser("0","0","0","0","0","0");
        ReportsTime reportsTime = new ReportsTime("0","0","0","0");
        UserLikes userLikes = new UserLikes("0","0");
        UserInfo userInfo = new UserInfo(TheQ,TheQ,TheQ,TheQ,TheQ,"100",UserCountry
                ,"0","0",UserHobby,UserHobby2,UserHobby3,UserProfession,reportsTime,"0","0",userLikes);
        myRef = database.getReference("Users").child(nickname);
        myRef.setValue(userInfo);


    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateUserPage.this, "SignIn Successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            changeActivity();
                            //changeActivityStarter();

                        } else {
                            Toast.makeText(CreateUserPage.this, "AEmail/Password wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void changeActivity() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    public void changeActivityBackToSignIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void Refresh() {
        finish();
        startActivity(getIntent());
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
                myRef = database.getReference("Rooms").child("RoomsString");
                myRef.setValue(RoomsString+Code);
                return Code;
            }
        }else {
            myRef = database.getReference("Rooms").child("RoomsString");
            myRef.setValue(Code);
            return Code;
            }

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

    public void getValueOfPeopleCounter(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String PeopleCount = dataSnapshot.getValue(String.class);
                if (PeopleCount==null){
                    getValueOfPeopleCounter(DatabaseGetValue);
                }
                else{
                    PeopleCounter = (PeopleCount);
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

    public void changeActivityStarter() {
        Intent intent = new Intent(this, WelcomePageMessageOnCreate.class);
        startActivity(intent);
    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                        IsEmailSent=true;
                    }
                });
    }




}