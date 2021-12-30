package com.example.nutracker;

public class Food {
    String name, unit;
    int calories, protein, fat, carbs, sugar, volume;

    public Food(String name, String unit, int calories, int protein, int fat, int carbs, int sugar, int volume) {
        this.name = name;
        this.unit = unit;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.sugar = sugar;
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", calories=" + calories +
                ", protein=" + protein +
                ", fat=" + fat +
                ", carbs=" + carbs +
                ", sugar=" + sugar +
                ", volume=" + volume +
                '}';
    }
}
