package com.example.mitch.alzheimersapp;


import android.icu.text.IDNA;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MedTaken extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), notification);
        mp.start();



        setContentView(R.layout.activity_med_taken);

        ContactCareGiver cg = null;
        try {
            // do stuff
            cg = new RetriveLastContact().execute().get();

        }
        catch (Exception e) {
            // handle exceptions
        }
        MedInfo cg2 = null;
        try{
            cg2 = new RetriveLastMedName().execute().get();

        }
        catch (Exception e){

        }

        TextView textView;
        AlertDialog alertDialog;
        EditText editText;

        textView = (TextView) findViewById(R.id.medicationName);
        textView.setText(cg2.getName());

        MedTimer countdown = new MedTimer(mp, cg);

        buttonPress(mp);

    }
    void buttonPress(final MediaPlayer mp){
        final Button stopAlarm = (Button) findViewById(R.id.okButton);
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                mp.stop();
                finish();
            }
        });
    }

    private class RetriveLastContact extends AsyncTask<ContactCareGiver, Void, ContactCareGiver> {
        @Override
        protected ContactCareGiver doInBackground(ContactCareGiver... cg) {
            final DatabaseHandler db = new DatabaseHandler(MedTaken.this);
            Log.v("NAME", db.getCareGiver(1).getName());
            Log.v("NUMBER", db.getCareGiver(1).getPhoneNumber());
            Log.v("COUNT", "" +  db.getCareGiversCount());

            List<ContactCareGiver> list = db.getAllCareGivers();
            ContactCareGiver cg2 = list.get(list.size() - 1);
            Log.v("LAST ADDED NAME", cg2.getName());
            Log.v("LAST ADDED Number", cg2.getPhoneNumber());
            Log.v("LAST ADDED Message", cg2.getMessage());
            return cg2;
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
    private class RetriveLastMedName extends AsyncTask<MedInfo, Void, MedInfo>{
        @Override
        protected MedInfo doInBackground(MedInfo... cg){
            final DatabaseHandler db = new DatabaseHandler(MedTaken.this);
            Log.v("NAME", db.getMedication(1).getName());
            List<MedInfo> list = db.getAllMedications();
            MedInfo cg2 = list.get(list.size()-1);
            Log.v("LAST ADDED MED NAME", cg2.getName());
            return cg2;
        }
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

    void sendSMS() {
        SmsManager SendSMS = SmsManager.getDefault();
        SendSMS.sendTextMessage(this.phoneNumber,null, this.message, null, null);
    }
}



class MedTimer {
    MedTimer(final MediaPlayer mp, final ContactCareGiver cg) {
        CountDownTimer c = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                String tempNum;
                String tempMsg;

                ContactCareGiver callPerson = new ContactCareGiver("tempName",cg.getPhoneNumber(), cg.getMessage()); //Calls class that will contact caregiver
                if (mp.isPlaying()) {
                    callPerson.sendSMS();
                }
                mp.stop();
            }
        }.start();
    }



}
