package com.example.nutritionbasics.model;

public class Usuario {

    private int id;
    private String name;
    private String   birthdate;
    private float  height;
    private int    weight;
    private int    activityLevel;
    private int    sex;
    private float  calorias;

    public Usuario() { }

    public Usuario(int id, String name, String birthdate, float height, int weight, int activityLevel, int sex, float calorias) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.sex = sex;
        this.calorias = calorias;
    }

    //GETTERS
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public float getHeight() {
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

    public float getCalorias() {
        return calorias;
    }

    //SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setCalorias(float calorias) {
        this.calorias = calorias;
    }

}
