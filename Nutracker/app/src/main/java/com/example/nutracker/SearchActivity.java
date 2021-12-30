package com.example.nutracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String TAG = "SearchActivity";

        EditText searchTerm = findViewById(R.id.foodSearchTerm);
        Button searchBtn = findViewById(R.id.searchBtn);




        searchBtn.setOnClickListener(v -> {
            if (!searchTerm.getText().toString().isEmpty()) {
                String searchString = searchTerm.getText().toString();
                Log.d(TAG, "onCreate: " + searchString);
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("search", searchString);
                startActivity(intent);
            }
        });
    }
}