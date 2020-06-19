package com.example.nutritionbasics.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionbasics.R;
import com.google.android.material.button.MaterialButton;

public class Info extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);

        final MaterialButton save = view.findViewById(R.id.profileButton);
        save.setOnClickListener(v -> {
            //Redireciona para o fragment profile
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Profile()).commit();
        });

        return view;
    }
}
