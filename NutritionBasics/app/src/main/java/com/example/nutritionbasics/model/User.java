package com.example.nutritionbasics.model;

import java.util.Date;

public class User {

    private int id;
    private String name;
    private String birthday;
    private int  height;
    private int weight;
    private int activityLevel;
    private int sex;

    private float calories;

    public User() { }

    public User(int id, String name, String birthday, int height, int weight, int activityLevel, int sex, float calories) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.sex = sex;
        this.calories = calories;
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

    public float getCalories() {
        return calories;
    }

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

    public void setCalories(float calories) {
        this.calories = calories;
    }

}
