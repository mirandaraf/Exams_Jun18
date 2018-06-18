package com.example.labuser.exams_jun18;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlaceAdapter extends ArrayAdapter<Place> {

    private Context mContext;

    public PlaceAdapter(@NonNull Context context,
                        int resource) {
        super(context, resource);

        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.list_item_place,
                            parent,
                            false);
        }


        // Get title element
        TextView formatted_addressTextView =
                (TextView) convertView.findViewById(R.id.list_item_place_formatted_address);
        TextView idTextView =
                (TextView) convertView.findViewById(R.id.list_item_place_id);
        TextView nameTextView =
                (TextView) convertView.findViewById(R.id.list_item_place_name);
        TextView opening_hoursTextView =
                (TextView) convertView.findViewById(R.id.list_item_place_opening_hours);
        TextView ratingTextView =
                (TextView) convertView.findViewById(R.id.list_item_place_rating);

        // Get thumbnail element

        Place place = (Place) getItem(position);

        formatted_addressTextView.setText(place.getFormatted_address());
        idTextView.setText(place.getId());
        nameTextView.setText(place.getName());
        opening_hoursTextView.setText(place.getOpening_hours());
        ratingTextView.setText(place.getRating());


        return convertView;
    }
}
