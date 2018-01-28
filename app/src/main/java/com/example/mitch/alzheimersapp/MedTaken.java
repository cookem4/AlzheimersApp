package com.example.mitch.alzheimersapp;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MedTaken extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), notification);
        mp.start();

        buttonPress(mp);

        setContentView(R.layout.activity_med_taken);
        MedTimer countdown = new  MedTimer();
    }
    void buttonPress(final MediaPlayer mp){
        final Button stopAlarm = (Button) findViewById(R.id.okButton);
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                mp.stop();
            }
        });
    }

}

class ContactCareGiver{
    String defaultMessage = "Patient has failed to take medication";
    //variables are sample info, will have to get actaul information from the database
    String samplePhone = "6472911538";
    ContactCareGiver(){
        sendSMS(samplePhone,defaultMessage);
    }
    void sendSMS(String phoneNumber, String message){
        SmsManager SendSMS = SmsManager.getDefault();
        SendSMS.sendTextMessage(phoneNumber,null, message, null, null);
    }
}



class MedTimer {
    MedTimer() {
        CountDownTimer c = new CountDownTimer(900000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                ContactCareGiver callPerson = new ContactCareGiver(); //Calls class that will contact caregiver
            }
        }.start();
    }
}
