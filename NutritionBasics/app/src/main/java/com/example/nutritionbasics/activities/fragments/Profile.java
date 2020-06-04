package com.example.nutritionbasics.activities.fragments;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.nutritionbasics.R;
import com.example.nutritionbasics.banco.BDfood;
import com.example.nutritionbasics.model.Food;
import com.example.nutritionbasics.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Profile extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText birthday;
    private DatePickerDialog picker;
    private BDfood bdF;
    private User user;
    private List<Food> _food;

    String sexSelected, activityLevel;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        bdF = new BDfood(getActivity().getApplicationContext());

        AutoCompleteTextView spinner = view.findViewById(R.id.sex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.sexs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                               sexSelected = (String) parent.getItemAtPosition(position);
                                           }
                                       });

        AutoCompleteTextView spinnerActivity = view.findViewById(R.id.activityLevel);
        adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.activitiesLevels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivity.setAdapter(adapter);
        spinnerActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        activityLevel = (String) parent.getItemAtPosition(position);
                                                    }
                                                });

            _food = new ArrayList<>();
            //int id, String foodName, int calories, int weight, float vitaminB, float vitaminD, float vitaminA, float vitaminC, float vitaminE, float calcium, float iron, float zinc, float fat, float protein, float carbohydrate) {
            _food.add(new Food(1, "Pears", 57, 350, 0, 0, 0, 0, 0, 0, 0, 0, 0.14, 0.36, 15.23));
            _food.add(new Food(2, "Cucumber", 15, 1333, 0, 0, 0, 0, 0, 0, 0, 0, 0.11, 0.65, 3.63));
            _food.add(new Food(3, "Bacon (Raw)", 393, 50, 0, 0, 0, 0, 0, 0, 0, 0, 37.13, 13.66, 0));

            /*for (int x = 0; x < _food.size(); x++) {
                bdF.addFood(_food.get(x));
            }*/

        user = bdF.getUser();

        final TextInputEditText name = (TextInputEditText) view.findViewById(R.id.name);
        final TextInputEditText weight = (TextInputEditText) view.findViewById(R.id.weight);
        final TextInputEditText height = (TextInputEditText) view.findViewById(R.id.height);
        final TextInputEditText userBirthday = (TextInputEditText) view.findViewById(R.id.birthday);
        final AutoCompleteTextView sex = (AutoCompleteTextView) view.findViewById(R.id.sex);
        final AutoCompleteTextView activitylevel = (AutoCompleteTextView) view.findViewById(R.id.activityLevel);

        if (user != null) {
            name.setText(user.getName());
            weight.setText(String.valueOf(user.getWeight()));
            height.setText(String.valueOf(user.getHeight()));
            userBirthday.setText(user.getBirthday());
            sex.setText(sex.getAdapter().getItem(user.getSex()).toString(), false);
            sexSelected = sex.getAdapter().getItem(user.getSex()).toString();
            activitylevel.setText(activitylevel.getAdapter().getItem(user.getActivityLevel()).toString(), false);
            activityLevel = activitylevel.getAdapter().getItem(user.getActivityLevel()).toString();
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

        final MaterialButton save = (MaterialButton) view.findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateProfile(name, weight, height, userBirthday)){
                    Toast.makeText(getActivity().getApplicationContext(), "Fill all fields!", Toast.LENGTH_LONG).show();
                    return;
                }
                User userProfile = new User();
                userProfile.setName(name.getText().toString());
                userProfile.setWeight(Integer.parseInt(weight.getText().toString()));
                userProfile.setHeight(Integer.parseInt(height.getText().toString()));
                userProfile.setBirthday(userBirthday.getText().toString());
                userProfile.setSex(sexSelected.toUpperCase().equals("MALE") ? 0 : 1);
                int activityLevelInt;
                if (activityLevel.toUpperCase().startsWith("S")) activityLevelInt = 0;
                else if(activityLevel.toUpperCase().startsWith("A")) activityLevelInt = 1;
                else activityLevelInt = 2;
                userProfile.setActivityLevel(activityLevelInt);
                userProfile.setCalories(0);

                if(user != null) bdF.updateUser(userProfile);
                else { bdF.addUser(userProfile);
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

    private boolean validateProfile(TextInputEditText name, TextInputEditText weight, TextInputEditText height, TextInputEditText userBirthday){
        return !(name == null || weight == null || height == null ||
                 userBirthday == null || sexSelected == null || activityLevel == null ||
                 name.getText().toString().trim().equals("") ||
                 weight.getText().toString().trim().equals("") ||
                 height.getText().toString().trim().equals("") ||
                 userBirthday.getText().toString().trim().equals(""));
    }
}
