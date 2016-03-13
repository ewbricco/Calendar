package com.example.eastin.calendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eastin on 3/11/2016.
 */
public class CalEvent {
    private String name;
    private Long time;

    CalEvent(String n, Long t){
        name = n;
        time = t;
    }

    public String getName(){
        return name;
    }

    public Long getTime(){
        return time;
    }
}
