package com.ufcg.si1.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ufcg.si1.model.DTO.LoteDTO;
import com.ufcg.si1.model.Lote;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.si1.model.Produto;
import com.ufcg.si1.service.LoteService;
import com.ufcg.si1.service.LoteServiceImpl;
import com.ufcg.si1.service.ProdutoService;
import com.ufcg.si1.service.ProdutoServiceImpl;
import com.ufcg.si1.util.CustomErrorType;

import exceptions.ObjetoInvalidoException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiController {

	ProdutoService produtoService = new ProdutoServiceImpl();
	LoteService loteService = new LoteServiceImpl();

	// -------------------Retrieve All
	// Products---------------------------------------------

	@RequestMapping(value = "/produto/", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listAllUsers() {
		List<Produto> produtos = produtoService.findAllProdutos();

		if (produtos.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	// -------------------Criar um
	// Produto-------------------------------------------

	@RequestMapping(value = "/produto/", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody Produto produto, UriComponentsBuilder ucBuilder) {

		boolean produtoExiste = false;

		for (Produto p : produtoService.findAllProdutos()) {
			if (p.getCodigoBarra().equals(produto.getCodigoBarra())) {
				produtoExiste = true;
			}
		}

		if (produtoExiste) {
			return new ResponseEntity(new CustomErrorType("O produto " + produto.getNome() + " do fabricante "
					+ produto.getFabricante() + " ja esta cadastrado!"), HttpStatus.CONFLICT);
		}

		try {
			produto.mudaSituacao(Produto.INDISPONIVEL);
		} catch (ObjetoInvalidoException e) {
			return new ResponseEntity(new CustomErrorType("Error: Produto" + produto.getNome() + " do fabricante "
					+ produto.getFabricante() + " alguma coisa errada aconteceu!"), HttpStatus.NOT_ACCEPTABLE);
		}

		produtoService.saveProduto(produto);

		// HttpHeaders headers = new HttpHeaders();
		// headers.setLocation(ucBuilder.path("/api/produto/{id}").buildAndExpand(produto.getId()).toUri());

		return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProduto(@PathVariable("id") long id) {

		Produto p = null;

		for (Produto produto : produtoService.findAllProdutos()) {
			if (produto.getId() == id) {
				p = produto;
			}
		}

		if (p == null) {
			return new ResponseEntity(new CustomErrorType("Produto with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Produto>(p, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduto(@PathVariable("id") long id, @RequestBody Produto produto) {

		Produto currentProduto = null;

		for (Produto p : produtoService.findAllProdutos()) {
			if (p.getId() == id) {
				currentProduto = p;
			}
		}

		if (currentProduto == null) {
			return new ResponseEntity(new CustomErrorType("Unable to upate. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentProduto.mudaNome(produto.getNome());
		currentProduto.setPreco(produto.getPreco());
		currentProduto.setCodigoBarra(produto.getCodigoBarra());
		currentProduto.mudaFabricante(produto.getFabricante());
		currentProduto.mudaCategoria(produto.getCategoria());

		// resolvi criar um servi�o na API s� para mudar a situa��o do produto
		// esse c�digo n�o precisa mais
		// try {
		// currentProduto.mudaSituacao(produto.pegaSituacao());
		// } catch (ObjetoInvalidoException e) {
		// return new ResponseEntity(new CustomErrorType("Unable to upate. Produto with
		// id " + id + " invalid."),
		// HttpStatus.NOT_FOUND);
		// }

		produtoService.updateProduto(currentProduto);
		return new ResponseEntity<Produto>(currentProduto, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {

		Produto user = null;

		for (Produto produto : produtoService.findAllProdutos()) {
			if (produto.getId() == id) {
				user = produto;
			}
		}

		if (user == null) {
			return new ResponseEntity(new CustomErrorType("Unable to delete. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		produtoService.deleteProdutoById(id);
		return new ResponseEntity<Produto>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/produto/{id}/lote", method = RequestMethod.POST)
	public ResponseEntity<?> criarLote(@PathVariable("id") long produtoId, @RequestBody LoteDTO loteDTO) {
		Produto product = produtoService.findById(produtoId);

		if (product == null) {
			return new ResponseEntity(
					new CustomErrorType("Unable to create lote. Produto with id " + produtoId + " not found."),
					HttpStatus.NOT_FOUND);
		}

		Lote lote = loteService.saveLote(new Lote(product, loteDTO.getNumeroDeItens(), loteDTO.getDataDeValidade()));

		try {
			if (product.getSituacao() == Produto.INDISPONIVEL) {
				if (loteDTO.getNumeroDeItens() > 0) {
					Produto produtoDisponivel = product;
					produtoDisponivel.situacao = Produto.DISPONIVEL;
					produtoService.updateProduto(produtoDisponivel);
				}
			}
		} catch (ObjetoInvalidoException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(lote, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/lote/", method = RequestMethod.GET)
	public ResponseEntity<List<Lote>> listAllLotess() {
		List<Lote> lotes = loteService.findAllLotes();

		if (lotes.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}
}
