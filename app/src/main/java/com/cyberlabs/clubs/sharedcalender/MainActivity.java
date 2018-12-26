package com.cyberlabs.clubs.sharedcalender;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
     AddEventFragment.AddEventFragmentlistener {
    RecyclerView recyclerView;           //Added Dependencies for Recycler View and Card View
    EventAdapter adapter;                //Event object adapter
    List<Event> eventList;              //Stores Data
    static private int yy,mm,dd,hh,mi;  //Variables to update year,month, date,hour , min
    Calendar c= Calendar.getInstance();
    String clubName=null;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button btnDate = (Button) findViewById(R.id.btnPickDate);
//        Button btnTime = (Button) findViewById(R.id.btnPickTime);
//        Button btnAddEvent = (Button) findViewById(R.id.btnAddEvent);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference();

        final DatabaseReference clubs=databaseReference.child("clubs");

        clubs.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key=dataSnapshot.getValue(String.class);


                if(key.equals(firebaseUser.getEmail()))
                {
                    clubName=dataSnapshot.getKey();

                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); //Here you will get club name, if not club user then clubName will be NULL





        eventList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int[] hamMenu = new int[]{R.string.edu,R.string.dandm,R.string.litry,R.string.aandd,R.string.add_event};
       // String[] strings=new String[]{"PICK DATE","PICK TIME","ADD EVENT"};


        //HAM Button
        BoomMenuButton bmb = findViewById(R.id.bmb);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_launcher_background)
                    .normalTextRes(hamMenu[i])
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch(index)
                            {
                                case 1:

                                    break;

                                case 4:    DialogFragment eventAdder = new AddEventFragment();
                                    eventAdder .show(getSupportFragmentManager(),"Add a Event"); break;

                            }
                        }
                    });
                  //  .subNormalTextRes(R.string.app_name);
            bmb.addBuilder(builder);
        }

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
                        "26/12/2018 10:00",
                        "SAC 112",
                        R.drawable.cyberlabs));   // to add

        eventList.add(
                new Event(
                        2,
                        "wtc",
                        "Dance Mania",
                        "Everyone",
                        "18/10/2018 18:00",
                        "SAC 112",
                        R.drawable.wtc));

        eventList.add(
                new Event(
                        1,
                        "Udaan",
                        "Touch the Sky",
                        "Everyone",
                        "01/12/2018 19:30",
                        "SAC 112",
                        R.drawable.udaan));
        Collections.sort(eventList);  //Sort EventList
        //Create adapter of the Recycler View and set Adapter
        adapter = new EventAdapter(this,eventList);
        recyclerView.setAdapter(adapter);
        //Code to delete items when swiped-------------
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        eventList.remove(viewHolder.getAdapterPosition());
                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }

                    @Override
                    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


//        btnAddEvent.setOnClickListener(new View.OnClickListener() {//Add Event Dialog Call
//            @Override
//            public void onClick(View view) {
//                DialogFragment eventAdder = new AddEventFragment();
//                eventAdder .show(getSupportFragmentManager(),"Add a Event");
//            }
//        });
//
//        btnDate.setOnClickListener(new View.OnClickListener() {//Pick Date Dialog Call
//            @Override
//            public void onClick(View view) {
//                DialogFragment datePicker = new DatePickerFragment();
//                datePicker.show(getSupportFragmentManager(),"Pick a Date");
//            }
//        });
//        btnTime.setOnClickListener(new View.OnClickListener() {//Pick Time Dialog Call
//            @Override
//            public void onClick(View view) {
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(getSupportFragmentManager(),"Pick a Time");
//            }
//        });



    }

//    @Override
//    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//        c.set(year,month,day);
//        //String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());   //Full format
//
//        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
////
//        yy= year; mm= month; dd= day;  //Storing values for postEvent Function
//    }
//
//    @Override
//    public void onTimeSet(TimePicker timePicker, int hour, int min) {
//      //  TextView textTimeView = (TextView) findViewById(R.id.timeView);
//        c.set(yy,mm,dd,hour,min);
//        String currTime =DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
//      //  textTimeView.setText(currTime);
//        hh= hour; mi=min; //Storing values for postEvent Function
//    }

    @Override
    public void postEvent(final String cname, final String ename, final String p) {

        SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "Select a Date",
                "OK",
                "Cancel"
        );
        final Date currDate = new Date();
        Calendar cal = Calendar.getInstance();
        c.setTime(currDate);
        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.set24HoursMode(true);
        dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2050, Calendar.DECEMBER, 31).getTime());
        dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 18, 15).getTime());
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {

                if(date.after(currDate)) {
                    //String cDateString =date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900)+" "+date.getHours()+":"+date.getMinutes();
                    String cDateString = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date);
                    eventList.add(                //Data addition
                            new Event(
                                    3,
                                    cname,                        //CLub Name will be available on Login
                                    ename,                        //Event Name Entry
                                    p,                            //Participants Entry
                                    cDateString,                //dd+"/"+mm+"/"+yy+ "   "+hh+":"+mli,  //Date Picked
                                    "SAC 112",  //updated from cname
                                    R.drawable.manthan));        //Image is the Club Logo which will be available on Login
                    Collections.sort(eventList);  //Sort EventList
                    adapter = new EventAdapter(getBaseContext(), eventList);   //Update Recycler View
                    recyclerView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please Select an upcoming Date",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Date is get on negative button click
            }
        });

        dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");

    }




}
