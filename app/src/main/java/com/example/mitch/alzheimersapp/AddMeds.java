package com.example.mitch.alzheimersapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class AddMeds extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meds);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
}
class MedInfo{
    String medTime;
    String medName;
    Map<String, Boolean> dateMap = new HashMap<String, Boolean>();

    public MedInfo(Map dateMap, String medName, String medTime){
        this.medName = medName;
        this.medTime = medTime;
        this.dateMap = dateMap;
    }
    public Map getDates(){
        return this.dateMap;
    }
    public String getMedName(){
        return this.medName;
    }
    public String getMedTime(){
        return this.medTime;
    }
}
