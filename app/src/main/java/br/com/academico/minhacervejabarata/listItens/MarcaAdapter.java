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
import android.widget.Toast;

import java.util.List;

import br.com.academico.minhacervejabarata.AddMarcaActivity;
import br.com.academico.minhacervejabarata.MarcaActivity;
import br.com.academico.minhacervejabarata.R;
import br.com.academico.minhacervejabarata.beans.Marca;
import br.com.academico.minhacervejabarata.db.DatabaseDjangoREST;
import br.com.academico.minhacervejabarata.db.IDatabase;

public class MarcaAdapter extends RecyclerView.Adapter<MarcaHolder> {

    private List<Marca> marcas;
    private MarcaActivity marcaActivity;
    private int lastIndex;

    public MarcaAdapter(List<Marca> marcas, MarcaActivity marcaActivity) {
        this.marcaActivity = marcaActivity;
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
        lastIndex = position;

        holder.btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastIndex = position;
                Activity activity = getActivity(v);
                Intent intent = new Intent(activity, AddMarcaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("marca", marcas.get(position));
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
                                lastIndex = position;
                                IDatabase db = DatabaseDjangoREST.getInstance(view.getContext());
                                db.deleteMarca(marca.getId());
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

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
        notifyDataSetChanged();
        marcaActivity.disableProgressBar();
    }

    public void deleteSuccessful(Context context){
        removerMarca(lastIndex);
        Toast toast = Toast.makeText(context, "Excluído com sucesso! ",Toast.LENGTH_LONG);
        toast.show();
    }
}
