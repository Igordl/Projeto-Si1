package com.ufcg.si1.service;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Sale;

@Service("SaleService")
public class SaleServiceImpl implements SaleService {
	
	private static final AtomicLong counter = new AtomicLong();
	
	public List<Sale> vendas;

	@Override
	public List<Sale> findAllSales() {
		return vendas;
	}

	@Override
	public void saveSale(Sale sale) {
		sale.setId(counter.incrementAndGet());
		vendas.add(sale);
		
	}

	@Override
	public Sale findById(long id) {
		for (Sale venda : vendas) {
			if (venda.getId() == id) {
				return venda;
			}
		}
		return null;
	}

	@Override
	public void deleteSaleById(long id) {
		for (Iterator<Sale> iterator = vendas.iterator(); iterator.hasNext();) {
			Sale v = iterator.next();
			if (v.getId() == id) {
				iterator.remove();
			}
		}
		
	}

}
