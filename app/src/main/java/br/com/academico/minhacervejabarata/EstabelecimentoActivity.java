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
import android.widget.Spinner;

import br.com.academico.minhacervejabarata.beans.Estabelecimento;
import br.com.academico.minhacervejabarata.db.DatabaseDjangoREST;
import br.com.academico.minhacervejabarata.db.DatabaseSqlite;
import br.com.academico.minhacervejabarata.db.IDatabase;
import br.com.academico.minhacervejabarata.listItens.EstabelecimentoAdapter;

public class EstabelecimentoActivity extends AppCompatActivity {

    private IDatabase db;
    private RecyclerView recyclerView;
    private EstabelecimentoAdapter adapter;
    private Estabelecimento estabelecimentoEditado = null;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estabelecimento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = DatabaseDjangoREST.getInstance(this);
        configurarRecycler();

        Intent intent = getIntent();
        if(intent.hasExtra("id")){
            showIncludeCadastro();
            estabelecimentoEditado = (Estabelecimento) db.getEstabelecimento((int)intent.getSerializableExtra("id"));
            position = (int) intent.getSerializableExtra("index");

            EditText txtNome = (EditText)findViewById(R.id.descricaoTxt);
            EditText txtEndereco = (EditText)findViewById(R.id.enderecoTxt);
            txtNome.setText(estabelecimentoEditado.getNome());
            txtEndereco.setText(estabelecimentoEditado.getEndereco());
        }

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
                EditText enderecoText = (EditText) findViewById(R.id.enderecoTxt);
                String nome = nomeText.getText().toString();
                String endereco = enderecoText.getText().toString();
                Estabelecimento estabelecimento = new Estabelecimento(nome,endereco);

                if(estabelecimentoEditado!=null)
                    estabelecimento.setId(estabelecimentoEditado.getId());
                if (db.insertOrUpdateEstabelecimento(estabelecimento)) {
                    if(estabelecimento.getId()<1) {
                        estabelecimento = db.getUltimoEstabelecimentoInserido();
                        adapter.adicionarEstabelecimento(estabelecimento);
                    }
                    else{
                        adapter.atualizarEstabelecimento(estabelecimento, position);
                    }
                    nomeText.setText("");
                    enderecoText.setText("");
                    Snackbar.make(view, "Salvo com sucesso!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else
                    Snackbar.make(view, "Erro ao inserir item!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                fecharTeclado();
                showEstabelecimentos();
            }
        });

        if(intent.hasExtra("add")){
            showIncludeCadastro();
            intent.removeExtra("add");
        }
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    private void configurarRecycler() {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        adapter = new EstabelecimentoAdapter(db.getAllEstabelecimentos());
        db.setEstabelecimentoAdapter(adapter);
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
