package br.com.academico.minhacervejabarata.listItens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

import br.com.academico.minhacervejabarata.ItensCestaActivity;
import br.com.academico.minhacervejabarata.R;
import br.com.academico.minhacervejabarata.beans.Cesta;
import br.com.academico.minhacervejabarata.beans.Produto;
import br.com.academico.minhacervejabarata.beans.ProdutoPreco;

public class ProdutoPrecoAdapter extends RecyclerView.Adapter<ProdutoPrecoAdapter.MyViewHolder> {

    private List<ProdutoPreco> produtoPrecoList;
    private Context context;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        TextView quantiadeTotalLitros;
        TextView precoTotalCesta;
        ImageButton itensCesta;

        public MyViewHolder(View view) {
            super(view);
            nome = (TextView)
                    view.findViewById(R.id.nome_cesta);

            quantiadeTotalLitros = (TextView)
                    view.findViewById(R.id.quatidade_litros);

            precoTotalCesta = (TextView)
                    view.findViewById(R.id.preco_total);

            itensCesta = (ImageButton)
                    view.findViewById(R.id.itens_cesta);
        }
    }

    public ProdutoPrecoAdapter(Context context, Activity activity, List<ProdutoPreco> produtoPrecoList) {
        this.context = context;
        this.activity = activity;
        this.produtoPrecoList = produtoPrecoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_cesta, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProdutoPreco produtoPreco = produtoPrecoList.get(position);

        DecimalFormat formatFloat = new DecimalFormat("0.00");

        holder.nome.setText("Cesta: "+produtoPreco.getCesta().getNome());
        holder.quantiadeTotalLitros.setText("Quantidade Litros: "+String.valueOf(produtoPreco.getLitros()));
        holder.precoTotalCesta.setText("Valor Total: R$ "+String.valueOf(formatFloat.format(produtoPreco.getValorTotal())));
        holder.itensCesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Cesta cesta = produtoPrecoList.get(position).getCesta();

                //Toast.makeText(context, "LISTAR ITENS", Toast.LENGTH_LONG).show();
                Intent i = new Intent(activity, ItensCestaActivity.class);
                i.putExtra("id", cesta.getId());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtoPrecoList.size();
    }

    /*public void updateData(ArrayList<ViewModel> viewModels) {
        cestaList.clear();
        cestaList.addAll(viewModels);
        notifyDataSetChanged();
    }*/
    public void addItem(int position, ProdutoPreco produtoPreco) {
        produtoPrecoList.add(position, produtoPreco);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        produtoPrecoList.remove(position);
        notifyItemRemoved(position);
    }

}