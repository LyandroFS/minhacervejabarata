package br.com.academico.minhacervejabarata;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Comparator;

import br.com.academico.minhacervejabarata.beans.ProdutoPreco;
import br.com.academico.minhacervejabarata.db.DatabaseSqlite;
import br.com.academico.minhacervejabarata.listItens.CestaAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseSqlite db;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        db = DatabaseSqlite.getInstance(getApplicationContext());




     /*  Estabelecimento estabelecimento = new Estabelecimento("Bompreço", "Brotas");
        estabelecimento = db.insertEstabelecimento(estabelecimento);


        Estabelecimento estabelecimento2 = new Estabelecimento("Extra", "Paralela");
        estabelecimento2 = db.insertEstabelecimento(estabelecimento2);

        Log.d("Tag CountSupermercado", "Tag Count: " + db.getAllEstabelecimentos().size());



          Marca marca1 = new Marca("Skol");
          marca1 = db.insertMarca(marca1);

          Marca marca2 = new Marca("Devassa");
            marca2 = db.insertMarca(marca2);

        Log.d("Tag CountMarca", "Tag Count: " + db.getAllMarca().size());


        Tipo tipo1 = new Tipo("Garrafa", 600, 12);
        tipo1 = db.createTipo(tipo1);

        Tipo tipo2 = new Tipo("Garrafa", 250, 12);
        tipo2 = db.createTipo(tipo2);

        Tipo tipo3 = new Tipo("Garrafa", 350, 15);
        tipo3 = db.createTipo(tipo2);

        Log.d("Tag CountTipo", "Tag Count: " + db.getAllTipo().size());

        Produto produto1 = new Produto(estabelecimento,marca1,tipo1, 5.20f);
        db.createProduto(produto1);

        Produto produto2 = new Produto(estabelecimento2,marca2,tipo2, 2.40f);
        db.createProduto(produto2);

        Produto produto3 = new Produto(estabelecimento,marca1,tipo3, 3.60f);
        db.createProduto(produto3);

        Log.d("Tag CountProduto", "Tag Count: " + db.getAllProduto().size());*/


