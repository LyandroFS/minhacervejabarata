package br.com.academico.minhacervejabarata;

import android.support.design.widget.Snackbar;
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
        nomeText = findViewById(R.id.nomeText);
        marcaDAO = new MarcaDAO(getApplicationContext());

    }

    public void addMarca(View view) {
        String nome = nomeText.getText().toString();
        Marca marca = new Marca(nome);
        if(marcaDAO.createMarca(marca))
            Snackbar.make(view, "Salvo com sucesso!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        else
            Snackbar.make(view, "Erro ao inserir item!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

    }
}
