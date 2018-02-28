package com.ufcg.si1.service;

import java.util.Iterator;
import java.util.List;

import com.ufcg.si1.model.Product;

public interface ProdutoService {

	List<Product> findAllProdutos();

	void saveProduto(Product produto);

	Product findById(long id);

	void updateProduto(Product user);

	void deleteProdutoById(long id);

	int size();

	Iterator<Product> getIterator();

	boolean doesProdutoExist(Product produto);
}
