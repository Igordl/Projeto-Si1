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
public class ProductServiceImpl implements ProductService {

	private static final AtomicLong counter = new AtomicLong();

	private static List<Product> products;

	private static List<Lot> lots;

	private static List<Product> expiredProducts;

	static {
		products = populateDummyProducts();
		lots = new ArrayList<>();
		expiredProducts = new ArrayList<>();
	}

	private static List<Product> populateDummyProducts() {
		List<Product> produtos = new ArrayList<Product>();

		produtos.add(new Product(counter.incrementAndGet(), "Leite Integral", "87654321-B", "Parmalat", "Mercearia"));
		produtos.add(new Product(counter.incrementAndGet(), "Arroz Integral", "87654322-B", "Tio Joao", "Perecíveis"));
		produtos.add(new Product(counter.incrementAndGet(), "Sabao em Po", "87654323-B", "OMO", "Limpeza"));
		produtos.add(new Product(counter.incrementAndGet(), "Agua Sanitaria", "87654324-C", "Dragao", "limpeza"));
		produtos.add(new Product(counter.incrementAndGet(), "Creme Dental", "87654325-C", "Colgate", "HIGIENE"));

		return produtos;
	}

	public List<Product> findAllProducts() {
		return products;
	}

	public void saveProduct(Product produto) {
		produto.setId(counter.incrementAndGet());
		products.add(produto);
	}

	public void updateProduct(Product produto) {
		int index = products.indexOf(produto);
		products.set(index, produto);
	}

	public void deleteProductById(long id) {

		for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
			Product p = iterator.next();
			if (p.getId() == id) {
				iterator.remove();
			}
		}
	}
	// reconhecendo bad smells
	// este metodo nao estava sendo utilizado mas agora utiliza-se ele nas classes Disponiveis e Indisponiveis
	
	public int size() {
		return products.size();
	}

	public Iterator<Product> getIterator() {
		return products.iterator();
	}

	public void deleteAllUsers() {
		products.clear();
	}
	
	public Product findById(long id) {
		for (Product produto : products) {
			if (produto.getId() == id) {
				return produto;
			}
		}
		return null;
	}

	public boolean doesProductExist(Product produto) {
		for (Product p : products) {
			if (p.getBarcode().equals(produto.getBarcode())) {
				return true;
			}
		}
		return false;
	}

	public Lot saveLot(Lot lote) {
		lote.setId(counter.incrementAndGet());
		lots.add(lote);

		return lote;
	}
	
	// implementando a nona user story

	// comparando a data atual com a data de validade do produto para saber se ta
	// vencido
	public void checkValidity() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		for (Lot lote : lots) {
			Date data = new Date(format.parse(lote.getExpirationDate()).getTime());
			Date dataAtual = new Date(format.parse(getDateTime()).getTime());
			
			if (data.after(dataAtual)) {
				// se o produto ta vencido ele passa a ser indisponivel
				lote.getProduct().setSituation(lote.getProduct().unavailable);
				expiredProducts.add(lote.getProduct());
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
