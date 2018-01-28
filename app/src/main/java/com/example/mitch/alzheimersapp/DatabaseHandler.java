package com.example.mitch.alzheimersapp;

/**
 * Created by tarunkhanna on 27/01/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dbManager";

    // Contacts table name
    private static final String TABLE_CAREGIVER_CONTACTS = "careGiverContacts";
    private static final String TABLE_MEDICATIONS = "medications";

    // Care Givers Table Columns names
    private static final String KEY_CG_ID = "id";
    private static final String KEY_CG_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_MESSAGE = "message";


    // Medications Table Columns names
    private static final String KEY_M_ID = "id";
    private static final String KEY_M_NAME = "name";
    private static final String KEY_TIME = "time";
    private static final String KEY_MON = "MONDAY";
    private static final String KEY_TUES = "TUESDAY";
    private static final String KEY_WED = "WEDNESDAY";
    private static final String KEY_THURS = "THURSDAY";
    private static final String KEY_FRI = "FRIDAY";
    private static final String KEY_SAT = "SATURDAY";
    private static final String KEY_SUN = "SUNDAY";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CAREGIVERS_TABLE = "CREATE TABLE " + TABLE_CAREGIVER_CONTACTS + "("
                + KEY_CG_ID + " INTEGER PRIMARY KEY," + KEY_CG_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_MESSAGE + " TEXT" + ")";
        String CREATE_MEDICATIONS_TABLE = "CREATE TABLE " + TABLE_MEDICATIONS + "("
                + KEY_M_ID + " INTEGER PRIMARY KEY," + KEY_M_NAME + " TEXT,"
                + KEY_TIME + " TEXT," + KEY_MON + " TEXT," + KEY_TUES + " TEXT,"
                + KEY_WED + " TEXT," + KEY_THURS + " TEXT," + KEY_FRI + " TEXT,"
                + KEY_SAT + " TEXT," + KEY_SUN + " TEXT" + ")";
        db.execSQL(CREATE_CAREGIVERS_TABLE);
        db.execSQL(CREATE_MEDICATIONS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAREGIVER_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICATIONS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations for Care Givers
     */

    // Adding new contact
    void addCareGiver(ContactCareGiver careGiver) {
        Log.v("DATABASE HANDLER", "STARTED addCareGiver");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CG_NAME, careGiver.getName()); // CareGiver Name
        values.put(KEY_PH_NO, careGiver.getPhoneNumber()); // CareGiver Phone
        values.put(KEY_MESSAGE, careGiver.getMessage()); // CareGiver Phone

        // Inserting Row
        db.insert(TABLE_CAREGIVER_CONTACTS, null, values);
        db.close(); // Closing database connection

        Log.v("DATABASE HANDLER", "COMPLETED addCareGiver");

    }

    // Getting single contact
    ContactCareGiver getCareGiver(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CAREGIVER_CONTACTS, new String[] {KEY_CG_ID,
                        KEY_CG_NAME, KEY_PH_NO, KEY_MESSAGE }, KEY_CG_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ContactCareGiver careGiver = new ContactCareGiver(cursor.getString(1),
                cursor.getString(2), cursor.getString(3));
        return careGiver;
    }

    // Getting All Contacts
    public List<ContactCareGiver> getAllCareGivers() {
        List<ContactCareGiver> careGiversList = new ArrayList<ContactCareGiver>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CAREGIVER_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactCareGiver careGiver = new ContactCareGiver(cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                // Adding contact to list
                careGiversList.add(careGiver);
            } while (cursor.moveToNext());
        }

        // return contact list
        return careGiversList;
    }


    // TODO: Add Update, Delete Functionality
//    // Updating single contact
//    public int updateContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName());
//        values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//        // updating row
//        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }
//
//    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }


    // Getting contacts Count
    public int getCareGiversCount() {
        int count = -1;
        String countQuery = "SELECT  * FROM " + TABLE_CAREGIVER_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        return count;
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations for Medications
     */

    // Adding new contact
    void addMedication(MedInfo medInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_M_NAME, medInfo.getName()); // Medication Name
        values.put(KEY_TIME, medInfo.getTime()); // Medication Time

        Map<String, Boolean> dateMap = medInfo.getDates();
        values.put(KEY_MON, dateMap.get("mon").toString());
        values.put(KEY_TUES, dateMap.get("tues").toString());
        values.put(KEY_WED, dateMap.get("wed").toString());
        values.put(KEY_THURS, dateMap.get("thurs").toString());
        values.put(KEY_FRI, dateMap.get("fri").toString());
        values.put(KEY_SAT, dateMap.get("sat").toString());
        values.put(KEY_SUN, dateMap.get("sun").toString());

        // Inserting Row
        db.insert(TABLE_MEDICATIONS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    MedInfo getMedication(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEDICATIONS, new String[] {KEY_M_ID,
                        KEY_M_NAME, KEY_TIME, KEY_MON, KEY_TUES, KEY_WED, KEY_THURS, KEY_FRI, KEY_SAT, KEY_SUN }, KEY_M_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Map<String, Boolean> dateMap = new HashMap<String, Boolean>();
        dateMap.put(KEY_MON, dateMap.get(cursor.getString(3)));
        dateMap.put(KEY_TUES, dateMap.get(cursor.getString(4)));
        dateMap.put(KEY_WED, dateMap.get(cursor.getString(5)));
        dateMap.put(KEY_THURS, dateMap.get(cursor.getString(6)));
        dateMap.put(KEY_FRI, dateMap.get(cursor.getString(7)));
        dateMap.put(KEY_SAT, dateMap.get(cursor.getString(8)));
        dateMap.put(KEY_SUN, dateMap.get(cursor.getString(9)));

        MedInfo medInfo = new MedInfo(dateMap, cursor.getString(1), cursor.getString(2));
        return medInfo;
    }

    // Getting All Contacts
    public List<MedInfo> getAllMedications() {
        List<MedInfo> medicationsist = new ArrayList<MedInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEDICATIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Map<String, Boolean> dateMap = new HashMap<String, Boolean>();
                dateMap.put("mon", cursor.getString(3).equalsIgnoreCase("true"));
                dateMap.put("tues", cursor.getString(4).equalsIgnoreCase("true"));
                dateMap.put("wed", cursor.getString(5).equalsIgnoreCase("true"));
                dateMap.put("thurs", cursor.getString(6).equalsIgnoreCase("true"));
                dateMap.put("fri", cursor.getString(7).equalsIgnoreCase("true"));
                dateMap.put("sat", cursor.getString(8).equalsIgnoreCase("true"));
                dateMap.put("sun", cursor.getString(9).equalsIgnoreCase("true"));

                MedInfo medInfo = new MedInfo(dateMap, cursor.getString(1), cursor.getString(2));

                medicationsist.add(medInfo);
            } while (cursor.moveToNext());
        }

        // return contact list
        return medicationsist;
    }


        // TODO: Add Update, Delete Functionality
//    // Updating single contact
//    public int updateContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName());
//        values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//        // updating row
//        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }
//
//    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }


    // Getting contacts Count
    public int getMedicationsCount() {
        int count = -1;
        String countQuery = "SELECT  * FROM " + TABLE_MEDICATIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        return count;
    }

}
