package com.example.nutritionbasics.activities.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.banco.Database;
import com.example.nutritionbasics.model.Food;
import com.example.nutritionbasics.parser.JSONParser;
import com.example.nutritionbasics.util.GetDataTask;
import com.example.nutritionbasics.util.InternetConnection;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Update extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_food_fragment, container, false);

        final MaterialButton save = view.findViewById(R.id.downloadButton);
        save.setOnClickListener(v -> {
            if (InternetConnection.checkConnection(getActivity().getApplicationContext())) {
                new GetDataTask(getContext()).execute();
            }
            else {
                Snackbar.make(view, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
            }
        });

        return view;
    }
}