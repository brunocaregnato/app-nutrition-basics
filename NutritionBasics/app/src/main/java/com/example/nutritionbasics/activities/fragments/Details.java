package com.example.nutritionbasics.activities.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

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

    private double caloriesCalc;
    private int proteinCalc, carbohydratesCalc, fatCalc;
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

        PieChart pieChart = view.findViewById(R.id.pieChart);
        ArrayList calories = new ArrayList();

        calories.add(new Entry(Integer.parseInt(String.valueOf(proteinCalc)), 0));
        calories.add(new Entry(Integer.parseInt(String.valueOf(carbohydratesCalc)), 1));
        calories.add(new Entry(Integer.parseInt(String.valueOf(fatCalc)), 2));
        PieDataSet dataSet = new PieDataSet(calories, " - Macronutrients (cal)");

        ArrayList macronutrients = new ArrayList();

        macronutrients.add("Proteins");
        macronutrients.add("Carbohydrates");
        macronutrients.add("Fat");

        PieData data = new PieData(macronutrients, dataSet);
        pieChart.setData(data);

        final int[] MY_COLORS = {Color.rgb(228, 33, 51), Color.rgb(29, 254, 130), Color.rgb(65, 139, 239)};
        dataSet.setColors(MY_COLORS);
        pieChart.animateXY(2000, 2000);


        final TextView detailTitle = view.findViewById(R.id.detailTitle);
        detailTitle.setText(meal.getMealtitle() + " Details - Total Calories: ".concat(String.format("%.2f", caloriesCalc)));

        final TextView vitaminA = view.findViewById(R.id.mealVitaminA);
        vitaminA.setText("Vitamin A: ".concat(String.format("%.2f", vitaminACalc).concat(" µg")));

        final TextView vitaminB = view.findViewById(R.id.mealVitaminB);
        vitaminB.setText("Vitamin B: ".concat(String.format("%.2f", vitaminBCalc).concat(" µg")));

        final TextView vitaminC = view.findViewById(R.id.mealVitaminC);
        vitaminC.setText("Vitamin C: ".concat(String.format("%.2f", vitaminCCalc).concat(" µg")));

        final TextView vitaminD = view.findViewById(R.id.mealVitaminD);
        vitaminD.setText("Vitamin D: ".concat(String.format("%.2f", vitaminDCalc).concat(" µg")));

        final TextView vitaminE = view.findViewById(R.id.mealVitaminE);
        vitaminE.setText("Vitamin E: ".concat(String.format("%.2f", vitaminECalc).concat(" µg")));

        final TextView calcium = view.findViewById(R.id.mealCalcium);
        calcium.setText("Calcium: ".concat(String.format("%.2f", calciumCalc).concat(" µg")));

        final TextView iron = view.findViewById(R.id.mealIron);
        iron.setText("Iron: ".concat(String.format("%.2f", ironCalc).concat(" µg")));

        final TextView zinc = view.findViewById(R.id.mealZinc);
        zinc.setText("Zinc: ".concat(String.format("%.2f", zincCalc).concat(" µg")));

        final TextView names = view.findViewById(R.id.mealFoods);

        foodsDetails.forEach((food, weight) -> {
            names.setText(names.getText() + food + " (" + String.valueOf(weight).concat("g), "));
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
