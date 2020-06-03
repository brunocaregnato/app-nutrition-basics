package com.example.nutritionbasics.activities.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.banco.BDfood;
import com.example.nutritionbasics.model.Meal;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterMeal extends Fragment {

    private EditText fragment_date;
    private DatePickerDialog picker;
    Spinner fragment_mealtitle_spinner;
    private BDfood bdF;
    String sNumber;
    int sPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_meal_fragment, container, false);

        bdF = new BDfood(getActivity().getApplicationContext());

        final TextInputEditText mealTitle = (TextInputEditText) view.findViewById(R.id.fragment_meal);
        final Spinner food = (Spinner) view.findViewById(R.id.fragment_mealtitle_spinner);
        final TextInputEditText weight = (TextInputEditText) view.findViewById(R.id.fragment_weight);

        final TextInputEditText mealDate = (TextInputEditText) view.findViewById(R.id.fragment_date);
        final TextInputEditText observation = (TextInputEditText) view.findViewById(R.id.fragment_observation);

        fragment_date = (TextInputEditText) view.findViewById(R.id.fragment_date);
        fragment_mealtitle_spinner = (Spinner) view.findViewById(R.id.fragment_mealtitle_spinner);

        List<String> numberList;
        numberList = new ArrayList<>();

        numberList.add("Select Food");
        numberList.add("Pears");
        numberList.add("Cucumber");
        numberList.add("Bacon (Raw)");

        fragment_mealtitle_spinner.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,numberList));

        fragment_mealtitle_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    //Toast.makeText(getContext(),"Please Select Number", Toast.LENGTH_SHORT).show();
                }else{
                        sNumber = parent.getItemAtPosition(position).toString();
                        sPosition = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragment_date.setInputType(InputType.TYPE_NULL);
        fragment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        fragment_date.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

       final Button save = (Button) view.findViewById(R.id.saveButtonMeal);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Meal _meal = new Meal();
                _meal.setMealtitle(mealTitle.getText().toString());
                _meal.setFood(sPosition);
                _meal.setTotalcalories(Double.parseDouble(weight.getText().toString()));
                _meal.setD_date(mealDate.getText().toString());
                _meal.setObservation(observation.getText().toString());

                bdF.addMeal(_meal);

                //Redireciona para o fragment home
                //Toast.makeText(getActivity().getApplicationContext(), _meal.getMealtitle() + " - " + _meal.getFood() + " - " + _meal.getTotalcalories() + " - " + _meal.getD_date() + " - " + _meal.getObservation() , Toast.LENGTH_LONG).show();

                Toast.makeText(getActivity().getApplicationContext(), "Meal Updated Successfully ", Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
            }
        });

        return view;
    }
}
