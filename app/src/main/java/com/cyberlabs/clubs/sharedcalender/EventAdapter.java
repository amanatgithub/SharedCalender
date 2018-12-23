package com.cyberlabs.clubs.sharedcalender;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.eventViewHolder> {

    private Context mContext;
    private List<Event> eventList;

    public EventAdapter(Context mContext, List<Event> eventList) {
        this.mContext = mContext;
        this.eventList = eventList;
    }


    @NonNull
    @Override
    public eventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_layout,null);
        eventViewHolder holder = new eventViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull eventViewHolder holder, int position) {
        final Event event = eventList.get(position);
        holder.textViewClubName.setText(event.getClubName());
        holder.textDesc.setText(event.getEventName());
        holder.textVenue.setText(event.getVenue());
        holder.textDateAndTime.setText(String.valueOf( event.getDateAndTime()));
        holder.textParticipators.setText("For "+String.valueOf( event.getParticipators()));
        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(event.getImage()));
        holder.list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Toast.makeText(mContext,event.getTitle(),Toast.LENGTH_SHORT).show();
              /*  Intent intent = new Intent(mContext,GalleryActivity.class); // Intent takes to new activity
                intent.putExtra("image_draw",String.valueOf( event.getImage()));
                intent.putExtra("event_title",event.getTitle());
                intent.putExtra("event_price","INR "+String.valueOf( event.getDateAndTime())); //added
                mContext.startActivity(intent);*/

            }
        });



    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class eventViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewClubName, textDesc, textParticipators, textDateAndTime, textVenue;
        LinearLayout list_layout;


        public eventViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewClubName =itemView.findViewById(R.id.textViewClubName);
            textDesc =itemView.findViewById(R.id.textViewEventName);
            textParticipators =itemView.findViewById(R.id.textViewParticipators);
            textDateAndTime =itemView.findViewById(R.id.textViewDateAndTime);
            textVenue=itemView.findViewById(R.id.textViewVenue);
            list_layout = itemView.findViewById(R.id.list_layout);
        }
    }
}

