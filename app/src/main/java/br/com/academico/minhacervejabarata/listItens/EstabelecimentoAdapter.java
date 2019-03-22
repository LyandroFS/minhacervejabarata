package br.com.academico.minhacervejabarata.listItens;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import br.com.academico.minhacervejabarata.EstabelecimentoActivity;
import br.com.academico.minhacervejabarata.R;
import br.com.academico.minhacervejabarata.beans.Estabelecimento;
import br.com.academico.minhacervejabarata.db.DatabaseDjangoREST;
import br.com.academico.minhacervejabarata.db.IDatabase;

public class EstabelecimentoAdapter extends RecyclerView.Adapter<EstabelecimentoHolder> {
    private List<Estabelecimento> estabelecimentos;
    private EstabelecimentoActivity estabelecimentoActivity;
    private int lastIndex;

    public void setEstabelecimentos(List<Estabelecimento> estabelecimentos) {
        this.estabelecimentos = estabelecimentos;
        notifyDataSetChanged();
        estabelecimentoActivity.disableProgressBar();
    }

    public EstabelecimentoAdapter(List<Estabelecimento> estabelecimentos, EstabelecimentoActivity estabelecimentoActivity){
        this.estabelecimentos = estabelecimentos;
        this.estabelecimentoActivity = estabelecimentoActivity;
    }

    @NonNull
    @Override
    public EstabelecimentoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EstabelecimentoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EstabelecimentoHolder holder, final int position) {
        lastIndex = position;
        holder.nome.setText(estabelecimentos.get(position).getNome());
        holder.btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("estabelecimento", estabelecimentos.get(position));
                intent.putExtra("index", position);
                activity.finish();
                activity.startActivity(intent);
            }
        });

        holder.btnExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este cliente?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Estabelecimento estabelecimento = estabelecimentos.get(position);
                                IDatabase db = DatabaseDjangoREST.getInstance(view.getContext());
                                db.deleteEstabelecimento(estabelecimento.getId());

                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
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
        estabelecimentoActivity.clearTexts();
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

    public void deleteSuccessful(Context context){
        removerEstabelecimento(lastIndex);
        Toast toast = Toast.makeText(context, "Excluido com sucesso! ",Toast.LENGTH_LONG);
        toast.show();
    }
}
