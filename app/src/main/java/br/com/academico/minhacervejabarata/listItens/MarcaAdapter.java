package br.com.academico.minhacervejabarata.listItens;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import br.com.academico.minhacervejabarata.R;
import br.com.academico.minhacervejabarata.beans.Marca;

public class MarcaAdapter extends RecyclerView.Adapter<MarcaHolder> {

    private final List<Marca> marcas;

    public MarcaAdapter(List<Marca> marcas) {
        this.marcas = marcas;
    }

    @Override
    public MarcaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MarcaHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(MarcaHolder holder, int position) {
        holder.nomeMarca.setText(marcas.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return marcas != null ? marcas.size() : 0;
    }
}
