package com.abhiroj.goonj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhiroj.goonj.R;
import com.abhiroj.goonj.viewholder.EventListsElementHolder;
import com.squareup.picasso.Picasso;

import static com.abhiroj.goonj.data.Constants.events;
import static com.abhiroj.goonj.data.Constants.image_placeholder;

/**
 * Created by ruthless on 20/4/17.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListsElementHolder> {

    Context context;

    public EventListAdapter(Context context)
    {
    this.context=context;
    }

    @Override
    public EventListsElementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View rootView=layoutInflater.inflate(R.layout.event_elements_list,parent,false);
        EventListsElementHolder listsElementHolder=new EventListsElementHolder(rootView);
        return listsElementHolder;
    }

    @Override
    public void onBindViewHolder(EventListsElementHolder holder, int position) {
        Picasso picasso=Picasso.with(context);
        picasso.setLoggingEnabled(true);
        picasso.load(image_placeholder).resize(800,R.dimen.event_card_height).centerInside().into(holder.event_card_image);
        holder.event_card_text.setText(events[position]);
    }

    @Override
    public int getItemCount() {
        return events.length;
    }
}