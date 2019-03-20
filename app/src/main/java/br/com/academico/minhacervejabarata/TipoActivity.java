package br.com.academico.minhacervejabarata;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import br.com.academico.minhacervejabarata.beans.Tipo;
import br.com.academico.minhacervejabarata.db.DatabaseSqlite;
import br.com.academico.minhacervejabarata.listItens.TipoAdapter;

public class TipoActivity extends AppCompatActivity {

    DatabaseSqlite db;
    RecyclerView recyclerView;
    TipoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = DatabaseSqlite.getInstance(this);
        setTitle("Tipo");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIncludeCadastro();
            }
        });

        Button btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEstabelecimentos();
            }
        });

        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nomeText = (EditText) findViewById(R.id.descricaoTxt);
                EditText mlTxt = (EditText) findViewById(R.id.mlTxt);
                String descricao = nomeText.getText().toString();
                Double ml = Double.parseDouble(mlTxt.getText().toString());
                Tipo tipo = new Tipo(descricao,ml,1);

                boolean sucesso = db.insertOrUpdateTipo(tipo);
                if(sucesso) {
                    tipo = db.getUltimoTipoInserido();
                    adapter.adicionarTipo(tipo);
                    //limpa os campos
                    nomeText.setText("");
                    mlTxt.setText("");

                    Snackbar.make(view, "Salvou!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(view, "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                fecharTeclado();
                showEstabelecimentos();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        configurarRecycler();

        Intent intent = getIntent();
        if(intent.hasExtra("add")){
            showIncludeCadastro();
            intent.removeExtra("add");
        }
    }

    private void configurarRecycler() {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        adapter = new TipoAdapter(db.getAllTipo());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void fecharTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showIncludeCadastro(){
        findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
        findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
        findViewById(R.id.fab).setVisibility(View.INVISIBLE);
    }

    private void showEstabelecimentos(){
        findViewById(R.id.includemain).setVisibility(View.VISIBLE);
        findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
        findViewById(R.id.fab).setVisibility(View.VISIBLE);
    }

}
