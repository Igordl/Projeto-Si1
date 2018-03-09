package com.ufcg.si1.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ufcg.si1.model.DTO.LoteDTO;
import com.ufcg.si1.model.situacao.Situacao;
import com.ufcg.si1.model.situacao.Disponivel;
import com.ufcg.si1.model.situacao.Indisponivel;
import com.ufcg.si1.model.Lot;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.si1.model.Product;
import com.ufcg.si1.model.Sale;
import com.ufcg.si1.service.LoteService;
import com.ufcg.si1.service.LoteServiceImpl;
import com.ufcg.si1.service.ProdutoService;
import com.ufcg.si1.service.ProdutoServiceImpl;
import com.ufcg.si1.service.SaleService;
import com.ufcg.si1.service.SaleServiceImpl;
import com.ufcg.si1.util.CustomErrorType;

import exceptions.ObjetoInvalidoException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiController {

	ProdutoService produtoService = new ProdutoServiceImpl();
	LoteService loteService = new LoteServiceImpl();
	SaleService saleService = new SaleServiceImpl();

	// ------------Retrieve All Products---------------

	@RequestMapping(value = "/produto/", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> listAllUsers() {
		List<Product> produtos = produtoService.findAllProdutos();

		if (produtos.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Product>>(produtos, HttpStatus.OK);
	}

	// ----------------- Criar um Produto ----------------

	@RequestMapping(value = "/produto/", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody Product produto, UriComponentsBuilder ucBuilder) {

	/*	boolean produtoExiste = false;

		for (Product p : produtoService.findAllProdutos()) {
			if (p.getBarcode().equals(produto.getBarcode())) {
				produtoExiste = true;
			}
		}

*/
		if (produtoExiste(produto)) {
			return new ResponseEntity(new CustomErrorType("O produto " + produto.getName() + " do fabricante "
					+ produto.getManufacturer() + " ja esta cadastrado!"), HttpStatus.CONFLICT);
		}

		try {
			produto.mudaSituacao();
		} catch (ObjetoInvalidoException e) {
			return new ResponseEntity(new CustomErrorType("Error: Produto" + produto.getName() + " do fabricante "
					+ produto.getManufacturer() + " alguma coisa errada aconteceu!"), HttpStatus.NOT_ACCEPTABLE);
		}

		produtoService.saveProduto(produto);

		// HttpHeaders headers = new HttpHeaders();
		// headers.setLocation(ucBuilder.path("/api/produto/{id}").buildAndExpand(produto.getId()).toUri());

		return new ResponseEntity<Product>(produto, HttpStatus.CREATED);
	}
	
	//usando o extract method para verificar se o produto existe
	private boolean produtoExiste(Product produto) {
		boolean produtoExiste = false;

		for (Product p : produtoService.findAllProdutos()) {
			if (p.getBarcode().equals(produto.getBarcode())) {
				produtoExiste = true;
			}
		}
		return produtoExiste;
	}
	

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProduto(@PathVariable("id") long id) {
		
		Product p = procuraPeloId(id);
		
		if (p == null) {
			return new ResponseEntity(new CustomErrorType("Produto with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(p, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduto(@PathVariable("id") long id, @RequestBody Product produto) {

		Product currentProduto = procuraPeloId(id);

		if (currentProduto == null) {
			return new ResponseEntity(new CustomErrorType("Unable to upate. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentProduto.setName(produto.getName());
		currentProduto.setPreco(produto.getPrice());
		currentProduto.setBarcode(produto.getBarcode());
		currentProduto.setManufacturer(produto.getManufacturer());
		currentProduto.setCategory(produto.getCategory());

		// resolvi criar um servi�o na API s� para mudar a situa��o do produto
		

		produtoService.updateProduto(currentProduto);
		return new ResponseEntity<Product>(currentProduto, HttpStatus.OK);
	}
	
	private Product procuraPeloId(long id) {
		Product currentProduto = null;

		for (Product p : produtoService.findAllProdutos()) {
			if (p.getId() == id) {
				currentProduto = p;
			}
		}
		return currentProduto;

	}
	
	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {

		Product user = procuraPeloId(id);

		if (user == null) {
			return new ResponseEntity(new CustomErrorType("Unable to delete. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		produtoService.deleteProdutoById(id);
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/produto/{id}/lote", method = RequestMethod.POST)
	public ResponseEntity<?> criarLote(@PathVariable("id") long produtoId, @RequestBody LoteDTO loteDTO) {
		Product product = produtoService.findById(produtoId);

		if (product == null) {
			return new ResponseEntity(
					new CustomErrorType("Unable to create lote. Produto with id " + produtoId + " not found."),
					HttpStatus.NOT_FOUND);
		}

		Lot lote = loteService.saveLote(new Lot(product, loteDTO.getNumeroDeItens(), loteDTO.getDataDeValidade()));

		try {
			if (product.getSituation() instanceof Indisponivel) {
				if (loteDTO.getNumeroDeItens() > 0) {
					Product produtoDisponivel = product;
					produtoDisponivel.situation = new Disponivel();
					produtoService.updateProduto(produtoDisponivel);
				}
			}
		} catch (ObjetoInvalidoException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(lote, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/lote/", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> listAllLotess() {
		List<Lot> lotes = loteService.findAllLotes();

		if (lotes.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Lot>>(lotes, HttpStatus.OK);
	}
	
	
	
	//---------------------- VENDAS ----------------------------------//
	
	
	@RequestMapping(value = "/sale/", method = RequestMethod.GET)
	public ResponseEntity<List<Sale>> listAllSales() {
		List<Sale> vendas = saleService.findAllSales();

		if (vendas.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Sale>>(vendas, HttpStatus.OK);
	}
}
