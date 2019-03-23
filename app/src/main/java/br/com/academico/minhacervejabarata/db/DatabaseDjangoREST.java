package br.com.academico.minhacervejabarata.db;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.academico.minhacervejabarata.R;
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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseDjangoREST implements IDatabase {

    //Singleton
    private static DatabaseDjangoREST instancia;
    private List<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
    private List<Marca> marcas = new ArrayList<Marca>();


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
    public boolean updateEstabelecimento(Estabelecimento estabelecimento, final int index) {

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nome",estabelecimento.getNome());
            jsonObject.put("endereco", estabelecimento.getEndereco());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://caiovosilva.pythonanywhere.com/estabelecimentos/"+estabelecimento.getId()+"/";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JsonParser parser = new JsonParser();
                        JsonElement mJson =  parser.parse(response.toString());
                        Gson gson = new Gson();
                        Estabelecimento object = gson.fromJson(mJson, Estabelecimento.class);

                        estabelecimentoAdapter.atualizarEstabelecimento(object, index);                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, "ERRO:    "+error.toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
        );

        requestQueue.add(objectRequest);

        return false;
    }

    @Override
    public Estabelecimento insertEstabelecimento(Estabelecimento estabelecimento) {

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nome",estabelecimento.getNome());
            jsonObject.put("endereco", estabelecimento.getEndereco());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://caiovosilva.pythonanywhere.com/estabelecimentos/";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JsonParser parser = new JsonParser();
                        JsonElement mJson =  parser.parse(response.toString());
                        Gson gson = new Gson();
                        Estabelecimento object = gson.fromJson(mJson, Estabelecimento.class);

                        estabelecimentoAdapter.adicionarEstabelecimento(object);
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

        requestQueue.add(objectRequest);

        return null;
    }

    @Override
    public Estabelecimento getEstabelecimento(int id) {

        String url = "http://caiovosilva.pythonanywhere.com/estabelecimentos/"+id+"/";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JsonParser parser = new JsonParser();
                        JsonElement mJson =  parser.parse(response.toString());
                        Gson gson = new Gson();
                        Estabelecimento object = gson.fromJson(mJson, Estabelecimento.class);

                        estabelecimentoAdapter.adicionarEstabelecimento(object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

    );

        requestQueue.add(objectRequest);
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
    public boolean deleteEstabelecimento(int id) {

        String url = "http://caiovosilva.pythonanywhere.com/estabelecimentos/"+id+"/";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        estabelecimentoAdapter.deleteSuccessful(context);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, "Erro: "+error.toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
        );

        requestQueue.add(stringRequest);
        return false;
    }


    @Override
    public Marca getMarca(int id) {
        return null;
    }

    @Override
    public Marca insertMarca(Marca marca) {

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nome",marca.getNome());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://caiovosilva.pythonanywhere.com/marcas/";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JsonParser parser = new JsonParser();
                        JsonElement mJson =  parser.parse(response.toString());
                        Gson gson = new Gson();
                        Marca object = gson.fromJson(mJson, Marca.class);

                        marcaAdapter.adicionarMarca(object);
                        Toast toast = Toast.makeText(context, "Salvo com sucesso!",Toast.LENGTH_LONG);
                        toast.show();
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

        requestQueue.add(objectRequest);

        return null;
    }

    @Override
    public List<Marca> getAllMarca() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                "http://caiovosilva.pythonanywhere.com/marcas",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type listType = new TypeToken<List<Marca>>() {}.getType();
                        marcas = new Gson().fromJson(String.valueOf(response), listType);
                        marcaAdapter.setMarcas(marcas);
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

        requestQueue.add(arrayRequest);
        return null;
    }

    @Override
    public boolean updateMarca(Marca marca, final int index) {

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nome",marca.getNome());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://caiovosilva.pythonanywhere.com/marcas/"+marca.getId()+"/";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JsonParser parser = new JsonParser();
                        JsonElement mJson =  parser.parse(response.toString());
                        Gson gson = new Gson();
                        Marca object = gson.fromJson(mJson, Marca.class);

                        marcaAdapter.atualizarMarca(object, index);                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, "ERRO:    "+error.toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
        );

        requestQueue.add(objectRequest);

        return false;
    }


    @Override
    public boolean deleteMarca(int id) {

        String url = "http://caiovosilva.pythonanywhere.com/marcas/"+id+"/";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        marcaAdapter.deleteSuccessful(context);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, "Erro: "+error.toString(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
        );

        requestQueue.add(stringRequest);
        return false;
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
    public Marca getUltimaMarcaInserida() {
        return null;
    }

    @Override
    public Estabelecimento getUltimoEstabelecimentoInserido() {
        return null;
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
