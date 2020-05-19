package com.example.nutritionbasics.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nutritionbasics.model.User;

import java.util.ArrayList;

public class BDuser extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "userDB";
    private static final String TABLE_USUARIO = "usuario";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String BIRTHDAY = "birthday";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String ACTIVITYLEVEL= "activitylevel";
    private static final String SEX = "sex";
    private static final String CALORIAS = "calorias";
    private static final String[] COLUNAS = { ID, NAME, BIRTHDAY, HEIGHT, WEIGHT, ACTIVITYLEVEL, SEX, CALORIAS };

    public BDuser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE usuario ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT,"+
                "birthday DATE,"+
                "height INTEGER,"+
                "weight INTEGER,"+
                "activitylevel INTEGER,"+
                "sex INTEGER,"+
                "calorias DOUBLE)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        this.onCreate(db);
    }

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
        values.put(CALORIAS, user.getCalories());
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
                COLUNAS, // b. colunas
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
        values.put(CALORIAS, new Double(user.getCalories()));
        int i = db.update(TABLE_USUARIO, //tabela
                values, // valores
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(1) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }

    public int deleteUsuario(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_USUARIO, //tabela
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(user.getId()) });
        db.close();
        return i; // número de linhas excluídas
    }
}
