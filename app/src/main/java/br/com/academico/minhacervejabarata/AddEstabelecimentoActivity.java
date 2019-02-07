package br.com.academico.minhacervejabarata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddEstabelecimentoActivity extends AppCompatActivity {

    //private EstabelecimentoDAO estabelecimentoDAO;
    private EditText nomeText;
    private EditText enderecoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_estabelecimento);
        nomeText = (EditText) findViewById(R.id.nomeTxt);
        enderecoText = (EditText) findViewById(R.id.enderecoTxt);

    }

    public void salvarMercado(View view) {


    }
}
