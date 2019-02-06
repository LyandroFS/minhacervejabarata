package br.com.academico.minhacervejabarata.beans;

public class Tipo {
    private int id;
    private String descricao;
    private double ml;
    private int qdtEmbalagem;

    public Tipo() {
    }

    public Tipo(String descricao, double ml, int qdtEmbalagem) {
        this.descricao = descricao;
        this.ml = ml;
        this.qdtEmbalagem = qdtEmbalagem;
    }

    public Tipo(int id, String descricao, double ml) {
        this.id = id;
        this.descricao = descricao;
        this.ml = ml;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getMl() {
        return ml;
    }

    public void setMl(double ml) {
        this.ml = ml;
    }

    public int getQdtEmbalagem() {
        return qdtEmbalagem;
    }

    public void setQdtEmbalagem(int qdtEmbalagem) {
        this.qdtEmbalagem = qdtEmbalagem;
    }
}
