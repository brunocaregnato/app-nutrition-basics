package com.example.nutritionbasics.activities.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.nutritionbasics.banco.Database;
import com.github.mikephil.charting.charts.BarChart;
import com.example.nutritionbasics.R;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Evolution extends Fragment {

    private BarChart barChart;
    private Database bd;
    private ArrayList days;
    private Map daysPosition;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.evolution_fragment, container, false);
        bd = new Database(getActivity().getApplicationContext());

        days = new ArrayList();
        daysPosition = new ArrayMap();
        populateDates();

        barChart = view.findViewById(R.id.barChart);
        ArrayList calories = new ArrayList();

        bd.getAllMealsRange().forEach(meal -> {
            int caloriesDay = meal.getFoods().stream().mapToInt(food -> (int) food.getFood().getCalories()).sum();

            daysPosition.forEach((day, position) -> {
                if(day.equals(meal.getD_date())) {
                    calories.add(new BarEntry(caloriesDay, Integer.parseInt(String.valueOf(position))));
                }
            });
        });

        BarDataSet bardataset = new BarDataSet(calories, "Calories consumed");
        barChart.animateY(1000);
        BarData data = new BarData(days, bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(data);

        return view;
    }

    private void populateDates(){
        days.add(getCalculatedDate("dd/MM/yyyy", -7));
        daysPosition.put(getCalculatedDate("dd/MM/yyyy", -7), 0);
        days.add(getCalculatedDate("dd/MM/yyyy", -6));
        daysPosition.put(getCalculatedDate("dd/MM/yyyy", -6), 1);
        days.add(getCalculatedDate("dd/MM/yyyy", -5));
        daysPosition.put(getCalculatedDate("dd/MM/yyyy", -5), 2);
        days.add(getCalculatedDate("dd/MM/yyyy", -4));
        daysPosition.put(getCalculatedDate("dd/MM/yyyy", -4), 3);
        days.add(getCalculatedDate("dd/MM/yyyy", -3));
        daysPosition.put(getCalculatedDate("dd/MM/yyyy", -3), 4);
        days.add(getCalculatedDate("dd/MM/yyyy", -2));
        daysPosition.put(getCalculatedDate("dd/MM/yyyy", -2), 5);
        days.add(getCalculatedDate("dd/MM/yyyy", -1));
        daysPosition.put(getCalculatedDate("dd/MM/yyyy", -1), 6);
        days.add(getCalculatedDate("dd/MM/yyyy", 0));
        daysPosition.put(getCalculatedDate("dd/MM/yyyy", 0), 7);

    }

    private static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }
}
