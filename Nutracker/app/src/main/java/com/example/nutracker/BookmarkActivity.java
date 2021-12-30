package com.example.nutracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {
    Context context = BookmarkActivity.this;
    private static final String TAG = "BookmarkActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        ListView savedListView = findViewById(R.id.savedList);
        TextView errMessage = findViewById(R.id.errorMessage);

        ArrayList<Food> savedItems = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String stringUUid = sh.getString("uuid", "");

        DatabaseReference myRef = database.getReference("users").child(stringUUid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    errMessage.setVisibility(View.GONE);
                    savedListView.setVisibility(View.VISIBLE);
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String name = data.getKey();
                        String unit = data.child("unit").getValue(String.class);
                        int cal = data.child("cal").getValue(Integer.class);
                        int carbs = data.child("carbs").getValue(Integer.class);
                        int fat = data.child("fat").getValue(Integer.class);
                        int protein = data.child("protein").getValue(Integer.class);
                        int sugar = data.child("sugar").getValue(Integer.class);
                        int vol = data.child("vol").getValue(Integer.class);

                        Food f = new Food(name, unit, cal, protein, fat, carbs, sugar, vol);
                        savedItems.add(f);

                    }
                    SearchListAdapter adapter = new SearchListAdapter(context, R.layout.search_item_layout, savedItems);
                    savedListView.setAdapter(adapter);

                    savedListView.setOnItemClickListener((parent, view, position, id) -> {
                        Intent intent = new Intent(BookmarkActivity.this, SingleFoodActivity.class);
                        intent.putExtra("activity", "BookmarkActivity");
                        intent.putExtra("name", savedItems.get(position).getName());
                        intent.putExtra("unit", savedItems.get(position).getUnit());
                        intent.putExtra("cal", savedItems.get(position).getCalories());
                        intent.putExtra("protein", savedItems.get(position).getProtein());
                        intent.putExtra("fat", savedItems.get(position).getFat());
                        intent.putExtra("carbs", savedItems.get(position).getCarbs());
                        intent.putExtra("sugar", savedItems.get(position).getSugar());
                        intent.putExtra("vol", savedItems.get(position).getVolume());

                        startActivity(intent);
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
}