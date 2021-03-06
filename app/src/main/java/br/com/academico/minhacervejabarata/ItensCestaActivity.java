package br.com.academico.minhacervejabarata;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.academico.minhacervejabarata.beans.Cesta;
import br.com.academico.minhacervejabarata.beans.Estabelecimento;
import br.com.academico.minhacervejabarata.beans.ItensCesta;
import br.com.academico.minhacervejabarata.beans.Marca;
import br.com.academico.minhacervejabarata.beans.Produto;
import br.com.academico.minhacervejabarata.beans.Tipo;
import br.com.academico.minhacervejabarata.db.DatabaseSqlite;
import br.com.academico.minhacervejabarata.listItens.ItensCestaAdapter;

public class ItensCestaActivity extends AppCompatActivity {

    private DatabaseSqlite db;
    private RecyclerView mRecyclerView;
    private ItensCestaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> list;
    private  List<Produto> listProdutos;
    private TextView idCesta;
    private TextView nomeCesta;
    private List<ItensCesta> itensCestaList;
    private Spinner spinnerSuperpercados;
    private Spinner spinnerMarcas;
    private Spinner spinnerTipos;
    private List<Estabelecimento> estabelecimentoList;
    private List<Marca> marcaList;
    private List<Tipo> tipoList;
    private Estabelecimento estabelecimento;
    private Marca marca;
    private Tipo tipo;
    private EditText preco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_cesta);
        db = DatabaseSqlite.getInstance(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        spinnerSuperpercados = (Spinner) findViewById(R.id.superpercados);
        spinnerMarcas = (Spinner) findViewById(R.id.marcas);
        spinnerTipos = (Spinner) findViewById(R.id.tipos);
        preco = (EditText) findViewById(R.id.preco);

        list = new ArrayList<>();
        idCesta = (TextView) findViewById(R.id.idCesta);
        nomeCesta = (TextView) findViewById(R.id.nome_cesta);

        Bundle extrasBundle = getIntent().getExtras();

        if(!extrasBundle.isEmpty()){
            Cesta cesta = db.getCesta(extrasBundle.getInt("id"));
            idCesta.setText(String.valueOf(cesta.getId()));
            nomeCesta.setText(cesta.getNome());
            itensCestaList = db.getAllItensCestaById(extrasBundle.getInt("id"));
            Log.w("ITENS", ""+itensCestaList.size());

            mAdapter = new ItensCestaAdapter(getApplicationContext(),this,itensCestaList, db);
            mRecyclerView.setAdapter(mAdapter);
        }

        listProdutos = db.getAllProduto();


        DecimalFormat df = new DecimalFormat("0.00");

        for(Produto produto : listProdutos){

            /*list.add(
                    produto.getMarca().getNome()+", "+
                            produto.getTipo().getDescricao()+" "+
                            df.format(produto.getTipo().getMl())+"ml R$: "+
                            df.format(produto.getValor())+" - "+
                            produto.getEstabelecimento().getNome());*/

            list.add(
                    produto.getIdMarca()+", "+
                            produto.getIdTipo()+" "+
                            "ml R$: "+
                            df.format(produto.getValor())+" - "+
                            produto.getIdSupermercado());
        }
        loadSpinnerEstabeleciementosRest();
        loadSpinnerMarcasRest();
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
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ItensCestaActivity.this, android.R.layout.simple_spinner_dropdown_item, estabelecimentos);
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
                estabelecimento = estabelecimentoList.get(posicao);
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
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ItensCestaActivity.this, android.R.layout.simple_spinner_dropdown_item, marcas);
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

    private void loadSpinnerTiposRest() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                "http://caiovosilva.pythonanywhere.com/tipos",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type listType = new TypeToken<List<Tipo>>() {}.getType();

                        List<String> tipos = new ArrayList<>();
                        tipoList = new Gson().fromJson(String.valueOf(response), listType);

                        for(Tipo tipo : tipoList)
                            tipos.add(tipo.getDescricao()+" "+tipo.getMl());

                        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ItensCestaActivity.this, android.R.layout.simple_spinner_dropdown_item, tipos);
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

    public void addItenCesta(View view) {
        if(!preco.getText().toString().isEmpty()) {
/*
            Log.d("Tag", "Nome Cesta: " + nomeCesta.getText().toString());
            Log.d("Tag", "Valor Produto: " + Float.parseFloat(preco.getText().toString()));
*/

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

    public void addMoreItenCesta(View view) {
        if(!preco.getText().toString().isEmpty()) {
            Produto produto = new Produto(estabelecimento, marca, tipo, Float.parseFloat(preco.getText().toString()));
            produto = db.createProduto(produto);
            Cesta cesta = db.getCesta(Integer.parseInt(idCesta.getText().toString()));

            ItensCesta item = new ItensCesta(produto, cesta);
            db.createItensCesta(item);

            this.recreate();
            Toast.makeText(getApplicationContext(),"Novo produto adicionado a cesta!!!",Toast.LENGTH_LONG).show();
        }else{
            Snackbar.make(view, "O valor do produto deve ser preenchido", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}