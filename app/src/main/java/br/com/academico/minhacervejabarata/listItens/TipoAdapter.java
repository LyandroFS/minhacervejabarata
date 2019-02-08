package br.com.academico.minhacervejabarata.listItens;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;

import br.com.academico.minhacervejabarata.R;
import br.com.academico.minhacervejabarata.beans.Tipo;

public class TipoAdapter extends RecyclerView.Adapter<TipoHolder> {

    private final List<Tipo> tipos;

    public TipoAdapter(List<Tipo> tipos) {
        this.tipos = tipos;
    }

    @Override
    public TipoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TipoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(TipoHolder holder, int position) {
        holder.nome.setText(tipos.get(position).getDescricao());
    }

    @Override
    public int getItemCount() {
        return tipos != null ? tipos.size() : 0;
    }

    public void adicionarTipo(Tipo tipo){
        tipos.add(tipo);
        notifyItemInserted(getItemCount());
    }
}