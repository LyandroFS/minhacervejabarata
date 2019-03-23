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
import br.com.academico.minhacervejabarata.db.DatabaseDjangoREST;
import br.com.academico.minhacervejabarata.db.IDatabase;

public class AddMarcaActivity extends AppCompatActivity {

    private IDatabase db;
    private EditText nomeText;
    private Marca marca;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marca);
        nomeText = findViewById(R.id.nomeText);
        db = DatabaseDjangoREST.getInstance(getApplicationContext());
        marca = new Marca();
        setTitle("Nova Marca");

        Intent intent = getIntent();
        if(intent.hasExtra("marca")) {
            marca = (Marca) getIntent().getSerializableExtra("marca");
            position = (int) intent.getSerializableExtra("index");
            nomeText.setText(marca.getNome());
        }
    }

    public void addMarca(View view) {
        String nome = nomeText.getText().toString();
        Handler handler = new Handler();
        marca.setNome(nome);

        if(marca.getId()<1)
            db.insertMarca(marca);
        else
            db.updateMarca(marca, position);

        fecharTeclado();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 1000);   //delay de 1 segundo
    }

    private void fecharTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
