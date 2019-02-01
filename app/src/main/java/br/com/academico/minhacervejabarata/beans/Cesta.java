package br.com.academico.minhacervejabarata.beans;

import java.util.Date;
import java.util.List;

public class Cesta {
    private int id;
    private String nome;
    private String date;

    private List<ItensCesta> itensCesta;

    public Cesta() {

    }

    public Cesta(String nome, String date) {
        this.nome = nome;
        this.date = date;
    }

    public Cesta(String nome) {
        this.nome = nome;
    }

    public Cesta(int id, String nome, String date) {
        this.id = id;
        this.nome = nome;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ItensCesta> getItensCesta() {
        return itensCesta;
    }

    public void setItensCesta(List<ItensCesta> itensCesta) {
        this.itensCesta = itensCesta;
    }
}
