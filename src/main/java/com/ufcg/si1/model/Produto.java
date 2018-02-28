package com.ufcg.si1.model;

import java.math.BigDecimal;

import com.ufcg.si1.model.descontos.Descontos;
import com.ufcg.si1.model.situacao.Disponivel;
import com.ufcg.si1.model.situacao.Indisponivel;
import com.ufcg.si1.model.situacao.Situacao;

import exceptions.ObjetoInvalidoException;

public class Produto {

	private long id;

	private String nome;

	private BigDecimal preco;

	private boolean temDesconto;
	
	private String codigoBarra;

	private String fabricante;

	private String categoria;

	public Situacao situacao;
	
	public Disponivel disponivel;
	
	public Indisponivel indisponivel;
	
	// reconhecendo bad smells
	
	/*
	 * public int situacao; // usa variaveis estaticas abaixo
	 * situacoes do produto 
	 * public static final int DISPONIVEL = 1;
	 * public static final int INDISPONIVEL = 2;
	 */

	public Produto() {
		this.id = 0;
		this.preco = new BigDecimal(0);
	}

	public Produto(long id, String nome, String codigoBarra, String fabricante,
			String nomeCategoria) {
		this.id = id;
		this.nome = nome;
		this.preco = new BigDecimal(0);
		this.temDesconto = true;
		this.codigoBarra = codigoBarra;
		this.fabricante = fabricante;
		this.categoria = nomeCategoria;
		this.situacao = this.indisponivel;
	}

	public String getNome() {
		return nome;
	}

	public void mudaNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	// implementando a sexta user story
	public void precoComDesconto(Descontos desconto){
		if(temDesconto){
			BigDecimal total = preco.subtract((BigDecimal) desconto);
			this.setPreco(total);
		}
	}
	
	public long getId() {
		return id;
	}

	public void mudaId(long codigo) {
		this.id = codigo;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void mudaFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void mudaCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Situacao getSituacao() throws ObjetoInvalidoException {
		return this.situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
	//houve modificações neste metodo afim de retirar os bad smells
	// implementando a oitava user story
	public void mudaSituacao() throws ObjetoInvalidoException {
		if(disponivel.containsProduto()){
			this.setSituacao(situacao);
		}else{
			throw new ObjetoInvalidoException("Situacao Invalida");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fabricante == null) ? 0 : fabricante.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Produto other = (Produto) obj;
		if (fabricante == null) {
			if (other.fabricante != null)
				return false;
		} else if (!fabricante.equals(other.fabricante))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
