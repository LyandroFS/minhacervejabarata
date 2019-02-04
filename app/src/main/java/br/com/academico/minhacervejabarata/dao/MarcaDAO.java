package br.com.academico.minhacervejabarata.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import br.com.academico.minhacervejabarata.beans.Marca;
import br.com.academico.minhacervejabarata.db.DatabaseHelper;

public class MarcaDAO {

    private SQLiteDatabase db;
    private static final String TABLE_MARCA_COLUNA_NOME = "nome";
    private static final String TABLE_MARCA = "marca";


    public MarcaDAO (Context context){
        db = new DatabaseHelper(context).getWritableDatabase();
    }

    public Marca createMarca(Marca marca) {
        ContentValues values = new ContentValues();
        values.put(TABLE_MARCA_COLUNA_NOME, marca.getNome());

        // insert row
        Long id = db.insert(TABLE_MARCA, null, values);

        marca.setId(id.intValue());

        return marca;
    }
}
