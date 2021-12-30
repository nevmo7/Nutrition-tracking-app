package com.example.nutracker;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FoodData extends AsyncTask<Void,Void,Void>{

        ArrayList<Food> foodList = new ArrayList<>();
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
                String name, unit;
                int sugar, fat, protein, carb, cal, vol;

                object = new JSONObject(response.toString());
                JSONArray getArray = object.getJSONArray("items");
                for (int i=0;i<15;i++) {
                    JSONObject o = getArray.optJSONObject(i);
                    JSONObject item = o.getJSONObject("item");

                    JSONArray servings = item.getJSONArray("serving_sizes");
                    JSONObject serving = servings.getJSONObject(0);
                    JSONObject nutrient = item.getJSONObject("nutritional_contents");

                    try{
                        vol = (int) Float.parseFloat(serving.getString("nutrition_multiplier"));
                        Log.d(TAG, "onPostExecute: Nutrient: " + nutrient.getString("nutrition_multiplier") );
                    }catch (JSONException e){
                        vol = 1;
                        Log.d(TAG, "onPostExecute: The value of protein is 0");
                    }

                    try{
                        name = item.getString("brand_name");
                        Log.d(TAG, "onPostExecute: Name: " + item.getString("brand_name"));
                    }catch(JSONException e){
                        name = item.getString("description");
                        Log.d(TAG, "onPostExecute: Name: " + item.getString("description"));
                    }


                    try{
                        carb = (int) Double.parseDouble(nutrient.getString("carbohydrates"));
                        Log.d(TAG, "onPostExecute: Nutrient: " + nutrient.getString("carbohydrates") );
                    }catch (JSONException e){
                        carb = 0;
                        Log.d(TAG, "onPostExecute: The value of protein is 0");
                    }

                    try{
                        protein = (int) Double.parseDouble(nutrient.getString("protein"));
                        Log.d(TAG, "onPostExecute: Nutrient: " + nutrient.getString("protein") );
                    }catch (JSONException e){
                        protein = 0;
                        Log.d(TAG, "onPostExecute: The value of protein is 0");
                    }

                    try{
                        fat = (int) Double.parseDouble(nutrient.getString("fat"));
                        Log.d(TAG, "onPostExecute: Nutrient: " + nutrient.getString("fat") );
                    }catch (JSONException e){
                        fat = 0;
                        Log.d(TAG, "onPostExecute: The value of protein is 0");
                    }

                    try{
                        sugar = (int) Double.parseDouble(nutrient.getString("sugar"));
                        Log.d(TAG, "onPostExecute: Nutrient: " + nutrient.getString("sugar") );
                    }catch (JSONException e){
                        sugar = 0;
                        Log.d(TAG, "onPostExecute: The value of protein is 0");
                    }

                    unit = serving.getString("unit");
                    Log.d(TAG, "onPostExecute: unit: " + unit);

                    JSONObject energy = nutrient.getJSONObject("energy");
                    try{
                        cal = (int) Double.parseDouble(energy.getString("value"));
                        Log.d(TAG, "onPostExecute: Nutrient: " + energy.getString("value") );
                    }catch (JSONException e){
                        cal = 0;
                        Log.d(TAG, "onPostExecute: The value of protein is 0");
                    }

                    Food food = new Food(name, unit , cal, protein, fat, carb, sugar, vol);
                    foodList.add(food);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public ArrayList<Food> getData(){
            return foodList;
        }



    }

