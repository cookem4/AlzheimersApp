package com.example.mitch.alzheimersapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SmsManager SendSMS = SmsManager.getDefault();
        SendSMS.sendTextMessage("6472911538",null, "test", null, null);
    }
}
