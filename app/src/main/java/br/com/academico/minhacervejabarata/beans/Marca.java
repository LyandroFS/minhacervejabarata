package br.com.academico.minhacervejabarata.beans;

public class Marca {
    private int id;
    private String nome;

    public Marca() {
        setId(0);
    }

    public Marca(String nome) {
        this.nome = nome;
    }

    public Marca(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
