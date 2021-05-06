package com.example.aska;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.SignInButton;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity implements LocationListener {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private String UserId ;
    LocationManager locationManager;

    private String StateOfUser;
    private String Cash;
    String LocationsOfQUser[] = new String[5];
    private String Q1="";private String Q2="";private String Q3="";
    private String Q4="";private String Q5="";
    private String A1="0";private String A2="0";private String A3="0";
    private String A4="0";private String A5="0";
    private String NumInState1="";private String NumInState2="";private String NumInState3="";
    private String NumInState4="";private String NumInState5="";

    private String TheQOfLocationCard="";private String TheAOfLocationCard="";private String UserQuestionId="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Button SignInBtn = findViewById(R.id.SignInBtn);
        Button CreateAccount = findViewById(R.id.logInBtn);
        Button LogOutBtn = findViewById(R.id.logOutBtn);
        EditText EmailInp = findViewById(R.id.Email);
        EditText PasswordInp = findViewById(R.id.Password);
        TextView WelcomeTxt = findViewById(R.id.WelcomeTxt);

        grantPermission();
        checkLocationIsEnabled();
        getLocationOfUser();

        SignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(EmailInp.getText().toString(), PasswordInp.getText().toString());
                if(mAuth.getCurrentUser()!=null){
                    UserId=mAuth.getCurrentUser().getUid();
                    myRef = database.getReference("Users").child(UserId);
                    getValueOfUser(myRef);
                    WelcomeTxt.setText(mAuth.getCurrentUser().getEmail());
                }
                else{
                    WelcomeTxt.setText("No User Found");
                }

            }
        });


        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grantPermission();
                checkLocationIsEnabled();
                getLocationOfUser();
                createAccount(EmailInp.getText().toString(),PasswordInp.getText().toString());
                if(mAuth.getCurrentUser()!=null){

                }
                else{
                    WelcomeTxt.setText("No User Found");
                }
            }

        });

        LogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "LogOut Successfully", Toast.LENGTH_SHORT).show();
                WelcomeTxt.setText("Welcome");
            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            AccountCreated();
                            //changeActivityStarter();
                        } else {
                            Toast.makeText(MainActivity.this, "Email Taken / Password<6", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "SignIn Successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            changeActivity();

                        } else {
                            Toast.makeText(MainActivity.this, "AEmail/Password wrong", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
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
                    }
                });
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }

    public void changeActivity() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    public void changeActivityStarter() {
        Intent intent = new Intent(this, WelcomePageMessageOnCreate.class);
        startActivity(intent);
    }

    public void AccountCreated(){
        String nickname = mAuth.getUid();
        TheQOfUser TheQ = new TheQOfUser("0","0","0","0");
        UserInfo userInfo = new UserInfo(TheQ,TheQ,TheQ,TheQ,TheQ,"100", "Israel","0","0");
        if (String.valueOf(StateOfUser).equals("null")){
            for(int i =0;i<20;i++) {
                grantPermission();
                checkLocationIsEnabled();
                getLocationOfUser();
            }
        }
        //UserInfo userInfo = new UserInfo(TheQ,TheQ,TheQ,TheQ,TheQ,"100",String.valueOf(StateOfUser),"0","0"); //-------------------- StateOfUser =  nul, Cant get Location of User
        myRef = database.getReference("Users").child(nickname);
        myRef.setValue(userInfo);

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

            StateOfUser = addresses.get(0).getCountryName();
            if(StateOfUser.equals("null")|| StateOfUser==null){
                for(int i =0;i<10;i++) {
                    grantPermission();
                    checkLocationIsEnabled();
                    getLocationOfUser();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getLocationOfUser() {
        try{
            locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500,5,(LocationListener) this);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }
    private void checkLocationIsEnabled() {
        LocationManager lm =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnable = false;
        boolean netEnable = false;
        try{
            gpsEnable = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            netEnable = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        catch (Exception e){
            e.printStackTrace();;
        }
        if(!gpsEnable && !netEnable){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Enable GPS")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel",null)
                    .show();
        }
    }
    private void grantPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }


    public void getValueOfUser(DatabaseReference DatabaseGetValue){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                if (userInfo!=null) {
                    StateOfUser = userInfo.getUserLocation();
                    Cash = userInfo.getCash();
                    Q1 = userInfo.getQnum1().getUserQ();
                    A1 = userInfo.getQnum1().getUserA();
                    Q2 = userInfo.getQnum2().getUserQ();
                    A2 = userInfo.getQnum2().getUserA();
                    Q3 = userInfo.getQnum3().getUserQ();
                    A3 = userInfo.getQnum3().getUserA();
                    Q4 = userInfo.getQnum4().getUserQ();
                    A4 = userInfo.getQnum4().getUserA();
                    Q5 = userInfo.getQnum5().getUserQ();
                    A5 = userInfo.getQnum5().getUserA();
                    NumInState1=userInfo.getQnum1().getNumInState();
                    NumInState2=userInfo.getQnum2().getNumInState();
                    NumInState3=userInfo.getQnum3().getNumInState();
                    NumInState4=userInfo.getQnum4().getNumInState();
                    NumInState5=userInfo.getQnum5().getNumInState();

                    LocationsOfQUser[0] = userInfo.getQnum1().getqLocation();
                    LocationsOfQUser[1] = userInfo.getQnum2().getqLocation();
                    LocationsOfQUser[2] = userInfo.getQnum3().getqLocation();
                    LocationsOfQUser[3] = userInfo.getQnum4().getqLocation();
                    LocationsOfQUser[4] = userInfo.getQnum5().getqLocation();
                }
                else{
                    getValueOfUser(DatabaseGetValue);
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
        // DataBaseChanges();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LocationCard Card = dataSnapshot.getValue(LocationCard.class);
                if(Card!=null) {
                    TheQOfLocationCard = Card.getTheQ();
                    TheAOfLocationCard = Card.getTheA();
                    UserQuestionId = Card.getUserId();
                    if(UserQuestionId==null||UserQuestionId.isEmpty()){
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
}

