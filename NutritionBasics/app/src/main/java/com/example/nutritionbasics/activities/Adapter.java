package com.example.nutritionbasics.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutritionbasics.R;
import com.example.nutritionbasics.model.HistoryMeal;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<HistoryMeal> historyMealList;

    public Adapter(List<HistoryMeal> historyMealList) {
        this.historyMealList = historyMealList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.setData(historyMealList.get(position));
    }

    @Override
    public int getItemCount() {
        return historyMealList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView historyText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            historyText = itemView.findViewById(R.id.historyText);
        }

        private void setData(HistoryMeal historyMeal) {
            historyText.setText(historyMeal.getDate().concat(" - ").concat(historyMeal.getMealTitle()));
        }

        @Override
        public void onClick(View v) {
        }
    }
}
