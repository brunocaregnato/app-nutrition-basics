package com.example.nutritionbasics.activities.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.banco.BDuser;
import com.example.nutritionbasics.model.User;

import java.text.NumberFormat;

public class Home extends Fragment {

    private BDuser bd;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        bd = new BDuser(getActivity().getApplicationContext());
        user = bd.getUser();

        if (user != null) {
            NumberFormat fmt = NumberFormat.getInstance();
            fmt.setGroupingUsed(false);
            fmt.setMaximumIntegerDigits(9999);
            fmt.setMaximumFractionDigits(99);

            final TextView name = (TextView) view.findViewById(R.id.welcome);
            name.setText("Welcome ".concat(String.valueOf(user.getName())).concat(("!")));

            final TextView calories = (TextView) view.findViewById(R.id.calories);
            calories.setText(String.format("%.2f", user.getCalories()).concat(" cal"));

            final TextView protein = (TextView) view.findViewById(R.id.protein);
            protein.setText(String.format("%.2f", user.getProteins()).concat(" cal"));

            final TextView carbohydrates = (TextView) view.findViewById(R.id.carbohydrates);
            carbohydrates.setText(String.format("%.2f", user.getCarbohydrates()).concat(" cal"));

            final TextView fat = (TextView) view.findViewById(R.id.fat);
            fat.setText(String.format("%.2f", user.getFat()).concat(" cal"));
        }
        else getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Profile()).commit();


        return view;
    }
}
