package com.example.nutracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Context context = MainActivity.this;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String uniqueID = UUID.randomUUID().toString();

        Button bookmarkBtn = findViewById(R.id.bookmarkBtn);
        Button searchBtn = findViewById(R.id.searchBtn);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String stringUUid = sh.getString("uuid", "");

        Log.d(TAG, "onCreate: " + stringUUid);

        if (stringUUid == ""){
            SharedPreferences.Editor editor = sh.edit();
            editor.putString("uuid", uniqueID);
            Log.d(TAG, "onCreate: No uid found. Creating one.");
            editor.commit();
        }

        searchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        bookmarkBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
            startActivity(intent);
        });
    }
}