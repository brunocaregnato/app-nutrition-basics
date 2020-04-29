package com.example.nutritionbasics.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nutritionbasics.model.Meal;

import java.util.ArrayList;

public class BDmeal  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mealDB";
    private static final String TABLE_MEAL = "meal";
    private static final String ID = "id";
    private static final String D_DATE = "d_date";
    private static final String FOOD = "food";
    private static final String MEALTITLE = "mealtitle";
    private static final String OBSERVATION = "observation";
    private static final String TOTALCALORIES= "totalcalories";
    private static final String[] COLUNAS = { ID, D_DATE, FOOD, MEALTITLE, OBSERVATION, TOTALCALORIES };

    public BDmeal(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE meal ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "d_date DATE,"+
                "food INTEGER,"+
                "mealtitle TEXT,"+
                "observation TEXT,"+
                "totalcalories FLOAT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS meal");
        this.onCreate(db);
    }

    public void addMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(D_DATE, meal.getD_date());
        values.put(FOOD, meal.getFood());
        values.put(MEALTITLE, meal.getMealtitle());
        values.put(OBSERVATION, meal.getObservation());
        values.put(TOTALCALORIES, meal.getTotalcalories());
        db.insert(TABLE_MEAL, null, values);
        db.close();
    }

    public Meal getMeal(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEAL, // a. tabela
                COLUNAS, // b. colunas
                " id = ?", // c. colunas para comparar
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

    private Meal cursorToMeal(Cursor cursor) {
        Meal meal = new Meal();
        meal.setId(Integer.parseInt(cursor.getString(0)));
        meal.setD_date(cursor.getString(1));
        meal.setFood(Integer.parseInt(cursor.getString(2)));
        meal.setMealtitle(cursor.getString(3));
        meal.setObservation(cursor.getString(4));
        meal.setTotalcalories(Float.parseFloat(cursor.getString(5)));
        return meal;
    }

    public int updateMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(D_DATE, meal.getD_date());
        values.put(FOOD, new Integer(meal.getFood()));
        values.put(MEALTITLE, meal.getMealtitle());
        values.put(OBSERVATION, meal.getObservation());
        values.put(TOTALCALORIES, new Float(meal.getTotalcalories()));
        int i = db.update(TABLE_MEAL, //tabela
                values, // valores
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(meal.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }

    public int deleteMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_MEAL, //tabela
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(meal.getId()) });
        db.close();
        return i; // número de linhas excluídas
    }
}
