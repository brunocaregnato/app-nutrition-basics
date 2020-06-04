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
    private double caloriesCalc;
    private double proteinCalc;
    private double carbohydratesCalc;
    private double fatCalc;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        bdF = new BDfood(getActivity().getApplicationContext());

        meal = new ArrayList<>();
        meal = bdF.getAllMealDay();

        for(int x = 0; x < meal.size(); x++){
            food = bdF.getFood(meal.get(x).getFood());
            caloriesCalc = caloriesCalc + ((meal.get(x).getTotalcalories() * food.getCalories())/food.getWeight());
            proteinCalc = proteinCalc + ((meal.get(x).getTotalcalories() * food.getProtein())/food.getWeight());
            carbohydratesCalc = carbohydratesCalc + ((meal.get(x).getTotalcalories() * food.getCarbohydrate())/food.getWeight());
            fatCalc = fatCalc + ((meal.get(x).getTotalcalories() * food.getFat())/food.getWeight());
        }

        user = bdF.getUser();

        if (user != null) {
            NumberFormat fmt = NumberFormat.getInstance();
            fmt.setGroupingUsed(false);
            fmt.setMaximumIntegerDigits(9999);
            fmt.setMaximumFractionDigits(99);
            final TextView name = (TextView) view.findViewById(R.id.welcome);
            name.setText("Welcome ".concat(String.valueOf(user.getName())).concat(("!")));

            final TextView calories = (TextView) view.findViewById(R.id.calories);
            calories.setText(String.format("%.2f", user.getCalories() - caloriesCalc).concat(" cal"));

            final TextView protein = (TextView) view.findViewById(R.id.protein);
            protein.setText("Proteins: ".concat(String.format("%.2f", user.getProteins() - proteinCalc).concat(" cal")));

            final TextView carbohydrates = (TextView) view.findViewById(R.id.carbohydrates);
            carbohydrates.setText("Carbohydrates: ".concat(String.format("%.2f", user.getCarbohydrates() - carbohydratesCalc).concat(" cal")));

            final TextView fat = (TextView) view.findViewById(R.id.fat);
            fat.setText("Fat: ".concat(String.format("%.2f", user.getFat() - fatCalc).concat(" cal")));
        }
        else getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Info()).commit();


        return view;
    }
}
