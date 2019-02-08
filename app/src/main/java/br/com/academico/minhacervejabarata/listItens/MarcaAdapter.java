package br.com.academico.minhacervejabarata.listItens;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import br.com.academico.minhacervejabarata.AddMarcaActivity;
import br.com.academico.minhacervejabarata.R;
import br.com.academico.minhacervejabarata.beans.Marca;
import br.com.academico.minhacervejabarata.db.DatabaseHelper;

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

        holder.btnExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir esta marca?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Marca marca = marcas.get(position);
                                DatabaseHelper db = DatabaseHelper.getInstance(view.getContext());
                                if(db.removeMarca(marca.getId())){
                                    removerMarca(position);
                                    Snackbar.make(view, "Excluiu!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else{
                                    Snackbar.make(view, "Erro ao excluir!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
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


    public void removerMarca(int index){
        marcas.remove(index);
        notifyItemRemoved(index);
    }

}
