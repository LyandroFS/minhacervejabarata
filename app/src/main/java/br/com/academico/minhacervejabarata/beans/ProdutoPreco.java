package br.com.academico.minhacervejabarata.beans;

public class ProdutoPreco {
    private Cesta cesta;
    private float litros;
    private float valorLitro;
    private float valorTotal;

    public ProdutoPreco(Cesta cesta, float litros, float valorLitro) {
        this.cesta = cesta;
        this.litros = litros;
        this.valorLitro = valorLitro;
    }

    public ProdutoPreco(Cesta cesta, float litros, float valorLitro, float valorTotal) {
        this.cesta = cesta;
        this.litros = litros;
        this.valorLitro = valorLitro;
        this.valorTotal = valorTotal;
    }

    public Cesta getCesta() {
        return cesta;
    }

    public void setCesta(Cesta cesta) {
        this.cesta = cesta;
    }

    public float getLitros() {
        return litros;
    }

    public void setLitros(float litros) {
        this.litros = litros;
    }

    public float getValorLitro() {
        return valorLitro;
    }

    public void setValorLitro(float valorLitro) {
        this.valorLitro = valorLitro;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
}
