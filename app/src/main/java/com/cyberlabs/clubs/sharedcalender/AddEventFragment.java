package com.cyberlabs.clubs.sharedcalender;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


//Class that opens Add Event Fragment
public class AddEventFragment extends DialogFragment {
    private EditText edit_venue, edit_eventName, edit_particpants;
    private AddEventFragmentlistener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.event_add_layout,null);
        builder.setView(view).setTitle("Add Event").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String venue = edit_venue.getText().toString();
                String eventName = edit_eventName .getText().toString();
                String participants = edit_particpants.getText().toString();
                listener.postEvent(venue,eventName,participants);      //Pass entered details as arguments

            }
        });
        edit_venue = (EditText) view.findViewById(R.id.editTextVenue);
        edit_eventName = (EditText) view.findViewById(R.id.editTextEventName);
        edit_particpants = (EditText) view.findViewById(R.id.editTextParticipators);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {  //Listener getts the context
        super.onAttach(context);
        try {
            listener = (AddEventFragmentlistener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Details Entered Listener
    public interface AddEventFragmentlistener{
        void postEvent(String venue, String ename, String p);  //cname is to be replaced by venue
    }
}
