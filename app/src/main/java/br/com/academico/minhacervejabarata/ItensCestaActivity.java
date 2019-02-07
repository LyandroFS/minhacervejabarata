package br.com.academico.minhacervejabarata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.academico.minhacervejabarata.beans.Cesta;
import br.com.academico.minhacervejabarata.beans.ItensCesta;
import br.com.academico.minhacervejabarata.beans.Produto;
import br.com.academico.minhacervejabarata.db.DatabaseHelper;
import br.com.academico.minhacervejabarata.listItens.ItensCestaAdapter;

public class ItensCestaActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RecyclerView mRecyclerView;
    private ItensCestaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> list;
    private Spinner spinnerProdutos;
    private  List<Produto> listProdutos;
    private TextView idCesta;
    private TextView nomeCesta;
    private List<ItensCesta> itensCestaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_cesta);
        db = DatabaseHelper.getInstance(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        list = new ArrayList<>();
        spinnerProdutos = (Spinner) findViewById(R.id.produtos);
        idCesta = (TextView) findViewById(R.id.idCesta);
        nomeCesta = (TextView) findViewById(R.id.nome_cesta);

        Bundle extrasBundle = getIntent().getExtras();

        if(!extrasBundle.isEmpty()){
            Cesta cesta = db.getCesta(extrasBundle.getInt("id"));
            idCesta.setText(String.valueOf(cesta.getId()));
            nomeCesta.setText(cesta.getNome());
            itensCestaList = db.getAllItensCestaById(extrasBundle.getInt("id"));
            mAdapter = new ItensCestaAdapter(getApplicationContext(),this,itensCestaList, db);
            mRecyclerView.setAdapter(mAdapter);
        }

        listProdutos = db.getAllProduto();


        DecimalFormat df = new DecimalFormat("0.00");

        for(Produto produto : listProdutos){
            list.add(produto.getMarca().getNome()+", "+produto.getTipo().getDescricao()+" "+df.format(produto.getTipo().getMl())+"ml R$: "+df.format(produto.getValor())+" - "+produto.getEstabelecimento().getNome());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProdutos.setAdapter(dataAdapter);
    }

    public void addMoreItenCesta(View view) {
        Produto produto = listProdutos.get(spinnerProdutos.getSelectedItemPosition());
        Cesta cesta = db.getCesta(Integer.parseInt(idCesta.getText().toString()));

        ItensCesta item = new ItensCesta(produto, cesta);
        db.createItensCesta(item);
        this.recreate();
        Toast.makeText(getApplicationContext(),"Novo produto adicionado a cesta!!!",Toast.LENGTH_LONG).show();
    }
}