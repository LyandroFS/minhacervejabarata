package br.com.academico.minhacervejabarata.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.academico.minhacervejabarata.beans.Marca;
import br.com.academico.minhacervejabarata.db.DatabaseHelper;

public class MarcaDAO {

    private SQLiteDatabase db;
    private static final String TABLE_MARCA_COLUNA_NOME = "nome";
    private static final String TABLE_MARCA = "marca";
    private static final String KEY_ID = "id";



    public MarcaDAO (Context context){
        db = new DatabaseHelper(context).getWritableDatabase();
    }

    public boolean createMarca(Marca marca) {
        ContentValues values = new ContentValues();
        values.put(TABLE_MARCA_COLUNA_NOME, marca.getNome());

        return db.insert(TABLE_MARCA, null, values) > 0;
    }

    public List<Marca> getAllMarca() {
        List<Marca> marcas = new ArrayList<Marca>();
        String selectQuery = "SELECT  * FROM " + TABLE_MARCA;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Marca td = new Marca();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setNome((c.getString(c.getColumnIndex(TABLE_MARCA_COLUNA_NOME))));

                marcas.add(td);
            } while (c.moveToNext());
        }

        return marcas;
    }
}
