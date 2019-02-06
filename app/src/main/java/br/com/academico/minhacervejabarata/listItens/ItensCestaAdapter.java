package br.com.academico.minhacervejabarata.listItens;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.academico.minhacervejabarata.R;
import br.com.academico.minhacervejabarata.beans.ItensCesta;
import br.com.academico.minhacervejabarata.db.DatabaseHelper;

public class ItensCestaAdapter extends RecyclerView.Adapter<ItensCestaAdapter.MyViewHolder> {

    private List<ItensCesta> itensCestaList;
    private Context context;
    private Activity activity;
    private DatabaseHelper db;
    SwipeRefreshLayout swiper;

    public ItensCestaAdapter(Context context, Activity activity, List<ItensCesta> itensCestaList, DatabaseHelper db) {
        this.context = context;
        this.activity = activity;
        this.itensCestaList = itensCestaList;
        this.db = db;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        ImageButton itensCesta;

        public MyViewHolder(View view) {
            super(view);
            nome = (TextView)
                    view.findViewById(R.id.nome_cesta);
            itensCesta = (ImageButton)
                    view.findViewById(R.id.itens_cesta);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_iten_cesta, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ItensCesta itensCesta = itensCestaList.get(position);
        DecimalFormat df = new DecimalFormat("0.00");
        //holder.nome.setText(itensCesta.getProduto().getMarca().getNome()+", "+itensCesta.getProduto().getTipo().getDescricao()+" "+itensCesta.getProduto().getTipo().getMl()+itensCesta.getProduto().getValor()+" - "+itensCesta.getProduto().getEstabelecimento().getNome());
        holder.nome.setText(itensCesta.getProduto().getMarca().getNome()+", "+df.format(itensCesta.getProduto().getTipo().getMl())+"ml R$: "+df.format(itensCesta.getProduto().getValor())+" Emb. "+itensCesta.getProduto().getTipo().getQdtEmbalagem()+ " - "+itensCesta.getProduto().getEstabelecimento().getNome());
        holder.itensCesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item Removido", Toast.LENGTH_LONG).show();
                db.removeItensCesta(itensCesta.getId());
                removeItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itensCestaList.size();
    }

    public void updateData(List<ItensCesta> viewModels) {
        itensCestaList.clear();
        itensCestaList.addAll(viewModels);
        notifyItemRangeChanged(0,itensCestaList.size());
    }
    public void addItem(int position, ItensCesta itensCesta) {
        itensCestaList.add(position, itensCesta);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        itensCestaList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itensCestaList.size());
    }
}