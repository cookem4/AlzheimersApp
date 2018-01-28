package com.example.mitch.alzheimersapp;


import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

public class MedTaken extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), notification);
        mp.start();

        buttonPress(mp);

        setContentView(R.layout.activity_med_taken);
        MedTimer countdown = new MedTimer();
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

class ContactCareGiver {
    String careGiverName;
    String phoneNumber;
    String message;

    public String getName(){
        return this.careGiverName;
    }
    public String getPhoneNumber(){ return this.phoneNumber; }
    public String getMessage(){ return this.message; }


    ContactCareGiver(String careGiverName, String phoneNumber, String message){
        this.careGiverName = careGiverName;
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    void sendSMS(String phoneNumber, String message) {
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
                ContactCareGiver callPerson = new ContactCareGiver("tempName", "6472911538", "Patient has failed to take medication"); //Calls class that will contact caregiver
            }
        }.start();
    }
}
