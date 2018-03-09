package com.ufcg.si1.model;

import java.util.Date;
import java.util.List;

public class Sale {
	
	
	private long id;
	
	private Date data;
	
	private List<ItemSale> itemsSale;
	
	public Sale (List<ItemSale> itemsSale) {
		this.itemsSale = itemsSale;
	}
	
	public double totalSalePrice() {
		double total = 0;
		for (ItemSale itemSale : itemsSale) {
			total += itemSale.TotalItemSale();
		}
		return total;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<ItemSale> getItensVenda() {
		return itemsSale;
	}

	public void setItensVenda(List<ItemSale> itemsSale) {
		this.itemsSale = itemsSale;
	}
	
	
	
	

}
