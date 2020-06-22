package com.example.nutritionbasics.activities.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.nutritionbasics.R;
import com.example.nutritionbasics.banco.BDfood;
import com.example.nutritionbasics.model.Food;
import com.example.nutritionbasics.model.User;
import com.example.nutritionbasics.parser.JSONParser;
import com.example.nutritionbasics.util.InternetConnection;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Profile extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText birthday;
    private DatePickerDialog picker;
    private BDfood bdF;
    private User user;
    private List<Food> _food;
    private ArrayList<Food> list;

    String sexSelected, activityLevel;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        bdF = new BDfood(getActivity().getApplicationContext());

        AutoCompleteTextView spinner = view.findViewById(R.id.sex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.sexs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener((parent, view1, position, id) -> sexSelected = (String) parent.getItemAtPosition(position));

        AutoCompleteTextView spinnerActivity = view.findViewById(R.id.activityLevel);
        adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.activitiesLevels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivity.setAdapter(adapter);
        spinnerActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        activityLevel = (String) parent.getItemAtPosition(position);
                                                    }
                                                });

            _food = new ArrayList<>();
            //int id, String foodName, int calories, int weight, float vitaminB, float vitaminD, float vitaminA, float vitaminC, float vitaminE, float calcium, float iron, float zinc, float fat, float protein, float carbohydrate) {
            _food.add(new Food(1, "Pears", 57, 350, 0, 0, 0, 0, 0, 0, 0, 0, 0.14, 0.36, 15.23));
            _food.add(new Food(2, "Cucumber", 15, 1333, 0, 0, 0, 0, 0, 0, 0, 0, 0.11, 0.65, 3.63));
            _food.add(new Food(3, "Bacon (Raw)", 393, 50, 0, 0, 0, 0, 0, 0, 0, 0, 37.13, 13.66, 0));

            /*for (int x = 0; x < _food.size(); x++) {
                bdF.addFood(_food.get(x));
            }*/

        user = bdF.getUser();

        final TextInputEditText name = view.findViewById(R.id.name);
        final TextInputEditText weight = view.findViewById(R.id.weight);
        final TextInputEditText height = view.findViewById(R.id.height);
        final TextInputEditText userBirthday = view.findViewById(R.id.birthday);
        final AutoCompleteTextView sex = view.findViewById(R.id.sex);
        final AutoCompleteTextView activitylevel = view.findViewById(R.id.activityLevel);

        if (user != null) {
            name.setText(user.getName());
            weight.setText(String.valueOf(user.getWeight()));
            height.setText(String.valueOf(user.getHeight()));
            userBirthday.setText(user.getBirthday());
            sex.setText(sex.getAdapter().getItem(user.getSex()).toString(), false);
            sexSelected = sex.getAdapter().getItem(user.getSex()).toString();
            activitylevel.setText(activitylevel.getAdapter().getItem(user.getActivityLevel()).toString(), false);
            activityLevel = activitylevel.getAdapter().getItem(user.getActivityLevel()).toString();
        }

        birthday = view.findViewById(R.id.birthday);
        birthday.setInputType(InputType.TYPE_NULL);
        birthday.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            // date picker dialog
            picker = new DatePickerDialog(getActivity(), (view12, year1, monthOfYear, dayOfMonth) -> birthday.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", monthOfYear + 1) + "/" + year1), year, month, day);
            picker.show();
        });

        final MaterialButton save = view.findViewById(R.id.saveButton);
        save.setOnClickListener(v -> {
            if(!validateProfile(name, weight, height, userBirthday)){
                Toast.makeText(getActivity().getApplicationContext(), "Fill all fields!", Toast.LENGTH_LONG).show();
                return;
            }
            User userProfile = new User();
            userProfile.setName(name.getText().toString());
            userProfile.setWeight(Integer.parseInt(weight.getText().toString()));
            userProfile.setHeight(Integer.parseInt(height.getText().toString()));
            userProfile.setBirthday(userBirthday.getText().toString());
            userProfile.setSex(sexSelected.toUpperCase().equals("MALE") ? 0 : 1);
            int activityLevelInt;
            if (activityLevel.toUpperCase().startsWith("S")) activityLevelInt = 0;
            else if(activityLevel.toUpperCase().startsWith("A")) activityLevelInt = 1;
            else activityLevelInt = 2;
            userProfile.setActivityLevel(activityLevelInt);
            userProfile.setCalories(0);

            if(user != null) bdF.updateUser(userProfile);
            else { bdF.addUser(userProfile);
                /*for (int x = 0; x < _food.size(); x++) {
                    bdF.addFood(_food.get(x));
                }*/
                list = new ArrayList<>();
                if (InternetConnection.checkConnection(getActivity().getApplicationContext())) {
                    new Profile.GetDataTask().execute();
                } else {
                    Snackbar.make(view, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }
            }

            //Redireciona para o fragment home
            Toast.makeText(getActivity().getApplicationContext(), "Profile Updated Successfully ", Toast.LENGTH_LONG).show();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //do whatever
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do whatever
    }

    private boolean validateProfile(TextInputEditText name, TextInputEditText weight, TextInputEditText height, TextInputEditText userBirthday){
        return !(name == null || weight == null || height == null ||
                 userBirthday == null || sexSelected == null || activityLevel == null ||
                 name.getText().toString().trim().equals("") ||
                 weight.getText().toString().trim().equals("") ||
                 height.getText().toString().trim().equals("") ||
                 userBirthday.getText().toString().trim().equals(""));
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
