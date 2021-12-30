package com.example.nutracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SearchListAdapter extends ArrayAdapter<Food> {
    int itemResource;
    Context itemContext;
    public SearchListAdapter(@NonNull Context context, int resource, @NonNull List<Food> objects) {
        super(context, resource, objects);
        itemContext = context;
        itemResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String foodName = getItem(position).getName();
        int volume = getItem(position).getVolume();
        int calories = getItem(position).getCalories();


        LayoutInflater inflater = LayoutInflater.from(itemContext);
        convertView = inflater.inflate(itemResource, parent, false);

        TextView foodNameTv = (TextView) convertView.findViewById(R.id.foodName);
        TextView caloriesTv = (TextView) convertView.findViewById(R.id.calValue);
        TextView volumeTv = (TextView) convertView.findViewById(R.id.volValue);

//        int customVol = Integer.parseInt(customVolTv.getText().toString());

        foodNameTv.setText(foodName);
        caloriesTv.setText(String.valueOf(calories));
        volumeTv.setText(String.valueOf(volume));

        return convertView;
    }
}
