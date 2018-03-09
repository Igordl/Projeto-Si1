package com.ufcg.si1.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ufcg.si1.model.DTO.LotDTO;
import com.ufcg.si1.model.situation.Available;
import com.ufcg.si1.model.situation.Situation;
import com.ufcg.si1.model.situation.Unavailable;
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
import com.ufcg.si1.service.LotService;
import com.ufcg.si1.service.LotServiceImpl;
import com.ufcg.si1.service.ProductService;
import com.ufcg.si1.service.ProductServiceImpl;
import com.ufcg.si1.service.SaleService;
import com.ufcg.si1.service.SaleServiceImpl;
import com.ufcg.si1.util.CustomErrorType;

import exceptions.InvalidObjectException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiController {

	ProductService productService = new ProductServiceImpl();
	LotService lotService = new LotServiceImpl();
	SaleService saleService = new SaleServiceImpl();

	// ------------Retrieve All Products---------------

	@RequestMapping(value = "/produto/", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> listAllUsers() {
		List<Product> products = productService.findAllProducts();

		if (products.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	// ----------------- Criar um Produto ----------------

	@RequestMapping(value = "/produto/", method = RequestMethod.POST)
	public ResponseEntity<?> createProduct(@RequestBody Product product, UriComponentsBuilder ucBuilder) {

	/*	boolean produtoExiste = false;

		for (Product p : produtoService.findAllProdutos()) {
			if (p.getBarcode().equals(produto.getBarcode())) {
				produtoExiste = true;
			}
		}

*/
		if (productExists(product)) {
			return new ResponseEntity(new CustomErrorType("O produto " + product.getName() + " do fabricante "
					+ product.getManufacturer() + " ja esta cadastrado!"), HttpStatus.CONFLICT);
		}

		try {
			product.changeSituation();
		} catch (InvalidObjectException e) {
			return new ResponseEntity(new CustomErrorType("Error: Produto" + product.getName() + " do fabricante "
					+ product.getManufacturer() + " alguma coisa errada aconteceu!"), HttpStatus.NOT_ACCEPTABLE);
		}

		productService.saveProduct(product);

		// HttpHeaders headers = new HttpHeaders();
		// headers.setLocation(ucBuilder.path("/api/produto/{id}").buildAndExpand(produto.getId()).toUri());

		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}
	
	//usando o extract method para verificar se o produto existe
	private boolean productExists(Product product) {
		boolean productExists = false;

		for (Product p : productService.findAllProducts()) {
			if (p.getBarcode().equals(product.getBarcode())) {
				productExists = true;
			}
		}
		return productExists;
	}
	

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> checkProduct(@PathVariable("id") long id) {
		
		Product p = searchById(id);
		
		if (p == null) {
			return new ResponseEntity(new CustomErrorType("Produto with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(p, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {

		Product currentProduct = searchById(id);

		if (currentProduct == null) {
			return new ResponseEntity(new CustomErrorType("Unable to upate. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentProduct.setName(product.getName());
		currentProduct.setPrice(product.getPrice());
		currentProduct.setBarcode(product.getBarcode());
		currentProduct.setManufacturer(product.getManufacturer());
		currentProduct.setCategory(product.getCategory());

		// resolvi criar um servi�o na API s� para mudar a situa��o do produto
		

		productService.updateProduct(currentProduct);
		return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
	}
	
	private Product searchById(long id) {
		Product currentProduto = null;

		for (Product p : productService.findAllProducts()) {
			if (p.getId() == id) {
				currentProduto = p;
			}
		}
		return currentProduto;

	}
	
	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {

		Product user = searchById(id);

		if (user == null) {
			return new ResponseEntity(new CustomErrorType("Unable to delete. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		productService.deleteProductById(id);
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/produto/{id}/lote", method = RequestMethod.POST)
	public ResponseEntity<?> createLot(@PathVariable("id") long productId, @RequestBody LotDTO lotDTO) {
		Product product = productService.findById(productId);

		if (product == null) {
			return new ResponseEntity(
					new CustomErrorType("Unable to create lote. Produto with id " + productId + " not found."),
					HttpStatus.NOT_FOUND);
		}

		Lot lot = lotService.saveLot(new Lot(product, lotDTO.getAmountItems(), lotDTO.getExpirationDate()));

		try {
			if (product.getSituation() instanceof Unavailable) {
				if (lotDTO.getAmountItems() > 0) {
					Product productAvailable = product;
					productAvailable.situation = new Available();
					productService.updateProduct(productAvailable);
				}
			}
		} catch (InvalidObjectException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(lot, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/lote/", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> listAllLots() {
		List<Lot> lots = lotService.findAllLots();

		if (lots.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Lot>>(lots, HttpStatus.OK);
	}
	
	
	
	//---------------------- VENDAS ----------------------------------//
	
	
	@RequestMapping(value = "/sale/", method = RequestMethod.GET)
	public ResponseEntity<List<Sale>> listAllSales() {
		List<Sale> sales = saleService.findAllSales();

		if (sales.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Sale>>(sales, HttpStatus.OK);
	}
}
