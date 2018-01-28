package com.example.mitch.alzheimersapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMeds extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.v("AddMeds", "Created");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meds);

        addSubmitButtonBehaviour();
    }

    private void addSubmitButtonBehaviour() {
        Button addmedicationBtn = (Button) findViewById(R.id.addmedication);

        addmedicationBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                Map<String, Boolean> dateMap = new HashMap<String, Boolean>();
                String medName = "";
                String medTime = "";

                EditText MedInfo = (EditText) findViewById(R.id.edittext_medname);
                medName = MedInfo.getText().toString();

                EditText MedTime = (EditText) findViewById(R.id.edittext_medtime);
                medTime = MedTime.getText().toString();

                CheckBox CMon = (CheckBox) findViewById(R.id.checkbox_mon);
                dateMap.put("mon", CMon.isChecked());
                CheckBox CTues = (CheckBox) findViewById(R.id.checkbox_tues);
                dateMap.put("tues", CTues.isChecked());
                CheckBox CWed = (CheckBox) findViewById(R.id.checkbox_wed);
                dateMap.put("wed", CWed.isChecked());
                CheckBox CThurs = (CheckBox) findViewById(R.id.checkbox_thurs);
                dateMap.put("thurs", CThurs.isChecked());
                CheckBox CFri = (CheckBox) findViewById(R.id.checkbox_fri);
                dateMap.put("fri", CFri.isChecked());
                CheckBox CSat = (CheckBox) findViewById(R.id.checkbox_sat);
                dateMap.put("sat", CSat.isChecked());
                CheckBox CSun = (CheckBox) findViewById(R.id.checkbox_sun);
                dateMap.put("sun", CSun.isChecked());

                MedInfo medData = new MedInfo(dateMap, medName, medTime);

                try {
                    Log.v("try", "Before execute");

                    new SubmitAddMedicationForm().execute(medData).get();
                    Log.v("try", "After execute");
                }
                catch (Exception e) {
                    Log.v("catch", e.getMessage());
                }

                finish();
            }
        });
    }



    private class SubmitAddMedicationForm extends AsyncTask<MedInfo, Void, String> {

        @Override
        protected String doInBackground(MedInfo... md) {
            final DatabaseHandler db = new DatabaseHandler(AddMeds.this);

            db.addMedication(md[0]);

            Log.v("COUNT", "" +  db.getMedicationsCount());

            List<MedInfo> list = db.getAllMedications();
            MedInfo md2 = list.get(list.size() - 1);
            Log.v("LAST ADDED NAME", md2.getName());
            Log.v("LAST ADDED TIME", md2.getTime());

            Map<String, Boolean> dm = md2.getDates();
            Log.v("dm.toString", dm.toString());
            Log.v("LAST ADDED mon", dm.get("mon").toString());
            Log.v("LAST ADDED tues", dm.get("tues").toString());
            Log.v("LAST ADDED wed", dm.get("wed").toString());
            Log.v("LAST ADDED thurs", dm.get("thurs").toString());
            Log.v("LAST ADDED fri", dm.get("fri").toString());
            Log.v("LAST ADDED sat", dm.get("sat").toString());
            Log.v("LAST ADDED sun", dm.get("sun").toString());
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



class MedInfo {
    String medTime;
    String medName;
    Map<String, Boolean> dateMap = new HashMap<String, Boolean>();

    public MedInfo(Map dateMap, String medName, String medTime){
        this.medName = medName;
        this.medTime = medTime;
        this.dateMap = dateMap;
    }
    public Map getDates(){ return this.dateMap; }
    public String getName(){
        return this.medName;
    }
    public String getTime(){
        return this.medTime;
    }
}
