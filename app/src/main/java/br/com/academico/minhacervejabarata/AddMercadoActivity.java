package br.com.academico.minhacervejabarata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.com.academico.minhacervejabarata.beans.Estabelecimento;
import br.com.academico.minhacervejabarata.dao.EstabelecimentoDAO;

public class AddMercadoActivity extends AppCompatActivity {

    private EstabelecimentoDAO estabelecimentoDAO;
    private EditText nomeText;
    private EditText enderecoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mercado);
        estabelecimentoDAO = new EstabelecimentoDAO(getApplicationContext());
        nomeText = (EditText) findViewById(R.id.nome);
        enderecoText = (EditText) findViewById(R.id.endereco);

    }

    public void salvarMercado(View view) {
        String nome = nomeText.getText().toString();
        String endereco = enderecoText.getText().toString();
        Estabelecimento estabelecimento = new Estabelecimento(nome,endereco);
        estabelecimentoDAO.createEstabelecimento(estabelecimento);

    }
}
