package com.example.nutracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SingleFoodActivity extends AppCompatActivity {

    String TAG = "SingleFoodActivity";
    Context context = SingleFoodActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);

        TextView nameTv = findViewById(R.id.name);
        TextView caloriesTv = findViewById(R.id.calories);
        EditText volumeTv = findViewById(R.id.volume);
        TextView proteinTv = findViewById(R.id.protein);
        TextView fatTv = findViewById(R.id.fat);
        TextView carbsTv = findViewById(R.id.carbs);
        TextView unitTv = findViewById(R.id.unit);
        TextView sugarTv = findViewById(R.id.sugar);
        Button saveFoodBtn = findViewById(R.id.saveBtn);

        Intent intent = getIntent();
        if (intent.hasExtra("name") && intent.hasExtra("cal")
                && intent.hasExtra("protein") && intent.hasExtra("fat")
                && intent.hasExtra("sugar") && intent.hasExtra("carbs")
                && intent.hasExtra("vol") && intent.hasExtra("unit")) {

            String activity = intent.getStringExtra("activity");
            String name = intent.getStringExtra("name");
            String unit = intent.getStringExtra("unit");
            int protein = intent.getIntExtra("protein", 0);
            int fat = intent.getIntExtra("fat", 0);
            int sugar = intent.getIntExtra("sugar", 0);
            int cal = intent.getIntExtra("cal", 0);
            int carbs = intent.getIntExtra("carbs", 0);
            int vol = intent.getIntExtra("vol", 1);

            float calMultiplier = (float) cal/vol;
            Log.d(TAG, "onCreate: multiple" + cal + " / " + vol);
            float fatMultiplier = (float) fat/vol;
            float sugarMultiplier = (float) sugar/vol;
            float proteinMultiplier = (float) protein/vol;
            float carbsMultiplier = (float) carbs/vol;

            Log.d(TAG, "onCreate: " + calMultiplier + " " + fatMultiplier + " " + sugarMultiplier + " " + proteinMultiplier + " " + carbsMultiplier);

            if (activity.equals("BookmarkActivity")){
                Log.d(TAG, "onCreate: Coming from bookmark");
                saveFoodBtn.setVisibility(View.GONE);
            }else{
                Log.d(TAG, "onCreate: coming from Search result");
            }

            nameTv.setText(name);
            unitTv.setText(unit);
            caloriesTv.setText("Calories: " + String.valueOf(cal) + "cal");
            volumeTv.setText(String.valueOf(vol));
            proteinTv.setText("Protein: " + String.valueOf(protein) + "g");
            fatTv.setText("Fat: " + String.valueOf(fat) + "g");
            carbsTv.setText("Carbohydrate: " + String.valueOf(carbs) + "g");
            sugarTv.setText("Sugar: " + String.valueOf(sugar) + "g");

            Pie pie = AnyChart.pie();

            ArrayList<DataEntry> data = new ArrayList<>();
            data.add(new ValueDataEntry("Protein", protein));
            data.add(new ValueDataEntry("Fat", fat));
            data.add(new ValueDataEntry("Carbohydrate", carbs));

            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
            String stringUUid = sh.getString("uuid", "");
            Log.d(TAG, "onCreate: uuid in single activity" + stringUUid);

            pie.data(data);
            AnyChartView anyChartView = findViewById(R.id.nutritionChart);
            anyChartView.setChart(pie);

            saveFoodBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users").child(stringUUid).child(name);

                    myRef.child("cal").setValue(cal);
                    myRef.child("protein").setValue(protein);
                    myRef.child("fat").setValue(fat);
                    myRef.child("carbs").setValue(carbs);
                    myRef.child("vol").setValue(vol);
                    myRef.child("sugar").setValue(sugar);
                    myRef.child("unit").setValue(unit);

                    saveFoodBtn.setEnabled(false);
                    saveFoodBtn.setText("Saved");
                }

            });

            volumeTv.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() != 0){

                        int multiplier = Integer.parseInt(s.toString());

                        float changeCal = calMultiplier * multiplier;
                        float changeFat = fatMultiplier * multiplier;
                        float changeSugar = sugarMultiplier * multiplier;
                        float changeProtein = proteinMultiplier * multiplier;
                        float changeCarbs = carbsMultiplier * multiplier;

                        caloriesTv.setText("Calories: " + (int) changeCal + "cal");
                        fatTv.setText("Fat: " + (int) changeFat + "g");
                        sugarTv.setText("Sugar: " + (int) changeSugar + "g");
                        proteinTv.setText("Protein: " + (int) changeProtein + "g");
                        carbsTv.setText("Carbohydrate: " + (int) changeCarbs + "g");
                    }else{
                        caloriesTv.setText("");
                        fatTv.setText("");
                        sugarTv.setText("");
                        proteinTv.setText("");
                        carbsTv.setText("");
                    }


                }


            });


        }

    }

}