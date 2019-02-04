package br.com.academico.minhacervejabarata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.com.academico.minhacervejabarata.beans.Marca;
import br.com.academico.minhacervejabarata.dao.MarcaDAO;

public class AddMarcaActivity extends AppCompatActivity {

    private MarcaDAO marcaDAO;
    private EditText nomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marca);
        nomeText = (EditText) findViewById(R.id.nome);
        marcaDAO = new MarcaDAO(getApplicationContext());

    }

    public void addMarca(View view) {
        String nome = nomeText.getText().toString();
        Marca marca = new Marca(nome);
        marcaDAO.createMarca(marca);

    }
}
