package com.example.nutritionbasics.activities.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.banco.BDfood;
import com.example.nutritionbasics.model.Food;
import com.example.nutritionbasics.model.Meal;
import com.example.nutritionbasics.model.User;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private BDfood bdF;
    private User user;
    private List<Meal> meal;
    private Food food;
    private double caloriesCalc, proteinCalc, carbohydratesCalc, fatCalc;
    private float vitaminBCalc, vitaminDCalc, vitaminACalc, vitaminCCalc, vitaminECalc, calciumCalc, ironCalc, zincCalc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        bdF = new BDfood(getActivity().getApplicationContext());

        this.calculateMacrosAndMicros();
        user = bdF.getUser();

        if (user != null) {
            NumberFormat fmt = NumberFormat.getInstance();
            fmt.setGroupingUsed(false);
            fmt.setMaximumIntegerDigits(9999);
            fmt.setMaximumFractionDigits(99);
            final TextView name = (TextView) view.findViewById(R.id.welcome);
            name.setText("Welcome ".concat(String.valueOf(user.getName())).concat(("!")));

            final TextView calories = view.findViewById(R.id.calories);
            calories.setText(String.format("%.2f", user.getCalories() - caloriesCalc).concat(" cal"));

            //MACROS
            final TextView protein = view.findViewById(R.id.protein);
            protein.setText("Proteins: ".concat(String.format("%.2f", user.getProteins() - proteinCalc).concat(" cal")));

            final TextView carbohydrates = view.findViewById(R.id.carbohydrates);
            carbohydrates.setText("Carbohydrates: ".concat(String.format("%.2f", user.getCarbohydrates() - carbohydratesCalc).concat(" cal")));

            final TextView fat = view.findViewById(R.id.fat);
            fat.setText("Fat: ".concat(String.format("%.2f", user.getFat() - fatCalc).concat(" cal")));

            //MICROS
            final TextView vitaminA = view.findViewById(R.id.vitaminA);
            vitaminA.setText("Vitamin A: ".concat(String.format("%.2f", 900 - vitaminACalc).concat(" µg")));

            final TextView vitaminB = view.findViewById(R.id.vitaminB);
            vitaminB.setText("Vitamin B: ".concat(String.format("%.2f", 4 - vitaminBCalc).concat(" µg")));

            final TextView vitaminC = view.findViewById(R.id.vitaminC);
            vitaminC.setText("Vitamin C: ".concat(String.format("%.2f", 90 - vitaminCCalc).concat(" µg")));

            final TextView vitaminD = view.findViewById(R.id.vitaminD);
            vitaminD.setText("Vitamin D: ".concat(String.format("%.2f", 35 - vitaminDCalc).concat(" µg")));

            final TextView vitaminE = view.findViewById(R.id.vitaminE);
            vitaminE.setText("Vitamin E: ".concat(String.format("%.2f", 15 - vitaminECalc).concat(" µg")));

            final TextView calcium = view.findViewById(R.id.calcium);
            calcium.setText("Calcium: ".concat(String.format("%.2f", 1000 - calciumCalc).concat(" µg")));

            final TextView iron = view.findViewById(R.id.iron);
            iron.setText("Iron: ".concat(String.format("%.2f", 8 - ironCalc).concat(" µg")));

            final TextView zinc = view.findViewById(R.id.zinc);
            zinc.setText("Zinc: ".concat(String.format("%.2f", 11 - zincCalc).concat(" µg")));

        }
        else getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Info()).commit();

        return view;
    }

    private void calculateMacrosAndMicros(){

        meal = new ArrayList<>();
        meal = bdF.getAllMealDay();

        for(int x = 0; x < meal.size(); x++){
            food = bdF.getFood(meal.get(x).getFood());
            caloriesCalc += ((meal.get(x).getTotalcalories() * food.getCalories())/food.getWeight());
            proteinCalc += ((meal.get(x).getTotalcalories() * food.getProtein())/food.getWeight());
            carbohydratesCalc += ((meal.get(x).getTotalcalories() * food.getCarbohydrate())/food.getWeight());
            fatCalc += ((meal.get(x).getTotalcalories() * food.getFat())/food.getWeight());
            vitaminBCalc += food.getVitaminB();
            vitaminDCalc += food.getVitaminD();
            vitaminACalc += food.getVitaminA();
            vitaminCCalc += food.getVitaminC();
            vitaminECalc += food.getVitaminE();
            calciumCalc += food.getCalcium();
            ironCalc += food.getIron();
            zincCalc += food.getZinc();
        }
    }
}
