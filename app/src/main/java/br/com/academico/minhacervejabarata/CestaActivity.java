package br.com.academico.minhacervejabarata;

import android.content.Intent;
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
import br.com.academico.minhacervejabarata.beans.ItensCesta;
import br.com.academico.minhacervejabarata.beans.Produto;
import br.com.academico.minhacervejabarata.db.DatabaseHelper;
import br.com.academico.minhacervejabarata.listItens.CestaAdapter;
import br.com.academico.minhacervejabarata.listItens.ItensCestaAdapter;

public class CestaActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ItensCesta> itensCestaList;
    private Spinner spinnerProdutos;
    private  List<String> list = new ArrayList<String>();
    private  List<Produto> listProdutos = new ArrayList<Produto>();
    private EditText nomeCesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);
        db = new DatabaseHelper(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        spinnerProdutos = (Spinner) findViewById(R.id.produtos);
        nomeCesta = (EditText) findViewById(R.id.nome_cesta);


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //cestaList = db.getAllCesta();
        itensCestaList = new ArrayList<>();

        mAdapter = new ItensCestaAdapter(getApplicationContext(),this,itensCestaList, db);
        mRecyclerView.setAdapter(mAdapter);

        listProdutos = db.getAllProduto();

        DecimalFormat df = new DecimalFormat("0.00");

        for(Produto produto : listProdutos){
            list.add(produto.getMarca().getNome()+", "+df.format(produto.getTipo().getMl())+"ml R$: "+df.format(produto.getValor())+" Emb. "+produto.getTipo().getQdtEmbalagem()+ " - "+produto.getEstabelecimento().getNome());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProdutos.setAdapter(dataAdapter);

        spinnerProdutos.setSelection(0);
        spinnerProdutos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();*/

                Cesta cesta = new Cesta(parent.getItemAtPosition(position).toString());

                //Log.d("Tag CountItensCesta", "Adicionar: "+listProdutos.get(position));

                int insertIndex = itensCestaList.size();

                ItensCesta itensCesta = new ItensCesta(listProdutos.get(position).getId(),listProdutos.get(position), cesta);


                itensCestaList.add(insertIndex, itensCesta);
                mAdapter.notifyItemInserted(insertIndex);

                mRecyclerView.scrollToPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

    }


    public void addItenCesta(View view) {

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
