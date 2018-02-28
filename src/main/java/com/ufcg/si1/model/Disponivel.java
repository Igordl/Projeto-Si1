package com.ufcg.si1.model;

import com.ufcg.si1.service.ProdutoServiceImpl;

public class Disponivel implements Situacao{
	
	private ProdutoServiceImpl produto;
	
	public boolean containsProduto(){
		if(produto.size() != 0){
			return true;
		}
		return false;
	}
}

