package br.com.academico.minhacervejabarata;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.academico.minhacervejabarata.beans.Cesta;
import br.com.academico.minhacervejabarata.beans.Estabelecimento;
import br.com.academico.minhacervejabarata.beans.ItensCesta;
import br.com.academico.minhacervejabarata.beans.Marca;
import br.com.academico.minhacervejabarata.beans.Produto;
import br.com.academico.minhacervejabarata.beans.Tipo;
import br.com.academico.minhacervejabarata.db.DatabaseSqlite;
import br.com.academico.minhacervejabarata.db.IDatabase;
import br.com.academico.minhacervejabarata.listItens.ItensCestaAdapter;

public class CestaActivity extends AppCompatActivity {

    private IDatabase db;
    private RecyclerView mRecyclerView;
    private ItensCestaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ItensCesta> itensCestaList;
    private Spinner spinnerSuperpercados;
    private Spinner spinnerMarcas;
    private Spinner spinnerTipos;
    private List<Estabelecimento> estabelecimentoList;
    private List<Marca> marcaList;
    private List<Tipo> tipoList;
    private  EditText nomeCesta;

    private Estabelecimento estabelecimento;
    private Marca marca;
    private Tipo tipo;
    private EditText preco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);
        db = DatabaseSqlite.getInstance(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        //spinnerProdutos = (Spinner) findViewById(R.id.produtos);
        spinnerSuperpercados = (Spinner) findViewById(R.id.superpercados);
        spinnerMarcas = (Spinner) findViewById(R.id.marcas);
        spinnerTipos = (Spinner) findViewById(R.id.tipos);
        nomeCesta = (EditText) findViewById(R.id.nome_cesta);
        preco = (EditText) findViewById(R.id.preco);


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //cestaList = db.getAllCesta();
        itensCestaList = new ArrayList<>();

        mAdapter = new ItensCestaAdapter(getApplicationContext(),this,itensCestaList, db);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cesta cesta = new Cesta(nomeCesta.getText().toString());
                cesta = db.createCesta(cesta);

                if(itensCestaList.size() > 0) {
                    for (ItensCesta itensCesta : itensCestaList) {

                        Estabelecimento estabelecimento = itensCesta.getProduto().getEstabelecimento();
                        db.insertEstabelecimento(estabelecimento);

                        Tipo tipo = itensCesta.getProduto().getTipo();
                        tipo = new Tipo(tipo.getDescricao(),tipo.getMl());
                        db.insertTipo(tipo);

                        Marca marca = itensCesta.getProduto().getMarca();
                        db.insertMarca(marca);

                        Produto produto = itensCesta.getProduto();
                        produto = db.createProduto(produto);

                        ItensCesta item = new ItensCesta(produto, cesta);
                        item = db.createItensCesta(item);

/*                        Log.d("Tag", "Valor Produto: " +item.getId()
                                +" - "+item.getProduto().getMarca().getNome()
                                +" - "+item.getProduto().getEstabelecimento().getNome()
                                +" - "+item.getProduto().getTipo().getDescricao());*/


                       /* Produto produto = itensCesta.getProduto();
                        produto = db.createProduto(produto);

                        ItensCesta item = new ItensCesta(produto, cesta);
                        db.createItensCesta(item);*/

/*                        Log.d("Tag", "Valor Produto: " +item.getId()
                                +" - "+item.getProduto().getMarca().getNome()
                                +" - "+item.getProduto().getEstabelecimento().getNome()
                                +" - "+item.getProduto().getTipo().getDescricao());*/
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Cesta criada com sucesso!!!", Toast.LENGTH_LONG).show();
                }else{
                    Snackbar.make(view, "Nenhum produto foi adicionado a sua cesta", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        //loadSpinnerEstabeleciementos();
        loadSpinnerEstabeleciementosRest();
        //loadSpinnerMarcas();
        loadSpinnerMarcasRest();
        //loadSpinnerTipos();
        loadSpinnerTiposRest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //loadSpinnerEstabeleciementos();
        loadSpinnerEstabeleciementosRest();
        //loadSpinnerMarcas();
        loadSpinnerMarcasRest();
        //loadSpinnerTipos();
        loadSpinnerTiposRest();
    }

    private void loadSpinnerEstabeleciementosRest() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                "http://caiovosilva.pythonanywhere.com/estabelecimentos",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type listType = new TypeToken<List<Estabelecimento>>() {}.getType();

                        List<String> estabelecimentos = new ArrayList<>();

                        estabelecimentoList = new Gson().fromJson(String.valueOf(response), listType);

                        for(Estabelecimento estabelecimento : estabelecimentoList)
                            estabelecimentos.add(estabelecimento.getNome());

                        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CestaActivity.this, android.R.layout.simple_spinner_dropdown_item, estabelecimentos);
                        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSuperpercados.setAdapter(spinnerArrayAdapter);

                        //Método do Spinner para capturar o item selecionado
                        spinnerSuperpercados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                                estabelecimento = estabelecimentoList.get(posicao);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        //estabelecimentoAdapter.setEstabelecimentos(estabelecimentos);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "ERRO:    "+error.toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
        );
        requestQueue.add(arrayRequest);
    }

    private void loadSpinnerEstabeleciementos(){

        List<String> estabelecimentos = new ArrayList<>();
        estabelecimentoList = db.getAllEstabelecimentos();

        for(Estabelecimento estabelecimento : estabelecimentoList)
            estabelecimentos.add(estabelecimento.getNome());

        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, estabelecimentos);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSuperpercados.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado
        spinnerSuperpercados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                //nome = parent.getItemAtPosition(posicao).toString();
                //imprime um Toast na tela com o nome que foi selecionado

                estabelecimento = estabelecimentoList.get(posicao);

                //Toast.makeText(CestaActivity.this, "Nome Selecionado: " +estabelecimento.getNome(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadSpinnerMarcas(){

        List<String> marcas = new ArrayList<>();

        marcaList = db.getAllMarca();

        for(Marca marca : marcaList)
            marcas.add(marca.getNome());

        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, marcas);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarcas.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado
        spinnerMarcas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                marca = marcaList.get(posicao);
                //Toast.makeText(CestaActivity.this, "Nome Selecionado: " + parent.getItemAtPosition(posicao).toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void loadSpinnerMarcasRest() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                "http://caiovosilva.pythonanywhere.com/marcas",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type listType = new TypeToken<List<Marca>>() {}.getType();

                        marcaList = new Gson().fromJson(String.valueOf(response), listType);
                        List<String> marcas = new ArrayList<>();

                        for(Marca marca : marcaList)
                            marcas.add(marca.getNome());

                        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CestaActivity.this, android.R.layout.simple_spinner_dropdown_item, marcas);
                        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerMarcas.setAdapter(spinnerArrayAdapter);

                        //Método do Spinner para capturar o item selecionado
                        spinnerMarcas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                                marca = marcaList.get(posicao);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "ERRO:    "+error.toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
        );
        requestQueue.add(arrayRequest);
    }

    private void loadSpinnerTipos(){

        List<String> tipos = new ArrayList<>();

        tipoList = db.getAllTipo();

        for(Tipo tipo : tipoList)
            tipos.add(tipo.getDescricao()+" "+tipo.getMl());

        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipos.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado
        spinnerTipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                tipo = tipoList.get(posicao);
                //Toast.makeText(CestaActivity.this, "Nome Selecionado: " + parent.getItemAtPosition(posicao).toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadSpinnerTiposRest() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                "http://caiovosilva.pythonanywhere.com/tipos",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type listType = new TypeToken<List<Tipo>>() {}.getType();

                        Log.w("TIPO", ""+String.valueOf(response));

                        List<String> tipos = new ArrayList<>();
                        tipoList = new Gson().fromJson(String.valueOf(response), listType);

                        for(Tipo tipo : tipoList)
                            tipos.add(tipo.getDescricao()+" "+tipo.getMl());

                        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CestaActivity.this, android.R.layout.simple_spinner_dropdown_item, tipos);
                        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerTipos.setAdapter(spinnerArrayAdapter);

                        //Método do Spinner para capturar o item selecionado
                        spinnerTipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                                tipo = tipoList.get(posicao);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "ERRO:    "+error.toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
        );
        requestQueue.add(arrayRequest);
    }


    public void addItenCesta(View view) {
        if(!preco.getText().toString().isEmpty()) {
            Log.d("Tag", "Nome Cesta: " + nomeCesta.getText().toString());
            Log.d("Tag", "Valor Produto: " + Float.parseFloat(preco.getText().toString()));

            Log.d("Tag", "ID ESTABELECIEMTNO: " + estabelecimento.getId());
            Log.d("Tag", "ID Marca: " + marca.getId());
            Log.d("Tag", "ID Tipo: " + tipo.getId());

            Produto produto = new Produto(estabelecimento, marca, tipo, Float.parseFloat(preco.getText().toString()));
            ItensCesta itensCesta = new ItensCesta(produto, new Cesta("TESTE TESSTE"));
            itensCestaList.add(itensCesta);
            mAdapter.notifyItemInserted(itensCestaList.size());
            mRecyclerView.scrollToPosition(itensCestaList.size());
        }else{
            Snackbar.make(view, "O valor do produto deve ser preenchido", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }

    }



    public void saveItenCesta(View view) {

        Log.d("Tag", "Salvando a Cesta " + nomeCesta.getText().toString());

        Cesta cesta = new Cesta(nomeCesta.getText().toString());
        cesta = db.createCesta(cesta);



        for(ItensCesta itensCesta: itensCestaList){

            Estabelecimento estabelecimento = itensCesta.getProduto().getEstabelecimento();
            if(db.getEstabelecimento(estabelecimento.getId()) == null){
                estabelecimento = new Estabelecimento(estabelecimento.getNome(), estabelecimento.getEndereco());
                db.insertEstabelecimento(estabelecimento);
            }

            Tipo tipo = itensCesta.getProduto().getTipo();

            Log.w("Tag", "idTipo: "+tipo.getId());

            if(db.getTipo(tipo.getId()) == null){
                tipo = new Tipo(tipo.getDescricao(),tipo.getMl());
                db.insertTipo(tipo);
            }

            Marca marca = itensCesta.getProduto().getMarca();
            if(db.getMarca(marca.getId()) == null){
                marca = new Marca(marca.getNome());
                db.insertMarca(marca);
            }

            Produto produto = itensCesta.getProduto();
            if(db.getProduto(produto.getId()) == null){
                produto = db.createProduto( new Produto(estabelecimento,marca,tipo, Float.parseFloat(preco.getText().toString())));
            }

            ItensCesta item = new ItensCesta(produto, cesta);
            db.createItensCesta(item);
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Cesta criada com sucesso OK!!!",Toast.LENGTH_LONG).show();
    }

    public void addEstabelecimento(View view) {
        Intent intent = new Intent(view.getContext(), EstabelecimentoActivity.class);
        intent.putExtra("add", "sim");
        startActivity(intent);
    }

    public void btnAddMarca(View view) {
        Intent intent = new Intent(view.getContext(), AddMarcaActivity.class);
        startActivity(intent);
    }

    public void btnAddTipo(View view) {
        Intent intent = new Intent(view.getContext(), TipoActivity.class);
        intent.putExtra("add", "sim");
        startActivity(intent);
    }
}
