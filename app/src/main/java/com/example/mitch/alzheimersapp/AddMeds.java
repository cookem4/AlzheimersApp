package com.example.mitch.alzheimersapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class AddMeds extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.v("AddMeds", "Created");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meds);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        addSubmitButtonBehaviour();

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

    }

    private void addSubmitButtonBehaviour() {
        Button addmedicationBtn = (Button) findViewById(R.id.addmedication);

        addmedicationBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.v("CLICKED", "Add Medication");
                finish();
            }
        });
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
