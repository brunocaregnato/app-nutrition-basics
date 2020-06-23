package com.example.nutritionbasics.activities.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.example.nutritionbasics.R;
import com.example.nutritionbasics.util.ListViewAdapter;
import com.example.nutritionbasics.banco.Database;
import com.example.nutritionbasics.model.Food;
import com.example.nutritionbasics.model.Meal;
import com.example.nutritionbasics.model.UserFood;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RegisterFood extends Fragment {

    private Meal meal;
    private Database bdF;

    private MultiSpinnerSearch searchSpinner;
    private List<KeyPairBoolData> foodAddList;
    private ListView foodAddListView;
    private ListViewAdapter listViewAdapter;
    private MaterialButton registerMealButton;

    public RegisterFood(Meal meal){
        this.meal = meal;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_food_fragment, container, false);
        bdF = new Database(getActivity().getApplicationContext());

        foodAddListView = view.findViewById(R.id.foodsListView);
        foodAddListView.setItemsCanFocus(true);

        searchSpinner = view.findViewById(R.id.searchMultiSpinnerUnlimited);
        createSpinner();

        final FloatingActionButton addFood = view.findViewById(R.id.addFoodButton);
        addFood.setOnClickListener(v -> {
            searchSpinner.performClick();
        });

        registerMealButton = view.findViewById(R.id.registerMealButton);
        registerMeal();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void registerMeal(){
        registerMealButton.setOnClickListener(result -> {

            if(foodAddList.size() == 0){
                Toast.makeText(getActivity().getApplicationContext(), "Add at least one food!", Toast.LENGTH_LONG).show();
                return;
            }
            else {
                boolean error = false;
                for(KeyPairBoolData food : foodAddList){
                    if(food.getObject() == null || String.valueOf(food.getObject()).trim().equals("")
                            || Integer.parseInt(String.valueOf(food.getObject())) == 0){
                        error = true;
                        break;
                    }
                }
                if (error){
                    Toast.makeText(getActivity().getApplicationContext(), "All weights must be filled!", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            List<UserFood> listFood = new ArrayList<>();
            AtomicReference<Double> calories = new AtomicReference<>((double) 0);
            foodAddList.forEach(item -> {
                UserFood food = new UserFood();
                food.setFood(bdF.getFood(Integer.parseInt(String.valueOf(item.getId()))));
                food.setWeight(Integer.parseInt(item.getObject().toString()));
                calories.updateAndGet(v -> new Double(v + ((food.getFood().getCalories() * food.getWeight()) / 100)));
                listFood.add(food);
            });

            meal.setTotalcalories(calories.get());
            meal.setFoods(listFood);
            bdF.addMeal(meal);

            Toast.makeText(getActivity().getApplicationContext(), "Meal registered successfully!", Toast.LENGTH_LONG).show();
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new Home()).commit();
        });
    }

    private void createSpinner(){

        List<Food> foodList = bdF.getAllFoodFilter();
        foodAddList = new ArrayList<>();

        final List<KeyPairBoolData> listArray = new ArrayList<>();

        for(int i=0; i < foodList.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(foodList.get(i).getId());
            h.setName(foodList.get(i).getFoodName());
            h.setSelected(false);
            listArray.add(h);
        }

        searchSpinner.setItems(listArray, -1, items -> {
            for(int i=0; i<items.size(); i++) {
                if(items.get(i).isSelected() && !foodAddList.contains((items.get(i)))) {
                    foodAddList.add(items.get(i));
                }
                else if(!items.get(i).isSelected() && foodAddList.contains((items.get(i)))){
                    foodAddList.remove(items.get(i));
                }
            }
            listViewAdapter = new ListViewAdapter(getActivity().getApplicationContext(), foodAddList);
            foodAddListView.setAdapter(listViewAdapter);
        });

        searchSpinner.setLimit(10, data -> Toast.makeText(getContext(),
                "Limit exceed ", Toast.LENGTH_LONG).show());
    }
}
