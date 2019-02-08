package br.com.academico.minhacervejabarata.listItens;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import br.com.academico.minhacervejabarata.AddMarcaActivity;
import br.com.academico.minhacervejabarata.R;
import br.com.academico.minhacervejabarata.beans.Estabelecimento;
import br.com.academico.minhacervejabarata.beans.Marca;

public class EstabelecimentoAdapter extends RecyclerView.Adapter<EstabelecimentoHolder> {
    private final List<Estabelecimento> estabelecimentos;

    public EstabelecimentoAdapter(List<Estabelecimento> estabelecimentos){
        this.estabelecimentos = estabelecimentos;
    }

    @NonNull
    @Override
    public EstabelecimentoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EstabelecimentoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EstabelecimentoHolder holder, final int position) {
        holder.nome.setText(estabelecimentos.get(position).getNome());
        holder.btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("id", estabelecimentos.get(position).getId());
                intent.putExtra("index", position);
                activity.finish();
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return estabelecimentos != null ? estabelecimentos.size() : 0;
    }

    public void adicionarEstabelecimento(Estabelecimento estabelecimento){
        estabelecimentos.add(estabelecimento);
        notifyItemInserted(getItemCount());
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }


    public void removerEstabelecimento(int index){
        estabelecimentos.remove(index);
        notifyItemRemoved(index);
    }

    public void atualizarEstabelecimento(Estabelecimento estabelecimento, int index){
        estabelecimentos.set(index, estabelecimento);
        notifyItemChanged(index);
    }


    public void atualizarEstabelecimento(Estabelecimento estabelecimento){
        estabelecimentos.set(estabelecimentos.indexOf(estabelecimento), estabelecimento);
        notifyItemChanged(estabelecimentos.indexOf(estabelecimento));
    }
}
