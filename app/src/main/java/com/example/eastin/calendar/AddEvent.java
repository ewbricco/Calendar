package com.example.eastin.calendar;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Permission;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEvent extends AppCompatActivity {

    int year;
    int month;
    int day;
    DB db;
    private static final String DEBUG_TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);


        Bundle extras = getIntent().getExtras();
        year = extras.getInt("year");
        month = extras.getInt("month");
        day = extras.getInt("day");
        db = (DB) getIntent().getSerializableExtra("data");
        if( getIntent().getSerializableExtra("data") == null){
            Log.d("app", "nullllllllllllllll");
        }
        Log.d("app", Integer.toString(db.numEvents()));
        TextView title = (TextView) findViewById(R.id.textView3);
        int monthDis = month+1;
        title.setText("ON " + monthDis + "/" + day + "/" + year);

    }

    //@TargetApi(23)
    public void gotoMain(View view) throws ParseException {
        EditText time = (EditText) findViewById(R.id.editText);
        EditText name = (EditText) findViewById(R.id.editText2);
        String eventName = name.getText().toString();
        String eventTime = time.getText().toString();
        Toast.makeText(getApplicationContext(), "Event Created", Toast.LENGTH_SHORT).show();
        String str = Integer.toString(month) + Integer.toString(day) + Integer.toString(year) + eventTime;
        SimpleDateFormat df = new SimpleDateFormat("MMddyyyykk:mm");
        Date date = df.parse(str);
        db.addEvent(eventName, date.getTime());
        if(db == null){
            Log.d("ERRRRRRRRORRRR", "null db");
        }
        String toDis = Integer.toString(db.numEvents());
                Toast.makeText(getApplicationContext(), toDis, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("data", db);
        startActivity(intent);


        /*String str = Integer.toString(month) + Integer.toString(day) + Integer.toString(year) + eventTime;
        SimpleDateFormat df = new SimpleDateFormat("mmddyyyykk:mm");
        try {
            Date date = df.parse(str);
            long epoch = date.getTime();
            ContentResolver cr = getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, epoch);
            values.put(CalendarContract.Events.DTEND, epoch+3600000);
            values.put(CalendarContract.Events.TITLE, eventName);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");


            String permission = "android.permission.WRITE_CALENDAR";
            String[] p = {permission};

            ActivityCompat.requestPermissions(this,
                    p, Permission.class.);
            //int res = checkCallingOrSelfPermission(permission);
            //int res = checkSelfPermission(permission);
            String permission = "android.permission.WRITE_CALENDAR";
            int res = checkSelfPermission(permission);
            if (res == PackageManager.PERMISSION_GRANTED) {
                Log.i(DEBUG_TAG, "Permission granted");
                cr.insert(CalendarContract.Events.CONTENT_URI, values);
            }
        }catch(ParseException pe){
            System.exit(0);
        }
        */

    }
}
