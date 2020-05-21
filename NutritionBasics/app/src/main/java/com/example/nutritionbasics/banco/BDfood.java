package com.example.nutritionbasics.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nutritionbasics.model.Food;

import java.util.ArrayList;
import java.util.List;

public class BDfood extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "foodDB";
    private static final String TABLE_FOOD = "food";
    private static final String ID = "id";
    private static final String FOOD_NAME = "foodName";
    private static final String CALORIES = "calories";
    private static final String WEIGHT = "weight";
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
    private static final String[] COLUNAS = { ID, FOOD_NAME, CALORIES, WEIGHT, VITAMINB, VITAMIND, VITAMINA, VITAMINC, VITAMINE, CALCIUM, IRON, ZINC, FAT, PROTEIN, CARBOHYDRATE };

    public BDfood(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE food ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "foodName TEXT,"+
                "calories INTEGER,"+
                "weight INTEGER,"+
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


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS food");
        this.onCreate(db);

        /*if(oldVersion != newVersion) {
            List<Food> _food = new ArrayList<>();
            //int id, String foodName, int calories, int weight, float vitaminB, float vitaminD, float vitaminA, float vitaminC, float vitaminE, float calcium, float iron, float zinc, float fat, float protein, float carbohydrate) {
            _food.add(new Food(1, "Pears", 57, 350, 0, 0, 0, 0, 0, 0, 0, 0, 0.14, 0.36, 15.23));
            _food.add(new Food(2, "Cucumber", 15, 1333, 0, 0, 0, 0, 0, 0, 0, 0, 0.11, 0.65, 3.63));
            _food.add(new Food(3, "Bacon (Raw)", 393, 50, 0, 0, 0, 0, 0, 0, 0, 0, 37.13, 13.66, 0));

            for (int x = 0; x < _food.size(); x++) {
                addFood(_food.get(x));
            }
        }*/

    }

    public void addFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, food.getId());
        values.put(FOOD_NAME, food.getFoodName());
        values.put(CALORIES, food.getCalories());
        values.put(WEIGHT, food.getWeight());
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
            Food food = cursorToFood(cursor);
            return food;
        }
    }

    private Food cursorToFood(Cursor cursor) {
        Food food = new Food();
        food.setId(Integer.parseInt(cursor.getString(0)));
        food.setFoodName(cursor.getString(1));
        food.setCalories(Double.parseDouble(cursor.getString(2)));
        food.setWeight(Double.parseDouble(cursor.getString(3)));
        food.setVitaminB(Float.parseFloat(cursor.getString(4)));
        food.setVitaminD(Float.parseFloat(cursor.getString(5)));
        food.setVitaminA(Float.parseFloat(cursor.getString(6)));
        food.setVitaminC(Float.parseFloat(cursor.getString(7)));
        food.setVitaminE(Float.parseFloat(cursor.getString(8)));
        food.setCalcium(Float.parseFloat(cursor.getString(9)));
        food.setIron(Float.parseFloat(cursor.getString(10)));
        food.setZinc(Float.parseFloat(cursor.getString(11)));
        food.setFat(Double.parseDouble(cursor.getString(12)));
        food.setProtein(Double.parseDouble(cursor.getString(13)));
        food.setCarbohydrate(Double.parseDouble(cursor.getString(14)));
        return food;
    }

    public int updateFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOOD_NAME, food.getFoodName());
        values.put(CALORIES, new Double(food.getCalories()));
        values.put(WEIGHT, new Double(food.getWeight()));
        values.put(VITAMINB, new Float(food.getVitaminB()));
        values.put(VITAMIND, new Float(food.getVitaminD()));
        values.put(VITAMINA, new Float(food.getVitaminA()));
        values.put(VITAMINC, new Float(food.getVitaminC()));
        values.put(VITAMINE, new Float(food.getVitaminE()));
        values.put(CALCIUM, new Float(food.getCalcium()));
        values.put(IRON, new Float(food.getIron()));
        values.put(ZINC, new Float(food.getZinc()));
        values.put(FAT, new Double(food.getFat()));
        values.put(PROTEIN, new Double(food.getProtein()));
        values.put(CARBOHYDRATE, new Double(food.getCarbohydrate()));
        int i = db.update(TABLE_FOOD, //tabela
                values, // valores
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(food.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }

    public int deleteFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_FOOD, //tabela
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(food.getId()) });
        db.close();
        return i; // número de linhas excluídas
    }
}
