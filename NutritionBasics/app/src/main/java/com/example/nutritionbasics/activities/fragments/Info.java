package com.example.nutritionbasics.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.model.User;
import com.google.android.material.button.MaterialButton;

public class Info extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);

        final MaterialButton save = (MaterialButton) view.findViewById(R.id.profileButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Redireciona para o fragment profile
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Profile()).commit();
            }
        });

        return view;
    }
}
