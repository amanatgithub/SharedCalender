package com.cyberlabs.clubs.sharedcalender;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event implements Comparable<Event>{
    private int id;
    private String clubName;           //club name
    private String eventName;       //Event Name
    private String participators;
    private String dateAndTime;            //date and time
    private int image;

    public Event(int id, String clubName, String eventName, String participators, String dateAndTime, int image){
        this.id = id;
        this.clubName = clubName;
        this.eventName = eventName;
        this.participators = participators;
        this.dateAndTime = dateAndTime;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getClubName() {
        return clubName;
    }

    public String getEventName() {
        return eventName;
    }

    public String getParticipators() {
        return participators;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public int getImage() {
        return image;
    }


    @Override
    public int compareTo(@NonNull Event u) {
        String arr1[] = getDateAndTime().split(" ", 2);
        String arr2[] = u.getDateAndTime().split(" ", 2);
        //Get 1st word of string
        String sDate1 = arr1[0];
        String sDate2 = arr2[0];
        //Convert string date to date
        Date date1= null;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2= null;
        try {
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1.compareTo(date2)>0) {
            return 0;
        }

        return getDateAndTime().compareTo(u.getDateAndTime());
    }
}
