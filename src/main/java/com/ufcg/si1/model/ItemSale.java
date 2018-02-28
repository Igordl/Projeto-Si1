package com.ufcg.si1.model;

public class ItemSale {
	
	private int id;
	
	private Product product;

	private int amount;

	private double price;
	
	
	public ItemSale (Product product, int amount, double price) {
		this.product = product;
		this.amount = amount;
		this.price = price;
	}
	
	public double TotalItemSale() {
		return amount * price;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}
	
	

}
