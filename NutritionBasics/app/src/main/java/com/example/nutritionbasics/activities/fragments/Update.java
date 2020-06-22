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
import com.example.nutritionbasics.banco.BDfood;
import com.example.nutritionbasics.model.Food;
import com.example.nutritionbasics.parser.JSONParser;
import com.example.nutritionbasics.util.InternetConnection;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Update extends Fragment {

    private BDfood bdF;
    private ArrayList<Food> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_food_fragment, container, false);

        final MaterialButton save = view.findViewById(R.id.downloadButton);
        save.setOnClickListener(v -> {
            bdF = new BDfood(getActivity().getApplicationContext());
            list = new ArrayList<>();
            if (InternetConnection.checkConnection(getActivity().getApplicationContext())) {
                new Update.GetDataTask().execute();
            } else {
                Snackbar.make(view, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
            }
        });

        return view;
    }

    class GetDataTask extends AsyncTask<Void, Void, Void> {
        //class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */

            //x=list.size();
            x=0;

            if(x==0)
                jIndex=0;
            else
                jIndex=x;

            dialog = new ProgressDialog(getContext());
            dialog.setTitle("Hey Wait Please..."+x);
            dialog.setMessage("Loading data");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.getDataFromWeb();
            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if(jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        //JSONArray array = jsonObject.getJSONArray("Sheet1");
                        JSONArray array = jsonObject.getJSONArray("records");

                        /**
                         * Check Length of Array...
                         */
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */
                                Food model = new Food();
                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                int f_id = innerObject.getInt("id");
                                String f_foodName = innerObject.getString("foodName");
                                double f_calories = innerObject.getDouble("calories");
                                double f_weight = innerObject.getDouble("weight");
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

                                //Log.i("vENI", f_id + "-" + f_foodName + "-" + f_calories + "-" + f_weight + "-" +  f_vitaminB + "-" + f_vitaminD  + "-" + f_vitaminA  + "-" + f_vitaminC + "-" + f_vitaminE + "-" + f_calcium + "-" + f_iron + "-" + f_zinc + "-" + f_fat + "-" + f_protein + "-" + f_carbohydrate) ;
                                //Toast.makeText(getActivity().getApplicationContext(), f_foodName, Toast.LENGTH_LONG).show();

                                /**
                                 * Getting Object from Object "phone"
                                 */
                                //JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                //String phone = phoneObject.getString(Keys.KEY_MOBILE);

                                model.setId(f_id);
                                model.setFoodName(f_foodName);
                                model.setCalories(f_calories);
                                model.setWeight(f_weight);
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
                                //                              model.setImage(image);
                                /**
                                 * Adding name and phone concatenation in List...
                                 */
                                list.add(model);
                                //Log.i("vENI obj", model.getId() + "-" + model.getFoodName() + "-" + model.getCalories() + "-" + model.getWeight() + "-" +  model.getVitaminB() + "-" + model.getVitaminD()  + "-" + model.getVitaminA()  + "-" + model.getVitaminC() + "-" + model.getVitaminE() + "-" + model.getCalcium() + "-" + model.getIron() + "-" + model.getZinc() + "-" + model.getFat() + "-" + model.getProtein() + "-" + model.getCarbohydrate()) ;
                            }

                            if(list.size() > 0){
                                bdF.deleteAllFood();
                                for (int x = 0; x < list.size(); x++) {
                                    bdF.addFood(list.get(x));
                                }
                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            if(list.size() > 0) {
                //adapter.notifyDataSetChanged();
            } else {
                //Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }

    }

}

