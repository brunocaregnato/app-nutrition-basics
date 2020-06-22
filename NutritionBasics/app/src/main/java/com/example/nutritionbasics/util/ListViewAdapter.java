package com.example.nutritionbasics.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.example.nutritionbasics.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<KeyPairBoolData> list;

    LayoutInflater mInflater;
    public ListViewAdapter(Context context, List<KeyPairBoolData> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        convertView=null;

        if (convertView == null) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.food_listview_adapter, null);

            EditText foodWeight = convertView.findViewById(R.id.foodWeight);

            TextView foodName = convertView.findViewById(R.id.foodName);
            foodName.setText(list.get(position).getName());

            foodWeight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    list.get(position).setObject(s);
                }
            });

        }

        return convertView;
    }

}