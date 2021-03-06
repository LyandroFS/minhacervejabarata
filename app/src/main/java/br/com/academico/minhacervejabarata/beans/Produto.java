package br.com.academico.minhacervejabarata.beans;

public class Produto {
    private int id;
    private int idSupermercado;
    private int idMarca;
    private int idTipo;
    private float valor;

    private Estabelecimento estabelecimento;
    private Marca marca;
    private Tipo tipo;

    public Produto(Estabelecimento estabelecimento, Marca marca, Tipo tipo) {
        this.estabelecimento = estabelecimento;
        this.marca = marca;
        this.tipo = tipo;
    }

    public Produto(Estabelecimento estabelecimento, Marca marca, Tipo tipo, float valor) {
        this.estabelecimento = estabelecimento;
        this.marca = marca;
        this.tipo = tipo;
        this.valor = valor;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Produto() {

    }

    public Produto(int idSupermercado, int idMarca, int idTipo, float valor) {
        this.idSupermercado = idSupermercado;
        this.idMarca = idMarca;
        this.idTipo = idTipo;
        this.valor = valor;
    }

    public Produto(int id, int idSupermercado, int idMarca, int idTipo, float valor) {
        this.id = id;
        this.idSupermercado = idSupermercado;
        this.idMarca = idMarca;
        this.idTipo = idTipo;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSupermercado() {
        return idSupermercado;
    }

    public void setIdSupermercado(int idSupermercado) {
        this.idSupermercado = idSupermercado;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
