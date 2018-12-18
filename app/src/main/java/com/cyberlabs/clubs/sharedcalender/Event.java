package com.cyberlabs.clubs.sharedcalender;

public class Event {
    private int id;
    private String clubName;           //club name
    private String eventName;       //Event Name
    private String participators;
    private String dateAndTime;            //date and time
    private int image;

    public Event(int id, String clubName, String eventName, String participators, String dateAndTime, int image) {
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
}
