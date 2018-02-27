package com.ufcg.si1.model;
/*
 * Eu, como administrador, 
 * gostaria que um produto fosse marcado como indisponível
 *  quando a quantidade de itens dele for igual a zero.
 *   Dessa forma, o seu preço não pode ser mais exibido 
 *   para os clientes e ele deve ser adicionado na lista de produtos em falta.

 */

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

