package com.example.nutritionbasics.activities.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.banco.Database;
import com.example.nutritionbasics.model.Meal;
import com.example.nutritionbasics.model.UserFood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Details extends Fragment {

    private int id;
    private Database bd;
    private Meal meal;
    private List<UserFood> foods;
    private Map<String, Integer> foodsDetails;

    private double caloriesCalc, proteinCalc, carbohydratesCalc, fatCalc;
    private float vitaminBCalc, vitaminDCalc, vitaminACalc, vitaminCCalc, vitaminECalc, calciumCalc, ironCalc, zincCalc;

    public Details(int id){
        this.id = id;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        bd = new Database(getActivity().getApplicationContext());

        meal = bd.getMeal(id);
        foods = bd.getFoodsFromMeal(meal.getId());

        calculateMacrosAndMicros();

        final TextView detailTitle = view.findViewById(R.id.detailTitle);
        detailTitle.setText(meal.getMealtitle() + " Details");

        final TextView mealCalories = view.findViewById(R.id.mealCalories);
        mealCalories.setText("Calories: ".concat(String.format("%.2f", caloriesCalc)).concat(" cal"));

        final TextView mealProtein = view.findViewById(R.id.mealProtein);
        mealProtein.setText("Proteins : ".concat(String.format("%.2f", proteinCalc)).concat(" cal"));

        final TextView mealCarbohydrates = view.findViewById(R.id.mealCarbohydrates);
        mealCarbohydrates.setText("Carbohydrates: ".concat(String.format("%.2f", carbohydratesCalc)).concat(" cal"));

        final TextView mealFat = view.findViewById(R.id.mealFat);
        mealFat.setText("Fat: ".concat(String.format("%.2f", fatCalc)).concat(" cal"));

        final TextView vitaminA = view.findViewById(R.id.mealVitaminA);
        vitaminA.setText("Vitamin A: ".concat(String.format("%.2f", 900 - vitaminACalc).concat(" µg")));

        final TextView vitaminB = view.findViewById(R.id.mealVitaminB);
        vitaminB.setText("Vitamin B: ".concat(String.format("%.2f", 4 - vitaminBCalc).concat(" µg")));

        final TextView vitaminC = view.findViewById(R.id.mealVitaminC);
        vitaminC.setText("Vitamin C: ".concat(String.format("%.2f", 90 - vitaminCCalc).concat(" µg")));

        final TextView vitaminD = view.findViewById(R.id.mealVitaminD);
        vitaminD.setText("Vitamin D: ".concat(String.format("%.2f", 35 - vitaminDCalc).concat(" µg")));

        final TextView vitaminE = view.findViewById(R.id.mealVitaminE);
        vitaminE.setText("Vitamin E: ".concat(String.format("%.2f", 15 - vitaminECalc).concat(" µg")));

        final TextView calcium = view.findViewById(R.id.mealCalcium);
        calcium.setText("Calcium: ".concat(String.format("%.2f", 1000 - calciumCalc).concat(" µg")));

        final TextView iron = view.findViewById(R.id.mealIron);
        iron.setText("Iron: ".concat(String.format("%.2f", 8 - ironCalc).concat(" µg")));

        final TextView zinc = view.findViewById(R.id.mealZinc);
        zinc.setText("Zinc: ".concat(String.format("%.2f", 11 - zincCalc).concat(" µg")));

        final TextView names = view.findViewById(R.id.mealFoods);
        foodsDetails.forEach((food, weight) -> {
            names.setText(names.getText() + food + " - " + String.valueOf(weight).concat("g") + "\n");
        });

        final TextView observation = view.findViewById(R.id.observation);
        observation.setText("Observation: ".concat(meal.getObservation().trim()));

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void calculateMacrosAndMicros(){
        foodsDetails = new HashMap<>();
        foods.forEach(food -> {
            foodsDetails.put(food.getFood().getFoodName(), food.getWeight());
            caloriesCalc += (food.getFood().getCalories() * food.getWeight()) / 100;
            proteinCalc += (food.getFood().getProtein() * food.getWeight()) / 100;
            carbohydratesCalc += (food.getFood().getCarbohydrate() * food.getWeight()) / 100;
            fatCalc += (food.getFood().getFat() * food.getWeight()) / 100;
            vitaminBCalc += food.getFood().getVitaminB();
            vitaminDCalc += food.getFood().getVitaminD();
            vitaminACalc += food.getFood().getVitaminA();
            vitaminCCalc += food.getFood().getVitaminC();
            vitaminECalc += food.getFood().getVitaminE();
            calciumCalc += food.getFood().getCalcium();
            ironCalc += food.getFood().getIron();
            zincCalc += food.getFood().getZinc();
        });
    }
}
