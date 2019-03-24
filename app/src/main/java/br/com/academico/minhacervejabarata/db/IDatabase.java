package br.com.academico.minhacervejabarata.db;

import java.util.List;

import br.com.academico.minhacervejabarata.beans.Cesta;
import br.com.academico.minhacervejabarata.beans.Estabelecimento;
import br.com.academico.minhacervejabarata.beans.ItensCesta;
import br.com.academico.minhacervejabarata.beans.Marca;
import br.com.academico.minhacervejabarata.beans.Produto;
import br.com.academico.minhacervejabarata.beans.Tipo;
import br.com.academico.minhacervejabarata.listItens.EstabelecimentoAdapter;
import br.com.academico.minhacervejabarata.listItens.MarcaAdapter;
import br.com.academico.minhacervejabarata.listItens.TipoAdapter;

public interface IDatabase {

    public MarcaAdapter getMarcaAdapter();
    public EstabelecimentoAdapter getEstabelecimentoAdapter();
    public void setMarcaAdapter(MarcaAdapter marcaAdapter);
    public void setEstabelecimentoAdapter(EstabelecimentoAdapter estabelecimentoAdapter);
    public void setTipoAdapter(TipoAdapter tipoAdapter);
    public Estabelecimento insertEstabelecimento(Estabelecimento estabelecimento);
    public boolean updateEstabelecimento(Estabelecimento estabelecimento, int index);
    public Estabelecimento getEstabelecimento(int id);
    public List<Estabelecimento> getAllEstabelecimentos();
    public Marca getMarca(int id);
    public boolean updateMarca(Marca marca,  int index);
    public Marca insertMarca(Marca marca);
    public List<Marca> getAllMarca();
    public Tipo getTipo(int id);
    public List<Tipo> getAllTipo();
    public Produto createProduto(Produto produto);
    public List<Produto> getAllProduto();
    public Produto getProduto(int id);
    public Cesta createCesta(Cesta cesta);
    public List<Cesta> getAllCesta();
    public Cesta getCesta(int id);
    public ItensCesta createItensCesta(ItensCesta itensCesta);
    public ItensCesta getItensCesta(int id);
    public List<ItensCesta> getAllItensCesta();
    public List<ItensCesta> getAllItensCestaById(int id);
    public boolean removeItensCesta(int id);
    public Marca getUltimaMarcaInserida();
    public boolean deleteMarca(int id);
    public Estabelecimento getUltimoEstabelecimentoInserido();
    public boolean deleteEstabelecimento(int id);
    public boolean insertTipo(Tipo tipo);
    public Tipo getUltimoTipoInserido();

    }
