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
import com.example.nutritionbasics.banco.BDuser;
import com.example.nutritionbasics.model.Food;
import com.example.nutritionbasics.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Profile extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText birthday;
    private DatePickerDialog picker;
    private BDuser bd;
    private BDfood bdF;
    private User user;
    private List<Food> _food;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        bd = new BDuser(getActivity().getApplicationContext());
        bdF = new BDfood(getActivity().getApplicationContext());

        Spinner spinner = view.findViewById(R.id.sex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.sexs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinnerActivity = view.findViewById(R.id.activityLevel);
        adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.activitiesLevels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivity.setAdapter(adapter);
        spinnerActivity.setOnItemSelectedListener(this);





            _food = new ArrayList<>();
            //int id, String foodName, int calories, int weight, float vitaminB, float vitaminD, float vitaminA, float vitaminC, float vitaminE, float calcium, float iron, float zinc, float fat, float protein, float carbohydrate) {
            _food.add(new Food(1, "Pears", 57, 350, 0, 0, 0, 0, 0, 0, 0, 0, 0.14, 0.36, 15.23));
            _food.add(new Food(2, "Cucumber", 15, 1333, 0, 0, 0, 0, 0, 0, 0, 0, 0.11, 0.65, 3.63));
            _food.add(new Food(3, "Bacon (Raw)", 393, 50, 0, 0, 0, 0, 0, 0, 0, 0, 37.13, 13.66, 0));

            /*for (int x = 0; x < _food.size(); x++) {
                bdF.addFood(_food.get(x));
            }*/





        user = bd.getUser();

        final EditText name = (EditText) view.findViewById(R.id.name);
        final EditText weight = (EditText) view.findViewById(R.id.weight);
        final EditText height = (EditText) view.findViewById(R.id.height);
        final EditText userBirthday = (EditText) view.findViewById(R.id.birthday);
        final Spinner sex = (Spinner) view.findViewById(R.id.sex);
        final Spinner activitylevel = (Spinner) view.findViewById(R.id.activityLevel);

        if (user != null) {
            name.setText(user.getName());
            weight.setText(String.valueOf(user.getWeight()));
            height.setText(String.valueOf(user.getHeight()));
            userBirthday.setText(user.getBirthday());
            sex.setSelection(user.getSex());
            activitylevel.setSelection(user.getActivityLevel());
        }

        birthday = (EditText) view.findViewById(R.id.birthday);
        birthday.setInputType(InputType.TYPE_NULL);
        birthday.setOnClickListener(new View.OnClickListener() {
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
                                birthday.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        final Button save = (Button) view.findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User userProfile = new User();
                userProfile.setName(name.getText().toString());
                userProfile.setWeight(Integer.parseInt(weight.getText().toString()));
                userProfile.setHeight(Integer.parseInt(height.getText().toString()));
                userProfile.setBirthday(userBirthday.getText().toString());
                userProfile.setSex(sex.getSelectedItem().toString().toUpperCase().equals("MALE") ? 0 : 1);
                int activityLevel;
                if (activitylevel.getSelectedItem().toString().toUpperCase().startsWith("S")) activityLevel = 0;
                else if(activitylevel.getSelectedItem().toString().toUpperCase().startsWith("A")) activityLevel = 1;
                else activityLevel = 2;
                userProfile.setActivityLevel(activityLevel);
                userProfile.setCalories(0);

                if(user != null) bd.updateUser(userProfile);
                else { bd.addUser(userProfile);
                    for (int x = 0; x < _food.size(); x++) {
                        bdF.addFood(_food.get(x));
                    }
                }

                //Redireciona para o fragment home
                Toast.makeText(getActivity().getApplicationContext(), "Profile Updated Successfully ", Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //do whatever
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do whatever
    }
}
