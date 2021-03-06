package com.abhiroj.goonj.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhiroj.goonj.R;

/**
 * Created by ruthless on 20/4/17.
 */

public class EventListsElementHolder extends RecyclerView.ViewHolder {

    public TextView event_fill_textview;
    public TextView  event_card_text;
    public CardView  event_card_view;

    public EventListsElementHolder(View itemView) {
        super(itemView);
        event_fill_textview=(TextView) itemView.findViewById(R.id.event_fill_textview);
        event_card_text=(TextView) itemView.findViewById(R.id.event_card_text);
        event_card_view=(CardView) itemView.findViewById(R.id.event_card_view);
    }
}
