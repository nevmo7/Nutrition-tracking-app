 package com.example.nutracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    ArrayList<Food> searchItems = new ArrayList<>();
    String TAG = "Search Result";
    Context myContext = SearchResultActivity.this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent incomingIntent = getIntent();

        String incomingSearchTerm = incomingIntent.getStringExtra("search");

        if (incomingIntent.hasExtra("search")) {
            new FoodData(incomingSearchTerm, 5).execute();
        }



    }

    public class FoodData extends AsyncTask<Void,Void,Void> {

        StringBuilder response;
        HttpURLConnection connection;
        String searchTerm;
        int inputVolume;
        JSONObject object;
        String TAG = "GET DATA";

        public FoodData(String searchTerm, int inputVolume) {
            this.searchTerm = searchTerm;
            this.inputVolume = inputVolume;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            URL url;
            try {
                url = new URL("https://api.myfitnesspal.com/public/nutrition?q=" + searchTerm);

                // Open a connection(?) on the URL(??) and cast the response(???)

                connection = (HttpURLConnection) url.openConnection();

                // Now it's "open", we can set the request method, headers etc.
                connection.setRequestProperty("accept", "application/json");


                // optional default is GET
                connection.setRequestMethod("GET");

                //add request header
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                // This line makes the request
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                object = new JSONObject(response.toString());
                JSONArray getArray = object.getJSONArray("items");

                Log.d(TAG, "onPostExecute: " + getArray.getJSONObject(0).getJSONObject("item"));

                for (int i=0;i<getArray.length();i++) {
                    String name, unit;
                    int sugar, fat, protein, carb, cal, vol;
                    JSONObject item = getArray.getJSONObject(i).getJSONObject("item");

                    JSONArray servings = item.getJSONArray("serving_sizes");
                    JSONObject serving = servings.getJSONObject(0);
                    JSONObject nutrient = item.getJSONObject("nutritional_contents");

                    try{
                        vol = (int) Float.parseFloat(serving.getString("value"));
                    }catch (JSONException e){
                        vol = 1;
                    }

                    try{
                        name = item.getString("brand_name");
                    }catch(JSONException e){
                        name = item.getString("description");
                    }


                    try{
                        carb = (int) Double.parseDouble(nutrient.getString("carbohydrates"));
                    }catch (JSONException e){
                        carb = 0;
                    }

                    try{
                        protein = (int) Double.parseDouble(nutrient.getString("protein"));
                    }catch (JSONException e){
                        protein = 0;
                    }

                    try{
                        fat = (int) Double.parseDouble(nutrient.getString("fat"));
                    }catch (JSONException e){
                        fat = 0;
                    }

                    try{
                        sugar = (int) Double.parseDouble(nutrient.getString("sugar"));
                    }catch (JSONException e){
                        sugar = 0;
                    }

                    unit = serving.getString("unit");
                    Log.d(TAG, "onPostExecute: unit: " + unit);

                    JSONObject energy = nutrient.getJSONObject("energy");
                    try{
                        cal = (int) Double.parseDouble(energy.getString("value"));
                    }catch (JSONException e){
                        cal = 0;
                    }

                    Food food = new Food(name, unit , cal, protein, fat, carb, sugar, vol);
                    Log.d(TAG, "onPostExecute: " + food.toString());
                    searchItems.add(food);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ListView searchList = findViewById(R.id.searchList);
            SearchListAdapter adapter = new SearchListAdapter(myContext, R.layout.search_item_layout, searchItems);
            searchList.setAdapter(adapter);



            searchList.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(SearchResultActivity.this, SingleFoodActivity.class);
                intent.putExtra("activity", "SearchResultActivity");
                intent.putExtra("name", searchItems.get(position).getName());
                intent.putExtra("unit", searchItems.get(position).getUnit());
                intent.putExtra("cal", searchItems.get(position).getCalories());
                intent.putExtra("protein", searchItems.get(position).getProtein());
                intent.putExtra("fat", searchItems.get(position).getFat());
                intent.putExtra("carbs", searchItems.get(position).getCarbs());
                intent.putExtra("sugar", searchItems.get(position).getSugar());
                intent.putExtra("vol", searchItems.get(position).getVolume());

                startActivity(intent);
            });

        }

    }

}