package com.ufcg.si1.model.situacao;

import com.ufcg.si1.service.ProductServiceImpl;

public class Disponivel implements Situacao{
	
	private ProductServiceImpl produto;
	
	public boolean containsProduto(){
		if(produto.size() != 0){
			return true;
		}
		return false;
	}
}

