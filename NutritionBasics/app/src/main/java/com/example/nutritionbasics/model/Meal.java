package com.example.nutritionbasics.model;

import java.util.List;

public class Meal {

    private int id;
    private String d_date;
    private List<UserFood> foods;
    private String mealtitle;
    private String observation;

    private double  totalcalories;

    public Meal() { }

    public Meal(int id, String d_date, List<UserFood> food, String mealtitle, String observation, double totalcalories) {
        this.id = id;
        this.d_date = d_date;
        this.foods = food;
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

    public List<UserFood> getFoods() {
        return foods;
    }

    public String getMealtitle() {
        return mealtitle;
    }

    public String getObservation() {
        return observation;
    }

    public double getTotalcalories() {
        return totalcalories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setD_date(String d_date) {
        this.d_date = d_date;
    }

    public void setFoods(List<UserFood> food) { this.foods = food; }

    public void setMealtitle(String mealtitle) {
        this.mealtitle = mealtitle;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void setTotalcalories(double totalcalories) { this.totalcalories = totalcalories; }

}
