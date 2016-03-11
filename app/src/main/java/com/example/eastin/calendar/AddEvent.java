package com.example.eastin.calendar;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEvent extends AppCompatActivity {

    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Bundle extras = getIntent().getExtras();
        year = extras.getInt("year");
        month = extras.getInt("month");
        day = extras.getInt("day");
        TextView title = (TextView) findViewById(R.id.textView3);
        title.setText("ON " + month + "/" + day + "/" + year);

    }
    public void gotoMain(View view){
        EditText time = (EditText) findViewById(R.id.editText);
        EditText name = (EditText) findViewById(R.id.editText2);
        String eventName = name.getText().toString();
        String eventTime = time.getText().toString();
        Toast.makeText(getApplicationContext(), eventTime, Toast.LENGTH_SHORT).show();
        String str = Integer.toString(month) + Integer.toString(day) + Integer.toString(year) + eventTime;
        SimpleDateFormat df = new SimpleDateFormat("mmddyyyykk:mm");
        Date date;
        long epoch=0;
        try {
            date = df.parse(str);
            epoch = date.getTime();
        }catch(ParseException pe){
            System.exit(0);
        }


        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, epoch);
        values.put(CalendarContract.Events.DTEND, epoch+3600000);
        values.put(CalendarContract.Events.TITLE, eventName);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");

        String permission = "android.permission.WRITE_CALENDAR";
        int res = checkCallingOrSelfPermission(permission);
        if (res == PackageManager.PERMISSION_GRANTED) {
            cr.insert(CalendarContract.Events.CONTENT_URI, values);
        }
        startActivity(new Intent(this, MainActivity.class));
    }
}
