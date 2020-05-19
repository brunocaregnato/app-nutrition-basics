package com.example.nutritionbasics.activities.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.nutritionbasics.R;
import com.example.nutritionbasics.banco.BDuser;
import com.example.nutritionbasics.model.User;
import com.google.android.material.navigation.NavigationView;
import java.util.Calendar;

public class Profile extends Fragment implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private EditText birthday;
    private DatePickerDialog picker;
    private BDuser bd;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        bd = new BDuser(getActivity().getApplicationContext());

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

                if(user != null) bd.updateUser(userProfile);
                else bd.addUser(userProfile);

                Toast.makeText(getActivity().getApplicationContext(), "Profile Updated Successfully ", Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
