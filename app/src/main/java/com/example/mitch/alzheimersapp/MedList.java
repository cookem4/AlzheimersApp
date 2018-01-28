package com.example.mitch.alzheimersapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MedList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_list);


        List<MedInfo> meds = null;
        try {
            meds = new RetrieveMedicationsList().execute().get();
            Log.v("RAN", "RetrieveMedicationsList");
            Log.v("MEDS LIST SIZE", "" + meds.size());


        }
        catch (Exception e) {
            Log.v("ERROR", e.getMessage());
        }


        ArrayList<String> medNameList = new ArrayList<>();
        for (MedInfo med: meds) {
            medNameList.add(med.getName());
        }

        ListView lv = (ListView) findViewById(R.id.medList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.med_item,
                medNameList );

        lv.setAdapter(arrayAdapter);

    }


    private class RetrieveMedicationsList extends AsyncTask<MedInfo, Void, List<MedInfo>> {

        @Override
        protected List<MedInfo> doInBackground(MedInfo... md) {
            final DatabaseHandler db = new DatabaseHandler(MedList.this);


            Log.v("COUNT", "" +  db.getMedicationsCount());

            List<MedInfo> list = db.getAllMedications();
            MedInfo md2 = list.get(list.size() - 1);
            Log.v("LIST SIZE", "" + list.size());
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

            return list;
        }

        @Override
        protected void onPostExecute(List<MedInfo> result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
