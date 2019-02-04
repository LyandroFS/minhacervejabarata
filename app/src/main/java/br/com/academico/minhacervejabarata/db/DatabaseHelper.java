package br.com.academico.minhacervejabarata.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.academico.minhacervejabarata.beans.Cesta;
import br.com.academico.minhacervejabarata.beans.Estabelecimento;
import br.com.academico.minhacervejabarata.beans.ItensCesta;
import br.com.academico.minhacervejabarata.beans.Marca;
import br.com.academico.minhacervejabarata.beans.Produto;
import br.com.academico.minhacervejabarata.beans.Tipo;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 14;

    // Database Name
    private static final String DATABASE_NAME = "minhaCervejaBarata";

    // Table Names
    private static final String TABLE_MARCA = "marca";
    private static final String TABLE_PRODUTO = "protudo";
    private static final String TABLE_SUPERMERCADO = "supermercado";
    private static final String TABLE_TIPO = "tipo";
    private static final String TABLE_CESTA = "cesta";
    private static final String TABLE_ITENS_CESTA = "itens_cesta";

    // Common column names
    private static final String KEY_ID = "id";
    //private static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column nmaes
    private static final String TABLE_SUPERMERCADO_COLUNA_NOME = "nome";
    private static final String TABLE_SUPERMERCADO_COLUNA_ENDERECO = "endereco";

    // NOTES Table - column nmaes
    private static final String TABLE_MARCA_COLUNA_NOME = "nome";


    // NOTES Table - column nmaes
    private static final String TABLE_TIPO_COLUNA_DESCRICAO = "descrica";
    private static final String TABLE_TIPO_COLUNA_ML = "ml";

    // NOTES Table - column nmaes
    private static final String TABLE_PRODUTO_COLUNA_VALOR = "valor";

    // NOTES Table - column nmaes
    private static final String TABLE_CESTA_COLUNA_NOME = "nome";
    private static final String TABLE_CESTA_COLUNA_DATA = "data";

    // TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";

    // NOTE_TAGS Table - column names
    private static final String KEY_SUPERMERCADO_ID = "idSupermercado";
    private static final String KEY_MARCA_ID = "idMarca";
    private static final String KEY_TIPO_ID = "idTipo";
    private static final String KEY_PRODUTO_ID = "idProduto";
    private static final String KEY_CESTA_ID = "idCesta";

    // Table Create Statements
    // Estabelecimento table create statement
    private static final String CREATE_TABLE_SUPERMERCADO = "CREATE TABLE "
            + TABLE_SUPERMERCADO + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + TABLE_SUPERMERCADO_COLUNA_NOME + " TEXT,"
            + TABLE_SUPERMERCADO_COLUNA_ENDERECO + " TEXT" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_MARCA = "CREATE TABLE " + TABLE_MARCA
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + TABLE_MARCA_COLUNA_NOME + " TEXT" + ")";


    // Table Create Statements
    // Estabelecimento table create statement
    private static final String CREATE_TABLE_TIPO = "CREATE TABLE "
            + TABLE_TIPO + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + TABLE_TIPO_COLUNA_DESCRICAO + " TEXT,"
            + TABLE_TIPO_COLUNA_ML + " REAL" + ")";


    // todo_tag table create statement
    private static final String CREATE_TABLE_PRODUTO = "CREATE TABLE "
            + TABLE_PRODUTO + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_SUPERMERCADO_ID + " INTEGER,"
            + KEY_MARCA_ID + " INTEGER,"
            + KEY_TIPO_ID + " INTEGER,"
            + TABLE_PRODUTO_COLUNA_VALOR + " REAL" + ")";


    // Table Create Statements
    // Estabelecimento table create statement
    private static final String CREATE_TABLE_CESTA = "CREATE TABLE "
            + TABLE_CESTA + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + TABLE_CESTA_COLUNA_NOME + " TEXT,"
            + TABLE_CESTA_COLUNA_DATA + " NUMERIC" + ")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_ITENS_CESTA = "CREATE TABLE "
            + TABLE_ITENS_CESTA + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PRODUTO_ID + " INTEGER,"
            + KEY_CESTA_ID + " INTEGER "
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_SUPERMERCADO);
        db.execSQL(CREATE_TABLE_MARCA);
        db.execSQL(CREATE_TABLE_TIPO);
        db.execSQL(CREATE_TABLE_PRODUTO);
        db.execSQL(CREATE_TABLE_CESTA);
        db.execSQL(CREATE_TABLE_ITENS_CESTA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITENS_CESTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CESTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARCA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPERMERCADO);

        // create new tables
        onCreate(db);
    }


    /*
     * get single supermercado
     */
    public Estabelecimento getSupermercado(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_SUPERMERCADO + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        estabelecimento.setNome((c.getString(c.getColumnIndex(TABLE_SUPERMERCADO_COLUNA_NOME))));
        estabelecimento.setEndereco(c.getString(c.getColumnIndex(TABLE_SUPERMERCADO_COLUNA_ENDERECO)));

        return estabelecimento;
    }


    //==================================================================================================

    /*
     * get single marca
     */
    public Marca getMarca(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_MARCA + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Marca marca = new Marca();
        marca.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        marca.setNome((c.getString(c.getColumnIndex(TABLE_MARCA_COLUNA_NOME))));

        return marca;
    }

    //==================================================================================================

    /*
     * Creating a tipo
     */
    public Tipo createTipo(Tipo tipo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_TIPO_COLUNA_DESCRICAO, tipo.getDescricao());
        values.put(TABLE_TIPO_COLUNA_ML, tipo.getMl());

        // insert row
        Long id = db.insert(TABLE_TIPO, null, values);
        //return todo_id;

        tipo.setId(id.intValue());

        return tipo;
    }

    /*
     * get single tipo
     */
    public Tipo getTipo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TIPO + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Tipo tipo = new Tipo();
        tipo.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        tipo.setDescricao((c.getString(c.getColumnIndex(TABLE_TIPO_COLUNA_DESCRICAO))));
        tipo.setMl((c.getDouble(c.getColumnIndex(TABLE_TIPO_COLUNA_ML))));

        return tipo;
    }

    /*
     * getting all tipo
     * */
    public List<Tipo> getAllTipo() {
        List<Tipo> tipos = new ArrayList<Tipo>();
        String selectQuery = "SELECT  * FROM " + TABLE_TIPO;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Tipo td = new Tipo();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setDescricao((c.getString(c.getColumnIndex(TABLE_TIPO_COLUNA_DESCRICAO))));
                td.setMl((c.getDouble(c.getColumnIndex(TABLE_TIPO_COLUNA_ML))));

                // adding to supermercados list
                tipos.add(td);
            } while (c.moveToNext());
        }

        return tipos;
    }

    //==================================================================================================

    /*
     * Creating a produto
     */
    public Produto createProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUPERMERCADO_ID, produto.getEstabelecimento().getId());
        values.put(KEY_MARCA_ID, produto.getMarca().getId());
        values.put(KEY_TIPO_ID, produto.getTipo().getId());
        values.put(TABLE_PRODUTO_COLUNA_VALOR, produto.getValor());
        System.out.println("VALOR PRODUTO: "+produto.getValor());

        // insert row
        Long id = db.insert(TABLE_PRODUTO, null, values);


        produto.setId(id.intValue());

        return produto;
    }

    /*
     * get single produto
     */
    public Produto getProduto(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PRODUTO + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Produto produto = new Produto();
        produto.setId(c.getInt(c.getColumnIndex(KEY_ID)));

        produto.setEstabelecimento(getSupermercado((c.getInt(c.getColumnIndex(KEY_SUPERMERCADO_ID)))));
        produto.setMarca(getMarca((c.getInt(c.getColumnIndex(KEY_MARCA_ID)))));
        produto.setTipo(getTipo((c.getInt(c.getColumnIndex(KEY_TIPO_ID)))));
        produto.setValor((c.getFloat(c.getColumnIndex(TABLE_PRODUTO_COLUNA_VALOR))));


        //produto.setIdSupermercado((c.getInt(c.getColumnIndex(KEY_SUPERMERCADO_ID))));
        //produto.setIdMarca((c.getInt(c.getColumnIndex(KEY_MARCA_ID))));
        //produto.setIdTipo((c.getInt(c.getColumnIndex(KEY_TIPO_ID))));

        return produto;
    }

    /*
     * getting all produto
     * */
    public List<Produto> getAllProduto() {
        List<Produto> produtos = new ArrayList<Produto>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUTO;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                /*Produto produto = new Produto();
                produto.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                produto.setIdSupermercado((c.getInt(c.getColumnIndex(KEY_SUPERMERCADO_ID))));
                produto.setIdMarca((c.getInt(c.getColumnIndex(KEY_MARCA_ID))));
                produto.setIdTipo((c.getInt(c.getColumnIndex(KEY_TIPO_ID))));*/

                Produto produto = new Produto();
                produto.setId(c.getInt(c.getColumnIndex(KEY_ID)));

                produto.setEstabelecimento(getSupermercado((c.getInt(c.getColumnIndex(KEY_SUPERMERCADO_ID)))));
                produto.setMarca(getMarca((c.getInt(c.getColumnIndex(KEY_MARCA_ID)))));
                produto.setTipo(getTipo((c.getInt(c.getColumnIndex(KEY_TIPO_ID)))));
                produto.setValor((c.getFloat(c.getColumnIndex(TABLE_PRODUTO_COLUNA_VALOR))));

                // adding to supermercados list
                produtos.add(produto);
            } while (c.moveToNext());
        }

        return produtos;
    }


    //==================================================================================================

    /*
     * Creating a cesta
     */
    public Cesta createCesta(Cesta cesta) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_CESTA_COLUNA_NOME, cesta.getNome());

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        values.put(TABLE_CESTA_COLUNA_DATA,  sdf.format(d));



        //values.put(TABLE_CESTA_COLUNA_DATA, cesta.getDate());


        // insert row
        Long id = db.insert(TABLE_CESTA, null, values);


        cesta.setId(id.intValue());

        return cesta;
    }

    /*
     * get single cesta
     */
    public Cesta getCesta(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CESTA + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Cesta cesta = new Cesta();
        cesta.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        cesta.setNome((c.getString(c.getColumnIndex(TABLE_CESTA_COLUNA_NOME))));
        cesta.setDate((c.getString(c.getColumnIndex(TABLE_CESTA_COLUNA_DATA))));
        //cesta.setItensCesta(getAllItensCestaById(id));

        return cesta;
    }

    /*
     * getting all cesta
     * */
    public List<Cesta> getAllCesta() {
        List<Cesta> cestas = new ArrayList<Cesta>();
        String selectQuery = "SELECT  * FROM " + TABLE_CESTA;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Cesta cesta = new Cesta();
                cesta.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                cesta.setNome((c.getString(c.getColumnIndex(TABLE_CESTA_COLUNA_NOME))));
                cesta.setDate((c.getString(c.getColumnIndex(TABLE_CESTA_COLUNA_DATA))));
                //cesta.setItensCesta(getAllItensCestaById(c.getInt(c.getColumnIndex(KEY_ID))));

                // adding to supermercados list
                cestas.add(cesta);
            } while (c.moveToNext());
        }

        return cestas;
    }

    //==================================================================================================

    /*
     * Creating a itens cesta
     */
    public ItensCesta createItensCesta(ItensCesta itensCesta) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUTO_ID, itensCesta.getProduto().getId());
        values.put(KEY_CESTA_ID, itensCesta.getCesta().getId());



        //values.put(TABLE_CESTA_COLUNA_DATA, cesta.getDate());


        // insert row
        Long id = db.insert(TABLE_ITENS_CESTA, null, values);


        itensCesta.setId(id.intValue());

        return itensCesta;
    }

    /*
     * get single itens cesta
     */
    public ItensCesta getItensCesta(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ITENS_CESTA + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        ItensCesta itensCesta = new ItensCesta();
        itensCesta.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        itensCesta.setProduto(getProduto((c.getInt(c.getColumnIndex(KEY_PRODUTO_ID)))));
        itensCesta.setCesta(getCesta((c.getInt(c.getColumnIndex(KEY_CESTA_ID)))));

        return itensCesta;
    }

    /*
     * getting all cesta
     * */
    public List<ItensCesta> getAllItensCesta() {
        List<ItensCesta> itensCesta = new ArrayList<ItensCesta>();
        String selectQuery = "SELECT  * FROM " + TABLE_ITENS_CESTA;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ItensCesta iCesta = new ItensCesta();
                iCesta.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                iCesta.setProduto(getProduto((c.getInt(c.getColumnIndex(KEY_PRODUTO_ID)))));
                iCesta.setCesta(getCesta((c.getInt(c.getColumnIndex(KEY_CESTA_ID)))));

                // adding to supermercados list
                itensCesta.add(iCesta);
            } while (c.moveToNext());
        }

        return itensCesta;
    }

    /*
     * getting all itens cesta by id
     * */
    public List<ItensCesta> getAllItensCestaById(int id) {
        List<ItensCesta> itensCesta = new ArrayList<ItensCesta>();
        String selectQuery = "SELECT  * FROM " + TABLE_ITENS_CESTA + " WHERE "
                + KEY_CESTA_ID + " = " + id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ItensCesta iCesta = new ItensCesta();
                iCesta.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                iCesta.setProduto(getProduto((c.getInt(c.getColumnIndex(KEY_PRODUTO_ID)))));
                iCesta.setCesta(getCesta((c.getInt(c.getColumnIndex(KEY_CESTA_ID)))));

                // adding to supermercados list
                itensCesta.add(iCesta);
            } while (c.moveToNext());
        }

        return itensCesta;
    }
}