package com.example.eastin.calendar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Eastin on 3/11/2016.
 */
public class DB implements Serializable {
    private ArrayList<String> names;
    private ArrayList<Long> times;

    DB(){
        names = new ArrayList<String>();
        times = new ArrayList<Long>();
    }

    public void addEvent(String name, Long time){
        names.add(name);
        times.add(time);
    }

    public void deleteEvent(String name, Long time){
        names.remove(names.indexOf(name));
        times.remove(times.indexOf(time));
    }

    public ArrayList<CalEvent> getEvents(Long start){
        ArrayList<String> onDay = new ArrayList<String>();
        ArrayList<Long> tOnDay = new ArrayList<Long>();
        for(int i=0; i<times.size(); i++){
            if(times.get(i) <= (start + (3600000*24)) && times.get(i) >= start){
                onDay.add(names.get(i));
                tOnDay.add(times.get(i));
            }
        }
        ArrayList<CalEvent> toReturn = new ArrayList<CalEvent>();
        for(int j=0 ; j<onDay.size(); j++){
            toReturn.add(new CalEvent(onDay.get(j), tOnDay.get(j)));
        }
        return toReturn;
    }
    public int numEvents(){
        return names.size();
    }
}
