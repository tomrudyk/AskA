package com.example.aska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomePageMessageOnCreate extends AppCompatActivity {

    int FirstClick= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_messege_on_create);

        Button Btn = findViewById(R.id.ContinueBtn);
        TextView welcomeTxt = findViewById(R.id.welcomeTxt);
        String t = "Welcome to Ask A!"+"\n"+"\n"+"Things you need to know:"+"\n"+ "\n"+ "To Ask a Q - you need to pay 2 Coins."+"\n"+"\n"+"To Earn Coins - you need to Answer Questions."
                +"\n"+"\n"+ "Please, make sure you Answer your Questions with full honesty."+"\n"+"\n"+"As a welcome gift you have been added with 5 Coins!"+"\n"+"\n"+ "To Continue - Press the Btn "
                +"\n"+"\n"+"\t"+"-Ask A-";
        welcomeTxt.setText(t);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirstClick==3) {
                    changeActivity();
                }
                if(FirstClick==2) {
                    FirstClick++;
                    String t = "\n"+"\n"+"In Addition to the main page:"+"\n"+"\n"+"My Info - You can view your questions, their answers and your cash."+"\n"+"Also you can report a user, resend question and delete it. "
                            +"\n"+"\n"+ "Answer Questions - The Page where you answer peoples questions that fit in your entered data - Location, Hobbies And Profession."
                            +"\n"+"\n"+"Private Rooms - You can create or join a private room which all of the questions will be send to the owner of the room.";
                    welcomeTxt.setText(t);
                }
                if(FirstClick==1){
                    FirstClick++;
                    String t = "\n"+"\n"+"For Example:"+"\n"+"\n"+"If you choose:"+"\n"+"\n"+ "Germany, None, None"+"\n"+"\n"+ "The Question will be answered by any German with no importance to his hobbies or profession.";
                    welcomeTxt.setText(t);
                }
                if(FirstClick==0){
                    FirstClick++;
                    String t = "\n"+"\n"+"In the main page you may choose to whom you want to send the question."+"\n"+"\n"+"If you choose Location as "+ "'null' "+ "- The Question will not get a specific state."
                            +"\n"+"\n"+"If you choose Profession as "+ "'None'"+" - The Question will not get a specific profession." +"\n"+"\n"+"And if you choose Hobby as "+ "'None'"+" - The Question will not get a specific hobby.";
                    welcomeTxt.setText(t);
                }


            }
        });
    }
    public void changeActivity() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}