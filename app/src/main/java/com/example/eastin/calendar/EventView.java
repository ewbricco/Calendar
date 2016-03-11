package com.example.eastin.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);


        Bundle extras = getIntent().getExtras();
        int year = extras.getInt("year");
        int month = extras.getInt("month");
        int day = extras.getInt("day");
        TextView title = (TextView) findViewById(R.id.textView7);
        title.setText("EVENTS ON " + month + "/" + day + "/" + year);
    }

    public void gotoMain(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
}
