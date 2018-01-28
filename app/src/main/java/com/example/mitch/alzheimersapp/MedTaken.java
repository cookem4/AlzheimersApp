package com.example.mitch.alzheimersapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;

public class MedTaken extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_taken);
        MedTimer countdown = new MedTimer();
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
