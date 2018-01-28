package com.example.mitch.alzheimersapp;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        addSubmitButtonBehaviour();

        String name;
        String phone;
        String message;

        EditText ContactName = (EditText) findViewById(R.id.name);
        name = ContactName.getText().toString();

        EditText ContactPhone = (EditText) findViewById(R.id.phone_number);
        phone = ContactPhone.getText().toString();

        EditText Message = (EditText) findViewById(R.id.message);
        message = Message.getText().toString();

        Contact c = new Contact(name,phone,message);

    }


    private void addSubmitButtonBehaviour() {
        Button addmedicationBtn = (Button) findViewById(R.id.addcontact);

        addmedicationBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.v("CLICKED", "Add Medication");
                finish();
            }
        });
    }

}
class Contact{
    private String name;
    private String phone;
    private String message;
    Contact(String name,String phone,String message){
        this.message = message;
        this.name = name;
        this.phone = phone;
    }
    public String getName(){
        return this.name;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getMessage(){
        return this.message;
    }
}
