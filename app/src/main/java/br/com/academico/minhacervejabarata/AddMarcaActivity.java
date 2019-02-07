package br.com.academico.minhacervejabarata;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import br.com.academico.minhacervejabarata.beans.Marca;
import br.com.academico.minhacervejabarata.db.DatabaseHelper;

public class AddMarcaActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private EditText nomeText;
    private Marca marca;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marca);
        nomeText = findViewById(R.id.nomeText);
        db = DatabaseHelper.getInstance(getApplicationContext());
        marca = new Marca();

        Intent intent = getIntent();
        if(intent.hasExtra("marcaId")) {
            int marcaId = (int) intent.getSerializableExtra("marcaId");
            position = (int) intent.getSerializableExtra("index");
            marca = db.getMarca(marcaId);
            nomeText.setText(marca.getNome());
        }
    }

    public void addMarca(View view) {
        String nome = nomeText.getText().toString();
        Handler handler = new Handler();
        marca.setNome(nome);
        if(db.insertOrUpdateMarca(marca)) {
            if(marca.getId()<1) {
                marca = db.getUltimaMarcaInserida();
                db.getMarcaAdapter().adicionarMarca(marca);
            }
            else{
                db.getMarcaAdapter().atualizarMarca(marca, position);
            }
            Snackbar.make(view, "Salvo com sucesso!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            fecharTeclado();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                }
            }, 1000);   //delay de 1 segundo
        }
        else
            Snackbar.make(view, "Erro ao salvar item!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();



    }

    private void fecharTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
