package br.com.academico.minhacervejabarata.beans;

public class ItensCesta {
    private int id;
    private int idProduto;
    private int idCesta;

    private Produto produto;
    private Cesta cesta;

    public ItensCesta() {

    }

    public ItensCesta(Produto produto, Cesta cesta) {
        this.produto = produto;
        this.cesta = cesta;

    }



    public ItensCesta(int idProduto, Produto produto, Cesta cesta) {
        this.idProduto = idProduto;
        this.produto = produto;
        this.cesta = cesta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdCesta() {
        return idCesta;
    }

    public void setIdCesta(int idCesta) {
        this.idCesta = idCesta;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Cesta getCesta() {
        return cesta;
    }

    public void setCesta(Cesta cesta) {
        this.cesta = cesta;
    }
}
