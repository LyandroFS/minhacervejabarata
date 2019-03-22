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
import br.com.academico.minhacervejabarata.listItens.EstabelecimentoAdapter;
import br.com.academico.minhacervejabarata.listItens.MarcaAdapter;

public class DatabaseSqlite extends SQLiteOpenHelper implements IDatabase {

    //Singleton
    private static DatabaseSqlite instancia;

    public static DatabaseSqlite getInstance(Context context){
        if(instancia == null)
            instancia = new DatabaseSqlite(context);
        return instancia;
    }

    public MarcaAdapter getMarcaAdapter() {
        return marcaAdapter;
    }

    public void setMarcaAdapter(MarcaAdapter marcaAdapter) {
        this.marcaAdapter = marcaAdapter;
    }

    //para o crud
    private MarcaAdapter marcaAdapter;
    private EstabelecimentoAdapter estabelecimentoAdapter;

    public EstabelecimentoAdapter getEstabelecimentoAdapter() {
        return estabelecimentoAdapter;
    }

    public void setEstabelecimentoAdapter(EstabelecimentoAdapter estabelecimentoAdapter) {
        this.estabelecimentoAdapter = estabelecimentoAdapter;
    }

    // Logcat tag
    private static final String LOG = "DatabaseSqlite";

    // IDatabase Version
    private static final int DATABASE_VERSION = 5;

    // IDatabase Name
    private static final String DATABASE_NAME = "minhaCervejaBarata";

    // Table Names
    private static final String TABLE_MARCA = "marca";
    private static final String TABLE_PRODUTO = "protudo";
    private static final String TABLE_ESTABELECIMENTO = "supermercado";
    private static final String TABLE_TIPO = "tipo";
    private static final String TABLE_CESTA = "cesta";
    private static final String TABLE_ITENS_CESTA = "itens_cesta";

    // Common column names
    private static final String KEY_ID = "id";
    //private static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column nmaes
    private static final String TABLE_ESTABELECIMENTO_COLUNA_NOME = "nome";
    private static final String TABLE_ESTABELECIMENTO_COLUNA_ENDERECO = "endereco";

    // NOTES Table - column nmaes
    private static final String TABLE_MARCA_COLUNA_NOME = "nome";


    // NOTES Table - column nmaes
    private static final String TABLE_TIPO_COLUNA_DESCRICAO = "descrica";
    private static final String TABLE_TIPO_COLUNA_ML = "ml";
    private static final String TABLE_TIPO_COLUNA_QTD_EMBALAGEM = "qtdEmbalagem";

    // NOTES Table - column nmaes
    private static final String TABLE_PRODUTO_COLUNA_VALOR = "valor";

    // NOTES Table - column nmaes
    private static final String TABLE_CESTA_COLUNA_NOME = "nome";
    private static final String TABLE_CESTA_COLUNA_DATA = "data";

    // TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";

    // NOTE_TAGS Table - column names
    private static final String KEY_ESTABELECIMENTO_ID = "idSupermercado";
    private static final String KEY_MARCA_ID = "idMarca";
    private static final String KEY_TIPO_ID = "idTipo";
    private static final String KEY_PRODUTO_ID = "idProduto";
    private static final String KEY_CESTA_ID = "idCesta";

    // Table Create Statements
    // Estabelecimento table create statement
    private static final String CREATE_TABLE_ESTABELECIMENTO = "CREATE TABLE "
            + TABLE_ESTABELECIMENTO + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + TABLE_ESTABELECIMENTO_COLUNA_NOME + " TEXT,"
            + TABLE_ESTABELECIMENTO_COLUNA_ENDERECO + " TEXT" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_MARCA = "CREATE TABLE " + TABLE_MARCA
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + TABLE_MARCA_COLUNA_NOME + " TEXT" + ")";


    // Table Create Statements
    // Estabelecimento table create statement
    private static final String CREATE_TABLE_TIPO = "CREATE TABLE "
            + TABLE_TIPO + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + TABLE_TIPO_COLUNA_DESCRICAO + " TEXT,"
            + TABLE_TIPO_COLUNA_QTD_EMBALAGEM + " INTEGER,"
            + TABLE_TIPO_COLUNA_ML + " REAL" + ")";


