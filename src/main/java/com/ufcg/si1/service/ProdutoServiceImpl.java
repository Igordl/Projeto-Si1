package com.ufcg.si1.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Lote;
import com.ufcg.si1.model.Produto;

@Service("produtoService")
public class ProdutoServiceImpl implements ProdutoService {

	private static final AtomicLong counter = new AtomicLong();

	private static List<Produto> produtos;
	
	private static List<Lote> lotes;

	static {
		produtos = populateDummyProdutos();
		lotes = new ArrayList<>();
	}

	private static List<Produto> populateDummyProdutos() {
		List<Produto> produtos = new ArrayList<Produto>();
		
		produtos.add(new Produto(counter.incrementAndGet(), "Leite Integral", "87654321-B", "Parmalat", "Mercearia"));
		produtos.add(new Produto(counter.incrementAndGet(), "Arroz Integral", "87654322-B", "Tio Joao", "Perecíveis"));
		produtos.add(new Produto(counter.incrementAndGet(), "Sabao em Po", "87654323-B", "OMO", "Limpeza"));
		produtos.add(new Produto(counter.incrementAndGet(), "Agua Sanitaria", "87654324-C", "Dragao", "limpesa"));
		produtos.add(new Produto(counter.incrementAndGet(), "Creme Dental", "87654325-C", "Colgate", "HIGIENE"));

		return produtos;
	}

	public List<Produto> findAllProdutos() {
		return produtos;
	}

	public void saveProduto(Produto produto) {
		produto.mudaId(counter.incrementAndGet());
		produtos.add(produto);
	}

	public void updateProduto(Produto produto) {
		int index = produtos.indexOf(produto);
		produtos.set(index, produto);
	}

	public void deleteProdutoById(long id) {

		for (Iterator<Produto> iterator = produtos.iterator(); iterator.hasNext();) {
			Produto p = iterator.next();
			if (p.getId() == id) {
				iterator.remove();
			}
		}
	}

	// este metodo nunca eh chamado, mas se precisar estah aqui
	public int size() {
		return produtos.size();
	}

	public Iterator<Produto> getIterator() {
		return produtos.iterator();
	}

	public void deleteAllUsers() {
		produtos.clear();
	}

	public Produto findById(long id) {
		for (Produto produto : produtos) {
			if (produto.getId() == id) {
				return produto;
			}
		}
		return null;
	}

	public boolean doesProdutoExist(Produto produto) {
		for (Produto p : produtos) {
			if (p.getCodigoBarra().equals(produto.getCodigoBarra())) {
				return true;
			}
		}
		return false;
	}
	
	public Lote saveLote(Lote lote) {
		lote.setId(counter.incrementAndGet());
		lotes.add(lote);

		return lote;
	}
}
