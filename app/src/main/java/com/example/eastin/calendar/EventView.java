package com.example.eastin.calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.util.provider.Calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventView extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    /*private GoogleApiClient client;



    private static final String DEBUG_TAG = "MyActivity";
    public static final String[] INSTANCE_PROJECTION = new String[]{
            CalendarContract.Instances.EVENT_ID,      // 0
            CalendarContract.Instances.BEGIN,         // 1
            CalendarContract.Instances.TITLE          // 2
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_BEGIN_INDEX = 1;
    private static final int PROJECTION_TITLE_INDEX = 2;*/

    DB db;
    int year;
    int month;
    int day;
    int num;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);


        Bundle extras = getIntent().getExtras();
        year = extras.getInt("year");
        month = extras.getInt("month");
        day = extras.getInt("day");
        db = (DB) getIntent().getSerializableExtra("data");
        TextView title = (TextView) findViewById(R.id.textView7);
        int monthDis = month+1;
        title.setText("EVENTS ON " + monthDis + "/" + day + "/" + year);


        Toast.makeText(getApplicationContext(), Integer.toString(db.numEvents()), Toast.LENGTH_SHORT).show();
        String str = Integer.toString(month) + Integer.toString(day) + Integer.toString(year);
        SimpleDateFormat df = new SimpleDateFormat("MMddyyyy");
        try {
            date = df.parse(str);
            Log.d("Gooood","parse");
            //String toDis = db.getEvents(date.getTime()).get(0).getName();
            //Log.d("GOOOOO", toDis);
            //Toast.makeText(getApplicationContext(), toDis, Toast.LENGTH_SHORT).show();

            RelativeLayout l=(RelativeLayout) findViewById(R.id.rl);

            ArrayList<RelativeLayout.LayoutParams> params = new ArrayList<RelativeLayout.LayoutParams>();
            ArrayList<RelativeLayout.LayoutParams> delParams = new ArrayList<RelativeLayout.LayoutParams>();
            num = db.getEvents(date.getTime()).size();
            for(CalEvent e: db.getEvents(date.getTime())){
                params.add(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                delParams.add(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            }

            for(int i = 0; i < db.getEvents(date.getTime()).size(); i++) {
                if(i==0){
                    TextView tv = new TextView(this);
                    tv.setText(db.getEvents(date.getTime()).get(i).getName());
                    tv.setTextSize(25);
                    tv.setId(i + 100);
                    params.get(i).addRule(RelativeLayout.BELOW, R.id.textView7);
                    params.get(i).setMargins(0, 100, 0, 0);
                    l.addView(tv, params.get(i));

                    Button del = new Button(this);
                    del.setText("delete");
                    del.setId(i + 200);
                    delParams.get(i).setMargins(250, 86, 0, 0);
                    delParams.get(i).addRule(RelativeLayout.RIGHT_OF, i + 100);
                    delParams.get(i).addRule(RelativeLayout.BELOW, R.id.textView7);
                    l.addView(del, delParams.get(i));
                    del.setOnClickListener(delEvent(del));
                }
                else {
                    TextView tv = new TextView(this);
                    tv.setText(db.getEvents(date.getTime()).get(i).getName());
                    tv.setTextSize(25);
                    tv.setId(i + 100);
                    params.get(i).addRule(RelativeLayout.BELOW, i + 99);
                    params.get(i).setMargins(0, 70, 0, 0);
                    l.addView(tv, params.get(i));

                    Button del = new Button(this);
                    del.setText("delete");
                    del.setId(i + 200);
                    delParams.get(i).setMargins(250, 63, 0, 0);
                    delParams.get(i).addRule(RelativeLayout.RIGHT_OF, i + 100);
                    delParams.get(i).addRule(RelativeLayout.BELOW, i + 199);
                    l.addView(del, delParams.get(i));
                    del.setOnClickListener(delEvent(del));
                }

                /*findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(getBaseContext(), "Deleted Event",
                                Toast.LENGTH_SHORT).show();
                        for (int j = 0; j < num; j++) {
                            TextView tv = (TextView) findViewById(j + 100);
                            tv.setText("DELETED");
                        }
                    }
                });*/

            }
        } catch (Exception e) {
            Log.d("Problem: " , "error with data or parse");
            //System.exit(0);
        }




    }

    View.OnClickListener delEvent(final Button button)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Deleted Event",
                        Toast.LENGTH_SHORT).show();
                TextView tv = (TextView) findViewById(button.getId() - 100);
                tv.setText("DELETED");
                db.deleteEvent(db.getEvents(date.getTime()).get(button.getId()-200).getName(), db.getEvents(date.getTime()).get(button.getId()-200).getTime() );
            }
        };
    }
// Specify the date range you want to search for recurring
// event instances
       /* Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month, day, 0, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day, 23, 59);
        long endMillis = endTime.getTimeInMillis();

        Cursor cur = null;
        ContentResolver cr = getContentResolver();

// The ID of the recurring event whose instances you are searching
// for in the Instances table
        String selection = CalendarContract.Instances.EVENT_ID + " = ?";
        String[] selectionArgs = new String[]{"207"};

// Construct the query with the desired date range.
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

// Submit the query
        String permission = "android.permission.READ_CALENDAR";
        String[] p = {permission};
        ActivityCompat.requestPermissions(this,
                p, 201);
        int res = checkCallingOrSelfPermission(permission);
        if (res == PackageManager.PERMISSION_GRANTED) {
            Log.i(DEBUG_TAG, "permission granted!!");
            cur = cr.query(builder.build(),
                    INSTANCE_PROJECTION,
                    selection,
                    selectionArgs,
                    null);
        }

        while (cur.moveToNext()) {
            String eventTitle = null;
            long eventID = 0;
            long beginVal = 0;

            // Get the field values
            eventID = cur.getLong(PROJECTION_ID_INDEX);
            beginVal = cur.getLong(PROJECTION_BEGIN_INDEX);
            eventTitle = cur.getString(PROJECTION_TITLE_INDEX);

            // Do something with the values.
            Log.i(DEBUG_TAG, "Event:  " + eventTitle);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(beginVal);
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Log.i(DEBUG_TAG, "Date: " + formatter.format(calendar.getTime()));
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

        /*String[] returnColumns = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.ACCOUNT_TYPE
        };

        Cursor cursor = null;
        ContentResolver cr = getContentResolver();


        String permission = "android.permission.READ_CALENDAR";
        int res = checkCallingOrSelfPermission(permission);
        if (res == PackageManager.PERMISSION_GRANTED) {
            cursor = cr.query(CalendarContract.Calendars.CONTENT_URI, returnColumns, null, null, null);
        }

        Uri calendars = Uri.parse("content://com.android.calendar/calendars");

        long calID = 0;
        String displayName = null;
        String accountName = null;
        String accountType = null;

        while(cursor.moveToNext()){
            calID = cursor.getLong(0);
            displayName = cursor.getString(1);
            accountName = cursor.getString(2);
            accountType = cursor.getString(3);
        }
        cursor.close();
        //CalendarProvider calendarProvider = new CalendarProvider(context);
        //List<ContactsContract.CommonDataKinds.Event> calEvents= calendarProvider.getEvents(calID).getList();
*/


//}

    public void gotoMain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("data", db);
        startActivity(intent);
        finish();
    }
/*
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "EventView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.eastin.calendar/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "EventView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.eastin.calendar/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/
}
