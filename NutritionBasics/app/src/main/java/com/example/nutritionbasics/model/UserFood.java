package com.example.nutritionbasics.model;

public class UserFood {

    private Food food;
    private Integer weight;

    public UserFood(){}

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
