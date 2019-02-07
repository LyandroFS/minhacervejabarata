package br.com.academico.minhacervejabarata.listItens;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import br.com.academico.minhacervejabarata.AddMarcaActivity;
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
    public void onBindViewHolder(MarcaHolder holder, final int position) {
        holder.nomeMarca.setText(marcas.get(position).getNome());

        holder.btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = new Intent(activity, AddMarcaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("marcaId", marcas.get(position).getId());
                intent.putExtra("index", position);
                //activity.finish();
                activity.startActivity(intent);
            }
        });
    }

    public void atualizarMarca(Marca marca, int index){
        marcas.set(index, marca);
        notifyItemChanged(index);
    }

    @Override
    public int getItemCount() {
        return marcas != null ? marcas.size() : 0;
    }

    public void adicionarMarca(Marca marca){
        marcas.add(marca);
        notifyItemInserted(getItemCount());
    }

    public void atualizarLista(){

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
}