//        for(Produto p : db.getAllProduto()){
//            System.out.println("Estabelecimento: "+p.getEstabelecimento().getNome());
//            System.out.println("Marca: "+p.getMarca().getNome());
//            System.out.println("Tipo: "+p.getTipo().getDescricao());
//            System.out.println("VALOR: "+p.getValor());
//            System.out.println("===========================================");
//
//        }
//
//
//
//        Cesta cesta = new Cesta("Cesta de Final de Semana");
//        cesta = db.createCesta(cesta);
//
//        Cesta cesta2 = new Cesta("Cesta de Aniversario");
//        cesta2 = db.createCesta(cesta2);
//
//        Log.d("Tag CountCesta", "Tag Count: " + db.getAllCesta().size());
//
//        for(Cesta c : db.getAllCesta()){
//            System.out.println("Nome: "+c.getNome());
//            System.out.println("DAta: "+c.getDate());
//            System.out.println("===========================================");
//        }
//
//        ItensCesta itensCesta = new ItensCesta(produto1, cesta);
//        db.createItensCesta(itensCesta);
//        ItensCesta itensCesta2 = new ItensCesta(produto2, cesta);
//        db.createItensCesta(itensCesta2);
//
//        ItensCesta itensCesta3 = new ItensCesta(produto1, cesta2);
//        db.createItensCesta(itensCesta3);




      /*  Cesta cesta = new Cesta("Cesta de Final de Semana");
        cesta = db.createCesta(cesta);

        Cesta cesta2 = new Cesta("Cesta de Aniversario");
        cesta2 = db.createCesta(cesta2);*/


        //Log.d("Tag CountItensCesta", "Tag Count: " + db.getAllCesta().size());

        /*Cesta ce = db.getCesta(2);
        System.out.println("Nome: "+ce.getNome());
        System.out.println("DAta: "+ce.getDate());*/

       /* for(ItensCesta ic : db.getAllItensCestaById(2)){
            System.out.println("NOME SUPERMERCADO: "+ic.getProduto().getEstabelecimento().getNome());
            System.out.println("MARCA: "+ic.getProduto().getMarca().getNome());
            System.out.println("===========================================");
        }*/


       /* for(ItensCesta ic : ce.getItensCesta()){
            System.out.println("NOME SUPERMERCADO: "+ic.getProduto().getEstabelecimento().getNome());
            System.out.println("MARCA: "+ic.getProduto().getMarca().getNome());
            System.out.println("===========================================");
        }*/






        /*for(ItensCesta ic : db.getAllItensCesta()){
            System.out.println("Nome: "+ic.getCesta().getNome());
            System.out.println("DAta: "+ic.getProduto().getEstabelecimento().getNome());
            System.out.println("===========================================");
        }*/


        //db.getAllItensCesta();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CestaActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        db = DatabaseSqlite.getInstance(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


     /*   DecimalFormat formatFloat = new DecimalFormat("0.00");
        DecimalFormat formatDecimal = new DecimalFormat("00");

        List<ProdutoPreco> produtoPrecoList =  new ArrayList<>();

        for (Cesta cesta : db.getAllCesta()) {
            //Cesta cesta = db.getCesta(4);
            float totalLitrosCesta = 0, totalPrecoCesta = 0,  precoUnitatio=0;

            List<ItensCesta> intens =  db.getAllItensCestaById(cesta.getId());
            int quantidade = intens.size();

            for (ItensCesta itensCesta : intens) {
                Produto produto = itensCesta.getProduto();

                float ml = Float.parseFloat(formatDecimal.format(produto.getTipo().getMl()).replace(",",".")) * quantidade;
                float litro = ml / 1000;
                float valor = Float.parseFloat(formatFloat.format(produto.getValor()).replace(",",".")) * quantidade;


                totalLitrosCesta += litro;
                totalPrecoCesta += valor;
                precoUnitatio = Float.parseFloat(formatFloat.format(produto.getValor()).replace(",","."));

                // Log.d("Tag CountItensCesta", "LITRO: " + litro);

               *//* Log.d("Tag CountItensCesta", "Tag produto valor: " + ml + " " + formatFloat.format(produto.getValor()));
               Log.d("Tag CountItensCesta", "LITRO: " + litro + " preço por litro: " + valorLitro);
                Log.d("Tag CountItensCesta", "Valor Total: " + totalPrecoCesta);*//*

            }

           *//* Log.d("Tag CountItensCesta", "TOTAL LITROS: " + totalLitrosCesta);
            Log.d("Tag CountItensCesta", "TOTAL PRECO: " + totalPrecoCesta);*//*

            produtoPrecoList.add(new ProdutoPreco(cesta, totalLitrosCesta, precoUnitatio, totalPrecoCesta));
        }

        Collections.sort(produtoPrecoList, new Comparator<ProdutoPreco>() {
            public int compare(ProdutoPreco p1, ProdutoPreco p2) {
                return Math.round(p1.getValorLitro() - p2.getValorLitro());
            }
        });

        Collections.sort(produtoPrecoList, new Comparator<ProdutoPreco>() {
            public int compare(ProdutoPreco p1, ProdutoPreco p2) {
                return Math.round(p1.getLitros() - p2.getLitros());
            }
        });


        mAdapter = new ProdutoPrecoAdapter(getApplicationContext(),this,produtoPrecoList);*/
        mAdapter = new CestaAdapter(getApplicationContext(),this,db.getAllCesta());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_estabelecimento) {
            Intent intent = new Intent(this, EstabelecimentoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_marca) {
            Intent intent = new Intent(this, MarcaActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_tipo) {
            Intent intent = new Intent(this, TipoActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class PrecoProdutoComparator implements Comparator<ProdutoPreco> {
        public int compare(ProdutoPreco chair1, ProdutoPreco chair2) {
            return Math.round(chair1.getValorLitro() - chair2.getValorLitro());
        }
    }
}
