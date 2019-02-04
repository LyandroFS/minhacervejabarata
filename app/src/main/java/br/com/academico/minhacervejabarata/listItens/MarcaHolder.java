package br.com.academico.minhacervejabarata.listItens;

    import android.support.v7.widget.RecyclerView;
    import android.view.View;
    import android.widget.ImageButton;
    import android.widget.TextView;

    import br.com.academico.minhacervejabarata.R;

public class MarcaHolder extends RecyclerView.ViewHolder {

    public TextView nomeMarca;
    public ImageButton btnEditar;
    public ImageButton btnExcluir;

    public MarcaHolder(View itemView) {
        super(itemView);
        nomeMarca = (TextView) itemView.findViewById(R.id.nomeMarca);
        btnEditar = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }
}