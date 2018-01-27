package com.example.mitch.alzheimersapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MedTaken extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_taken);
        MedTimer countdown = new  MedTimer();
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
    static final int delay = 1000; //These control how long the timer runs
    static final int period = 1000; //Presumably in milliseconds
    static int interval;
    static Timer timer;

    MedTimer() {
        start();
    }

    static void start() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Input seconds => : ");
        String secs = sc.nextLine();
        timer = new Timer();
        interval = Integer.parseInt(secs);
        System.out.println(secs);
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                setInterval();
            }
        }, delay, period);
    }

    static final int setInterval() {
        if (interval == 1) {
            timer.cancel();
            ContactCareGiver callPerson = new ContactCareGiver(); //Calls class that will contact caregiver
        }
        return interval--;
    }
}
