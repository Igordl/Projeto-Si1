package com.ufcg.si1.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	private static List<Produto> vencidos;

	static {
		produtos = populateDummyProdutos();
		lotes = new ArrayList<>();
		vencidos = new ArrayList<>();
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
	
	// implementando a nona user story

	// comparando a data atual com a data de validade do produto para saber se ta
	// vencido
	public void verificaValidade() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		for (Lote lote : lotes) {
			Date data = new Date(format.parse(lote.getDataDeValidade()).getTime());
			Date dataAtual = new Date(format.parse(getDateTime()).getTime());
			
			if (data.after(dataAtual)) {
				// se o produto ta vencido ele passa a ser indisponivel
				lote.getProduto().setSituacao(lote.getProduto().indisponivel);
				vencidos.add(lote.getProduto());
			}
		}
		
	}

	// data atual
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
