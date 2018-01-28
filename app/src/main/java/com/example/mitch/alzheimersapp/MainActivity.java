package com.example.mitch.alzheimersapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButtonBehavior();
    }

    private void addButtonBehavior() {
        final Button viewmedicationsBtn = findViewById(R.id.viewmedications);
        viewmedicationsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.v("CLICKED", "View Medications");
                Intent myIntent = new Intent(MainActivity.this, AddMeds.class);
//                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
}
