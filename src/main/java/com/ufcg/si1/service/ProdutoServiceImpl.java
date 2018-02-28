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

import com.ufcg.si1.model.Lot;
import com.ufcg.si1.model.Product;

@Service("produtoService")
public class ProdutoServiceImpl implements ProdutoService {

	private static final AtomicLong counter = new AtomicLong();

	private static List<Product> produtos;

	private static List<Lot> lotes;

	private static List<Product> vencidos;

	static {
		produtos = populateDummyProdutos();
		lotes = new ArrayList<>();
		vencidos = new ArrayList<>();
	}

	private static List<Product> populateDummyProdutos() {
		List<Product> produtos = new ArrayList<Product>();

		produtos.add(new Product(counter.incrementAndGet(), "Leite Integral", "87654321-B", "Parmalat", "Mercearia"));
		produtos.add(new Product(counter.incrementAndGet(), "Arroz Integral", "87654322-B", "Tio Joao", "Perecíveis"));
		produtos.add(new Product(counter.incrementAndGet(), "Sabao em Po", "87654323-B", "OMO", "Limpeza"));
		produtos.add(new Product(counter.incrementAndGet(), "Agua Sanitaria", "87654324-C", "Dragao", "limpeza"));
		produtos.add(new Product(counter.incrementAndGet(), "Creme Dental", "87654325-C", "Colgate", "HIGIENE"));

		return produtos;
	}

	public List<Product> findAllProdutos() {
		return produtos;
	}

	public void saveProduto(Product produto) {
		produto.setId(counter.incrementAndGet());
		produtos.add(produto);
	}

	public void updateProduto(Product produto) {
		int index = produtos.indexOf(produto);
		produtos.set(index, produto);
	}

	public void deleteProdutoById(long id) {

		for (Iterator<Product> iterator = produtos.iterator(); iterator.hasNext();) {
			Product p = iterator.next();
			if (p.getId() == id) {
				iterator.remove();
			}
		}
	}
	// reconhecendo bad smells
	// este metodo nao estava sendo utilizado mas agora utiliza-se ele nas classes Disponiveis e Indisponiveis
	
	public int size() {
		return produtos.size();
	}

	public Iterator<Product> getIterator() {
		return produtos.iterator();
	}

	public void deleteAllUsers() {
		produtos.clear();
	}
	
	public Product findById(long id) {
		for (Product produto : produtos) {
			if (produto.getId() == id) {
				return produto;
			}
		}
		return null;
	}

	public boolean doesProdutoExist(Product produto) {
		for (Product p : produtos) {
			if (p.getBarcode().equals(produto.getBarcode())) {
				return true;
			}
		}
		return false;
	}

	public Lot saveLote(Lot lote) {
		lote.setId(counter.incrementAndGet());
		lotes.add(lote);

		return lote;
	}
	
	// implementando a nona user story

	// comparando a data atual com a data de validade do produto para saber se ta
	// vencido
	public void verificaValidade() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		for (Lot lote : lotes) {
			Date data = new Date(format.parse(lote.getExpirationDate()).getTime());
			Date dataAtual = new Date(format.parse(getDateTime()).getTime());
			
			if (data.after(dataAtual)) {
				// se o produto ta vencido ele passa a ser indisponivel
				lote.getProduct().setSituation(lote.getProduct().unavailable);
				vencidos.add(lote.getProduct());
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
