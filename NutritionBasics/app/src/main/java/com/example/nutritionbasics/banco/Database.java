package com.example.nutritionbasics.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.nutritionbasics.model.Food;
import com.example.nutritionbasics.model.Meal;
import com.example.nutritionbasics.model.User;
import com.example.nutritionbasics.model.UserFood;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "foodDB";

    //TABLES
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_MEAL = "meal";
    private static final String TABLE_USUARIO = "usuario";
    private static final String TABLE_FOODS_MEAL = "foods_meal";

    //CAMPOS FOOD
    private static final String ID_FOOD = "id_food";
    private static final String FOOD_NAME = "foodName";
    private static final String CALORIES_FOOD = "calories_food";
    private static final String VITAMINB = "vitaminB";
    private static final String VITAMIND = "vitaminD";
    private static final String VITAMINA = "vitaminA";
    private static final String VITAMINC = "vitaminC";
    private static final String VITAMINE = "vitaminE";
    private static final String CALCIUM = "calcium";
    private static final String IRON = "iron";
    private static final String ZINC = "zinc";
    private static final String FAT = "fat";
    private static final String PROTEIN = "protein";
    private static final String CARBOHYDRATE = "carbohydrate";
    private static final String[] COLUNAS = { ID_FOOD, FOOD_NAME, CALORIES_FOOD, VITAMINB, VITAMIND, VITAMINA, VITAMINC, VITAMINE, CALCIUM, IRON, ZINC, FAT, PROTEIN, CARBOHYDRATE };
    private static final String[] COLUNASFILTER = { ID_FOOD, FOOD_NAME };

    //CAMPOS MEAL
    private static final String ID_MEAL = "id_meal";
    private static final String D_DATE = "d_date";
    private static final String MEALTITLE = "mealtitle";
    private static final String OBSERVATION = "observation";
    private static final String TOTALCALORIES= "totalcalories";
    private static final String[] COLUNAS_MEAL = { ID_MEAL, D_DATE, MEALTITLE, OBSERVATION, TOTALCALORIES };

    //CAMPOS USER
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String BIRTHDAY = "birthday";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String ACTIVITYLEVEL= "activitylevel";
    private static final String SEX = "sex";
    private static final String CALORIES = "calories";
    private static final String[] COLUNAS_USER = { ID, NAME, BIRTHDAY, HEIGHT, WEIGHT, ACTIVITYLEVEL, SEX, CALORIES };

    //CAMPOS FOODS_MEAL
    private static final String ID_FM_MEAL = "id_meal";
    private static final String ID_FM_FOOD = "id_food";
    private static final String FOOD_WEIGHT = "food_weight";
    private static final String[] COLUNAS_FOODS_MEAL = { ID_FM_MEAL, ID_FM_FOOD, FOOD_WEIGHT };

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE food ("+
                "id_food INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "foodName TEXT,"+
                "calories_food INTEGER,"+
                "weight_food INTEGER,"+
                "vitaminB FLOAT," +
                "vitaminD FLOAT," +
                "vitaminA FLOAT," +
                "vitaminC FLOAT," +
                "vitaminE FLOAT," +
                "calcium FLOAT," +
                "iron FLOAT," +
                "zinc FLOAT," +
                "fat FLOAT," +
                "protein FLOAT," +
                "carbohydrate FLOAT)";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE_MEAL = "CREATE TABLE meal ("+
                "id_meal INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "d_date DATE,"+
                "mealtitle TEXT,"+
                "observation TEXT,"+
                "totalcalories DOUBLE)";
        db.execSQL(CREATE_TABLE_MEAL);

        String CREATE_TABLE_USER = "CREATE TABLE usuario ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT,"+
                "birthday DATE,"+
                "height INTEGER,"+
                "weight INTEGER,"+
                "activitylevel INTEGER,"+
                "sex INTEGER,"+
                "calories DOUBLE)";
        db.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_FOODS_MEAL = "CREATE TABLE foods_meal ("+
                "id_meal INTEGER," +
                "id_food INTEGER," +
                "food_weight INTEGER," +
                "PRIMARY KEY (id_meal, id_food))";
        db.execSQL(CREATE_TABLE_FOODS_MEAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS food");
        db.execSQL("DROP TABLE IF EXISTS meal");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS foods_meal");
        this.onCreate(db);
    }

    //FUNCTIONS FOOD --------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void addFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_FOOD, food.getId());
        values.put(FOOD_NAME, food.getFoodName());
        values.put(CALORIES_FOOD, food.getCalories());
        values.put(VITAMINB, food.getVitaminB());
        values.put(VITAMIND, food.getVitaminD());
        values.put(VITAMINA, food.getVitaminA());
        values.put(VITAMINC, food.getVitaminC());
        values.put(VITAMINE, food.getVitaminE());
        values.put(CALCIUM, food.getCalcium());
        values.put(IRON, food.getIron());
        values.put(ZINC, food.getZinc());
        values.put(FAT, food.getFat());
        values.put(PROTEIN, food.getProtein());
        values.put(CARBOHYDRATE, food.getCarbohydrate());
        db.insert(TABLE_FOOD, null, values);
        db.close();
    }

    private boolean hasFood(SQLiteDatabase db){
        return DatabaseUtils.queryNumEntries(db, TABLE_FOOD) > 0;
    }

    public Food getFood(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        if (!hasFood(db)) return null;

        Cursor cursor = db.query(TABLE_FOOD, // a. tabela
                COLUNAS, // b. colunas
                " id_food = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            return null;
        } else {
            cursor.moveToFirst();
            Food food = cursorToFood(cursor);
            return food;
        }
    }

    private Food cursorToFood(Cursor cursor) {
        Food food = new Food();
        food.setId(Integer.parseInt(cursor.getString(0)));
        food.setFoodName(cursor.getString(1));
        food.setCalories(Double.parseDouble(cursor.getString(2)));
        food.setVitaminB(Float.parseFloat(cursor.getString(3)));
        food.setVitaminD(Float.parseFloat(cursor.getString(4)));
        food.setVitaminA(Float.parseFloat(cursor.getString(5)));
        food.setVitaminC(Float.parseFloat(cursor.getString(6)));
        food.setVitaminE(Float.parseFloat(cursor.getString(7)));
        food.setCalcium(Float.parseFloat(cursor.getString(8)));
        food.setIron(Float.parseFloat(cursor.getString(9)));
        food.setZinc(Float.parseFloat(cursor.getString(10)));
        food.setFat(Double.parseDouble(cursor.getString(11)));
        food.setProtein(Double.parseDouble(cursor.getString(12)));
        food.setCarbohydrate(Double.parseDouble(cursor.getString(13)));
        return food;
    }


    public int deleteAllFood() {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_FOOD, null, null);
        db.close();
        return i; // número de linhas excluídas
    }

    public List<Food> getAllFoodFilter(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOOD, // a. tabela
                COLUNASFILTER, // b. colunas
                null, // c. colunas para comparar
                null, // d. parâmetros
                null, // e. group by
                null, // f. having
                null); // h. limit
        if (cursor == null) {
            return null;
        } else {
            List<Food> food = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    Food _food = new Food();
                    _food.setId(Integer.parseInt(cursor.getString(0)));
                    _food.setFoodName(cursor.getString(1));
                    food.add(_food);
                } while (cursor.moveToNext());
            }

            return food;
        }
    }

    //FUNCTIONS MEAL --------------------------------------------------------------------------------------------------------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(D_DATE, meal.getD_date());
        values.put(MEALTITLE, meal.getMealtitle());
        values.put(OBSERVATION, meal.getObservation());
        values.put(TOTALCALORIES, meal.getTotalcalories());
        db.insert(TABLE_MEAL, null, values);

        final ContentValues contentValues = new ContentValues();

        meal.getFoods().forEach(food -> {
            contentValues.put(ID_FM_MEAL, getIdLastMeal());
            contentValues.put(ID_FM_FOOD, food.getFood().getId());
            contentValues.put(FOOD_WEIGHT, food.getWeight());

            db.insert(TABLE_FOODS_MEAL, null, contentValues);
        });

        db.close();
    }

    public Meal getMeal(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEAL, // a. tabela
                COLUNAS_MEAL, // b. colunas
                " id_meal = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            return null;
        } else {
            cursor.moveToFirst();
            Meal meal = cursorToMeal(cursor);
            return meal;
        }
    }

    private int getIdLastMeal() {
        SQLiteDatabase db = this.getReadableDatabase();
        int id;
        Cursor cursor = db.query(TABLE_MEAL, // a. tabela
                COLUNAS_MEAL, // b. colunas
                null, // c. colunas para comparar
                null, // d. parâmetros
                null, // e. group by
                null, // f. having
                ID_MEAL, // g. order by
                null); // h. limit
        if (cursor == null) {
            return 0;
        }
        else {
            cursor.moveToLast();
            return cursorToMeal(cursor).getId();
        }
    }

    private Meal cursorToMeal(Cursor cursor) {
        Meal meal = new Meal();
        meal.setId(Integer.parseInt(cursor.getString(0)));
        meal.setD_date(cursor.getString(1));
        meal.setMealtitle(cursor.getString(2));
        meal.setObservation(cursor.getString(3));
        meal.setTotalcalories(Double.parseDouble(cursor.getString(4)));



        return meal;
    }

    public List<Meal> getAllMealDay() {
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Cursor cursor = db.query(TABLE_MEAL, // a. tabela
                COLUNAS_MEAL, // b. colunas
                " d_date = ?", // c. colunas para comparar
                new String[] { sdf.format(new Date()) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null); // h. limit
        if (cursor == null) {
            return null;
        }
        else {
            List<Meal> meal = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    Meal _meal = new Meal();
                    _meal.setId(Integer.parseInt(cursor.getString(0)));
                    _meal.setD_date(cursor.getString(1));
                    _meal.setMealtitle(cursor.getString(2));
                    _meal.setObservation(cursor.getString(3));
                    _meal.setTotalcalories(Double.parseDouble(cursor.getString(4)));

                    _meal.setFoods(getFoodsFromMeal(_meal.getId()));

                    meal.add(_meal);
                } while (cursor.moveToNext());
            }

            return meal;
        }
    }

    public List<Meal> getAllMealsRange() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEAL, // a. tabela
                COLUNAS_MEAL, // b. colunas
                " d_date between ? and ?", // c. colunas para comparar
                new String[] { getCalculatedDate("dd/MM/yyyy", -7), getCalculatedDate("dd/MM/yyyy", 0) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null); // h. limit
        if (cursor == null) {
            return null;
        }
        else {
            List<Meal> meal = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    Meal _meal = new Meal();
                    _meal.setId(Integer.parseInt(cursor.getString(0)));
                    _meal.setD_date(cursor.getString(1));
                    _meal.setMealtitle(cursor.getString(2));
                    _meal.setObservation(cursor.getString(3));
                    _meal.setTotalcalories(Double.parseDouble(cursor.getString(4)));

                    _meal.setFoods(getFoodsFromMeal(_meal.getId()));

                    meal.add(_meal);
                } while (cursor.moveToNext());
            }

            return meal;
        }
    }

    private static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    public List<Meal> getAllMeals(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEAL, // a. tabela
                COLUNAS_MEAL, // b. colunas
                null, // c. colunas para comparar
                null, // d. parâmetros
                null, // e. group by
                null, // f. having
                null); // h. limit
        if (cursor == null) {
            return null;
        } else {
            List<Meal> meal = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    Meal _meal = new Meal();
                    _meal.setId(Integer.parseInt(cursor.getString(0)));
                    _meal.setD_date(cursor.getString(1));
                    _meal.setMealtitle(cursor.getString(2));
                    _meal.setObservation(cursor.getString(3));
                    _meal.setTotalcalories(Double.parseDouble(cursor.getString(4)));

                    _meal.setFoods(getFoodsFromMeal(_meal.getId()));
                    meal.add(_meal);
                } while (cursor.moveToNext());
            }

            return meal;
        }
    }

    public List<UserFood> getFoodsFromMeal(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOODS_MEAL, // a. tabela
                COLUNAS_FOODS_MEAL, // b. colunas
                "id_meal = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null); // h. limit
        if (cursor == null) {
            return null;
        }
        else {
            List<UserFood> foods = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    UserFood food = new UserFood();
                    food.setFood(getFood(Integer.parseInt(cursor.getString(1))));
                    food.setWeight(Integer.parseInt(cursor.getString(2)));

                    foods.add(food);
                } while (cursor.moveToNext());
            }

            return foods;
        }
    }

    //FUNCTIONS USER --------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, 1);
        values.put(NAME, user.getName());
        values.put(BIRTHDAY, user.getBirthday());
        values.put(HEIGHT, user.getHeight());
        values.put(WEIGHT, user.getWeight());
        values.put(ACTIVITYLEVEL, user.getActivityLevel());
        values.put(SEX, user.getSex());
        values.put(CALORIES, user.getCalories());
        db.insert(TABLE_USUARIO, null, values);
        db.close();
    }

    private boolean hasUser(SQLiteDatabase db){
        return DatabaseUtils.queryNumEntries(db, TABLE_USUARIO) > 0;
    }

    public User getUser() {
        SQLiteDatabase db = this.getReadableDatabase();

        if (!hasUser(db)) return null;

        Cursor cursor = db.query(TABLE_USUARIO, // a. tabela
                COLUNAS_USER, // b. colunas
                " id = ?", // c. colunas para comparar
                new String[] { String.valueOf(1) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            return null;
        }
        else {
            cursor.moveToFirst();
            User user = cursorToUser(cursor);
            return user;
        }
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(Integer.parseInt(cursor.getString(0)));
        user.setName(cursor.getString(1));
        user.setBirthday(cursor.getString(2));
        user.setHeight(Integer.parseInt(cursor.getString(3)));
        user.setWeight(Integer.parseInt(cursor.getString(4)));
        user.setActivityLevel(Integer.parseInt(cursor.getString(5)));
        user.setSex(Integer.parseInt(cursor.getString(6)));
        user.setCalories(Double.parseDouble(cursor.getString(7)));
        return user;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(BIRTHDAY, user.getBirthday());
        values.put(HEIGHT, new Integer(user.getHeight()));
        values.put(WEIGHT, new Integer(user.getWeight()));
        values.put(ACTIVITYLEVEL, new Integer(user.getActivityLevel()));
        values.put(SEX, new Integer(user.getSex()));
        values.put(CALORIES, new Double(user.getCalories()));
        int i = db.update(TABLE_USUARIO, //tabela
                values, // valores
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(1) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }
}
