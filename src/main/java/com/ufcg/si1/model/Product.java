package com.ufcg.si1.model;

import java.math.BigDecimal;

import com.ufcg.si1.model.discounts.Discounts;
import com.ufcg.si1.model.situation.Available;
import com.ufcg.si1.model.situation.Situation;
import com.ufcg.si1.model.situation.Unavailable;

import exceptions.InvalidObjectException;

public class Product {

	private long id;

	private String name;

	private BigDecimal price;

	private boolean hasDiscount;
	
	private String barcode;

	private String manufacturer;

	private String category;

	public Situation situation;
	
	public Available available;
	
	public Unavailable unavailable;
	
	// reconhecendo bad smells
	
	/*
	 * public int situacao; // usa variaveis estaticas abaixo
	 * situacoes do produto 
	 * public static final int DISPONIVEL = 1;
	 * public static final int INDISPONIVEL = 2;
	 */

	public Product() {
		this.id = 0;
		this.price = new BigDecimal(0);
	}

	public Product(long id, String name, String barcode, String manufacturer, String category) {
		this.id = id;
		this.name = name;
		this.price = new BigDecimal(0);
		this.hasDiscount = true;
		this.barcode = barcode;
		this.manufacturer = manufacturer;
		this.category = category;
		this.situation = this.unavailable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPreco(BigDecimal price) {
		this.price = price;
	}
	
	// implementando a sexta user story
	public void discountPrice(Discounts discount){
		if(hasDiscount){
			BigDecimal total = price.subtract((BigDecimal) discount);
			this.setPreco(total);
		}
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Situation getSituation() throws InvalidObjectException {
		return this.situation;
	}

	public void setSituation(Situation situacion) {
		this.situation = situacion;
	}
	
	//houve modificações neste metodo afim de retirar os bad smells
	// implementando a oitava user story
	public void changeSituation() throws InvalidObjectException {
		if(available.containsProduct()){
			this.setSituation(situation);
		}else{
			throw new InvalidObjectException("Situacao Invalida");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
