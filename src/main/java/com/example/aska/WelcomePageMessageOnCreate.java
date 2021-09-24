package com.example.aska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomePageMessageOnCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_messege_on_create);

        Button Btn = findViewById(R.id.ContinueBtn);
        TextView welcomeTxt = findViewById(R.id.welcomeTxt);
        String t = "Welcome to Ask A!"+"\n"+"\n"+"Things you need to know:"+"\n"+ "To Ask a Q - you need to pay 2 Coins."+"\n"+"\n"+"To Earn Coins - you need to Answer Questions."
                +"\n"+"\n"+ "Please, make sure you Answer your Questions with full honesty."+"\n"+"\n"+"As a welcome gift you have been added 5 Coins!"+"\n"+"\n"+ "To Continue - Press the Btn "
                +"\n"+"\n"+"\t"+"-Ask A-";
        welcomeTxt.setText(t);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });
    }
    public void changeActivity() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}