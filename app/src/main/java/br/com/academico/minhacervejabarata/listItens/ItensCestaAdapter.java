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

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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

    public ItensCestaAdapter(Context context, Activity activity, List<ItensCesta> itensCestaList, DatabaseHelper db) {
        this.context = context;
        this.activity = activity;
        this.itensCestaList = itensCestaList;
        this.db = db;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeSupermercado;
        TextView nomeMarca;
        TextView valorMl;
        TextView precoUnitario;
        TextView precosPacotes;
        ImageButton itensCesta;

        public MyViewHolder(View view) {
            super(view);
            nomeSupermercado = (TextView) view.findViewById(R.id.nome_supermercado);
            nomeMarca = (TextView) view.findViewById(R.id.nome_marca);
            valorMl = (TextView) view.findViewById(R.id.valor_ml);
            precoUnitario = (TextView) view.findViewById(R.id.preco_unitario);
            precosPacotes = (TextView) view.findViewById(R.id.precos_pacotes);
            itensCesta = (ImageButton) view.findViewById(R.id.itens_cesta);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_iten_cesta, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ItensCesta itensCesta = itensCestaList.get(position);
        DecimalFormat df = new DecimalFormat("0.00");
        DecimalFormat df2 = new DecimalFormat("000");

        BigDecimal valorProduto = new BigDecimal(itensCesta.getProduto().getValor());
        BigDecimal ml = new BigDecimal(itensCesta.getProduto().getTipo().getMl());
        //BigDecimal valorMl =  valorProduto.divide(ml,new MathContext(4, RoundingMode.HALF_EVEN));
        BigDecimal valorMl =  valorProduto.divide(ml,new MathContext(4, RoundingMode.HALF_EVEN)).setScale(3, RoundingMode.HALF_EVEN);

        holder.nomeSupermercado.setText("Estabelecimento: "+itensCesta.getProduto().getEstabelecimento().getNome());
        holder.nomeMarca.setText(itensCesta.getProduto().getMarca().getNome() +" "+itensCesta.getProduto().getTipo().getDescricao() +" "+df2.format(itensCesta.getProduto().getTipo().getMl())+"ml");

        holder.precoUnitario.setText("Preço Unitário: R$"+df.format(itensCesta.getProduto().getValor()));
        holder.valorMl.setText("Valor ml: R$ "+valorMl);

        String x6 =  df.format(itensCesta.getProduto().getValor() * 6);
        String x12 =  df.format(itensCesta.getProduto().getValor() * 12);
        String x24 =  df.format(itensCesta.getProduto().getValor() * 24);

        holder.precosPacotes.setText("Valores 1x: R$ "+df.format(itensCesta.getProduto().getValor())+" 6x: R$ "+x6+" 12x: R$ "+x12+" 24x: R$ "+x24);

        //holder.nome.setText(itensCesta.getProduto().getMarca().getNome()+", "+itensCesta.getProduto().getTipo().getDescricao()+" "+itensCesta.getProduto().getTipo().getMl()+itensCesta.getProduto().getValor()+" - "+itensCesta.getProduto().getEstabelecimento().getNome());
        //holder.nome.setText(itensCesta.getProduto().getMarca().getNome()+", "+df.format(itensCesta.getProduto().getTipo().getMl())+"ml R$: "+df.format(itensCesta.getProduto().getValor())+" Emb. "+itensCesta.getProduto().getTipo().getQdtEmbalagem()+ " - "+itensCesta.getProduto().getEstabelecimento().getNome());
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