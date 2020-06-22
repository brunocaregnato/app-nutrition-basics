package com.example.nutritionbasics.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.Nullable;

import com.example.nutritionbasics.banco.Database;
import com.example.nutritionbasics.model.Food;
import com.example.nutritionbasics.parser.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDataTask extends AsyncTask<Void, Void, Void> {

    private ArrayList<Food> list = new ArrayList<>();
    private Database bdF;
    private ProgressDialog dialog;
    private int jIndex;
    private Context context;

    public GetDataTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        bdF = new Database(context.getApplicationContext());

        dialog = new ProgressDialog(this.context);
        dialog.setTitle("Wait");
        dialog.setMessage("Downloading data...");
        dialog.show();
    }

    @Nullable
    @Override
    protected Void doInBackground(Void... params) {
        JSONObject jsonObject = JSONParser.getDataFromWeb();
        try {
            if (jsonObject != null) {
                if(jsonObject.length() > 0) {
                    JSONArray array = jsonObject.getJSONArray("records");
                    int lenArray = array.length();
                    if(lenArray > 0) {
                        for( ; jIndex < lenArray; jIndex++) {
                            Food model = new Food();

                            JSONObject innerObject = array.getJSONObject(jIndex);
                            int f_id = innerObject.getInt("id");
                            String f_foodName = innerObject.getString("foodName");
                            double f_calories = innerObject.getDouble("calories");
                            double f_vitaminB = innerObject.getDouble("vitaminB");
                            double f_vitaminD = innerObject.getDouble("vitaminD");
                            double f_vitaminA = innerObject.getDouble("vitaminA");
                            double f_vitaminC = innerObject.getDouble("vitaminC");
                            double f_vitaminE = innerObject.getDouble("vitaminE");
                            double f_calcium = innerObject.getDouble("calcium");
                            double f_iron = innerObject.getDouble("iron");
                            double f_zinc = innerObject.getDouble("zinc");
                            double f_fat = innerObject.getDouble("fat");
                            double f_protein = innerObject.getDouble("protein");
                            double f_carbohydrate = innerObject.getDouble("carbohydrate");

                            model.setId(f_id);
                            model.setFoodName(f_foodName);
                            model.setCalories(f_calories);
                            model.setVitaminB(Float.parseFloat(String.valueOf(f_vitaminB)));
                            model.setVitaminD(Float.parseFloat(String.valueOf(f_vitaminD)));
                            model.setVitaminA(Float.parseFloat(String.valueOf(f_vitaminA)));
                            model.setVitaminC(Float.parseFloat(String.valueOf(f_vitaminC)));
                            model.setVitaminE(Float.parseFloat(String.valueOf(f_vitaminE)));
                            model.setCalcium(Float.parseFloat(String.valueOf(f_calcium)));
                            model.setIron(Float.parseFloat(String.valueOf(f_iron)));
                            model.setZinc(Float.parseFloat(String.valueOf(f_zinc)));
                            model.setFat(f_fat);
                            model.setProtein(f_protein);
                            model.setCarbohydrate(f_carbohydrate);

                            list.add(model);
                        }

                        if(list.size() > 0){
                            bdF.deleteAllFood();
                            for (int x = 0; x < list.size(); x++) {
                                bdF.addFood(list.get(x));
                            }
                        }
                    }
                }
            }
        } catch (JSONException je) {
            Log.e(JSONParser.TAG, je.getLocalizedMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
    }
}