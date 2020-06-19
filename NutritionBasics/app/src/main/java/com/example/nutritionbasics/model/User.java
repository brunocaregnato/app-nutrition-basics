package com.example.nutritionbasics.model;

import java.util.Calendar;

public class User {

    private int id;
    private String name;
    private String birthday;
    private int  height;
    private int weight;
    private int activityLevel;
    private int sex;

    private double calories;
    private double proteins;
    private double carbohydrates;
    private double fat;

    public User() { }

    public User(int id, String name, String birthday, int height, int weight, int activityLevel, int sex, double calories,
                double proteins, double carbohydrates, double fat) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.sex = sex;
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
    }

    //GETTERS
    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public int getSex() {
        return sex;
    }

    public double getCalories() {
        return calories;
    }

    public double getProteins() { return proteins; }

    public double getCarbohydrates() { return carbohydrates; }

    public double getFat() { return fat; }

    //SETTERS
    public void setId(int id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHeight(int height) { this.height = height; }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setCalories(double calories) {
        if (calories > 0) this.calories = calories;
        else this.calories = CalculateBMR();

        calculateMacros(this.calories);
    }

    private double CalculateBMR(){
        double bmr = (10 * getWeight()) + (6.25 * getWeight()) - (5 * getAge());
        switch (getSex()){
            case 0: bmr += 5;
                    break;
            case 1: bmr -= 161;
        }
        switch (getActivityLevel()){
            case 0: bmr *= 1.53;
                    break;
            case 1: bmr *= 1.76;
                    break;
            case 2: bmr *= 2.25;
                    break;
        }

        return bmr;
    }

    private int getAge(){
        Calendar today = Calendar.getInstance();
        return today.get(Calendar.YEAR) - Integer.parseInt(this.birthday.substring(6));
    }

    private void calculateMacros(double calories) {
        this.proteins = (calories * 27) / 100;
        this.carbohydrates = (calories * 50) / 100;;
        this.fat = (calories * 23) / 100;;
    }

}
