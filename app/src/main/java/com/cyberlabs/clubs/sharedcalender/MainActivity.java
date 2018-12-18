package com.cyberlabs.clubs.sharedcalender;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, AddEventFragment.AddEventFragmentlistener{
    RecyclerView recyclerView;           //Added Dependencies for Recycler View and Card View
    EventAdapter adapter;                //Event object adapter
    List<Event> eventList;              //Stores Data
    static private int yy,mm,dd,hh,mi;  //Variables to update year,month, date,hour , min
    Calendar c= Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDate = (Button) findViewById(R.id.btnPickDate);
        Button btnTime = (Button) findViewById(R.id.btnPickTime);
        Button btnAddEvent = (Button) findViewById(R.id.btnAddEvent);
        eventList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initiaise to current Date Values
        yy=c.get(Calendar.YEAR); mm=c.get(Calendar.MONTH); dd=c.get(Calendar.DAY_OF_MONTH);
        hh=c.get(Calendar.HOUR_OF_DAY);  mi=c.get(Calendar.MINUTE);
        //Data added to List for Recycler View
        eventList.add(
                new Event(
                        1,
                        "CYBERLABS", //to add
                        "Android WorkShop",
                        "Club Members",
                        "16/12/2018 10 am",
                        R.drawable.macbook));   // to add

        eventList.add(
                new Event(
                        1,
                        "wtc",
                        "Dance Mania",
                        "Everyone",
                        "16/10/2018 6pm",
                        R.drawable.dellinspiron));

        eventList.add(
                new Event(
                        1,
                        "Udaan",
                        "Chai pe Kahrcha",
                        "Everyone",
                        "16/12/2018 7pm",
                        R.drawable.surface));
        //Create adapter of the Recycler View and set Adapter
        adapter = new EventAdapter(this,eventList);
        recyclerView.setAdapter(adapter);


        btnAddEvent.setOnClickListener(new View.OnClickListener() {//Add Event Dialog Call
            @Override
            public void onClick(View view) {
                DialogFragment eventAdder = new AddEventFragment();
                eventAdder .show(getSupportFragmentManager(),"Add a Event");
            }
        });
        
        btnDate.setOnClickListener(new View.OnClickListener() {//Pick Date Dialog Call
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"Pick a Date");
            }
        });
        btnTime.setOnClickListener(new View.OnClickListener() {//Pick Time Dialog Call
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"Pick a Time");
            }
        });



    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        c.set(year,month,day);
        //String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());   //Full format

        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        TextView textDateView = (TextView) findViewById(R.id.dateView);
        textDateView.setText(currentDateString);
        yy= year; mm= month; dd= day;  //Storing values for postEvent Function
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        TextView textTimeView = (TextView) findViewById(R.id.timeView);
        c.set(yy,mm,dd,hour,min);
        String currTime =DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        textTimeView.setText(currTime);
        hh= hour; mi=min; //Storing values for postEvent Function
    }

    @Override
    public void postEvent(String cname, String ename, String p) {
        c.set(yy,mm,dd,hh,mi);
        String cDateString = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT).format(c.getTime());
        eventList.add(                //Data addition
                new Event(
                        1,
                        cname,                        //CLub Name will be available on Login
                        ename,                        //Event Name Entry
                        p,                            //Participants Entry
                        cDateString ,                //dd+"/"+mm+"/"+yy+ "   "+hh+":"+mli,  //Date Picked
                        R.drawable.macbook));        //Image is the Club Logo which will be available on Login
        adapter = new EventAdapter(this,eventList);   //Update Recycler View
        recyclerView.setAdapter(adapter);

    }
}
