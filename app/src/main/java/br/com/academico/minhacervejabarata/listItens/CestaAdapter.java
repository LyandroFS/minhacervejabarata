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

import java.util.List;

import br.com.academico.minhacervejabarata.ItensCestaActivity;
import br.com.academico.minhacervejabarata.R;
import br.com.academico.minhacervejabarata.beans.Cesta;

public class CestaAdapter extends RecyclerView.Adapter<CestaAdapter.MyViewHolder> {

    private List<Cesta> cestaList;
    private Context context;
    private Activity activity;

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

    public CestaAdapter(Context context, Activity activity, List<Cesta> transmissaoListList) {
        this.context = context;
        this.activity = activity;
        this.cestaList = transmissaoListList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_iten_cesta, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Cesta cesta = cestaList.get(position);

        holder.nome.setText(cesta.getNome());
        holder.itensCesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "LISTAR ITENS", Toast.LENGTH_LONG).show();
                Intent i = new Intent(activity, ItensCestaActivity.class);
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cestaList.size();
    }

    /*public void updateData(ArrayList<ViewModel> viewModels) {
        cestaList.clear();
        cestaList.addAll(viewModels);
        notifyDataSetChanged();
    }*/
    public void addItem(int position, Cesta cesta) {
        cestaList.add(position, cesta);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        cestaList.remove(position);
        notifyItemRemoved(position);
    }

}