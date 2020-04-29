package com.example.nutritionbasics.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nutritionbasics.model.Usuario;

import java.util.ArrayList;

public class BDuser extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB";
    private static final String TABLE_USUARIO = "usuario";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String BIRTHDATE = "birthdate";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String ACTIVITYLEVEL= "activitylevel";
    private static final String SEX = "sex";
    private static final String CALORIAS = "calorias";
    private static final String[] COLUNAS = { ID, NAME, BIRTHDATE, HEIGHT, WEIGHT, ACTIVITYLEVEL, SEX, CALORIAS };

    public BDuser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE usuario ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT,"+
                "birthdate DATE,"+
                "height FLOAT,"+
                "weight INTEGER,"+
                "activitylevel INTEGER,"+
                "sex INTEGER,"+
                "calorias FLOAT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        this.onCreate(db);
    }

    public void addUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, usuario.getName());
        values.put(BIRTHDATE, usuario.getBirthdate());
        values.put(HEIGHT, usuario.getHeight());
        values.put(WEIGHT, usuario.getWeight());
        values.put(ACTIVITYLEVEL, usuario.getActivityLevel());
        values.put(SEX, usuario.getSex());
        values.put(CALORIAS, usuario.getCalorias());
        db.insert(TABLE_USUARIO, null, values);
        db.close();
    }

    public Usuario getUsuario(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USUARIO, // a. tabela
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
            Usuario usuario = cursorToUsuario(cursor);
            return usuario;
        }
    }

    private Usuario cursorToUsuario(Cursor cursor) {
        Usuario usuario = new Usuario();
        usuario.setId(Integer.parseInt(cursor.getString(0)));
        usuario.setName(cursor.getString(1));
        usuario.setBirthdate(cursor.getString(2));
        usuario.setHeight(Float.parseFloat(cursor.getString(3)));
        usuario.setWeight(Integer.parseInt(cursor.getString(4)));
        usuario.setActivityLevel(Integer.parseInt(cursor.getString(5)));
        usuario.setSex(Integer.parseInt(cursor.getString(6)));
        usuario.setCalorias(Float.parseFloat(cursor.getString(7)));
        return usuario;
    }

    public ArrayList<Usuario> getAllUsuarios() {
        ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
        String query = "SELECT * FROM " + TABLE_USUARIO + " ORDER BY " + NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Usuario usuario = cursorToUsuario(cursor);
                listaUsuarios.add(usuario);
            } while (cursor.moveToNext());
        }
        return listaUsuarios;
    }

    public int updateUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, usuario.getName());
        values.put(BIRTHDATE, usuario.getBirthdate());
        values.put(HEIGHT, new Float(usuario.getHeight()));
        values.put(WEIGHT, new Integer(usuario.getWeight()));
        values.put(ACTIVITYLEVEL, new Integer(usuario.getActivityLevel()));
        values.put(SEX, new Integer(usuario.getSex()));
        values.put(CALORIAS, new Float(usuario.getCalorias()));
        int i = db.update(TABLE_USUARIO, //tabela
                values, // valores
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(usuario.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }

    public int deleteUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_USUARIO, //tabela
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(usuario.getId()) });
        db.close();
        return i; // número de linhas excluídas
    }
}
