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
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.academico.minhacervejabarata.beans.Cesta;
import br.com.academico.minhacervejabarata.beans.Estabelecimento;
import br.com.academico.minhacervejabarata.beans.ItensCesta;
import br.com.academico.minhacervejabarata.beans.Marca;
import br.com.academico.minhacervejabarata.beans.Produto;
import br.com.academico.minhacervejabarata.beans.Tipo;
import br.com.academico.minhacervejabarata.db.DatabaseHelper;
import br.com.academico.minhacervejabarata.listItens.CestaAdapter;
import br.com.academico.minhacervejabarata.listItens.ItensCestaAdapter;

public class CestaActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RecyclerView mRecyclerView;
    private ItensCestaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ItensCesta> itensCestaList;
    //private Spinner spinnerProdutos;
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
        db = DatabaseHelper.getInstance(getApplicationContext());

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
                        Produto produto = itensCesta.getProduto();
                        produto = db.createProduto(produto);

                        ItensCesta item = new ItensCesta(produto, cesta);
                        db.createItensCesta(item);

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

        loadSpinnerEstabeleciementos();
        loadSpinnerMarcas();
        loadSpinnerTipos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSpinnerEstabeleciementos();
        loadSpinnerMarcas();
        loadSpinnerTipos();
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
            /*Log.d("Tag", "Nome Cesta: " + nomeCesta.getText().toString());
            Log.d("Tag", "Valor Produto: " + Float.parseFloat(preco.getText().toString()));*/

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

        Cesta cesta = new Cesta(nomeCesta.getText().toString());
        cesta = db.createCesta(cesta);

        for(ItensCesta itensCesta: itensCestaList){
            ItensCesta item = new ItensCesta(itensCesta.getProduto(), cesta);
            db.createItensCesta(item);
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Cesta criada com sucesso!!!",Toast.LENGTH_LONG).show();
    }
}
