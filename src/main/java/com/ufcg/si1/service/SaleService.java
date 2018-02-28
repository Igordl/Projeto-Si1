package com.ufcg.si1.service;

import java.util.List;

import com.ufcg.si1.model.Sale;

public interface SaleService {

	List<Sale> findAllSales();

	void saveSale(Sale sale);

	Sale findById(long id);

	void deleteSaleById(long id);
}
