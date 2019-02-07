package br.com.academico.minhacervejabarata;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.com.academico.minhacervejabarata.db.DatabaseHelper;
import br.com.academico.minhacervejabarata.listItens.MarcaAdapter;

public class MarcaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MarcaAdapter adapter;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca);
        db = DatabaseHelper.getInstance(this);
        configurarRecycler();
    }

    private void configurarRecycler() {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        adapter = new MarcaAdapter(db.getAllMarca());
        db.setMarcaAdapter(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    public void btnAddMarca(View view) {
        Intent intent = new Intent(this, AddMarcaActivity.class);
        startActivity(intent);
    }
}
