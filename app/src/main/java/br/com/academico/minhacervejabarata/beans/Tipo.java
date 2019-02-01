package br.com.academico.minhacervejabarata.beans;

public class Tipo {
    private int id;
    private String descricao;
    private double ml;

    public Tipo() {
    }

    public Tipo(String descricao, double ml) {
        this.descricao = descricao;
        this.ml = ml;
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
}
