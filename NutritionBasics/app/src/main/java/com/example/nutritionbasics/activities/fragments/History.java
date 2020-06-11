package com.example.nutritionbasics.activities.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.activities.Adapter;
import com.example.nutritionbasics.activities.MainActivity;
import com.example.nutritionbasics.activities.RecyclerItemClickListener;
import com.example.nutritionbasics.banco.BDfood;
import com.example.nutritionbasics.model.HistoryMeal;
import com.example.nutritionbasics.model.Meal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class History extends Fragment {

    private BDfood bd;
    private List<HistoryMeal> historyList;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        bd = new BDfood(getActivity().getApplicationContext());

        this.populateHistoryList();
        this.setRecyclerView(view);

        return view;
    }

    private void setRecyclerView(View view){
        recyclerView = view.findViewById(R.id.historyDates);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));

        final Adapter adapter = new Adapter(historyList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext().getApplicationContext(), recyclerView ,
                        (view1, position) -> getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new Details(historyList.get(position).getId())).commit())
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateHistoryList(){
        historyList = new ArrayList<>();
        for(Meal meal : bd.getAllMeals()){
            HistoryMeal historyMeal = new HistoryMeal();
            historyMeal.setId(meal.getId());
            historyMeal.setDate(meal.getD_date());
            historyMeal.setMealTitle(meal.getMealtitle());
            historyList.add(historyMeal);
        }

        historyList.sort(Collections.reverseOrder(Comparator.comparing(historyMeal -> historyMeal.getDate() + historyMeal.getId())));
    }
}
