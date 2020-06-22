package com.example.nutritionbasics.activities.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.banco.Database;
import com.example.nutritionbasics.model.Meal;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class RegisterMeal extends Fragment {

    private EditText fragment_date;
    private DatePickerDialog picker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_meal_fragment, container, false);

        final TextInputEditText mealTitle = view.findViewById(R.id.fragment_meal);
        final TextInputEditText mealDate = view.findViewById(R.id.fragment_date);
        final TextInputEditText observation = view.findViewById(R.id.fragment_observation);

        fragment_date = (TextInputEditText) view.findViewById(R.id.fragment_date);

        fragment_date.setInputType(InputType.TYPE_NULL);
        fragment_date.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            picker = new DatePickerDialog(getActivity(), (view1, year1, monthOfYear, dayOfMonth) ->
                    fragment_date.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", monthOfYear + 1) + "/" + year1), year, month, day);
            picker.show();
        });

        final Button addFoodsButton = view.findViewById(R.id.addFoodsButton);
        addFoodsButton.setOnClickListener(v -> {

            if (mealTitle.getText() == null || mealTitle.getText().toString().trim().equals("")){
                Toast.makeText(getActivity().getApplicationContext(), "Meal title must be filled!", Toast.LENGTH_LONG).show();
                return;
            }
            else if(mealDate.getText() == null || mealDate.getText().toString().trim().equals("")){
                Toast.makeText(getActivity().getApplicationContext(), "Meal date must be filled!", Toast.LENGTH_LONG).show();
                return;
            }

            Meal _meal = new Meal();
            _meal.setMealtitle(mealTitle.getText().toString());
            _meal.setD_date(mealDate.getText().toString());
            _meal.setObservation(observation.getText().toString());

            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegisterFood(_meal)).commit();
        });

        return view;
    }

}
