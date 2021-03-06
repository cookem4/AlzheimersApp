package com.example.mitch.alzheimersapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Log.v("MainActivity", "OnCreate");

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButtonBehavior();
    }

    private void addButtonBehavior() {
        // Open View Medication Activity
        final Button viewmedicationsBtn = (Button) findViewById(R.id.viewmedications);
        viewmedicationsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.v("CLICKED", "View Medications");
                Intent myIntent = new Intent(MainActivity.this, MedList.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        // Open Add Medication Activity
        final Button addmedicationsBtn = (Button) findViewById(R.id.addmedications);
        addmedicationsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.v("CLICKED", "Add Medications");
                Intent myIntent = new Intent(MainActivity.this, AddMeds.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        // Open Add Contact Activity
        final Button addcontactBtn = (Button) findViewById(R.id.addcontact);
        addcontactBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.v("CLICKED", "Add Contact");
                Intent myIntent = new Intent(MainActivity.this, AddContacts.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        final Button demoBtn = (Button) findViewById(R.id.demolaunch);
        demoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.v("CLICKED", "Add Contact");
                Intent myIntent = new Intent(MainActivity.this, MedTaken.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
}