    // todo_tag table create statement
    private static final String CREATE_TABLE_PRODUTO = "CREATE TABLE "
            + TABLE_PRODUTO + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_ESTABELECIMENTO_ID + " INTEGER,"
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

    private DatabaseSqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_ESTABELECIMENTO);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTABELECIMENTO);

        // create new tables
        onCreate(db);
    }


    /*
     * Creating a tipo
     */
    public Estabelecimento insertEstabelecimento(Estabelecimento estabelecimento) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_ESTABELECIMENTO_COLUNA_NOME, estabelecimento.getNome());
        values.put(TABLE_ESTABELECIMENTO_COLUNA_ENDERECO, estabelecimento.getEndereco());

        // insert row
        Long id = db.insert(TABLE_ESTABELECIMENTO, null, values);
        //return todo_id;

        estabelecimento.setId(id.intValue());

        return estabelecimento;
    }


    /*
     * get single ESTABELECIMENTO
     */
    public Estabelecimento getEstabelecimento(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ESTABELECIMENTO + " WHERE "
                + KEY_ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        estabelecimento.setNome((c.getString(c.getColumnIndex(TABLE_ESTABELECIMENTO_COLUNA_NOME))));
        estabelecimento.setEndereco(c.getString(c.getColumnIndex(TABLE_ESTABELECIMENTO_COLUNA_ENDERECO)));

        return estabelecimento;
    }

    public List<Estabelecimento> getAllEstabelecimentos() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
        String selectQuery = "SELECT  * FROM " + TABLE_ESTABELECIMENTO;

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Estabelecimento td = new Estabelecimento();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setNome((c.getString(c.getColumnIndex(TABLE_ESTABELECIMENTO_COLUNA_NOME))));
                td.setEndereco(c.getString(c.getColumnIndex(TABLE_ESTABELECIMENTO_COLUNA_ENDERECO)));

                // adding to estabelecimentos list
                estabelecimentos.add(td);
            } while (c.moveToNext());
        }

        return estabelecimentos;
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

    public Marca createMarca(Marca marca) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_MARCA_COLUNA_NOME, marca.getNome());

        Long id = db.insert(TABLE_MARCA, null, values);
        marca.setId(id.intValue());
         return marca;
    }

    public List<Marca> getAllMarca() {
        SQLiteDatabase db = this.getReadableDatabase();
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

    //==================================================================================================

    /*
     * Creating a tipo
     */
    public Tipo createTipo(Tipo tipo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_TIPO_COLUNA_DESCRICAO, tipo.getDescricao());
        values.put(TABLE_TIPO_COLUNA_ML, tipo.getMl());
        values.put(TABLE_TIPO_COLUNA_QTD_EMBALAGEM, tipo.getQdtEmbalagem());

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
        tipo.setQdtEmbalagem((c.getInt(c.getColumnIndex(TABLE_TIPO_COLUNA_QTD_EMBALAGEM))));

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
                td.setQdtEmbalagem((c.getInt(c.getColumnIndex(TABLE_TIPO_COLUNA_QTD_EMBALAGEM))));


                // adding to Estabelecimento list
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
        values.put(KEY_ESTABELECIMENTO_ID, produto.getEstabelecimento().getId());
        values.put(KEY_MARCA_ID, produto.getMarca().getId());
        values.put(KEY_TIPO_ID, produto.getTipo().getId());
        values.put(TABLE_PRODUTO_COLUNA_VALOR, produto.getValor());

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

        produto.setEstabelecimento(getEstabelecimento((c.getInt(c.getColumnIndex(KEY_ESTABELECIMENTO_ID)))));
        produto.setMarca(getMarca((c.getInt(c.getColumnIndex(KEY_MARCA_ID)))));
        produto.setTipo(getTipo((c.getInt(c.getColumnIndex(KEY_TIPO_ID)))));
        produto.setValor((c.getFloat(c.getColumnIndex(TABLE_PRODUTO_COLUNA_VALOR))));


        //produto.setIdSupermercado((c.getInt(c.getColumnIndex(KEY_ESTABELECIMENTO_ID))));
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
                produto.setIdSupermercado((c.getInt(c.getColumnIndex(KEY_ESTABELECIMENTO_ID))));
                produto.setIdMarca((c.getInt(c.getColumnIndex(KEY_MARCA_ID))));
                produto.setIdTipo((c.getInt(c.getColumnIndex(KEY_TIPO_ID))));*/

                Produto produto = new Produto();
                produto.setId(c.getInt(c.getColumnIndex(KEY_ID)));

                produto.setEstabelecimento(getEstabelecimento((c.getInt(c.getColumnIndex(KEY_ESTABELECIMENTO_ID)))));
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

//        private DatabaseSqlite dbhelper;
//        dbhelper = new DatabaseSqlite();
        SQLiteDatabase db = getReadableDatabase();
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

     /* remove single itens cesta
     */
    public boolean removeItensCesta(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "DELETE FROM " + TABLE_ITENS_CESTA + " WHERE "
                + KEY_ID + " = " + id;
        return  db.delete(TABLE_ITENS_CESTA, "id=?", new String[]{String.valueOf(id)}) > 0;
    }


    public boolean insertOrUpdateMarca(Marca marca) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_MARCA_COLUNA_NOME, marca.getNome());

        if(marca.getId()>0)
            return db.update(TABLE_MARCA, values, "ID=?", new String[]{ marca.getId() + "" }) > 0;
        else
            return db.insert(TABLE_MARCA, null, values) > 0;
    }

    public Marca getUltimaMarcaInserida() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_MARCA + " ORDER BY ID DESC ", null);

        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            String nome = cursor.getString(cursor.getColumnIndex(TABLE_MARCA_COLUNA_NOME));
            cursor.close();
            return new Marca(id, nome);
        }
        return null;
    }

    public boolean removeMarca(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "DELETE FROM " + TABLE_MARCA + " WHERE "
                + KEY_ID + " = " + id;
        return  db.delete(TABLE_MARCA, "id=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean updateEstabelecimento(Estabelecimento estabelecimento, int index) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_ESTABELECIMENTO_COLUNA_NOME, estabelecimento.getNome());
        values.put(TABLE_ESTABELECIMENTO_COLUNA_ENDERECO, estabelecimento.getEndereco());

        if(estabelecimento.getId()>0)
            return db.update(TABLE_ESTABELECIMENTO, values, "ID=?", new String[]{ estabelecimento.getId() + "" }) > 0;
        else
            return db.insert(TABLE_ESTABELECIMENTO, null, values) > 0;
    }

    public Estabelecimento getUltimoEstabelecimentoInserido() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_ESTABELECIMENTO + " ORDER BY ID DESC ", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            String nome = cursor.getString(cursor.getColumnIndex(TABLE_ESTABELECIMENTO_COLUNA_NOME));
            String endereco = cursor.getString(cursor.getColumnIndex(TABLE_ESTABELECIMENTO_COLUNA_ENDERECO));
            cursor.close();
            return new Estabelecimento(id, nome, endereco);
        }
        return null;
    }

    public boolean deleteEstabelecimento(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "DELETE FROM " + TABLE_ESTABELECIMENTO + " WHERE "
                + KEY_ID + " = " + id;
        return  db.delete(TABLE_ESTABELECIMENTO, "id=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean insertOrUpdateTipo (Tipo tipo/*, long[] tag_ids*/) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_TIPO_COLUNA_DESCRICAO, tipo.getDescricao());
        values.put(TABLE_TIPO_COLUNA_ML, tipo.getMl());
        values.put(TABLE_TIPO_COLUNA_QTD_EMBALAGEM, tipo.getQdtEmbalagem());

        if(tipo.getId()>0)
            return db.update(TABLE_TIPO, values, "ID=?", new String[]{ tipo.getId() + "" }) > 0;
        else
            return db.insert(TABLE_TIPO, null, values) > 0;
    }

    public Tipo getUltimoTipoInserido() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  * FROM " + TABLE_TIPO + " ORDER BY ID DESC ", null);
        if(c.moveToFirst()){
            Tipo tipo = new Tipo();
            tipo.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            tipo.setDescricao((c.getString(c.getColumnIndex(TABLE_TIPO_COLUNA_DESCRICAO))));
            tipo.setMl((c.getDouble(c.getColumnIndex(TABLE_TIPO_COLUNA_ML))));
            tipo.setQdtEmbalagem((c.getInt(c.getColumnIndex(TABLE_TIPO_COLUNA_QTD_EMBALAGEM))));
            c.close();
            return tipo;
        }
        return null;
    }



}