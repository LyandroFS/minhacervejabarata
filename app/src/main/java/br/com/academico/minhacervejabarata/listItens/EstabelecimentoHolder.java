package br.com.academico.minhacervejabarata.listItens;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.academico.minhacervejabarata.R;

public class EstabelecimentoHolder extends RecyclerView.ViewHolder {
    public EstabelecimentoHolder(View itemView) {
        super(itemView);
        nome = (TextView) itemView.findViewById(R.id.nome);
        btnEditar = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }

    public TextView nome;
    public ImageButton btnEditar;
    public ImageButton btnExcluir;
}
