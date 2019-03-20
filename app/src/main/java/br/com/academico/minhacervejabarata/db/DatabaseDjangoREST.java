package br.com.academico.minhacervejabarata.db;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.academico.minhacervejabarata.beans.Cesta;
import br.com.academico.minhacervejabarata.beans.Estabelecimento;
import br.com.academico.minhacervejabarata.beans.ItensCesta;
import br.com.academico.minhacervejabarata.beans.Marca;
import br.com.academico.minhacervejabarata.beans.Produto;
import br.com.academico.minhacervejabarata.beans.Tipo;
import br.com.academico.minhacervejabarata.listItens.EstabelecimentoAdapter;
import br.com.academico.minhacervejabarata.listItens.MarcaAdapter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseDjangoREST implements IDatabase {

    //Singleton
    private static DatabaseDjangoREST instancia;
    private List<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();


    public static DatabaseDjangoREST getInstance(Context context){
        if(instancia == null)
            instancia = new DatabaseDjangoREST(context);
        return instancia;
    }

    public static DatabaseDjangoREST getInstance() {
        return instancia;
    }

    private Context context;
    private DatabaseDjangoREST(Context context) {
        this.context = context;
    }

    //para o crud
    private MarcaAdapter marcaAdapter;
    private EstabelecimentoAdapter estabelecimentoAdapter;

    @Override
    public MarcaAdapter getMarcaAdapter() {
        return marcaAdapter;
    }

    @Override
    public void setMarcaAdapter(MarcaAdapter marcaAdapter) {
        this.marcaAdapter = marcaAdapter;
    }

    @Override
    public EstabelecimentoAdapter getEstabelecimentoAdapter() {
        return estabelecimentoAdapter;
    }

    @Override
    public void setEstabelecimentoAdapter(EstabelecimentoAdapter estabelecimentoAdapter) {
        this.estabelecimentoAdapter = estabelecimentoAdapter;
    }

    @Override
    public Estabelecimento createEstabelecimento(Estabelecimento estabelecimento) {
        return null;
    }

    @Override
    public Estabelecimento getEstabelecimento(int id) {
        return null;
    }

    @Override
    public List<Estabelecimento> getAllEstabelecimentos() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                "http://caiovosilva.pythonanywhere.com/estabelecimentos",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type listType = new TypeToken<List<Estabelecimento>>() {}.getType();
                        estabelecimentos = new Gson().fromJson(String.valueOf(response), listType);
                        estabelecimentoAdapter.setEstabelecimentos(estabelecimentos);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, "ERRO:    "+error.toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
        );
//        JsonObjectRequest objectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                "http://caiovosilva.pythonanywhere.com/estabelecimentos", //http://caiovosilva.pythonanywhere.com/estabelecimentos/?format=json"
//                null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        Toast toast = Toast.makeText(context, response.toString(),Toast.LENGTH_LONG);
//                        toast.show();
//                        JsonParser parser = new JsonParser();
//                        JsonElement mJson =  parser.parse(response.toString());
//                        Gson gson = new Gson();
//                        Estabelecimento object = gson.fromJson(mJson, Estabelecimento.class);
//                        estabelecimentos.add(object);
//
//
////
////                        InputStream source = retrieveStream(url);
////                        Reader reader = new InputStreamReader(response);
////                        Gson gson = new Gson();
////                        Estabelecimento estabelecimento = gson.fromJson()// response, Estabelecimento.class);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast toast = Toast.makeText(context, error.toString(),Toast.LENGTH_LONG);
//                        toast.show();
//                    }
//                }
//        );


        requestQueue.add(arrayRequest);
        return estabelecimentos;
    }

    @Override
    public Marca getMarca(int id) {
        return null;
    }

    @Override
    public Marca createMarca(Marca marca) {
        return null;
    }

    @Override
    public List<Marca> getAllMarca() {
        return null;
    }

    @Override
    public Tipo createTipo(Tipo tipo) {
        return null;
    }

    @Override
    public Tipo getTipo(int id) {
        return null;
    }

    @Override
    public List<Tipo> getAllTipo() {
        return null;
    }

    @Override
    public Produto createProduto(Produto produto) {
        return null;
    }

    @Override
    public List<Produto> getAllProduto() {
        return null;
    }

    @Override
    public Produto getProduto(int id) {
        return null;
    }

    @Override
    public Cesta createCesta(Cesta cesta) {
        return null;
    }

    @Override
    public List<Cesta> getAllCesta() {
        return null;
    }

    @Override
    public Cesta getCesta(int id) {
        return null;
    }

    @Override
    public ItensCesta createItensCesta(ItensCesta itensCesta) {
        return null;
    }

    @Override
    public ItensCesta getItensCesta(int id) {
        return null;
    }

    @Override
    public List<ItensCesta> getAllItensCesta() {
        return null;
    }

    @Override
    public List<ItensCesta> getAllItensCestaById(int id) {
        return null;
    }

    @Override
    public boolean removeItensCesta(int id) {
        return false;
    }

    @Override
    public boolean insertOrUpdateMarca(Marca marca) {
        return false;
    }

    @Override
    public Marca getUltimaMarcaInserida() {
        return null;
    }

    @Override
    public boolean removeMarca(int id) {
        return false;
    }

    @Override
    public boolean insertOrUpdateEstabelecimento(Estabelecimento estabelecimento) {
        return false;
    }

    @Override
    public Estabelecimento getUltimoEstabelecimentoInserido() {
        return null;
    }

    @Override
    public boolean removeEstabelecimento(int id) {
        return false;
    }

    @Override
    public boolean insertOrUpdateTipo(Tipo tipo) {
        return false;
    }

    @Override
    public Tipo getUltimoTipoInserido() {
        return null;
    }
}
