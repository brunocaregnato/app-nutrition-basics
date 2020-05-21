package com.example.nutritionbasics.model;

public class Food {

    private int id;
    private String foodName;
    private double    calories;
    private double weight;
    private float vitaminB;
    private float vitaminD;
    private float vitaminA;
    private float vitaminC;
    private float vitaminE;
    private float calcium;
    private float iron;
    private float zinc;
    private double fat;
    private double protein;
    private double carbohydrate;

    public Food() {

    }

    public Food(int id, String foodName, double calories, double weight, float vitaminB, float vitaminD, float vitaminA, float vitaminC, float vitaminE, float calcium, float iron, float zinc, double fat, double protein, double carbohydrate) {
        this.id = id;
        this.foodName = foodName;
        this.calories = calories;
        this.weight = weight;
        this.vitaminB = vitaminB;
        this.vitaminD = vitaminD;
        this.vitaminA = vitaminA;
        this.vitaminC = vitaminC;
        this.vitaminE = vitaminE;
        this.calcium = calcium;
        this.iron = iron;
        this.zinc = zinc;
        this.fat = fat;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setVitaminB(float vitaminB) {
        this.vitaminB = vitaminB;
    }

    public void setVitaminD(float vitaminD) {
        this.vitaminD = vitaminD;
    }

    public void setVitaminA(float vitaminA) {
        this.vitaminA = vitaminA;
    }

    public void setVitaminC(float vitaminC) {
        this.vitaminC = vitaminC;
    }

    public void setVitaminE(float vitaminE) {
        this.vitaminE = vitaminE;
    }

    public void setCalcium(float calcium) {
        this.calcium = calcium;
    }

    public void setIron(float iron) {
        this.iron = iron;
    }

    public void setZinc(float zinc) {
        this.zinc = zinc;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public int getId() {
        return id;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getCalories() {
        return calories;
    }

    public double getWeight() {
        return weight;
    }

    public float getVitaminB() {
        return vitaminB;
    }

    public float getVitaminD() {
        return vitaminD;
    }

    public float getVitaminA() {
        return vitaminA;
    }

    public float getVitaminC() {
        return vitaminC;
    }

    public float getVitaminE() {
        return vitaminE;
    }

    public float getCalcium() {
        return calcium;
    }

    public float getIron() {
        return iron;
    }

    public float getZinc() {
        return zinc;
    }

    public double getFat() {
        return fat;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

}
