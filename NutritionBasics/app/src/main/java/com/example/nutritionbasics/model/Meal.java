package com.example.nutritionbasics.model;

public class Meal {

    private int id;
    private String d_date;
    private int    food;
    private String mealtitle;
    private String observation;
    private float  totalcalories;

    public Meal() { }

    public Meal(int id, String d_date, int food, String mealtitle, String observation, float totalcalories) {
        this.id = id;
        this.d_date = d_date;
        this.food = food;
        this.mealtitle = mealtitle;
        this.observation = observation;
        this.totalcalories = totalcalories;
    }

    public int getId() {
        return id;
    }

    public String getD_date() {
        return d_date;
    }

    public int getFood() {
        return food;
    }

    public String getMealtitle() {
        return mealtitle;
    }

    public String getObservation() {
        return observation;
    }

    public float getTotalcalories() {
        return totalcalories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setD_date(String d_date) {
        this.d_date = d_date;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setMealtitle(String mealtitle) {
        this.mealtitle = mealtitle;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void setTotalcalories(float totalcalories) {
        this.totalcalories = totalcalories;
    }

}
