package com.example.mitch.alzheimersapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class AddContacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        addSubmitButtonBehaviour();

    }


    private void addSubmitButtonBehaviour() {
//        final DatabaseHandler db = db2;
        final DatabaseHandler db = new DatabaseHandler(this);

        Button addmedicationBtn = (Button) findViewById(R.id.addcontact);

        addmedicationBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            // Code here executes on main thread after user presses button
            Log.v("CLICKED", "Add Medication");
            EditText ContactName = (EditText) findViewById(R.id.name);
            String name = ContactName.getText().toString();

            EditText ContactPhone = (EditText) findViewById(R.id.phone_number);
            String phone = ContactPhone.getText().toString();

            EditText Message = (EditText) findViewById(R.id.message);
            String message = Message.getText().toString();

            ContactCareGiver c = new ContactCareGiver(name,phone,message);

            try {
                Log.v("try", "Before execute");

                new LongOperation().execute(c).get();
                Log.v("try", "After execute");
            }
            catch (Exception e) {
                Log.v("catch", e.getMessage());
            }


            finish();
            }
        });
    }

    private class LongOperation extends AsyncTask<ContactCareGiver, Void, String> {

        @Override
        protected String doInBackground(ContactCareGiver... cg) {
            final DatabaseHandler db = new DatabaseHandler(AddContacts.this);

            db.addCareGiver(cg[0]);
//            db.getCareGiver(0).getName();
            Log.v("NAME", db.getCareGiver(1).getName());
            Log.v("NUMBER", db.getCareGiver(1).getPhoneNumber());
//            Log.v("NAME", db.getCareGiver(1).getName());
            Log.v("COUNT", "" +  db.getCareGiversCount());

            List<ContactCareGiver> list = db.getAllCareGivers();
            ContactCareGiver cg2 = list.get(list.size() - 1);
            Log.v("LAST ADDED NAME", cg2.getName());
            Log.v("LAST ADDED Number", cg2.getPhoneNumber());
            Log.v("LAST ADDED Message", cg2.getMessage());
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
//class Contact{
//    private String name;
//    private String phone;
//    private String message;
//    Contact(String name,String phone,String message){
//        this.message = message;
//        this.name = name;
//        this.phone = phone;
//    }
//    public String getName(){
//        return this.name;
//    }
//    public String getPhone(){
//        return this.phone;
//    }
//    public String getMessage(){
//        return this.message;
//    }
//}
