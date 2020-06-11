package com.example.nutritionbasics.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.banco.BDfood;
import com.example.nutritionbasics.model.Meal;

public class Details extends Fragment {

    private int id;
    private BDfood bd;
    private Meal meal;

    public Details(int id){
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        bd = new BDfood(getActivity().getApplicationContext());

        meal = bd.getMeal(id);

        TextView detailTitle = view.findViewById(R.id.detailTitle);
        detailTitle.setText(meal.getMealtitle() + " Details");

        return view;
    }
}
