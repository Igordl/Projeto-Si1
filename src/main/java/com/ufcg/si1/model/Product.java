package com.ufcg.si1.model;

import java.math.BigDecimal;

import com.ufcg.si1.model.descontos.Descontos;
import com.ufcg.si1.model.situacao.Disponivel;
import com.ufcg.si1.model.situacao.Indisponivel;
import com.ufcg.si1.model.situacao.Situacao;

import exceptions.ObjetoInvalidoException;

public class Product {

	private long id;

	private String name;

	private BigDecimal price;

	private boolean hasDiscount;
	
	private String barcode;

	private String manufacturer;

	private String category;

	public Situacao situation;
	
	public Disponivel available;
	
	public Indisponivel unavailable;
	
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

	public void setName(String nome) {
		this.name = nome;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPreco(BigDecimal preco) {
		this.price = preco;
	}
	
	// implementando a sexta user story
	public void discountPrice(Descontos desconto){
		if(hasDiscount){
			BigDecimal total = price.subtract((BigDecimal) desconto);
			this.setPreco(total);
		}
	}
	
	public long getId() {
		return id;
	}

	public void setId(long codigo) {
		this.id = codigo;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String fabricante) {
		this.manufacturer = fabricante;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String codigoBarra) {
		this.barcode = codigoBarra;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String categoria) {
		this.category = categoria;
	}

	public Situacao getSituation() throws ObjetoInvalidoException {
		return this.situation;
	}

	public void setSituation(Situacao situacao) {
		this.situation = situacao;
	}
	
	//houve modificações neste metodo afim de retirar os bad smells
	// implementando a oitava user story
	public void mudaSituacao() throws ObjetoInvalidoException {
		if(available.containsProduto()){
			this.setSituation(situation);
		}else{
			throw new ObjetoInvalidoException("Situacao Invalida");
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
